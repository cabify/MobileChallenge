//
//  ProductListCell.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct ProductListCell: View {
    
    // Properties
    var cartItemViewModel: CartLayoutViewModel.CartItem
    var onIncreaseAction: () -> Void
    var onDecreaseAction: () -> Void
    
    var body: some View {
        HStack(alignment: .firstTextBaseline, spacing: 10) {
            // Product name, badge and cart counting
            VStack(alignment: .leading, spacing: 5) {
                // Product name and cart counting
                HStack {
                    // Name
                    Text(cartItemViewModel.name)
                        .font(.system(size: 16, weight: .medium))
                        .foregroundColor(.black)
                    
                    Spacer()
                    
                    // Cart counting
                    CartQuantityView(
                        cartQuantity: cartItemViewModel.cartQuantity,
                        onIncreaseAction: onIncreaseAction,
                        onDecreaseAction: onDecreaseAction
                    )
                }
                
                // Badge
                if let badgeText = cartItemViewModel.productType.discountBadgeText {
                    DiscountBadgeView(badgeText: badgeText)
                }
            }
            
            // Product price
            PriceView(price: cartItemViewModel.formattedPrice, specialPrice: cartItemViewModel.formattedSpecialPrice)
                .frame(minWidth: 60, alignment: .trailing)
        }
        .padding(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 0))
    }
}

// MARK: - Preview
#if DEBUG
struct ProductListCell_Previews: PreviewProvider {
    static var previews: some View {
        List(CartLayoutViewModel.CartItem.productsListPreview) { aCartItem in
            ProductListCell(
                cartItemViewModel: aCartItem,
                onIncreaseAction: { },
                onDecreaseAction: { }
            )
        }
    }
}
#endif
