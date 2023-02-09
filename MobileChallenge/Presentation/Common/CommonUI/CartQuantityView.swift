//
//  CartQuantityView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct CartQuantityView: View {
    
    var cartItem: CartLayoutViewModel.CartItem
    @State private var cartQuantity: Int
    var onChangeQuantityAction: ProductsView.ProductsViewActionBlock
    
    init(cartItem: CartLayoutViewModel.CartItem, onChangeQuantityAction: @escaping ProductsView.ProductsViewActionBlock) {
        self.cartItem = cartItem
        self.onChangeQuantityAction = onChangeQuantityAction
        _cartQuantity = State(initialValue: cartItem.cartQuantity)
    }
    
    var body: some View {
        VStack(alignment: .trailing) {
            HStack {
                // Minus button
                quantityButton(imageNamed: "minus.rectangle") {
                    onChangeQuantityAction(.remove(cartItem))
                    cartQuantity -= 1
                }.disabled(cartQuantity <= 0)
                
                // Current quantity
                Text("\(cartItem.cartQuantity)")
                    .font(.system(size: 16))
                
                // Plus button
                quantityButton(imageNamed: "plus.rectangle") {
                    onChangeQuantityAction(.add(cartItem))
                    cartQuantity += 1
                }
            }
        }
        .buttonStyle(BorderlessButtonStyle())
    }
    
    @ViewBuilder
    private func quantityButton(imageNamed: String, onTap: @escaping () -> Void) -> some View {
        Button {
            onTap()
        } label: {
            Image(systemName: imageNamed)
        }
        .tint(.black)
    }
}

// MARK: - Preview
#if DEBUG
struct CartQuantityView_Previews: PreviewProvider {
    static var previews: some View {
        List(CartLayoutViewModel.CartItem.cartItemsPreview) { aCartItem in
            CartQuantityView(
                cartItem: aCartItem,
                onChangeQuantityAction: { _ in }
            )
        }
    }
}
#endif
