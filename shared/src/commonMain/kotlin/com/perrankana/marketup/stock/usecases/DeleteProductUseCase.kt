package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository

interface DeleteProductUseCase {
    suspend operator fun invoke(product: Product): Result<Unit>
}

class DeleteProductUseCaseImpl(private val repository: StockRepository) : DeleteProductUseCase {
    override suspend fun invoke(product: Product): Result<Unit> = kotlin.runCatching {
        repository.deleteProduct(product)
    }
}