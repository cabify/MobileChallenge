//
//  CartItemCell.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import SwiftUI

struct CartItemCell: View {
    
    // Properties
    var cartItemViewModel: CartItemViewModel
    var onIncreaseAction: () -> Void
    var onDecreaseAction: () -> Void
    
    var body: some View {
        VStack(alignment: .leading, spacing: 20) {
            // Product name and discount badge
            HStack(alignment: .center) {
                // Name
                Text(cartItemViewModel.name)
                    .font(.system(size: 16, weight: .medium))
                    .foregroundColor(.black)
                
                Spacer()
                
                // Badge
                if let badgeText = cartItemViewModel.productType.discountBadgeText {
                    DiscountBadgeView(badgeText: badgeText)
                        .frame(alignment: .trailing)
                }
            }
            // Cart counting and price
            HStack(alignment: .center) {
                // Cart counting
                CartQuantityView(
                    cartQuantity: cartItemViewModel.cartQuantity,
                    onIncreaseAction: onIncreaseAction,
                    onDecreaseAction: onDecreaseAction
                )
                
                Spacer()
                
                // Product price
                PriceView(
                    price: cartItemViewModel.formattedTotalPrice,
                    specialPrice: cartItemViewModel.formattedTotalPriceWithDiscounts,
                    inline: true
                )
                .frame(minWidth: 60, alignment: .trailing)
            }
        }
        .padding(EdgeInsets(top: 20, leading: 0, bottom: 20, trailing: 0))
    }
}

// MARK: - Preview
#if DEBUG
struct CartItemCell_Previews: PreviewProvider {
    static var previews: some View {
        List(CartItemViewModel.preview) { aCartItem in
            CartItemCell(
                cartItemViewModel: aCartItem,
                onIncreaseAction: { },
                onDecreaseAction: { }
            )
        }
    }
}
#endif
