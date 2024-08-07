package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.repositories.FormatRepository
import kotlinx.coroutines.flow.Flow

interface SaveNewFormatUseCase {
    suspend operator fun invoke(format: String): Result<Flow<List<String>>>
}

class SaveNewFormatUseCaseImpl(private val repository: FormatRepository): SaveNewFormatUseCase {
    override suspend fun invoke(format: String): Result<Flow<List<String>>> = kotlin.runCatching {
        repository.saveFormat(format)
        repository.getFormats()
    }
}