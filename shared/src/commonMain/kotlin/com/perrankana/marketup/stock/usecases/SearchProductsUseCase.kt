package com.perrankana.marketup.stock.usecases


import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface SearchProductsUseCase {
    suspend operator fun invoke(
        query: String,
    ): Result<Flow<List<Product>>>
}

class SearchProductsUseCaseImpl(private val repository: StockRepository) : SearchProductsUseCase {
    override suspend fun invoke(
        query: String,
    ): Result<Flow<List<Product>>> = kotlin.runCatching {
        repository.getStock().map {
            it.filter { product ->
                if (query.length >= 2) {
                    product.name.contains(query, ignoreCase = true)
                } else true
            }
        }
    }
}