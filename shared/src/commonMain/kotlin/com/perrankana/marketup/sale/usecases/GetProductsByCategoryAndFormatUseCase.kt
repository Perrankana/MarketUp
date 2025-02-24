package com.perrankana.marketup.sale.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetProductsByCategoryAndFormatUseCase {
    suspend operator fun invoke(category: String?, format: String): Result<Flow<List<Product>>>
}

class GetProductsByCategoryAndFormatUseCaseImpl(
    private val productRepository: StockRepository
): GetProductsByCategoryAndFormatUseCase{
    override suspend fun invoke(category: String?, format: String): Result<Flow<List<Product>>> = kotlin.runCatching {
        productRepository.getStock().map {
            it.filter { product ->
                category?.let {
                    product.format == format && product.categories.contains(category)
                } ?: (product.format == format)
            }.filter { product ->
                product.stock > 0
            }
        }
    }
}