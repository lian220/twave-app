package lian.sample.config

import com.zaxxer.hikari.HikariDataSource
import lian.sample.jpa.MasterSlaveRoutingDataSource
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackages = ["lian.sample.domain"],
  entityManagerFactoryRef = "productEntityManagerFactory",
  transactionManagerRef = "productTransactionManager"
)
class ProductDataSourceConfiguration(
  private val jpaProperties: JpaProperties
) {

  @Bean
  @ConfigurationProperties("datasource.product.master")
  fun productMasterDatasource(): DataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

  @Bean
  @ConfigurationProperties("datasource.product.slave")
  fun productSlaveDatasource(): DataSource = DataSourceBuilder.create().type(HikariDataSource::class.java).build()

  @Bean
  fun productRoutingDataSource() = MasterSlaveRoutingDataSource().apply {
    setTargetDataSources(hashMapOf<Any, Any>(
      "master" to productMasterDatasource(),
      "slave" to productSlaveDatasource()))
    setDefaultTargetDataSource(productMasterDatasource())
  }

  @Primary
  @Bean
  fun productLazyDataSource() = LazyConnectionDataSourceProxy(productRoutingDataSource())

  @Primary
  @Bean("productEntityManagerFactory")
  fun productEntityManagerFactory() = LocalContainerEntityManagerFactoryBean().apply {
    dataSource = productLazyDataSource()
    setPackagesToScan("lian.sample.domain")
    jpaVendorAdapter = HibernateJpaVendorAdapter().apply {
      setShowSql(jpaProperties.isShowSql)
      setGenerateDdl(jpaProperties.isGenerateDdl)
      setJpaPropertyMap(jpaProperties.properties)
    }
    persistenceUnitName = "productEntityManager"
    afterPropertiesSet()
  }

  @Primary
  @Bean("productTransactionManager")
  fun productTransactionManager() = JpaTransactionManager(productEntityManagerFactory().`object`!!)
}

