package com.cabify.demo.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.cabify.demo.ui.cart.ShoppingCartContent
import com.cabify.demo.ui.cart.ShoppingCartViewModel
import com.cabify.demo.ui.navigation.*
import com.cabify.demo.ui.utils.*
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CabifyApp(
    windowSize: WindowSizeClass, displayFeatures: List<DisplayFeature>
) {
    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: NavigationType
    val contentType: ContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) -> DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) -> DevicePosture.Separating(
            foldingFeature.bounds, foldingFeature.orientation
        )

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ContentType.DUAL_PANE
            } else {
                ContentType.SINGLE_PANE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.DUAL_PANE
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.SINGLE_PANE
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            NavigationContentPosition.TOP
        }
        WindowHeightSizeClass.Medium, WindowHeightSizeClass.Expanded -> {
            NavigationContentPosition.CENTER
        }
        else -> {
            NavigationContentPosition.TOP
        }
    }

    NavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationWrapper(
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        NavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: CabifyRoute.PRODUCTS

    if (navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(drawerContent = {
            PermanentNavigationDrawerContent(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigationActions::navigateTo,
            )
        }) {
            CabifyAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }
    } else {
        ModalNavigationDrawer(
            drawerContent = {
                ModalNavigationDrawerContent(
                    selectedDestination = selectedDestination,
                    navigationContentPosition = navigationContentPosition,
                    navigateToTopLevelDestination = navigationActions::navigateTo,
                )
            },
        ) {
            CabifyAppContent(
                navigationType = navigationType,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationContentPosition = navigationContentPosition,
                navController = navController,
                selectedDestination = selectedDestination,
                navigateToTopLevelDestination = navigationActions::navigateTo
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CabifyAppContent(
    modifier: Modifier = Modifier,
    navigationType: NavigationType,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: NavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (TopLevelDestination) -> Unit
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == NavigationType.NAVIGATION_RAIL) {
            NavigationRail(
                selectedDestination = selectedDestination,
                navigationContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            NavHost(
                navController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                modifier = Modifier.weight(1f)
            )
            AnimatedVisibility(visible = navigationType == NavigationType.BOTTOM_NAVIGATION) {
                CabifyBottomNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
private fun NavHost(
    navController: NavHostController,
    contentType: ContentType,
    displayFeatures: List<DisplayFeature>,
    modifier: Modifier = Modifier
) {
    val shoppingCartViewModel = koinViewModel<ShoppingCartViewModel>()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = CabifyRoute.PRODUCTS,
    ) {
        composable(CabifyRoute.PRODUCTS) {
            ProductsScreen(
                contentType = contentType,
                displayFeatures = displayFeatures,
                shoppingCartViewModel = shoppingCartViewModel
            )
        }
        composable(CabifyRoute.EXTRA) {
            EmptyComingSoon()
        }
        composable(CabifyRoute.SHOPPINGCART) {
            ShoppingCartContent(shoppingCartViewModel)
        }
        composable(CabifyRoute.ABOUTME) {
            EmptyComingSoon()
        }
    }
}