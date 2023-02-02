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
        List(viewModel.products) { aProduct in
            ProductListCell(product: aProduct)
        }
        // Hack to disable row selection and allow
        // the tap on inner buttons
        .onTapGesture { return }
        .navigationTitle(Text("Products list"))
        .toolbar {
            CartButtonView(tapAction: viewModel.openCart)
                .modifier(cartModifier)
        }
        .onAppear {
            viewModel.fetchProducts()
        }
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsListView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListView(
            viewModel: ProductsListViewModel.preview,
            cartModifier: SheetModifier(item: .constant(CartViewModel.preview)) { viewModel in
                CartView(viewModel: viewModel)
            }
        )
    }
}
#endif
