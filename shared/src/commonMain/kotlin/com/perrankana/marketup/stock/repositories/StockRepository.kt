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
    image="content://media/picker/0/com.google.android.apps.photos.cloudpicker/media/49c52711-9ff4-4417-a1e3-c3966ee161a4-1_all_15564",
    categories= listOf("anime", "fanart"),
    format="A5",
    cost=0.3f,
    price=5.0f,
    offers= listOf(Offer.NxMOffer(n = 2, price = 8.0f))
)