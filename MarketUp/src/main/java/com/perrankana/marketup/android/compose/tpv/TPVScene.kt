package com.perrankana.marketup.android.compose.tpv

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.Background
import com.perrankana.marketup.android.viewmodels.TPVViewModel
import com.perrankana.marketup.sale.ApplyOfferStep
import com.perrankana.marketup.sale.CategoriesStep
import com.perrankana.marketup.sale.Error
import com.perrankana.marketup.sale.FormatStep
import com.perrankana.marketup.sale.Idle
import com.perrankana.marketup.sale.OfferStep
import com.perrankana.marketup.sale.ProductStep
import com.perrankana.marketup.sale.SoldStep
import com.perrankana.marketup.sale.TPVSceneData
import com.perrankana.marketup.sale.models.CanNotApplyOfferException
import com.perrankana.marketup.sale.models.NoStockException
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product

@Composable
fun TPVScene(
    onNewProduct: () -> Unit,
) {

    val viewModel: TPVViewModel = hiltViewModel()
    val tpvSceneData by viewModel.tpvSceneData.observeAsState()

    TPVView(tpvSceneData = tpvSceneData!!, onCategorySelected = {
        viewModel.onCategorySelected(it)
    }, onFormatSelected = {
        viewModel.onFormatSelected(it)
    }, onQuickSearch = {
        viewModel.onQuickSearch(it)
    }, onResetSearch = {
        viewModel.onResetSearch()
    }, onProductSelected = {
        viewModel.onProductSelected(it)
    }, onOfferSelected = {
        viewModel.onOfferSelected(it)
    }, onNewProduct = {
        onNewProduct()
    }, onContinueShopping = {
        viewModel.onContinueShopping()
    }, onEndEvent = {

    })
}

@Composable
fun TPVView(
    tpvSceneData: TPVSceneData,
    onCategorySelected: (String) -> Unit,
    onFormatSelected: (String) -> Unit,
    onQuickSearch: (String) -> Unit,
    onResetSearch: () -> Unit,
    onProductSelected: (Product) -> Unit,
    onOfferSelected: (Offer) -> Unit,
    onNewProduct: () -> Unit,
    onContinueShopping: () -> Unit,
    onEndEvent: () -> Unit
) {
    Scaffold(topBar = {
        TPVHeaderView(totalSold = tpvSceneData.totalSold, onEndEvent = {
            onEndEvent()
        })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { onNewProduct() }) {
            Icon(Icons.Filled.Add, contentDescription = "")
        }
    }) { contentPadding ->
        Background(modifier = Modifier.padding(contentPadding)) {

            Column {

                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        val borderColor = MaterialTheme.colors.secondary

                        val breadCrumb = when (tpvSceneData) {
                            is CategoriesStep -> ""
                            is Idle -> ""
                            is FormatStep -> tpvSceneData.selectedCat
                            is ProductStep -> "${tpvSceneData.selectedCat} / ${tpvSceneData.selectedFormat}"
                            is OfferStep -> "${tpvSceneData.selectedCat} / ${tpvSceneData.selectedFormat} / ${tpvSceneData.product.name}"
                            is SoldStep -> "${tpvSceneData.selectedFormat} / ${tpvSceneData.productsSold.map { it.name }.reduce { acc, s -> "$acc - $s" }}"
                            is ApplyOfferStep -> "${tpvSceneData.selectedFormat} / ${tpvSceneData.offer} / ${
                                tpvSceneData.selectedProducts.map { it.name }.reduce { acc, s -> "$acc - $s" }
                            }"

                            is Error -> when (tpvSceneData.exception) {
                                is NoStockException -> stringResource(R.string.not_enough_stock)
                                is CanNotApplyOfferException -> stringResource(R.string.cant_apply_offer)
                                else -> tpvSceneData.exception.message.orEmpty()
                            }
                        }

                        val instructions = when (tpvSceneData) {
                            is Error, is Idle -> ""

                            is CategoriesStep -> "Select a Category"
                            is FormatStep -> "Select a Format"
                            is ApplyOfferStep, is ProductStep -> "Select your product"

                            is OfferStep -> "Apply offer"
                            is SoldStep -> "Sold!"
                        }
                        Text(
                            modifier = Modifier.drawBehind {
                                    val borderSize = 2.dp.toPx()
                                    drawLine(
                                        color = borderColor, start = Offset(0f, size.height), end = Offset(size.width, size.height), strokeWidth = borderSize
                                    )
                                }, text = breadCrumb, style = MaterialTheme.typography.h6
                        )
                        Text(
                            modifier = Modifier.padding(end = 8.dp, top = 2.dp), text = instructions, style = MaterialTheme.typography.subtitle1
                        )
                    }
                }

                Log.d("TPVView", "tpvSceneData= $tpvSceneData")
                when (tpvSceneData) {
                    is Idle -> Unit
                    is CategoriesStep -> TpvCategoriesStepView(tpvSceneData.categories, onCategorySelected)
                    is FormatStep -> TpvFormatStepView(tpvSceneData.formats, onFormatSelected)
                    is ProductStep -> TpvProductStepView(
                        tpvSceneData.filteredProducts, tpvSceneData.quickSearch, onQuickSearch, onResetSearch, onProductSelected
                    )

                    is OfferStep -> TpvOffersStepView(tpvSceneData.product, onOfferSelected)
                    is ApplyOfferStep -> TpvProductStepView(
                        tpvSceneData.filteredProducts, tpvSceneData.quickSearch, onQuickSearch, onResetSearch, onProductSelected
                    )

                    is SoldStep -> TpvSoldStepView(tpvSceneData.productsSold, onContinueShopping)
                    is Error -> TpvErrorView(onContinueShopping)
                }
            }
        }
    }
}

