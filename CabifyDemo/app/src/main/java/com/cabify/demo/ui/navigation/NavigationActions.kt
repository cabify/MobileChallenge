package com.cabify.demo.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.cabify.demo.R

object CabifyRoute {
    const val PRODUCTS = "Products"
    const val SHOPPINGCART = "ShoppingCart"
    const val EXTRA = "Extra"
    const val ABOUTME = "AboutMe"
}

data class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

class NavigationActions(private val navController: NavHostController) {

    fun navigateTo(destination: TopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    TopLevelDestination(
        route = CabifyRoute.PRODUCTS,
        selectedIcon = Icons.Default.List,
        unselectedIcon = Icons.Default.List,
        iconTextId = R.string.tab_products
    ),
    TopLevelDestination(
        route = CabifyRoute.SHOPPINGCART,
        selectedIcon = Icons.Default.ShoppingCart,
        unselectedIcon = Icons.Default.ShoppingCart,
        iconTextId = R.string.tab_cart
    ),
    TopLevelDestination(
        route = CabifyRoute.EXTRA,
        selectedIcon = Icons.Outlined.ChatBubbleOutline,
        unselectedIcon = Icons.Outlined.ChatBubbleOutline,
        iconTextId = R.string.tab_extra
    ),
    TopLevelDestination(
        route = CabifyRoute.ABOUTME,
        selectedIcon = Icons.Default.Info,
        unselectedIcon = Icons.Default.Info,
        iconTextId = R.string.tab_aboutme
    )
)
