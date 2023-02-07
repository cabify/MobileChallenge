//
//  ProductsListView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct ProductsListView<CartModifier: ViewModifier>: View {
    
    @ObservedObject var viewModel: ProductsListViewModel
    let cartModifier: CartModifier
    
    var body: some View {
        LoadableContentView(source: viewModel) { products in
            List(products) { aCartItem in
                ProductListCell(
                    singleCartItem: aCartItem,
                    onIncreaseAction: {
                        self.viewModel.addItemToCart(aCartItem)
                        
                }, onDecreaseAction: {
                    self.viewModel.removeItemFromCart(aCartItem)
                })
            }
            // Hack to disable row selection to allow
            // the tap on inner buttons
            .onTapGesture { return }
        }
        .navigationTitle(Text("Products list"))
        .toolbar {
            CartButtonView(onTapAction: viewModel.openCart)
                .modifier(cartModifier)
        }
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsListView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListView(
            viewModel: ProductsListViewModel.preview,
            cartModifier: SheetModifier(item: .constant(CartDetailViewModel.preview)) { viewModel in
                CartDetailView(viewModel: viewModel)
            }
        )
    }
}
#endif
