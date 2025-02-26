package com.perrankana.marketup.sale.models

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product

data class TpvEvent(
    val id: Long = 0,
    val event: Event,
    val totalSold: Float,
    val soldItem: List<SoldItem>
)

data class SoldItem(
    val id: Long = 0,
    val tpvEventId: Long,
    val products: List<Long>,
    val offer: String,
    val price: Float
)

data class SoldItemToDisplay(
    val id: Long = 0,
    val products: List<Product>,
    val offer: String,
    val price: Float
)

data class EventEnded(
    val tpvEvent: Long,
    val totalSold: Float,
    val eventName: String,
    val soldItems: List<SoldItemToDisplay>
)