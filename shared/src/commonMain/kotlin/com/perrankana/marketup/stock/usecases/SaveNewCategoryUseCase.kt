package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.repositories.CategoryRepository
import kotlinx.coroutines.flow.Flow

interface SaveNewCategoryUseCase {
    suspend operator fun invoke(category: String): Result<Flow<List<String>>>
}

class SaveNewCategoryUseCaseImpl(private val repository: CategoryRepository): SaveNewCategoryUseCase {
    override suspend fun invoke(category: String): Result<Flow<List<String>>> = kotlin.runCatching {
        repository.saveCategory(category)
        repository.getCategories()
    }
}