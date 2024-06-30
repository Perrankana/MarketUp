package com.perrankana.marketup.android.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MarketUpApp() {
    val navController = rememberNavController()
    MarketUpNavHost(navController = navController)
}

@Composable
fun MarketUpNavHost(
    navController: NavHostController
){
    
}