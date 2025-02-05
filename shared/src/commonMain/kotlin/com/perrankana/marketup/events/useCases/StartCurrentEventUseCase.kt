package com.perrankana.marketup.events.useCases

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.Status
import com.perrankana.marketup.events.repositories.CurrentEventRepository
import com.perrankana.marketup.sale.models.TpvEvent
import com.perrankana.marketup.sale.repositories.TpvEventRepository

interface StartCurrentEventUseCase {
    suspend operator fun invoke(event: Event): Result<TpvEvent>
}

class StartCurrentEventUseCaseImpl(
    private val repository: CurrentEventRepository,
    private val tpvEventRepository: TpvEventRepository
) : StartCurrentEventUseCase {
    override suspend fun invoke(event: Event): Result<TpvEvent> = kotlin.runCatching {
        val startedEvent = event.copy(status = Status.Started)
        repository.saveEvent(startedEvent)
        tpvEventRepository.saveTpvEvent(
            TpvEvent(
                event = startedEvent,
                totalSold = 0f,
                soldItem = emptyList()
            )
        )
        tpvEventRepository.getTpvEvent()
    }
}