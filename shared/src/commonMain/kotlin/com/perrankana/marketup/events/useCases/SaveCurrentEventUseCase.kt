package com.perrankana.marketup.events.useCases

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.repositories.CurrentEventRepository

interface SaveCurrentEventUseCase {
    suspend operator fun invoke(event: Event): Result<Event>
}

class SaveCurrentEventUseCaseImpl(private val repository: CurrentEventRepository) : SaveCurrentEventUseCase {
    override suspend fun invoke(event: Event): Result<Event> = kotlin.runCatching {
        repository.saveEvent(event)
    }
}