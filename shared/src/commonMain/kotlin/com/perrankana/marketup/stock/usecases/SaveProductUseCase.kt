package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository

interface SaveProductUseCase {
    suspend operator fun invoke(product: Product): Result<Unit>
}

class SaveProductUseCaseImpl(private val repository: StockRepository) : SaveProductUseCase {
    override suspend fun invoke(product: Product): Result<Unit> = kotlin.runCatching {
        repository.saveProduct(product)
    }
}