package com.cabify.demo.ui.cart

import android.icu.util.Currency
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cabify.demo.R
import com.cabify.demo.data.model.Product
import com.cabify.demo.data.model.ProductDiscount
import com.cabify.demo.ui.cart.Buttons.CTAButtonGreen
import com.cabify.demo.ui.cart.Image.CartProductIcon
import com.cabify.demo.ui.utils.AlertDialog.StartCashOutProcess
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ShoppingCartContent(
    shoppingCartViewModel: ShoppingCartViewModel
) {
    val shoppingCartItemList: List<ShoppingCartItem> =
        shoppingCartViewModel.shoppingCartItems
    val totalPrice by shoppingCartViewModel.shoppingCartTotalPriceState
    val isCtaButtonEnabled by shoppingCartViewModel.isCtaButtonEnabledState
    val productLazyListState = rememberLazyListState()

    ShoppingCartContent(
        shoppingCartItemList = shoppingCartItemList,
        totalPrice = totalPrice,
        lazyListState = productLazyListState,
        isCtaButtonEnabled = isCtaButtonEnabled,
        startCashOutProcess = shoppingCartViewModel::onOpenDialogClicked
    )

    val showDialogState: Boolean by shoppingCartViewModel.showDialog.collectAsState()

    StartCashOutProcess(
        showDialogState,
        shoppingCartViewModel::onDialogDismiss,
        shoppingCartViewModel::onDialogConfirm,
        totalPrice,
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ShoppingCartContent(
    shoppingCartItemList: List<ShoppingCartItem>,
    totalPrice: BigDecimal,
    lazyListState: LazyListState,
    isCtaButtonEnabled: Boolean,
    startCashOutProcess: () -> Unit,
) {
    Column {
        ShoppingCartHeader()
        ShoppingCartList(
            lazyListState, shoppingCartItemList, totalPrice, isCtaButtonEnabled, startCashOutProcess
        )
    }
}

@Composable
fun ShoppingCartHeader() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(start = 24.dp, end = 24.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                stringResource(R.string.shopping_cart),
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
}

@Preview(showBackground = true)
@Composable
fun ShoppingCartHeaderPreview() {
    ShoppingCartHeader()
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ShoppingCartList(
    lazyListState: LazyListState,
    shoppingCartItemList: List<ShoppingCartItem>,
    totalPrice: BigDecimal,
    isCtaButtonEnabled: Boolean,
    startCashOutProcess: () -> Unit
) {
    LazyColumn(modifier = Modifier.padding(top = 12.dp, bottom = 12.dp), state = lazyListState) {

        items(items = shoppingCartItemList) { shoppingCartItem ->
            CartListItem(
                cartItemProductData = shoppingCartItem.cartItemProductData,
                removeProductItemFromCart = shoppingCartItem::removeProductItemFromShoppingCart
            )
        }
        item {
            ShoppingCartBottom(totalPrice, isCtaButtonEnabled, startCashOutProcess)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CartListItem(
    cartItemProductData: Product, removeProductItemFromCart: () -> Unit
) {
    val symbol = Currency.getInstance(Locale.getDefault()).symbol

    ShoppingCartItemBackground {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                this@ShoppingCartItemBackground.CartProductIcon(drawable = R.drawable.avatar_express)
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = cartItemProductData.name)
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Text(
                            text = stringResource(
                                R.string.cart_quantity, cartItemProductData.quantity
                            ), style = MaterialTheme.typography.labelMedium
                        )
                        Text(
                            text = stringResource(
                                id = R.string.amount_unit,
                                symbol,
                                cartItemProductData.amountUnit().toString()
                            ),
                            style = MaterialTheme.typography.labelMedium,
                            textDecoration = TextDecoration.Underline
                        )
                    }
                }
                Column {
                    IconButton(
                        onClick = removeProductItemFromCart::invoke,
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = stringResource(id = R.string.remove_button),
                            tint = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            }
            if (cartItemProductData.discount()) {
                Text(
                    text = stringResource(
                        R.string.discount_value, cartItemProductData.discountValue(), symbol
                    ), style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShoppingCartListPreview(
    lazyListState: LazyListState,
    totalPrice: BigDecimal = BigDecimal.ZERO,
    isCtaButtonEnabled: Boolean = false,
    startCashOutProcess: () -> Unit
) {
    ShoppingCartList(
        lazyListState = lazyListState, listOf(
            ShoppingCartItem(cartItemProductData = Product(
                productId = UUID.randomUUID(),
                code = ProductDiscount.VOUCHER.name,
                name = ProductDiscount.VOUCHER.name,
                price = BigDecimal(5.0).setScale(2, RoundingMode.DOWN),
                quantity = 2
            ),
                onShoppingCartStateEvent = remember { mutableStateOf(ShoppingCartStates.Initial) }),
            ShoppingCartItem(cartItemProductData = Product(
                productId = UUID.randomUUID(),
                code = ProductDiscount.TSHIRT.name,
                name = ProductDiscount.TSHIRT.name,
                price = BigDecimal(20.0).setScale(2, RoundingMode.DOWN),
                quantity = 2
            ),
                onShoppingCartStateEvent = remember { mutableStateOf(ShoppingCartStates.Initial) })
        ), totalPrice, isCtaButtonEnabled, startCashOutProcess
    )
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun ShoppingCartBottom(
    totalPrice: BigDecimal, isCtaButtonEnabled: Boolean, startCashOutProcess: () -> Unit
) {
    Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    CTAButtonGreen(
        text = if (!isCtaButtonEnabled) {
            stringResource(R.string.shopping_cart_no_items_cta_label)
        } else {
            val symbol = Currency.getInstance(Locale.getDefault()).symbol

            stringResource(
                R.string.shopping_cart_cta_label, totalPrice, symbol
            )
        }, isCtaButtonEnabled = isCtaButtonEnabled, onButtonClickCallback = startCashOutProcess
    )
}

@Composable
fun ShoppingCartItemBackground(content: @Composable BoxWithConstraintsScope.() -> Unit) {
    BoxWithConstraints(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant
            )
    ) {
        content.invoke(this)
    }
}


object Image {
    @Composable
    fun BoxWithConstraintsScope.CartProductIcon(drawable: Int) {
        Image(
            painter = painterResource(id = drawable),
            contentDescription = String(),
            Modifier
                .height(maxHeight)
                .width(80.dp)
                .padding(12.dp)
        )
    }
}

object Buttons {
    @Composable
    fun CTAButtonGreen(
        text: String, isCtaButtonEnabled: Boolean, onButtonClickCallback: () -> Unit
    ) {
        Button(
            onClick = onButtonClickCallback,
            enabled = isCtaButtonEnabled,
            colors = ButtonDefaults.buttonColors(Color(176, 213, 83)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(start = 25.dp, end = 25.dp),
        ) {
            Text(text = text, color = Color.White)
        }
    }
}