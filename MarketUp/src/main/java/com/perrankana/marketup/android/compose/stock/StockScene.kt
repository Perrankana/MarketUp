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
import com.perrankana.marketup.stock.models.Product

@Composable
fun StockScene() {

    val stockViewModel: StockViewModel = hiltViewModel()
    val stockSceneData by stockViewModel.stockSceneData.observeAsState()

    when (val data = stockSceneData) {
        is NewProduct -> NewProductView(
            categoriesList = data.categories,
            formats = data.formats,
            saveNewCategory = { newCategory ->
                stockViewModel.saveNewCategory(newCategory)
            },
            saveNewFormat = { newFormat ->
                stockViewModel.saveNewFormat(newFormat)
            }
        ) { product ->
            stockViewModel.saveProduct(product)
        }
        is ShowStock -> StockView(
            products = data.stock,
            onNewProduct = { stockViewModel.onNewProduct() }
        )
        else -> EmptyStockView {
            stockViewModel.onNewProduct()
        }
    }
}

@Composable
fun StockView(
    products: List<Product>,
    onNewProduct: () -> Unit
) {
    StockListView(
        products = products,
        onNewProduct = onNewProduct,
        onSearch = {}
    )
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