package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.repositories.CategoryRepository
import com.perrankana.marketup.stock.repositories.FormatRepository

interface GetNewProductDataUseCase {
    suspend operator fun invoke(): Result<Pair<List<String>, List<String>>>
}

class GetNewProductDataUseCaseImpl(private val categoryRepository: CategoryRepository, private val formatRepository: FormatRepository): GetNewProductDataUseCase {
    override suspend fun invoke(): Result<Pair<List<String>, List<String>>> = kotlin.runCatching {
        val categories = categoryRepository.getCategories()
        val formats = formatRepository.getFormats()
        categories to formats
    }
}