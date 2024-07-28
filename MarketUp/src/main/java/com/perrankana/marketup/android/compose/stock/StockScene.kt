package com.perrankana.marketup.android.compose.stock

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.BackgroundCard
import com.perrankana.marketup.android.viewmodels.StockViewModel
import com.perrankana.marketup.stock.NewProduct
import com.perrankana.marketup.stock.ShowStock

@Composable
fun StockScene() {

    val stockViewModel: StockViewModel = hiltViewModel()
    val stockSceneData by stockViewModel.stockSceneData.observeAsState()

    when (stockSceneData) {
        NewProduct -> NewProductView(
            categoriesList = listOf("fanart", "cosecha", "anime"),
            formats = listOf("A4", "A6", "A5"),
            addNewCategory = {}
        ) { product ->
            stockViewModel.saveProduct(product)
        }
        is ShowStock -> StockView()
        else -> EmptyStockView {
            stockViewModel.onNewProduct()
        }
    }
}

@Composable
fun StockView() {
    BackgroundCard {
        Text(
            modifier = Modifier.padding(20.dp),
            text = "Stock"
        )
    }
}

@Composable
fun EmptyStockView(addProduct: () -> Unit) {
    BackgroundCard {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(20.dp),
                text = stringResource(id = R.string.empty_stock)
            )
            Button(
                onClick = { addProduct() }
            ) {
                Text(text = stringResource(id = R.string.add_product))
            }
        }
    }
}

@Preview
@Composable
fun StockPreview() {
    MyApplicationTheme {
        EmptyStockView({})
    }
}