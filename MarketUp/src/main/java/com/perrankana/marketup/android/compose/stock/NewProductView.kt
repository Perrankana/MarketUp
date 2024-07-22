package com.perrankana.marketup.android.compose.stock

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.BackgroundCard
import com.perrankana.marketup.stock.models.Product

@Composable
fun NewProductView(saveProduct: (Product) -> Unit) {

    var name by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var format by rememberSaveable { mutableStateOf("") }
    var cost by rememberSaveable { mutableStateOf("") }
    var sellPrice by rememberSaveable { mutableStateOf("") }
    var offert by rememberSaveable { mutableStateOf("") }
    Box {
        BackgroundCard {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(ScrollState(0)),
            ) {
                Spacer(modifier = Modifier.padding(8.dp))

                if (image.isEmpty()) {
                    Button(
                        onClick = { saveProduct(Product(name)) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                            contentDescription = ""
                        )
                    }
                } else {
                    // Show Image
                    Button(
                        onClick = { saveProduct(Product(name)) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                            contentDescription = ""
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    placeholder = { Text(text = "Print") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.category),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = category,
                    onValueChange = { category = it },
                    placeholder = { Text(text = "Fanart") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.format),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = format,
                    onValueChange = { category = it },
                    placeholder = { Text(text = "A4") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.cost),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = cost,
                    onValueChange = { value ->
                        if (value.isEmpty()) {
                            cost = value
                        } else {
                            value.toDoubleOrNull()?.let {
                                cost = value
                            }
                        }
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.price),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = sellPrice,
                    onValueChange = { sellPrice = it },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                )
                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray
                    ),
                    onClick = { }) {
                    Text(
                        text = stringResource(id = R.string.offers)
                    )
                }

                Spacer(modifier = Modifier.padding(20.dp))
            }
        }
        Card (
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
            ){
            Button(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = { saveProduct(Product(name)) }) {
                Text(text = stringResource(id = R.string.save_product))
            }
        }
    }
}

@Preview
@Composable
fun NewProductPreview() {
    MyApplicationTheme {
        NewProductView {}
    }
}