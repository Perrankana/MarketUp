package com.perrankana.marketup.android.compose.stock

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Chip
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FilterChip
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.BackgroundCard
import com.perrankana.marketup.stock.models.Offer
import com.perrankana.marketup.stock.models.Product
import java.io.File

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun NewProductView(
    categoriesList: List<String>,
    formats: List<String>,
    saveNewCategory: (String) -> Unit,
    saveNewFormat: (String) -> Unit,
    saveProduct: (Product) -> Unit,
) {

    var name by rememberSaveable { mutableStateOf("") }
    var nameError by rememberSaveable { mutableStateOf(false) }
    var image by rememberSaveable { mutableStateOf<Uri?>(null) }
    val categories by rememberSaveable { mutableStateOf(mutableListOf<String>()) }
    var format by rememberSaveable { mutableStateOf("") }
    var formatError by rememberSaveable { mutableStateOf(false) }
    var cost by rememberSaveable { mutableStateOf("") }
    var costError by rememberSaveable { mutableStateOf(false) }
    var sellPrice by rememberSaveable { mutableStateOf("") }
    var priceError by rememberSaveable { mutableStateOf(false) }
    var offers by rememberSaveable { mutableStateOf(listOf<Offer>()) }

    var addCategoryVisible by remember { mutableStateOf(false) }
    var newCategory by rememberSaveable { mutableStateOf("") }
    var addFormatVisible by remember { mutableStateOf(false) }
    var newFormat by rememberSaveable { mutableStateOf("") }

    var showOffersDialog by remember { mutableStateOf(false) }

    val localContext = LocalContext.current
    val directory: File = localContext.createDirectoryFile()

    Box {
        BackgroundCard {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 20.dp)
                    .verticalScroll(ScrollState(0)),
            ) {
                Spacer(modifier = Modifier.padding(8.dp))

                ImagePickerView(
                    context = localContext,
                    directory = directory,
                    setImage = { image = it },
                    image = image
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.name),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = {
                        nameError = false
                        name = it
                    },
                    placeholder = { Text(text = "Print") },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    isError = nameError
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.category),
                    style = MaterialTheme.typography.body1
                )

                FlowRow {
                    for (cat in categoriesList) {

                        var selected by remember { mutableStateOf(false) }

                        FilterChip(
                            onClick = {
                                if(selected){
                                    selected = false
                                    categories.remove(cat)
                                } else {
                                    selected = true
                                    categories.add(cat)
                                }
                            },
                            selected = selected,
                            selectedIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            },
                            ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.body1
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    Chip(onClick = {
                        addCategoryVisible = true
                    }) {
                        Text(
                            text = stringResource(id = R.string.add_one_category),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    if (addCategoryVisible){
                        Row {
                            TextField(
                                modifier = Modifier.weight(0.5f),
                                value = newCategory,
                                onValueChange = { newCategory = it },
                                placeholder = { },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                            )

                            Spacer(modifier = Modifier.padding(4.dp))

                            Button(
                                onClick = {
                                    if (newCategory.isNotEmpty()) {
                                        saveNewCategory(newCategory)
                                        addCategoryVisible = false
                                        newCategory = ""
                                    }
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_24),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.format),
                    style = MaterialTheme.typography.body1
                )

                if (formatError) {
                    Text(
                        text = stringResource(id = R.string.format_error),
                        style = MaterialTheme.typography.body1,
                        color = Color.Red
                    )
                }
                FlowRow {
                    for (formatItem in formats) {
                        FilterChip(
                            onClick = {
                                format = formatItem
                                formatError = false
                            },
                            selected = format == formatItem,
                            selectedIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Done icon",
                                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                                )
                            },
                        ) {
                            Text(
                                text = formatItem,
                                style = MaterialTheme.typography.body1
                            )
                        }
                        Spacer(modifier = Modifier.padding(4.dp))
                    }
                    Chip(onClick = {
                        addFormatVisible = true
                    }) {
                        Text(
                            text = stringResource(id = R.string.add_one_category),
                            style = MaterialTheme.typography.body1
                        )
                    }
                    if (addFormatVisible){
                        Row {
                            TextField(
                                modifier = Modifier.weight(0.5f),
                                value = newFormat,
                                onValueChange = { newFormat = it },
                                placeholder = { },
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Next
                                ),
                            )

                            Spacer(modifier = Modifier.padding(4.dp))

                            Button(
                                onClick = {
                                    if (newFormat.isNotEmpty()) {
                                        saveNewFormat(newFormat)
                                        addFormatVisible = false
                                        newFormat = ""
                                    }
                                }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_24),
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                }

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
                        costError = false
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next,
                        keyboardType = KeyboardType.Number
                    ),
                    isError = costError
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Text(
                    text = stringResource(id = R.string.price),
                    style = MaterialTheme.typography.body1
                )

                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = sellPrice,
                    onValueChange = {
                        sellPrice = it
                        priceError = false
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    isError = priceError
                )

                Spacer(modifier = Modifier.padding(8.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.LightGray
                    ),
                    onClick = {
                        showOffersDialog = true
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.offers)
                    )
                }

                if (offers.isNotEmpty()) {

                    Spacer(modifier = Modifier.padding(8.dp))

                    OffersListView(
                        offers = offers,
                        removeOffer = { offer ->
                            val tempOfferList = offers.toMutableList()
                            tempOfferList.remove(offer)
                            offers = tempOfferList
                        }
                    )
                }

                Spacer(modifier = Modifier.padding(40.dp))
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = {
                    if (name.isEmpty()) {
                        nameError = true
                    }
                    if (format.isEmpty()) {
                        formatError = true
                    }
                    if (cost.isEmpty()) {
                        costError = true
                    }
                    if (sellPrice.isEmpty()) {
                        priceError = true
                    }
                    if (
                        name.isNotEmpty() &&
                        format.isNotEmpty() &&
                        cost.isNotEmpty() &&
                        sellPrice.isNotEmpty()
                    ) {
                        saveProduct(
                            Product(
                                name = name,
                                image = image?.toString(),
                                categories = categories,
                                format = format,
                                cost = cost.toFloat(),
                                price = sellPrice.toFloat(),
                                offers = offers
                            )
                        )
                    }
                }) {
                Text(text = stringResource(id = R.string.save_product))
            }
        }

        if (showOffersDialog) {
            NewOfferView(
                saveNxMOffer = { n, price ->
                    val tempOfferList = offers.toMutableList()
                    tempOfferList.add(Offer.NxMOffer(n, price))
                    offers = tempOfferList
                    showOffersDialog = false
                },
                saveDiscountOffer = { discount ->
                    val tempOfferList = offers.toMutableList()
                    tempOfferList.add(Offer.DiscountOffer(discount))
                    offers = tempOfferList
                    showOffersDialog = false
                },
                onClose = {
                    showOffersDialog = false
                }
            )
        }
    }
}

