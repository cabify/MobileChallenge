package com.cabify.demo.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.cabify.demo.data.model.Product
import com.cabify.demo.ui.components.CabifyProductListItem
import com.cabify.demo.ui.components.DetailAppBar
import com.cabify.demo.ui.components.SearchBar
import com.cabify.demo.ui.utils.ContentType
import com.cabify.demo.ui.utils.NavigationType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

@Composable
fun ProductsScreen(
    contentType: ContentType,
    homeUIState: HomeUIState,
    navigationType: NavigationType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (String, ContentType) -> Unit,
    modifier: Modifier = Modifier
) {
    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == ContentType.SINGLE_PANE && !homeUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

    val productLazyListState = rememberLazyListState()

    if (contentType == ContentType.DUAL_PANE) {
        TwoPane(
            first = {
                CabifyProductList(
                    products = homeUIState.products,
                    lazyListState = productLazyListState,
                    navigateToDetail = navigateToDetail
                )
            },
            second = {
                CabifyProductDetail(
                    item = (homeUIState.selectedProduct ?: homeUIState.products.first()),
                    isFullScreen = false
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            SinglePaneContent(
                cabifyHomeUIState = homeUIState,
                productLazyListState = productLazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
fun SinglePaneContent(
    cabifyHomeUIState: HomeUIState,
    productLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (String, ContentType) -> Unit
) {
    if (cabifyHomeUIState.selectedProduct != null && cabifyHomeUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        CabifyProductDetail(item = cabifyHomeUIState.selectedProduct) {
            closeDetailScreen()
        }
    } else {
        CabifyProductList(
            products = cabifyHomeUIState.products,
            lazyListState = productLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail
        )
    }
}

@Composable
fun CabifyProductList(
    products: List<Product>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (String, ContentType) -> Unit
) {
    LazyColumn(modifier = modifier, state = lazyListState) {
        item {
            SearchBar(modifier = Modifier.fillMaxWidth())
        }
        if (products.isEmpty()) {
            item {
                CabifyProductListItem(Product()) { productId ->
                    //navigateToDetail(productId, ContentType.SINGLE_PANE)
                }
            }
            item {
                CabifyProductListItem(Product()) { productId ->
                    //navigateToDetail(productId, ContentType.SINGLE_PANE)
                }
            }
        } else {
            items(items = products, key = { it.code }) { product ->
                CabifyProductListItem(productItem = product) { productId ->
                    //navigateToDetail(productId, ContentType.SINGLE_PANE)
                }
            }
        }
    }
}

@Composable
fun CabifyProductDetail(
    item: Product,
    isFullScreen: Boolean = true,
    modifier: Modifier = Modifier.fillMaxSize(),
    onBackPressed: () -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
            .background(MaterialTheme.colorScheme.inverseOnSurface)
            .padding(top = 16.dp)
    ) {
        item {
            DetailAppBar(item, isFullScreen) {
                onBackPressed()
            }
        }
    }
}
