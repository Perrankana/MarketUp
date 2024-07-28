package com.perrankana.marketup.android.compose.stock

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.ImageLoader
import coil.compose.AsyncImage
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R
import com.perrankana.marketup.android.compose.BackgroundCard
import com.perrankana.marketup.stock.models.Product
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun NewProductView(saveProduct: (Product) -> Unit) {

    var name by rememberSaveable { mutableStateOf("") }
    var image by rememberSaveable { mutableStateOf<Uri?>(null) }
    var category by rememberSaveable { mutableStateOf("") }
    var format by rememberSaveable { mutableStateOf("") }
    var cost by rememberSaveable { mutableStateOf("") }
    var sellPrice by rememberSaveable { mutableStateOf("") }
    var offert by rememberSaveable { mutableStateOf("") }

    val localContext = LocalContext.current
    val directory: File = localContext.createDirectoryFile()

    val tempUri = remember { mutableStateOf<Uri?>(null) }



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
                    tempUri = tempUri,
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
                onClick = { saveProduct(Product(name)) }) {
                Text(text = stringResource(id = R.string.save_product))
            }
        }
    }
}

fun Context.createDirectoryFile(): File = File(cacheDir, "images")

@Composable
fun ImagePickerView(
    context: Context,
    directory: File,
    tempUri: MutableState<Uri?>,
    setImage: (Uri) -> Unit,
    image: Uri?
    ){

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

@Preview
@Composable
fun NewProductPreview() {
    MyApplicationTheme {
        NewProductView {}
    }
}