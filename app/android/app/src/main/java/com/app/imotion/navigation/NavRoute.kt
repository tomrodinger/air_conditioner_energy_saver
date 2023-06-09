package com.app.imotion.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

/**
 * Created by hani.fakhouri on 2023-06-08.
 */

abstract class NavRoute(open val route: String)

fun NavController.navigateTo(navRoute: NavRoute, navOptions: NavOptions? = null) =
    this.navigate(navRoute.route, navOptions)