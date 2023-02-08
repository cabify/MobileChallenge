//
//  ProductsView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct ProductsView: View {
    
    // Actions
    enum Actions {
        case add(CartLayoutViewModel.CartItem)
        case remove(CartLayoutViewModel.CartItem)
        case clearCart
    }
    typealias ProductsViewActionBlock = ((Actions) -> Void)
    
    @ObservedObject var viewModel: ProductsViewModel
    
    var body: some View {
        switch viewModel.state {
        case .idle:
            // Render a clear color and start the loading process
            // when the view first appears, which should make the
            // view model transition into its loading state:
            Color.clear.onAppear(perform: viewModel.load)
            
        case .loading:
            ProgressView()
                .controlSize(.large)
                .tint(.purple)
            
        case .failed(let error):
            EmptyStateView(emptyType: .error(error))
            
        case .loaded(let cart):
            if cart.items.isEmpty {
                EmptyStateView(emptyType: .products)
                
            } else {
                ProductsListView(cart: cart) { anAction in
                    switch anAction {
                    case .add(let cartItem): self.viewModel.addItemToCart(cartItem)
                    case .remove(let cartItem): self.viewModel.removeItemFromCart(cartItem)
                    case .clearCart: break
                    }
                }
            }
        }
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsView(viewModel: ProductsViewModel.preview)
    }
}
#endif
