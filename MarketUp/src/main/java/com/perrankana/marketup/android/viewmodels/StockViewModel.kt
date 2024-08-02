package com.perrankana.marketup.android.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.stock.Empty
import com.perrankana.marketup.stock.NewProduct
import com.perrankana.marketup.stock.ShowStock
import com.perrankana.marketup.stock.StockSceneData
import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.usecases.GetNewProductDataUseCase
import com.perrankana.marketup.stock.usecases.GetStockUseCase
import com.perrankana.marketup.stock.usecases.SaveNewCategoryUseCase
import com.perrankana.marketup.stock.usecases.SaveNewFormatUseCase
import com.perrankana.marketup.stock.usecases.SaveProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase,
    private val saveProductUseCase: SaveProductUseCase,
    private val saveNewCategoryUseCase: SaveNewCategoryUseCase,
    private val saveNewFormatUseCase: SaveNewFormatUseCase,
    private val getNewProductDataUseCase: GetNewProductDataUseCase
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
                if (it.isEmpty()){
                    _stockSceneData.value = Empty
                } else {
                    _stockSceneData.value = ShowStock(it)
                    Log.d("BANANAS", "stock= $it")
                }
            }, onFailure = {
                _stockSceneData.value = Empty
            })
        }
    }

    fun onNewProduct() {
        viewModelScope.launch {
            getNewProductDataUseCase().fold(
                onSuccess = {
                    _stockSceneData.value = NewProduct(
                        categories = it.first,
                        formats = it.second
                    )
                },
                onFailure = {}
            )
        }
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            saveProductUseCase(product)
        }
        getStockData()
    }

    fun saveNewCategory(newCategory: String) {
        viewModelScope.launch {
            saveNewCategoryUseCase(newCategory).fold(
                onSuccess = {
                    _stockSceneData.value = (_stockSceneData.value as NewProduct).copy(categories = it)
                },
                onFailure = {}
            )
        }
    }

    fun saveNewFormat(newFormat: String) {
        viewModelScope.launch {
            saveNewFormatUseCase(newFormat).fold(
                onSuccess = {
                    _stockSceneData.value = (_stockSceneData.value as NewProduct).copy(formats = it)
                },
                onFailure = {}
            )
        }
    }
}