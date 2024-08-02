package com.perrankana.marketup.stock.repositories

import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product

interface StockRepository {
    fun getStock(): List<Product>

    fun saveProduct(product: Product)
}

object StockRepositoryImpl: StockRepository {
    private var stock: MutableList<Product> = mutableListOf( testProduct, testProduct, testProduct )

    override fun getStock(): List<Product> {
        return stock
    }

    override fun saveProduct(product: Product) {
        stock.add(product)
    }
}

val testProduct = Product(
    name="Akane y Ranma",
    image = "content://com.perrankana.marketup.android.fileprovider/temp_images/image_17225983151295947473856042023082.jpg",
    categories= listOf("anime", "fanart"),
    format="A5",
    cost=0.3f,
    price=5.0f,
    offers= listOf(Offer.NxMOffer(n = 2, price = 8.0f))
)