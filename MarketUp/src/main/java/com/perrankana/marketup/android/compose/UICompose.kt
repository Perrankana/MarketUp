package com.perrankana.marketup.android.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.R

@Composable
fun Background(modifier: Modifier = Modifier, cornerButton: @Composable (BoxScope.() -> Unit)? = null, content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
    ) {
        Image(
            modifier = modifier
                .fillMaxSize()
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )
        cornerButton?.let { cornerButton() }
        content()
    }
}

@Composable
fun BackgroundCard(modifier: Modifier = Modifier, cornerButton: @Composable (BoxScope.() -> Unit)? = null, cardContent: @Composable () -> Unit){
    Background(modifier, cornerButton) {
        Card(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(40.dp)
        ) {
            cardContent()

        }
    }
}