package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface FilterProductsUseCase {
    suspend operator fun invoke(
        categories: List<String>,
        formats: List<String>,
        stock: Int?
    ): Result<Flow<List<Product>>>
}

class FilterProductsUseCaseImpl(private val repository: StockRepository) : FilterProductsUseCase {
    override suspend fun invoke(
        categories: List<String>,
        formats: List<String>,
        stock: Int?
    ): Result<Flow<List<Product>>> = kotlin.runCatching {
        repository.getStock().map {
            it.filter { product ->
                product.containsAnyCategory(categories) && product.isFormatIn(formats) && product.inStock(stock)
            }
        }
    }
}

private fun Product.containsAnyCategory(categories: List<String>): Boolean {
    if( categories.isEmpty()) return true
    for (cat in categories) {
        if (this.categories.contains(cat)) {
            return true
        }
    }
    return false
}

private fun Product.isFormatIn(formats: List<String>): Boolean {
    if(formats.isEmpty()) return true
    return formats.contains(this.format)
}

private fun Product.inStock(stock: Int?): Boolean {
    return stock?.let {
        this.stock == stock
    } ?: true
}