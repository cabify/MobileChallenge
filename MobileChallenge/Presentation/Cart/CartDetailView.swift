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
    @EnvironmentObject var viewState: ProductsListCoordinator.ViewState
    private let viewModel: CartDetailViewModel
    
    init(viewModel: CartDetailViewModel) {
        self.viewModel = viewModel
    }
    
    var body: some View {
        NavigationView {
            switch viewState.state {
            case .loaded(let cart):
                VStack(spacing: 10) {
                    CartSummaryView(cartViewModel: cart)
                        .accessibilityIdentifier(AccessibilityID.CartDetailView.Summary)
                    
                    if cart.cartItems.isEmpty {
                        EmptyStateView(emptyType: .cart, onRetryAction: dismiss.callAsFunction)
                            .accessibilityIdentifier(AccessibilityID.CartDetailView.EmptyCart)
                        
                    } else {
                        CartItemsView(cart: cart) { anAction in
                            switch anAction {
                            case .add(let cartItem): self.viewModel.addItemToCart(cartItem)
                            case .remove(let cartItem): self.viewModel.removeItemFromCart(cartItem)
                            case .clearCart: self.viewModel.clearCart()
                            }
                        }
                        .accessibilityIdentifier(AccessibilityID.CartDetailView.ItemList)
                    }
                }
                .navigationTitle(AccessibilityID.Navigation.Cart)
                .navigationBarTitleDisplayMode(.inline)
                .toolbar() {
                    Button("Close", action: closeCart)
                    .tint(.purple)
                    .accessibilityIdentifier(AccessibilityID.CartDetailView.CloseButton)
                }
                // Hack to disable row selection to allow
                // the tap on inner buttons
                .onTapGesture { return }
                
            default:
                EmptyStateView(emptyType: .cart, onRetryAction: dismiss.callAsFunction)
            }
        }
    }
    
    private func closeCart() {
        dismiss.callAsFunction()
        self.viewModel.closeCart()
    }
}

// MARK: - Preview
#if DEBUG && TESTING
struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        CartDetailView(viewModel: CartDetailViewModel.preview)
    }
}
#endif
