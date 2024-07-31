package com.perrankana.marketup.stock

import com.perrankana.marketup.stock.models.Product

sealed class StockSceneData

object Empty: StockSceneData()
data class NewProduct(val categories: List<String>, val formats: List<String>): StockSceneData()
data class ShowStock(val stock: List<Product>): StockSceneData()