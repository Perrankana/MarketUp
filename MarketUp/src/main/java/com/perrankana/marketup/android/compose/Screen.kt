package com.perrankana.marketup.android.compose

import androidx.navigation.NamedNavArgument

sealed class Screen(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    object Dashboard: Screen("dashboard")

    object Profile: Screen("profile")
    object Stock: Screen("stock")
    object Event: Screen("event")
}