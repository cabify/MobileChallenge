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
                    
                    if cart.items.isEmpty {
                        EmptyStateView(emptyType: .cart) {
                            dismiss()
                        }
                        
                    } else {
                        CartItemsView(cart: cart) { anAction in
                            switch anAction {
                            case .add(let cartItem): self.viewModel.addItemToCart(cartItem)
                            case .remove(let cartItem): self.viewModel.removeItemFromCart(cartItem)
                            case .clearCart: self.viewModel.clearCart()
                            }
                        }
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
