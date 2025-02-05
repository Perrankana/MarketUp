package com.perrankana.marketup.sale.models

import com.perrankana.marketup.events.models.Event
import com.perrankana.marketup.stock.models.Product

data class Tpv(
    val products: List<Product>,
    val categories: List<String>,
    val formats: List<String>,
    val tpvEvent: TpvEvent
)

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