@Composable
fun TpvCategoriesStepView(categories: List<String>, onCategorySelected: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Log.d("TPVView", "[TpvCategoriesStepView] $categories")
        items(categories) { cat ->
            CatFormatItemView(cat) {
                onCategorySelected(cat)
            }
        }
    }
}

@Composable
fun TpvFormatStepView(formats: List<String>, onFormatSelected: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(formats) { format ->
            CatFormatItemView(format) {
                onFormatSelected(format)
            }
        }
    }
}

@Composable
fun TpvProductStepView(
    products: List<Product>, quickSearch: List<String>, onQuickSearchSelected: (String) -> Unit, onResetSearch: () -> Unit, onProductSelected: (Product) -> Unit
) {
    Column {
        val all = stringResource(R.string.all)

        LazyRow(
            contentPadding = PaddingValues(8.dp)
        ) {
            val queries = mutableListOf<String>().apply {
                this.add(0, all)
                this.addAll(1, quickSearch)
            }
            items(queries) {
                Card(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 35.dp)
                        .padding(2.dp), backgroundColor = MaterialTheme.colors.onBackground
                ) {
                    Text(
                        modifier = Modifier
                            .padding(4.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .clickable {
                                if (it == all) {
                                    onResetSearch()
                                } else {
                                    onQuickSearchSelected(it)
                                }
                            }, text = it, textAlign = TextAlign.Center, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.background
                    )
                }
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(products) {
                ProductItemView(it) {
                    onProductSelected(it)
                }
            }
        }
    }
}

@Composable
fun TpvSoldStepView(products: List<Product>, onContinueShopping: () -> Unit) {
    Column {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            items(products) {
                ProductItemView(it)
            }
        }
        Button(modifier = Modifier.align(alignment = Alignment.CenterHorizontally), onClick = {
            onContinueShopping()
        }) {
            Text(text = stringResource(id = R.string.continue_shopping))
        }
    }
}