fun Context.createDirectoryFile(): File = File(cacheDir, "images")

@Composable
fun ImagePickerView(
    context: Context,
    directory: File,
    setImage: (Uri) -> Unit,
    image: Uri?
    ){

    val tempUri = remember { mutableStateOf<Uri?>(null) }

    val authority = stringResource(id = R.string.fileprovider)

    fun getTempUri(): Uri? {
        directory.mkdirs()
        val file = File.createTempFile(
            "image_" + System.currentTimeMillis().toString(),
            ".jpg",
            directory
        )

        return FileProvider.getUriForFile(
            context,
            authority,
            file
        )
    }

    val takePhotoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { isSaved ->
            if(isSaved){
                tempUri.value?.let {
                    setImage(it)
                }
            }
        }
    )

    val cameraPermissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                tempUri.value = getTempUri()
                takePhotoLauncher.launch(tempUri.value)
            } else {
                // show info about requesting permission
            }
        }

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { imageUri ->
            imageUri?.let {
                setImage(it)
            }
        }
    )

    Box{
        if( image != null) {
            AsyncImage(model = image, contentDescription = "")
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        ) {
            Button(
                onClick = {
                    val permission = Manifest.permission.CAMERA
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        // Permission is already granted, proceed to step 2
                        val tmpUri = getTempUri()
                        tempUri.value = tmpUri
                        takePhotoLauncher.launch(tempUri.value)
                    } else {
                        // Permission is not granted, request it
                        cameraPermissionLauncher.launch(permission)
                    }

                }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_a_photo_24),
                    contentDescription = ""
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                onClick = {
                    val permission = Manifest.permission.CAMERA
                    if (ContextCompat.checkSelfPermission(
                            context,
                            permission
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        imagePicker.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    } else {
                        // Permission is not granted, request it
                        cameraPermissionLauncher.launch(permission)
                    }

                }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_photo_alternate_24),
                    contentDescription = ""
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalLayoutApi::class)
@Composable
fun NewOfferView(
    saveNxMOffer: (Int, Float) -> Unit,
    saveDiscountOffer: (Int) -> Unit,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.dark_overlay))
    ) {
        var nxmOfferSelected by remember { mutableStateOf(false) }
        var discountOfferSelected by remember { mutableStateOf(false) }

        var nxOffer by rememberSaveable { mutableStateOf("") }
        var nxmOfferPrice by rememberSaveable { mutableStateOf("") }
        var discountOffer by rememberSaveable { mutableStateOf("") }

        Card(
            modifier = Modifier
                .align(Alignment.Center)
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
                Text(text = stringResource(id = R.string.select_offer_type))

                Spacer(modifier = Modifier.padding(8.dp))

                FlowRow {
                    FilterChip(
                        selected = nxmOfferSelected,
                        onClick = {
                            nxmOfferSelected = !nxmOfferSelected
                            discountOfferSelected = !nxmOfferSelected
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        },
                    ) {
                        Text(text = stringResource(id = R.string.n_x_m_offer))
                    }

                    Spacer(modifier = Modifier.padding(4.dp))

                    FilterChip(
                        selected = discountOfferSelected,
                        onClick = {
                            discountOfferSelected = !discountOfferSelected
                            nxmOfferSelected = !discountOfferSelected
                        },
                        selectedIcon = {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Done icon",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        },
                    ) {
                        Text(text = stringResource(id = R.string.discount_offer))
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                if (nxmOfferSelected) {

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        TextField(
                            modifier = Modifier.width(60.dp),
                            value = nxOffer, onValueChange = {
                                nxOffer = it
                            }, keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Next,
                                keyboardType = KeyboardType.Number
                            ),
                            placeholder = {
                                Text(text = "N")
                            })
                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.CenterVertically),
                            text = "x", fontSize = 22.sp
                        )
                        TextField(
                            modifier = Modifier.width(80.dp),
                            value = nxmOfferPrice, onValueChange = {
                                nxmOfferPrice = it
                            }, keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.CenterVertically),
                            text = "€", fontSize = 22.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            if (nxOffer.isNotEmpty() && nxmOfferPrice.isNotEmpty()) {
                                saveNxMOffer(
                                    nxOffer.toInt(),
                                    nxmOfferPrice.toFloat()
                                )
                            }
                        }) {
                        Text(text = stringResource(id = R.string.save_this_offer))
                    }
                }

                if (discountOfferSelected) {

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        TextField(
                            modifier = Modifier.width(60.dp),
                            value = discountOffer, onValueChange = {
                                discountOffer = it
                            }, keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Done,
                                keyboardType = KeyboardType.Number
                            )
                        )
                        Text(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.CenterVertically),
                            text = "%", fontSize = 22.sp
                        )

                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            if (discountOffer.isNotEmpty()) {
                                saveDiscountOffer(discountOffer.toInt())
                            }
                        }) {
                        Text(text = stringResource(id = R.string.save_this_offer))
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun OffersListView(
    offers: List<Offer>,
    removeOffer: (Offer) -> Unit
) {
    FlowRow {
        for (offer in offers) {
            when (offer) {
                is Offer.DiscountOffer -> {
                    Chip(onClick = {
                        removeOffer(offer)
                    }) {
                        Text(text = "${offer.discount} %")
                        Icon(
                            modifier = Modifier
                                .padding(2.dp)
                                .clickable { removeOffer(offer) },
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = ""
                        )
                    }
                }

                is Offer.NxMOffer -> {
                    Chip(onClick = {}) {
                        Text(text = "${offer.n} x 1 = ${offer.price}€")
                        Icon(
                            modifier = Modifier
                                .padding(2.dp)
                                .clickable { removeOffer(offer) },
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = ""
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview
@Composable
fun NewProductPreview() {
    MyApplicationTheme {
        NewProductView(
            categoriesList = listOf("fanart", "cosecha"),
            formats = listOf("A5", "A4", "A3", "Pegatinas"),
            {},
            {}
        ) {}
    }
}