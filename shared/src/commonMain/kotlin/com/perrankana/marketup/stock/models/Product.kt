package com.perrankana.marketup.stock.models

data class Product(
    val name: String,
    val image: String?,
    val categories: List<String>,
    val format: String,
    val cost: Float,
    val price: Float,
    val offers: List<Offer>
)

sealed class Offer {
    data class NxMOffer(val n: Int, val price: Float): Offer()
    data class DiscountOffer(val discount: Int): Offer()
}