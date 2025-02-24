package com.perrankana.marketup.sale.usecases

import com.perrankana.marketup.sale.models.CanNotApplyOfferException
import com.perrankana.marketup.sale.models.NoStockException
import com.perrankana.marketup.sale.models.SoldItem
import com.perrankana.marketup.sale.models.TpvEvent
import com.perrankana.marketup.sale.repositories.TpvEventRepository
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.StockRepository

interface SellItemUseCase {
    suspend operator fun invoke(products: List<Product>, offer: Offer): Result<TpvEvent>
}

class SellItemUseCaseImpl(
    private val tpvEventRepository: TpvEventRepository, private val stockRepository: StockRepository
) : SellItemUseCase {
    override suspend fun invoke(products: List<Product>, offer: Offer): Result<TpvEvent> = kotlin.runCatching {
        val tpvEvent = tpvEventRepository.getTpvEvent()
        val calculatedPrice = calculatePrice(products, offer)
        updateStock(products)
        tpvEventRepository.saveSoldItem(
            SoldItem(
                tpvEventId = tpvEvent.id, products = products.map { it.id }, offer = offer.toString(), price = calculatedPrice
            )
        )
        tpvEventRepository.saveTpvEvent(tpvEvent.copy(totalSold = tpvEvent.totalSold + calculatedPrice))
    }

    private suspend fun updateStock(products: List<Product>) {
        val groupById = products.map {
            it.id
        }.groupBy {
            it
        }
        val updatedStock = mutableMapOf<Long, Int>()
        try {
            groupById.forEach {
                val product = stockRepository.getProduct(it.key)
                if (product.stock >= it.value.size) {
                    stockRepository.saveProduct(product.copy(stock = product.stock - it.value.size))
                    updatedStock[product.id] = it.value.size
                } else throw NoStockException()
            }
        } catch (e: NoStockException) {
            updatedStock.forEach {
                val product = stockRepository.getProduct(it.key)
                if (product.stock != 0) {
                    stockRepository.saveProduct(product.copy(stock = product.stock + it.value))
                } else {
                    return@forEach
                }
            }
            throw e
        }
    }

    private fun calculatePrice(products: List<Product>, offer: Offer): Float {
        if (canApplyOfferOnProducts(products, offer)) {
            return when (offer) {
                is Offer.DiscountOffer -> products.first().price - (products.first().price * offer.discount) / 100
                Offer.None -> products.first().price
                is Offer.NxMOffer -> offer.price
            }
        } else {
            throw CanNotApplyOfferException()
        }
    }

    private fun canApplyOfferOnProducts(products: List<Product>, offer: Offer): Boolean = when (offer) {
        Offer.None -> true
        else -> products.size == products.filter { it.offers.contains(offer) }.size
    }

}