package com.perrankana.marketup.android.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.sale.ApplyOfferStep
import com.perrankana.marketup.sale.CategoriesStep
import com.perrankana.marketup.sale.Error
import com.perrankana.marketup.sale.FormatStep
import com.perrankana.marketup.sale.Idle
import com.perrankana.marketup.sale.OfferStep
import com.perrankana.marketup.sale.ProductStep
import com.perrankana.marketup.sale.SoldStep
import com.perrankana.marketup.sale.TPVSceneData
import com.perrankana.marketup.sale.usecases.GetFormatsUseCase
import com.perrankana.marketup.sale.usecases.GetProductsByCategoryAndFormatUseCase
import com.perrankana.marketup.sale.usecases.GetTpvDataUseCase
import com.perrankana.marketup.sale.usecases.SellItemUseCase
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TPVViewModel @Inject constructor(
    private val getTpvEventData : GetTpvDataUseCase,
    private val getFormats: GetFormatsUseCase,
    private val getProductsByCategoryAndFormat: GetProductsByCategoryAndFormatUseCase,
    private val sellItem: SellItemUseCase
): ViewModel() {

    private val _tpvSceneData: MutableLiveData<TPVSceneData> = MutableLiveData(Idle())
    val tpvSceneData: LiveData<TPVSceneData>
        get() = _tpvSceneData

    init {
        getData()
    }

    private fun getData(){
        Log.d("TPV", "[getData]")
        viewModelScope.launch {
            getTpvEventData().fold(
                onSuccess = { result ->
                        Log.d("TPV", "[getData] $result")
                    result.collect {
                        Log.d("TPV", "[getData] $it")
                        _tpvSceneData.value = CategoriesStep(
                            totalSold = it.first.totalSold,
                            categories = it.second
                        )
                    }
                },
                onFailure = {
                    Log.e("TPV", "[getData] ${it.message}")
                }
            )
        }
    }

    fun onCategorySelected(category: String) {
        viewModelScope.launch {
            getFormats().fold(
                onSuccess = { result ->
                    result.collect{
                        Log.d("TPV", "[onCategorySelected] $it")
                        _tpvSceneData.value = FormatStep(
                            totalSold = _tpvSceneData.value?.totalSold ?: 0f,
                            formats = it,
                            selectedCat = category)

                    }
                },
                onFailure = {
                    Log.e("TPV", "[onCategorySelected] ${it.message}")
                }
            )
        }

    }

    fun onFormatSelected(format: String) {
        viewModelScope.launch {
            when(val tpvSceneData = _tpvSceneData.value){
                is FormatStep -> {
                    getProductsByCategoryAndFormat(tpvSceneData.selectedCat, format).fold(
                        onSuccess = { result ->
                            result.collect{
                                Log.d("TPV", "[onFormatSelected] $it")
                                when(val currentTpvSceneData = _tpvSceneData.value){
                                    is FormatStep -> _tpvSceneData.value = ProductStep(
                                        totalSold = currentTpvSceneData.totalSold,
                                        selectedFormat = format,
                                        selectedCat = currentTpvSceneData.selectedCat,
                                        products = it)
                                    else -> Unit
                                }

                            }
                        },
                        onFailure = {
                            Log.e("TPV", "[onFormatSelected] ${it.message}")
                        }
                    )
                }
                else -> Unit
            }
        }
    }

    fun onProductSelected(product: Product) {
        when(val tpvSceneData = _tpvSceneData.value){
            is ProductStep -> {
                if (product.offers.isNotEmpty()) {
                    _tpvSceneData.value = OfferStep(
                        totalSold = tpvSceneData.totalSold,
                        product = product,
                        selectedFormat = tpvSceneData.selectedFormat,
                        selectedCat = tpvSceneData.selectedCat
                    )
                } else {
                    sellProduct(
                        products = listOf(product),
                        offer = Offer.None
                    )
                }
            }
            is ApplyOfferStep -> {
                when(val offer = tpvSceneData.offer){
                    is Offer.NxMOffer -> {
                        val products = tpvSceneData.selectedProducts.toMutableList()
                        products.add(product)
                        if (offer.n == products.size){
                           sellProduct(products, offer)
                        }
                    }
                    else -> Unit
                }
            }
            else -> Unit
        }
    }

    fun onOfferSelected(offer: Offer) {
        when(val tpvSceneData = _tpvSceneData.value){
            is OfferStep -> {
                when(offer){
                    is Offer.NxMOffer -> {
                        viewModelScope.launch {
                            getProductsByCategoryAndFormat(
                                category = null,
                                format = tpvSceneData.selectedFormat
                            ).fold(
                                onSuccess = { result ->
                                    result.collect{
                                        when(_tpvSceneData.value){
                                            is OfferStep -> _tpvSceneData.value = ApplyOfferStep(
                                                totalSold = tpvSceneData.totalSold,
                                                offer = offer,
                                                products = it,
                                                selectedFormat = tpvSceneData.selectedFormat,
                                                selectedProducts = listOf(tpvSceneData.product),
                                            )
                                            else ->Unit
                                        }
                                    }
                                },
                                onFailure = {

                                }
                            )
                        }
                    }// Choose new product
                    else -> sellProduct(
                        products = listOf(tpvSceneData.product),
                        offer = offer
                    )
                }
            }
            else -> Unit
        }
    }

    private fun sellProduct(products: List<Product>, offer: Offer) {
        viewModelScope.launch {
            sellItem(products, offer).fold(
                onSuccess = {
                    Log.d("TPV", "[sellItem] SUCCESS $it")
                    when(val tpvSceneData = _tpvSceneData.value){
                        is ApplyOfferStep -> _tpvSceneData.value = SoldStep(
                            totalSold = it.totalSold,
                            productsSold = products,
                            selectedFormat = tpvSceneData.selectedFormat
                        )
                        is OfferStep -> _tpvSceneData.value = SoldStep(
                            totalSold = it.totalSold,
                            productsSold = products,
                            selectedFormat = tpvSceneData.selectedFormat
                        )
                        is ProductStep -> _tpvSceneData.value = SoldStep(
                            totalSold = it.totalSold,
                            productsSold = products,
                            selectedFormat = tpvSceneData.selectedFormat
                        )
                        else -> Unit
                    }
                },
                onFailure = {
                    Log.e("TPV", "[sellItem] $it")
                    _tpvSceneData.value = Error(_tpvSceneData.value?.totalSold ?: 0f, it)
                }
            )
        }
    }

    fun onContinueShopping() {
        getData()
    }
}