package com.perrankana.marketup.stock.models

import kotlinx.serialization.Serializable

data class Product(
    val id: Long = 0,
    val name: String,
    val image: String?,
    val categories: List<String>,
    val format: String,
    val cost: Float,
    val price: Float,
    val offers: List<Offer>,
    val stock: Int
)

@Serializable
sealed class Offer {
    @Serializable
    data class NxMOffer(val n: Int, val price: Float): Offer()
    @Serializable
    data class DiscountOffer(val discount: Int): Offer()
    data object None: Offer()
}