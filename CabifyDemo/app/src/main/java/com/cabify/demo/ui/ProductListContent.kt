package com.cabify.demo.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.cabify.demo.data.model.Product
import com.cabify.demo.ui.components.CabifyProductListItem
import com.cabify.demo.ui.components.DetailAppBar
import com.cabify.demo.ui.components.SearchBar
import com.cabify.demo.ui.utils.ContentType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane
import java.math.BigDecimal

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ProductsScreen(
    contentType: ContentType,
    homeUIState: HomeUIState,
    displayFeatures: List<DisplayFeature>,
    onAddToCartClicked: (String, String, BigDecimal) -> Unit,
    modifier: Modifier = Modifier
) {
    val productLazyListState = rememberLazyListState()

    if (contentType == ContentType.DUAL_PANE) {
        TwoPane(
            first = {
                CabifyProductList(
                    products = homeUIState.products,
                    lazyListState = productLazyListState,
                    onAddToCartClicked = onAddToCartClicked
                )
            },
            second = {
                CabifyProductDetail(
                    item = (homeUIState.products.first()), isFullScreen = false
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
                onAddToCartClicked = onAddToCartClicked,
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun SinglePaneContent(
    cabifyHomeUIState: HomeUIState,
    productLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onAddToCartClicked: (String, String, BigDecimal) -> Unit,
) {
    CabifyProductList(
        products = cabifyHomeUIState.products,
        lazyListState = productLazyListState,
        modifier = modifier,
        onAddToCartClicked = onAddToCartClicked,
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CabifyProductList(
    products: List<Product>,
    lazyListState: LazyListState,
    modifier: Modifier = Modifier,
    onAddToCartClicked: (String, String, BigDecimal) -> Unit,
) {
    LazyColumn(modifier = modifier, state = lazyListState) {
        item {
            SearchBar(modifier = Modifier.fillMaxWidth())
        }
        if (products.isEmpty()) {
            item {
                CabifyProductListItem(
                    productItem = Product(), onAddToCartClicked = onAddToCartClicked
                )
            }
            item {
                CabifyProductListItem(
                    productItem = Product(), onAddToCartClicked = onAddToCartClicked
                )
            }
        } else {
            items(items = products, key = { it.code }) { product ->
                CabifyProductListItem(
                    productItem = product, onAddToCartClicked = onAddToCartClicked
                )
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
