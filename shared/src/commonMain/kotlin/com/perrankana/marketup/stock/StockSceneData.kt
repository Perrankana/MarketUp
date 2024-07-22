package com.perrankana.marketup.stock

import com.perrankana.marketup.stock.models.Product

sealed class StockSceneData

object Empty: StockSceneData()
object NewProduct: StockSceneData()
data class ShowStock(val stock: List<Product>): StockSceneData()