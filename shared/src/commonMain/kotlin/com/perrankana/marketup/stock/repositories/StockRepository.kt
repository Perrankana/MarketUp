package com.perrankana.marketup.stock.repositories

import com.perrankana.marketup.database.ProductDao
import com.perrankana.marketup.database.ProductEntity
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.serializer

interface StockRepository {
    suspend fun getStock(): Flow<List<Product>>

    suspend fun saveProduct(product: Product)
}

class StockRepositoryImpl(private val productDao: ProductDao): StockRepository {

    private var stock: MutableList<Product> = mutableListOf( testProduct, testProduct, testProduct )

    override suspend fun getStock(): Flow<List<Product>> = productDao.getAllAsFlow().map {
        it.map { entity ->
            entity.toData()
        }
    }

    override suspend fun saveProduct(product: Product) {
        productDao.insert(product.toNewEntity())
    }
}

val testProduct = Product(
    name="Akane y Ranma",
    image = "content://com.perrankana.marketup.android.fileprovider/temp_images/image_17225983151295947473856042023082.jpg",
    categories= listOf("anime", "fanart"),
    format="A5",
    cost=0.3f,
    price=5.0f,
    offers= listOf(Offer.NxMOffer(n = 2, price = 8.0f)),
    stock = 3
)

fun Product.toNewEntity(): ProductEntity = ProductEntity(
    name = name,
    image = image,
    categories = Json.encodeToString(serializersModule.serializer(),categories),
    format = format,
    cost = cost,
    price = price,
    offers = Json.encodeToString(serializersModule.serializer(),offers),
    stock = stock
)

fun ProductEntity.toData(): Product {
    return Product(
        id = id,
        name = name,
        image = image,
        categories = Json.decodeFromString<List<String>>(categories),
        format = format,
        cost = cost,
        price = price,
        offers = Json.decodeFromString<List<Offer>>(offers),
        stock = stock
    )
}
