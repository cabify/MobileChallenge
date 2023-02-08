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
    @ObservedObject var viewModel: ProductsViewModel
    
    var body: some View {
        NavigationView {
            switch viewModel.state {
            case .loaded(let cart):
                VStack(spacing: 10) {
                    CartSummaryView(cartViewModel: cart)
                    
                    if !cart.items.isEmpty {
                        Section(content: {
                            List(cart.items) { aCartItem in
                                CartItemCell(
                                    cartItemViewModel: aCartItem,
                                    onIncreaseAction: {
                                        self.viewModel.addItemToCart(aCartItem)
                                        
                                    }, onDecreaseAction: {
                                        self.viewModel.removeItemFromCart(aCartItem)
                                    }
                                )
                            }
                        }, header: {
                            PrimaryButtonView(buttonText: "Clear cart", onTapAction: {
                                self.viewModel.clearCart()
                            })
                        })
                        
                    } else {
                        EmptyStateView(emptyType: .cart, onRetryAction: {
                            dismiss()
                        })
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
                
            default:
                EmptyStateView(emptyType: .cart, onRetryAction: {
                    dismiss()
                })
            }
        }
    }
}

// MARK: - Preview
#if DEBUG
struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        CartDetailView(viewModel: ProductsViewModel.preview)
    }
}
#endif
