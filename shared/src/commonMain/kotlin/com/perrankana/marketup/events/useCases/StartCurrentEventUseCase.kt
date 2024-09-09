package com.perrankana.marketup.events.useCases

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.Status
import com.perrankana.marketup.events.repositories.CurrentEventRepository

interface StartCurrentEventUseCase {
    suspend operator fun invoke(event: Event): Result<Event>
}

class StartCurrentEventUseCaseImpl(private val repository: CurrentEventRepository) : StartCurrentEventUseCase {
    override suspend fun invoke(event: Event): Result<Event> = kotlin.runCatching {
        val startedEvent = event.copy(status = Status.Started)
        repository.saveEvent(startedEvent)
        startedEvent
    }
}