@Composable
fun TpvErrorView(onContinueShopping: () -> Unit) {
    Column {
        Button(modifier = Modifier.align(alignment = Alignment.CenterHorizontally), onClick = {
            onContinueShopping()
        }) {
            Text(
                text = stringResource(id = R.string.continue_shopping)
            )
        }
    }
}

@Composable
fun ProductItemView(product: Product, action: ((Product) -> Unit)? = null) {
    Card(
        modifier = Modifier.height(150.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable(action != null) {
                action?.invoke(product)
            }) {
            AsyncImage(
                model = product.image?.toUri(), contentDescription = "", contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier.align(Alignment.Center), text = product.name, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.secondary
            )
            Text(
                modifier = Modifier
                    .padding(end = 8.dp, bottom = 2.dp)
                    .align(Alignment.BottomEnd),
                text = "${product.price}€",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        color = if (product.stock == 0) {
                            MaterialTheme.colors.error
                        } else {
                            MaterialTheme.colors.secondary
                        }, shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .align(Alignment.BottomStart),
                    text = product.stock.toString(),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.End,
                    color = if (product.stock == 0) {
                        MaterialTheme.colors.onError
                    } else {
                        MaterialTheme.colors.onSecondary
                    }
                )
            }

        }
    }
}

@Composable
fun CatFormatItemView(value: String, action: (String) -> Unit) {
    Card(
        modifier = Modifier.height(150.dp)
    ) {
        Card(
            modifier = Modifier.height(150.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable {
                    action(value)
                }) {
                Text(
                    modifier = Modifier
                        .padding(end = 8.dp, top = 2.dp)
                        .align(Alignment.Center),
                    text = value,
                    style = MaterialTheme.typography.h5,
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Composable
fun TpvOffersStepView(product: Product, onOfferSelected: (Offer) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        items(product.offers + Offer.None) {
            Card(
                modifier = Modifier.height(150.dp)
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        onOfferSelected(it)
                    }) {
                    val offer = when (it) {
                        is Offer.DiscountOffer -> "Discount ${it.discount}"
                        Offer.None -> "None"
                        is Offer.NxMOffer -> "${it.n} x ${it.price}"
                    }
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp, top = 2.dp)
                            .align(Alignment.Center),
                        text = offer,
                        style = MaterialTheme.typography.h5,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }
}

@Composable
fun TPVHeaderView(
    totalSold: Float, onEndEvent: () -> Unit
) {
    val borderColor = MaterialTheme.colors.secondary

    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.primary)
        .drawBehind {
            val borderSize = 2.dp.toPx()
            drawLine(
                color = borderColor, start = Offset(0f, size.height), end = Offset(size.width, size.height), strokeWidth = borderSize
            )
        }) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$totalSold€", color = MaterialTheme.colors.onPrimary, style = MaterialTheme.typography.h6
            )
            Button(onClick = {
                onEndEvent()
            }) {
                Text(text = stringResource(id = R.string.end))
            }
        }
    }


}

@Preview
@Composable
fun StockPrevziew() {
    MyApplicationTheme {
        TPVView(tpvSceneData = ProductStep(
            0f, products = products, filteredProducts = products, quickSearch = quick(products), selectedFormat = "A4", selectedCat = "Fanart"
        ),
            onCategorySelected = {},
            onFormatSelected = {},
            onQuickSearch = {},
            onResetSearch = {},
            onProductSelected = {},
            onOfferSelected = {},
            onNewProduct = {},
            onContinueShopping = {},
            onEndEvent = {})
    }
}

fun quick(products: List<Product>) = products.map { it.name.first().toString() }.groupBy { it }.keys.toList()

val products = listOf(
    Product(
        name = "Miercoles", image = null, categories = emptyList(), format = "A4", cost = 1f, price = 8f, offers = listOf(), stock = 2
    ), Product(
        name = "Iercoles", image = null, categories = emptyList(), format = "A4", cost = 1f, price = 8f, offers = listOf(), stock = 2
    )
)