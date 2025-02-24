package com.perrankana.marketup.android.compose.stock

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.Background
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product
import com.perrankana.marketup.stock.repositories.testProduct

@Composable
fun StockListView(
    products: List<Product>,
    categories: List<String>,
    formats: List<String>,
    onNewProduct: () -> Unit,
    onSearch: (String) -> Unit,
    onProductClick: (Product) -> Unit,
    onFilterProducts: (List<String>, List<String>, Int?) -> Unit
) {
    var showFiltersDialog by remember { mutableStateOf(false) }
    var filterSelection by remember {
        mutableStateOf(FilterSelection())
    }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            StockHeaderView(
                searchQuery = searchQuery,
                onSearch = {
                    searchQuery = it
                    onSearch(it)
                },
                onFiltersClick = { showFiltersDialog = true },
                onMagicClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNewProduct() }) {
                Icon(Icons.Filled.Add, contentDescription = "")
            }
        }
    ) { contentPadding ->
        Background(modifier = Modifier.padding(contentPadding)) {
            LazyColumn {
                items(products) { product ->
                    ProductItem(product = product) { product ->
                        onProductClick(product)
                    }
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
        }

        if (showFiltersDialog) {
            FilterProductsView(
                categories = categories,
                formats = formats,
                filterSelection = filterSelection,
                onFilter = { cats, formats, stock ->
                    filterSelection = filterSelection.copy(
                        categories = cats,
                        formats = formats,
                        stock = stock
                    )
                    onFilterProducts(cats, formats, stock)
                    showFiltersDialog = false
                },
                onClose = {
                    showFiltersDialog = false
                },
                onClearFilters = {
                    filterSelection = filterSelection.copy(
                        categories = emptyList(),
                        formats = emptyList(),
                        stock = null
                    )
                    onFilterProducts(emptyList(), emptyList(), null)
                    showFiltersDialog = false
                }
            )
        }

    }
}

@Composable
fun StockHeaderView(
    searchQuery: String,
    onSearch: (String) -> Unit,
    onFiltersClick: () -> Unit,
    onMagicClick: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf(searchQuery) }
    val borderColor = MaterialTheme.colors.secondary
    Row(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colors.primary)
        .drawBehind {
            val borderSize = 2.dp.toPx()
            drawLine(
                color = borderColor,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = borderSize
            )
        }
    ) {
        TextField(
            modifier = Modifier.weight(1f),
            value = query,
            onValueChange = {
                query = it
                onSearch(query)
            },
            placeholder = {
                Text(text = "Search", color = MaterialTheme.colors.onPrimary)
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colors.secondary
            ),
        )

        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
            IconButton(onClick = {
                onFiltersClick()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            IconButton(
                onClick = {
                    onMagicClick()
                }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_auto_fix_high_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    }


}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun ProductItem(product: Product, onProductClick: (Product) -> Unit) {
    Card(
        modifier = Modifier
            .padding(end = 20.dp, start = 20.dp)
            .clickable {
                onProductClick(product)
            }
    ) {
        Row {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .align(alignment = Alignment.CenterVertically)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .aspectRatio(1f),
                    model = product.image?.toUri(), contentDescription = "",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(
                            color = MaterialTheme.colors.primary,
                            shape = MaterialTheme.shapes.medium
                        ),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier
                            .padding(end = 8.dp, top = 2.dp),
                        text = "-${product.cost}€",
                        style = MaterialTheme.typography.body1,
                        color = MaterialTheme.colors.secondary
                    )

                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp),
                        text = "${product.price}€",
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colors.onPrimary
                    )
                }

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .background(
                            color = if (product.stock == 0) {
                                MaterialTheme.colors.error
                            } else {
                                MaterialTheme.colors.secondary
                            },
                            shape = MaterialTheme.shapes.medium
                        ),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp),
                        text = product.stock.toString(),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.End,
                        color = if (product.stock == 0) {
                            MaterialTheme.colors.onError}
                        else {
                            MaterialTheme.colors.onSecondary
                        }
                    )
                }

            }
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(10.dp)
            ) {
                val borderColor = MaterialTheme.colors.secondary
                Text(
                    modifier = Modifier
                        .drawBehind {
                            val borderSize = 2.dp.toPx()
                            drawLine(
                                color = borderColor,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = borderSize
                            )
                        },
                    text = product.name,
                    style = MaterialTheme.typography.h6
                )
                Spacer(
                    modifier = Modifier.padding(2.dp)
                )

                Text(
                    text = product.format,
                    style = MaterialTheme.typography.body1
                )
                Spacer(
                    modifier = Modifier.padding(2.dp)
                )
                FlowRow(
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (category in product.categories) {
                        Chip(onClick = {}) {
                            Text(text = category)
                        }
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                    for (offer in product.offers) {
                        Chip(onClick = {}) {
                            Text(
                                text = when (offer) {
                                    is Offer.DiscountOffer -> "${offer.discount}%"
                                    is Offer.NxMOffer -> "${offer.n} x ${offer.price}€"
                                    else -> ""
                                }
                            )
                        }
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                }
            }

        }
    }
}
@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun FilterProductsView(
    categories: List<String>,
    formats: List<String>,
    filterSelection: FilterSelection,
    onFilter:(List<String>, List<String>, Int?) -> Unit,
    onClose: () -> Unit,
    onClearFilters: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.dark_overlay))
    ) {
        var selectedCategories by rememberSaveable { mutableStateOf(filterSelection.categories) }
        var selectedFormats by rememberSaveable { mutableStateOf(filterSelection.formats) }
        var stock by rememberSaveable { mutableStateOf(filterSelection.stock?.toString().orEmpty() ) }

        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(20.dp)
                .verticalScroll(ScrollState(0))
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .clickable {
                        onClose()
                    },
                painter = painterResource(id = R.drawable.baseline_close_24),
                contentDescription = ""
            )
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(text = stringResource(id = R.string.category))

                Spacer(modifier = Modifier.padding(8.dp))

                FlowRow {
                    for (cat in categories){

                        var selected by remember { mutableStateOf(selectedCategories.contains(cat)) }

                        FilterChip(
                            selected = selected,
                            onClick = {
                                selectedCategories = if (selected) {
                                    selectedCategories.remove(cat)
                                } else {
                                    selectedCategories.add(cat)
                                }
                                selected = !selected
                            },
                            selectedIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            },
                        ) {
                            Text(cat)
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(text = stringResource(id = R.string.format))

                Spacer(modifier = Modifier.padding(8.dp))

                FlowRow {
                    for (format in formats){
                        var selected by remember { mutableStateOf(selectedFormats.contains(format)) }

                        FilterChip(
                            selected = selected,
                            onClick = {
                                selectedFormats = if (selected) {
                                    selectedFormats.remove(format)
                                } else {
                                    selectedFormats.add(format)
                                }
                                selected = !selected
                            },
                            selectedIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            },
                        ) {
                            Text(format)
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(text = stringResource(id = R.string.stock))
                Spacer(modifier = Modifier.padding(4.dp))

                TextField(
                    value = stock,
                    onValueChange = {
                        stock = it
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.padding(8.dp))

                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            onFilter(
                                selectedCategories,
                                selectedFormats,
                                stock.toIntOrNull()
                            )
                        }) {
                        Text(text = stringResource(id = R.string.apply_filters))
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            onClearFilters()
                        }) {
                        Text(text = stringResource(id = R.string.clear_filters))
                    }

            }
        }
    }
}

@Preview
@Composable
fun StockListViewPreview() {
    MyApplicationTheme {
        StockListView(
            products = listOf(testProduct),
            categories = listOf(),
            formats = listOf(),
            onNewProduct = {},
            onSearch = {},
            onProductClick = {},
            onFilterProducts = {_,_,_ -> }
        )
    }
}

fun <T> List<T>.add(t:T) : List<T>{
    val temp = this.toMutableList()
    temp.add(t)
    return temp
}

fun <T> List<T>.remove(t:T) : List<T>{
    val temp = this.toMutableList()
    temp.remove(t)
    return temp
}

data class FilterSelection (
    val categories: List<String> = emptyList(),
    val formats : List<String> = emptyList(),
    val stock: Int? = null
)