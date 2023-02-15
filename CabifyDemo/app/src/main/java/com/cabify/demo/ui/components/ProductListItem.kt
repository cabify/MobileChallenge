package com.cabify.demo.ui.components

import android.icu.util.Currency
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.cabify.demo.R
import com.cabify.demo.data.model.Product
import com.cabify.demo.ui.cart.ShoppingCartViewModel
import java.math.BigDecimal
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CabifyProductListItem(
    productItem: Product,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    shoppingCartViewModel: ShoppingCartViewModel
) {

    val semanticsModifier = if (isSelectable) modifier
        .padding(horizontal = 16.dp, vertical = 4.dp)
        .semantics { selected = isSelected }
    else modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    Card(
        modifier = semanticsModifier, colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProductImage(
                    drawableResource = R.drawable.avatar_express,
                    description = productItem.name,
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = productItem.name, style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = productItem.code,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                IconButton(
                    onClick = {
                        shoppingCartViewModel.addItemToCart(
                            productItem.code, productItem.name, productItem.price
                        )
                    },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

            val symbol = Currency.getInstance(Locale.getDefault()).symbol

            Text(
                text = if (productItem.price == BigDecimal.ZERO) {
                    String()
                } else {
                    stringResource(id = R.string.list_item_cost, symbol, productItem.price)
                },
                style = MaterialTheme.typography.bodyLarge,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
            )
            Text(
                text = productItem.name,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
