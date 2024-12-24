package lian.sample.presentation

import config.JwtUtil
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import lian.sample.app.ProductUseCase
import lian.sample.presentation.dto.product.Product
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
  private val productUseCase: ProductUseCase

) {
  @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
  @PostMapping
  fun createProduct(@RequestBody @Valid productDto: Product) = productUseCase.createProduct(productDto)

  @Operation(summary = "상품 조회", description = "단일 상품을 조회 합니다.")
  @GetMapping("/{id}")
  fun getProduct(@PathVariable id: Long) = productUseCase.getProduct(id)

  @Operation(summary = "상품 리스트 조회", description = "전체 상품을 조회 합니다.")
  @GetMapping
  fun getAllProducts(
    @RequestParam("catalogId") catalogId: Long?,
    @RequestParam("productIds") productIds: List<Long>?
  ) = productUseCase.getAllProducts(catalogId, productIds)

  @Operation(summary = "상품 수정", description = "단일 상품을 수정합니다.")
  @PutMapping("/{id}")
  fun updateProduct(@PathVariable id: Long, @RequestBody productDto: Product) =
    productUseCase.updateProduct(id, productDto)

  @Operation(summary = "상품 수정", description = "단일 상품을 삭제합니다.")
  @DeleteMapping("/{id}")
  fun deleteProduct(@PathVariable id: Long) = productUseCase.deleteProduct(id)
}
