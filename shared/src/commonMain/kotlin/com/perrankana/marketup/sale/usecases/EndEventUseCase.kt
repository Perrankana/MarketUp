package com.perrankana.marketup.sale.usecases

import com.perrankana.marketup.events.models.Status
import com.perrankana.marketup.events.repositories.CurrentEventRepository
import com.perrankana.marketup.sale.models.EventEnded
import com.perrankana.marketup.sale.models.NoEventException
import com.perrankana.marketup.sale.models.SoldItemToDisplay
import com.perrankana.marketup.sale.repositories.TpvEventRepository
import com.perrankana.marketup.stock.repositories.StockRepository

interface EndEventUseCase {
    suspend operator fun invoke(): Result<EventEnded>
}

class EndEventUseCaseImpl(private val tpvEventRepository: TpvEventRepository, private val stockRepository: StockRepository, private val currentEventRepository: CurrentEventRepository) : EndEventUseCase{
    override suspend fun invoke(): Result<EventEnded> = kotlin.runCatching {
        val tpvEvent = tpvEventRepository.getTpvEvent()
        val event = tpvEvent.event.copy(status = Status.Ended)
        tpvEventRepository.saveTpvEvent(tpvEvent.copy(event = event))
        try {
            currentEventRepository.saveEvent(event)
        } catch (e: NoEventException) {
            // Do nothing
        }
        EventEnded(
            tpvEvent = tpvEvent.id,
            totalSold = tpvEvent.totalSold,
            eventName = tpvEvent.event.name,
            soldItems = tpvEvent.soldItem.map {
                SoldItemToDisplay(
                    id = it.id,
                    products = it.products.mapNotNull { productId ->
                        try {
                            stockRepository.getProduct(productId)
                        } catch (e: Exception) {
                            null
                        }
                    },
                    offer = it.offer,
                    price = it.price
                )
            }
        )
    }
}