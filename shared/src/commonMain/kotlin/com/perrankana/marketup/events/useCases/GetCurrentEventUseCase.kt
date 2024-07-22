package com.perrankana.marketup.events.useCases

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.repositories.CurrentEventRepository

interface GetCurrentEventUseCase {
    suspend operator fun invoke(): Result<Event>
}

class GetCurrentEventUseCaseImpl(val repository: CurrentEventRepository): GetCurrentEventUseCase {
    override suspend fun invoke(): Result<Event> = kotlin.runCatching {
        repository.getCurrentEvent()
    }
}