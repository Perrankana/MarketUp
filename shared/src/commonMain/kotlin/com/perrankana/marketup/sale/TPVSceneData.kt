package com.perrankana.marketup.sale

import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product

sealed class TPVSceneData(open val totalSold: Float)

data class Idle(override val totalSold: Float = 0f): TPVSceneData(totalSold)
data class CategoriesStep(override val totalSold: Float, val categories: List<String>): TPVSceneData(totalSold)
data class FormatStep(override val totalSold: Float, val formats: List<String>, val selectedCat: String): TPVSceneData(totalSold)
data class ProductStep(override val totalSold: Float, val products: List<Product>, val selectedFormat: String, val selectedCat: String): TPVSceneData(totalSold)
data class OfferStep(override val totalSold: Float, val product: Product, val selectedFormat: String, val selectedCat: String): TPVSceneData(totalSold)
data class ApplyOfferStep(override val totalSold: Float, val offer: Offer, val products: List<Product>, val selectedProducts: List<Product>, val selectedFormat: String): TPVSceneData(totalSold)
data class SoldStep(override val totalSold: Float, val productsSold: List<Product>, val selectedFormat: String): TPVSceneData(totalSold)
data class Error(override val totalSold: Float, val exception: Throwable): TPVSceneData(totalSold)