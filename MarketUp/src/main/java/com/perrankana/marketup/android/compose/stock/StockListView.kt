package com.perrankana.marketup.android.compose.stock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
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
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    onNewProduct: () -> Unit,
    onSearch: (String) -> Unit
) {
    Scaffold(
        topBar = {
            StockHeaderView(
                onSearch = onSearch
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
                    ProductItem(product = product)
                    Spacer(modifier = Modifier.padding(2.dp))
                }
            }
        }

    }
}

@Composable
fun StockHeaderView(
    onSearch: (String) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
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
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                onSearch(searchQuery)
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
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_filter_list_24),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }

            IconButton(
                onClick = { /*TODO*/ }) {
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
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier.padding(end = 20.dp, start = 20.dp)
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


@Preview
@Composable
fun StockListViewPreview() {
    MyApplicationTheme {
        StockListView(
            products = listOf(testProduct),
            onNewProduct = {},
            onSearch = {}
        )
    }
}