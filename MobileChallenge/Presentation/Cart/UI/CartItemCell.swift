//
//  CartItemCell.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import SwiftUI

struct CartItemCell: View {
    
    // Properties
    private let cartItem: CartLayoutViewModel.CartItem
    private let onChangeQuantityAction: ProductsView.ProductsViewActionBlock
    
    init(cartItem: CartLayoutViewModel.CartItem, onChangeQuantityAction: @escaping ProductsView.ProductsViewActionBlock) {
        self.cartItem = cartItem
        self.onChangeQuantityAction = onChangeQuantityAction
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            // Product name and discount badge
            HStack(alignment: .center) {
                // Name
                Text(cartItem.name)
                    .font(.system(size: 16, weight: .medium))
                    .foregroundColor(.black)
                
                Spacer()
                
                // Badge
                if let badgeText = cartItem.productType.discountBadgeText {
                    DiscountBadgeView(badgeText: badgeText)
                        .frame(alignment: .trailing)
                }
            }
            // Cart counting and price
            HStack(alignment: .center) {
                // Cart counting
                CartQuantityView(
                    cartItem: cartItem,
                    onChangeQuantityAction: onChangeQuantityAction
                )
                
                Spacer()
                
                // Product price
                PriceView(
                    price: cartItem.formattedTotalPrice,
                    specialPrice: cartItem.formattedTotalPriceWithDiscounts,
                    inline: true
                )
                .frame(minWidth: 60, alignment: .trailing)
            }
        }
    }
}

// MARK: - Preview
#if DEBUG && TESTING
struct CartItemCell_Previews: PreviewProvider {
    static var previews: some View {
        List(CartLayoutViewModel.CartItem.cartItemsPreview) { aCartItem in
            CartItemCell(
                cartItem: aCartItem,
                onChangeQuantityAction: { _ in }
            )
        }
    }
}
#endif
