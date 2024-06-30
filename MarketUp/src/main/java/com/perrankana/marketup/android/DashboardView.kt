package com.perrankana.marketup.android

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.perrankana.marketup.DashboardData

@Composable
fun DashboardView(
    dashboardData: DashboardData,
    onStock: () -> Unit,
    onEvent: () -> Unit,
    onProfile: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(all = 20.dp)
    ) {
        Button(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .background(Color.Transparent),
            onClick = { onProfile() }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.face),
                contentDescription = stringResource(id = R.string.profile),
                tint = colorResource(id = R.color.icon_color)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .wrapContentWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onStock() }) {
                Text(text = stringResource(id = R.string.stock))
            }
            Button(onClick = { onEvent() }) {
                Text(text = dashboardData.eventName ?: stringResource(id = R.string.create_event))
            }
        }
    }
}

@Preview
@Composable
fun DashboardPreview() {
    MyApplicationTheme {
        DashboardView(
            dashboardData = DashboardData(null),
            onEvent = {},
            onStock = {},
            onProfile = {}
        )
    }
}