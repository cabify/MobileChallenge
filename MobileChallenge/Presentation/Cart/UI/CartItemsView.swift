//
//  CartItemsView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import SwiftUI

struct CartItemsView: View {
    
    // Properties
    var cart: CartLayoutViewModel
    var onChangeQuantityAction: ProductsView.ProductsViewActionBlock
    
    var body: some View {
        Section(content: {
            List(cart.items) { aCartItem in
                CartItemCell(
                    cartItem: aCartItem,
                    onChangeQuantityAction: onChangeQuantityAction
                )
            }
            // Hack to disable row selection to allow
            // the tap on inner buttons
            .onTapGesture { return }
            
        }, header: {
            PrimaryButtonView(buttonText: "Clear cart", onTapAction: {
                onChangeQuantityAction(.clearCart)
            })
        })
    }
}

// MARK: - Preview
#if DEBUG
struct CartItemsView_Previews: PreviewProvider {
    static var previews: some View {
        CartItemsView(
            cart: CartLayoutViewModel.preview,
            onChangeQuantityAction: { _ in }
        )
    }
}
#endif
