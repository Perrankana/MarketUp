package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository

interface GetStockUseCase {
    suspend operator fun invoke(): Result<List<Product>>
}

class GetStockUseCaseImpl(private val repository: StockRepository) : GetStockUseCase {
    override suspend fun invoke(): Result<List<Product>> = kotlin.runCatching {
        repository.getStock()
    }
}