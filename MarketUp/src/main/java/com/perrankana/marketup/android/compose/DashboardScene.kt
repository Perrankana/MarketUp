package com.perrankana.marketup.android.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.android.viewmodels.DashboardViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.perrankana.marketup.DashboardData
import com.perrankana.marketup.android.MyApplicationTheme
import com.perrankana.marketup.android.R

@Composable
fun DashboardScene(
    onStock: () -> Unit,
    onEvent: () -> Unit,
    onProfile: () -> Unit
) {

    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val dashboardData by dashboardViewModel.dashboardData.observeAsState()

    DashboardView(dashboardData = dashboardData, onStock = onStock, onEvent = onEvent, onProfile = onProfile)
}

@Composable
fun DashboardView(
    dashboardData: DashboardData?,
    onStock: () -> Unit,
    onEvent: () -> Unit,
    onProfile: () -> Unit
) {
    BackgroundCard(
        cornerButton = {
            Button(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(Color.Transparent)
                    .padding(20.dp),
                onClick = { onProfile() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.face),
                    contentDescription = stringResource(id = R.string.profile),
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_2211),
                contentDescription = "",
                contentScale = ContentScale.Fit
            )
            Button(
                onClick = { onStock() }) {
                Text(text = stringResource(id = R.string.stock))
            }
            Button(onClick = {
                onEvent()
            }) {
                Text(text = dashboardData?.eventName ?: stringResource(id = R.string.create_event))
            }
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    MyApplicationTheme {
        DashboardView(
            dashboardData = DashboardData(),
            onEvent = {},
            onStock = {},
            onProfile = {}
        )
    }
}