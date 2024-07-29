package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.repositories.CategoryRepository

interface SaveNewCategoryUseCase {
    suspend operator fun invoke(category: String): Result<List<String>>
}

class SaveNewCategoryUseCaseImpl(private val repository: CategoryRepository): SaveNewCategoryUseCase {
    override suspend fun invoke(category: String): Result<List<String>> = kotlin.runCatching {
        repository.saveCategory(category)
    }
}