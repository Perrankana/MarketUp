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
        Log.d(TAG, "[init]")
        getStockData()
    }

    private fun getStockData() {
        viewModelScope.launch {
            getStockUseCase().fold(onSuccess = {
                it.collect {
                    Log.d(TAG, "[getStockData] ${it}")
                    when(_stockSceneData.value){
                        is ShowStock,
                        Empty -> if (it.first.isEmpty()) {
                            _stockSceneData.value = Empty
                        } else {
                            _stockSceneData.value = ShowStock(it.first, it.second, it.third)
                        }
                        else -> Unit
                    }
                }
            }, onFailure = {
                Log.e(TAG, "[getStockData] ${it.message}")
                _stockSceneData.value = Empty
            })
        }
    }

    fun onNewProduct() {
        viewModelScope.launch {
            getNewProductDataUseCase().fold(
                onSuccess = { result ->
                    result.collect {
                    Log.d(TAG, "[onNewProduct] Failed = ${it}")
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
                    _stockSceneData.value = ShowStock(
                        stock = emptyList(),
                        categories = emptyList(),
                        formats = emptyList()
                    )
                },
                onFailure = {
                    Log.e(TAG, "[saveProduct] Failed = ${it.message}")
                }
            )
        }
    }

    fun saveNewCategory(newCategory: String) {
        viewModelScope.launch {
            saveNewCategoryUseCase(newCategory).fold(
                onSuccess = { result ->
                    result.collect{
                        Log.d(TAG, "[saveNewCategory] = $it")
                        _stockSceneData.value = when (val stockSceneData = _stockSceneData.value){
                            is EditProduct -> stockSceneData.copy(categories = it)
                            is NewProduct -> stockSceneData.copy(categories = it)
                            is ShowStock -> stockSceneData.copy(categories = it)
                            else -> stockSceneData
                        }
                    }
                },
                onFailure = {
                    Log.e(TAG, "[saveNewCategory] = ${it.message}")
                }
            )
        }
    }

    fun saveNewFormat(newFormat: String) {
        viewModelScope.launch {
            saveNewFormatUseCase(newFormat).fold(
                onSuccess = {result ->
                    result.collect {
                        Log.d(TAG, "[saveNewFormat] = $it")
                        _stockSceneData.value = when (val stockSceneData = _stockSceneData.value){
                            is EditProduct -> stockSceneData.copy(formats = it)
                            is NewProduct -> stockSceneData.copy(formats = it)
                            is ShowStock -> stockSceneData.copy(formats = it)
                            else -> stockSceneData
                        }
                    }
                },
                onFailure = {
                    Log.e(TAG, "[saveNewFormat] = ${it.message}")
                }
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
                    _stockSceneData.value = ShowStock(
                        stock = emptyList(),
                        categories = emptyList(),
                        formats = emptyList()
                    )
                },
                onFailure = {
                    Log.e(TAG, "[deleteProduct] Failed = ${it.message}")
                }
            )
        }
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