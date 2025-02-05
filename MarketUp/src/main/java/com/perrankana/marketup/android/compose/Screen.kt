package com.perrankana.marketup.android.compose

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Dashboard: Screen("dashboard")

    data object Profile: Screen("profile")
    data object Stock: Screen("stock")
    data object Event: Screen("event")
    data object TPV: Screen("tpv")
}