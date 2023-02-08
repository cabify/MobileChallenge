//
//  CartDetailView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct CartDetailView: View {
    
    @Environment(\.dismiss) var dismiss
    @ObservedObject var viewModel: CartDetailViewModel
    
    var body: some View {
        LoadableContentView(source: viewModel) { cart in
            NavigationView {
                VStack {
                    CartSummaryView(cartViewModel: cart)
                    
                    List(cart.items) { aCartItem in
                        CartItemCell(
                            cartItemViewModel: aCartItem,
                            onIncreaseAction: {
                                // self.viewModel.addItemToCart(aCartItem)
                                
                            }, onDecreaseAction: {
                                // self.viewModel.removeItemFromCart(aCartItem)
                            }
                        )
                    }
                }
                .navigationTitle("Place your order")
                .navigationBarTitleDisplayMode(.inline)
                .toolbar() {
                    Button("Close") {
                        dismiss()
                    }
                    .tint(.purple)
                }
                // Hack to disable row selection to allow
                // the tap on inner buttons
                .onTapGesture { return }
            }
        }
    }
}

// MARK: - Preview
#if DEBUG
struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        CartDetailView(viewModel: CartDetailViewModel.preview)
    }
}
#endif
