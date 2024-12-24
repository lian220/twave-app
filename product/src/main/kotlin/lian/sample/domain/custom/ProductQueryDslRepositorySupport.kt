package lian.sample.domain.custom

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class ProductQueryDslRepositorySupport(domainClass: Class<*>) :
  QuerydslRepositorySupport(domainClass) {

  lateinit var queryFactory: JPAQueryFactory

  @PersistenceContext(unitName = "productEntityManagerFactory")
  override fun setEntityManager(entityManager: EntityManager) {
    super.setEntityManager(entityManager)
    queryFactory = JPAQueryFactory(entityManager)
  }
}