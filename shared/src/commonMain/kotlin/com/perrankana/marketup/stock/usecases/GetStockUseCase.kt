package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository
import kotlinx.coroutines.flow.Flow

interface GetStockUseCase {
    suspend operator fun invoke(): Result<Flow<List<Product>>>
}

class GetStockUseCaseImpl(private val repository: StockRepository) : GetStockUseCase {
    override suspend fun invoke(): Result<Flow<List<Product>>> = kotlin.runCatching {
        repository.getStock()
    }
}