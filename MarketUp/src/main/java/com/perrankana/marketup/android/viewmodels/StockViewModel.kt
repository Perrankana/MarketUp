package com.perrankana.marketup.android.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.stock.EditProduct
import com.perrankana.marketup.stock.Empty
import com.perrankana.marketup.stock.NewProduct
import com.perrankana.marketup.stock.ShowStock
import com.perrankana.marketup.stock.StockSceneData
import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.usecases.DeleteProductUseCase
import com.perrankana.marketup.stock.usecases.FilterProductsUseCase
import com.perrankana.marketup.stock.usecases.GetNewProductDataUseCase
import com.perrankana.marketup.stock.usecases.GetStockUseCase
import com.perrankana.marketup.stock.usecases.SaveNewCategoryUseCase
import com.perrankana.marketup.stock.usecases.SaveNewFormatUseCase
import com.perrankana.marketup.stock.usecases.SaveProductUseCase
import com.perrankana.marketup.stock.usecases.SearchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val saveNewCategoryUseCase: SaveNewCategoryUseCase,
    private val saveNewFormatUseCase: SaveNewFormatUseCase,
    private val getNewProductDataUseCase: GetNewProductDataUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val filterProductsUseCase: FilterProductsUseCase,
    private val searchStockUseCase: SearchProductsUseCase
) : ViewModel() {

    private val _stockSceneData: MutableLiveData<StockSceneData> = MutableLiveData(Empty)
    val stockSceneData: LiveData<StockSceneData>
        get() = _stockSceneData

    init {
        getStockData()
    }

    private fun getStockData() {
        viewModelScope.launch {
            getStockUseCase().fold(onSuccess = {
                it.collect {
                    if (it.first.isEmpty()) {
                        _stockSceneData.value = Empty
                    } else {
                        _stockSceneData.value = ShowStock(it.first, it.second, it.third)
                    }
                }
            }, onFailure = {
                _stockSceneData.value = Empty
            })
        }
    }

    fun onNewProduct() {
        viewModelScope.launch {
            getNewProductDataUseCase().fold(
                onSuccess = { result ->
                    result.collect {
                        _stockSceneData.value = NewProduct(
                            categories = it.first,
                            formats = it.second
                        )
                    }
                },
                onFailure = {
                    Log.e(TAG, "[onNewProduct] Failed = ${it.message}")
                }
            )
        }
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            saveProductUseCase(product).fold(
                onSuccess = {

                },
                onFailure = {
                    Log.e(TAG, "[saveProduct] Failed = ${it.message}")
                }
            )
        }
        getStockData()
    }

    fun saveNewCategory(newCategory: String) {
        viewModelScope.launch {
            saveNewCategoryUseCase(newCategory).fold(
                onSuccess = { result ->
                    result.collect{
                        _stockSceneData.value = (_stockSceneData.value as NewProduct).copy(categories = it)
                    }
                },
                onFailure = {}
            )
        }
    }

    fun saveNewFormat(newFormat: String) {
        viewModelScope.launch {
            saveNewFormatUseCase(newFormat).fold(
                onSuccess = {result ->
                    result.collect {
                        _stockSceneData.value =
                            (_stockSceneData.value as NewProduct).copy(formats = it)
                    }
                },
                onFailure = {}
            )
        }
    }

    fun onProductSelected(product: Product) {
        viewModelScope.launch {
            getNewProductDataUseCase().fold(
                onSuccess = { result ->
                    result.collect {
                        _stockSceneData.value = EditProduct(
                            product = product,
                            categories = it.first,
                            formats = it.second
                        )
                    }
                },
                onFailure = {}
            )
        }
    }

    fun deleteProduct(product: Product) {
        viewModelScope.launch {
            deleteProductUseCase(product).fold(
                onSuccess = {

                },
                onFailure = {
                    Log.e(TAG, "[deleteProduct] Failed = ${it.message}")
                }
            )
        }
        getStockData()
    }

    fun onFilterProducts(categories: List<String>, filters: List<String>, stock: Int?) {
        viewModelScope.launch {
            filterProductsUseCase(categories, filters, stock).fold(
                onSuccess = {
                    it.collect {
                        when (val stockData = _stockSceneData.value) {
                            is ShowStock -> _stockSceneData.value = stockData.copy(stock = it)
                            else -> Unit
                        }
                    }
                },
                onFailure = {
                    Log.e(TAG, "[onFilterProducts]", it)
                }
            )
        }
    }

    fun onSearch(searchQuery: String) {
        viewModelScope.launch {
            searchStockUseCase(searchQuery).fold(
                onSuccess = {
                    it.collect {
                        when (val stockData = _stockSceneData.value) {
                            is ShowStock -> _stockSceneData.value = stockData.copy(stock = it)
                            else -> Unit
                        }
                    }
                },
                onFailure = {

                }
            )
        }
    }

    companion object {
        const val TAG = "StockView"
    }
}