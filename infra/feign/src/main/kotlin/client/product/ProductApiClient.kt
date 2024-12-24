package client.product
import client.product.dto.ProductRes
import feign.Param
import feign.RequestLine
import org.springframework.cloud.openfeign.FeignClient

@FeignClient(
  value = "product",
)
interface ProductApiClient {
  @RequestLine("GET /products/{id}")
  fun getProduct(@Param("id") id: Long): List<ProductRes>

  @RequestLine("GET /products?catalogId={catalogId}&productIds={productIds}")
  fun getAllProducts(
    @Param("catalogId") catalogId: Long?,
    @Param("productIds") productIds: List<Long>?
  ): List<ProductRes>

}
