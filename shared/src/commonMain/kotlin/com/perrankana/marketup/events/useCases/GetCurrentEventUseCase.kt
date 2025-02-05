package com.perrankana.marketup.events.useCases

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.events.models.Status
import com.perrankana.marketup.events.repositories.CurrentEventRepository
import com.perrankana.marketup.sale.models.TpvEvent
import com.perrankana.marketup.sale.repositories.TpvEventRepository

interface GetCurrentEventUseCase {
    suspend operator fun invoke(): Result<Pair<Event, TpvEvent?>>
}

class GetCurrentEventUseCaseImpl(
    private val repository: CurrentEventRepository,
    private val tpvEventRepository: TpvEventRepository
): GetCurrentEventUseCase {
    override suspend fun invoke(): Result<Pair<Event, TpvEvent?>> = kotlin.runCatching {
        val event = repository.getCurrentEvent()
        val tpvEvent = if(event.status == Status.Started){
            tpvEventRepository.getTpvEvent()
        } else { null }
        event to tpvEvent
    }
}