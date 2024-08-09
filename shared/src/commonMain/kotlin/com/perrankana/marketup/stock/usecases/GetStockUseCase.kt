package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.CategoryRepository
import com.perrankana.marketup.stock.repositories.FormatRepository
import com.perrankana.marketup.stock.repositories.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetStockUseCase {
    suspend operator fun invoke(): Result<Flow<Triple<List<Product>, List<String>, List<String>>>>
}

class GetStockUseCaseImpl(
    private val repository: StockRepository,
    private val categoryRepository: CategoryRepository,
    private val formatRepository: FormatRepository
) : GetStockUseCase {
    override suspend fun invoke(): Result<Flow<Triple<List<Product>, List<String>, List<String>>>> = kotlin.runCatching {
        repository.getStock().combine(categoryRepository.getCategories()){ a, b ->
            a to b
        }.combine(formatRepository.getFormats()){ c, d ->
            Triple(c.first, c.second, d)
        }
    }
}