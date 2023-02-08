//
//  ProductsListView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import SwiftUI

struct ProductsListView: View {
    
    // Properties
    var cart: CartLayoutViewModel
    var onChangeQuantityAction: ProductsView.ProductsViewActionBlock
    
    var body: some View {
        List(cart.items) { aCartItem in
            ProductListCell(
                cartItem: aCartItem,
                onChangeQuantityAction: onChangeQuantityAction
            )
        }
        // Hack to disable row selection to allow
        // the tap on inner buttons
        .onTapGesture { return }
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsListView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListView(
            cart: CartLayoutViewModel.preview,
            onChangeQuantityAction: { _ in }
        )
    }
}
#endif
