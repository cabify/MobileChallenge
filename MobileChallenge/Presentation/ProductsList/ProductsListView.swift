//
//  ProductsListView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct ProductsListView: View {
    
    @ObservedObject var viewModel: ProductsListViewModel
    
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
            List(cart.items) { aCartItem in
                ProductListCell(
                    cartItemViewModel: aCartItem,
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
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsListView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListView(viewModel: ProductsListViewModel.preview)
    }
}
#endif
