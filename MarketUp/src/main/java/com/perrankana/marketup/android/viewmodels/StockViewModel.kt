package com.perrankana.marketup.android.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perrankana.marketup.dashboard.DashboardData
import com.perrankana.marketup.stock.Empty
import com.perrankana.marketup.stock.NewProduct
import com.perrankana.marketup.stock.ShowStock
import com.perrankana.marketup.stock.StockSceneData
import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.usecases.GetStockUseCase
import com.perrankana.marketup.stock.usecases.SaveProductUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    val getStockUseCase: GetStockUseCase,
    val saveProductUseCase: SaveProductUseCase
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
                }
            }, onFailure = {
                _stockSceneData.value = Empty
            })
        }
    }

    fun onNewProduct() {
        _stockSceneData.value = NewProduct
    }

    fun saveProduct(product: Product) {
        viewModelScope.launch {
            saveProductUseCase(product)
        }
    }
}