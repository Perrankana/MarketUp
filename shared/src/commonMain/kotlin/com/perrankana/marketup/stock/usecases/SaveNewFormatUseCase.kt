package com.perrankana.marketup.stock.usecases

import com.perrankana.marketup.stock.repositories.FormatRepository

interface SaveNewFormatUseCase {
    suspend operator fun invoke(format: String): Result<List<String>>
}

class SaveNewFormatUseCaseImpl(private val repository: FormatRepository): SaveNewFormatUseCase {
    override suspend fun invoke(format: String): Result<List<String>> = kotlin.runCatching {
        repository.saveFormat(format)
    }
}