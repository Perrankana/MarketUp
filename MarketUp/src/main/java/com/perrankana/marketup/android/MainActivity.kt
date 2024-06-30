package com.perrankana.marketup.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.perrankana.marketup.DashboardData
import com.perrankana.marketup.Greeting

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DashboardView(
                        dashboardData = DashboardData(null),
                        onEvent = {},
                        onStock = {},
                        onProfile = {}
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        DashboardView(
            dashboardData = DashboardData(null),
            onEvent = {},
            onStock = {},
            onProfile = {}
        )
    }
}
