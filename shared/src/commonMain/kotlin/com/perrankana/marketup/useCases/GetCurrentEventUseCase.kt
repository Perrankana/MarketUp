package com.perrankana.marketup.useCases

import com.perrankana.marketup.models.Event
import com.perrankana.marketup.repositories.CurrentEventRepository

interface GetCurrentEventUseCase {
    suspend operator fun invoke(): Result<Event>
}

class GetCurrentEventUseCaseImpl(val repository: CurrentEventRepository): GetCurrentEventUseCase{
    override suspend fun invoke(): Result<Event> = kotlin.runCatching {
        repository.getCurrentEvent()
    }
}