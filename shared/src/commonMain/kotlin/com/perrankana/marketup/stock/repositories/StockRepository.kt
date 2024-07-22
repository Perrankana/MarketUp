package com.perrankana.marketup.stock.repositories

import com.perrankana.marketup.stock.models.Product

interface StockRepository {
    fun getStock(): List<Product>

    fun saveProduct(product: Product)
}

object StockRepositoryImpl: StockRepository {
    private var stock: MutableList<Product> = mutableListOf()
    override fun getStock(): List<Product> {
        return stock
    }

    override fun saveProduct(product: Product) {
        stock.add(product)
    }


}