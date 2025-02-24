package com.perrankana.marketup.sale.usecases


import com.perrankana.marketup.stock.repositories.FormatRepository
import kotlinx.coroutines.flow.Flow

interface GetFormatsUseCase {
    suspend operator fun invoke(): Result<Flow<List<String>>>
}

class GetFormatsUseCaseImpl(
    private val formatRepository: FormatRepository
) : GetFormatsUseCase {
    override suspend fun invoke(): Result<Flow<List<String>>> = kotlin.runCatching {
        formatRepository.getFormats()
    }
}