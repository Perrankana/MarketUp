package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.repositories.CategoryRepository
import com.perrankana.marketup.stock.repositories.FormatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

interface GetNewProductDataUseCase {
    suspend operator fun invoke(): Result<Flow<Pair<List<String>, List<String>>>>
}

class GetNewProductDataUseCaseImpl(private val categoryRepository: CategoryRepository, private val formatRepository: FormatRepository): GetNewProductDataUseCase {
    override suspend fun invoke(): Result<Flow<Pair<List<String>, List<String>>>> = kotlin.runCatching {
        formatRepository.getFormats().combine(categoryRepository.getCategories()){ formats, categories ->
            categories to formats
        }
    }
}