package com.perrankana.marketup.android.compose

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.perrankana.marketup.android.compose.event.EventScene
import com.perrankana.marketup.android.compose.stock.StockScene
import com.perrankana.marketup.android.compose.tpv.TPVScene

@Composable
fun MarketUpApp() {
    val navController = rememberNavController()
    MarketUpNavHost(navController = navController)
}

@Composable
fun MarketUpNavHost(
    navController: NavHostController
){
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            DashboardScene(
                onStock = { navController.navigate(Screen.Stock.route) },
                onEvent = { navController.navigate(Screen.Event.route) },
                onProfile = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(route = Screen.Event.route) {
            EventScene(
                onStartEvent = { navController.navigate(Screen.TPV.route) }
            )
        }
        composable(route = Screen.Stock.route) {
            StockScene()
        }
        composable(route = Screen.Profile.route) {
            ProfileScene()
        }
        composable(route = Screen.TPV.route) {
            TPVScene(
                onNewProduct = {navController.navigate(Screen.Stock.route)},
                onClose = {
                    navController.navigate(Screen.Dashboard.route)
                }
            )
        }
    }
}