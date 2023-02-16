//
//  CartDetailViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import Foundation
import Combine

final class CartDetailViewModel: ObservableObject {
    
    private unowned let coordinator: ProductsListCoordinator
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    private let clearCartUseCase: ClearCartUseCase
    private var cancellables = Set<AnyCancellable>()
    
    init(coordinator: ProductsListCoordinator, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase, clearCartUseCase: ClearCartUseCase) {
        self.coordinator = coordinator
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
        self.clearCartUseCase = clearCartUseCase
    }
    
    func closeCart() {
        self.coordinator.closeCart()
    }
    
    // MARK: - Add/remove items
    private func updateItem(_ item: CartLayoutViewModel.CartItem, newCartQuantity: Int) {
        guard case .loaded(let cart) = self.coordinator.viewState.state else { return }
        
        var updatedCart = cart
        updatedCart.updateItem(item, newCartQuantity: newCartQuantity)
        
        self.coordinator.viewState.setNew(.loaded(cart: updatedCart))
    }
    
    func addItemToCart(_ item: CartLayoutViewModel.CartItem) {
        addItemToCartUseCase.addItem(item.domainObject)
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] newQuantity in
                self?.updateItem(item, newCartQuantity: newQuantity)
            })
            .store(in: &cancellables)
    }
    
    func removeItemFromCart(_ item: CartLayoutViewModel.CartItem) {
        removeItemToCartUseCase.removeItem(item.domainObject)
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] newQuantity in
                self?.updateItem(item, newCartQuantity: newQuantity)
            })
            .store(in: &cancellables)
    }
    
    func clearCart() {
        clearCartUseCase.clearCart()
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] clearedCart in
                self?.coordinator.viewState.setNew(.loaded(cart: .init(cart: clearedCart)))
            })
            .store(in: &cancellables)
    }
}

#if DEBUG && TESTING
extension CartDetailViewModel {
    static var preview: Self {
        .init(
            coordinator: ProductsListCoordinator.preview,
            addItemToCartUseCase: DefaultAddItemToCartUseCase.preview,
            removeItemToCartUseCase: DefaultRemoveItemFromCartUseCase.preview,
            clearCartUseCase: DefaultClearCartUseCase.preview
        )
    }
}
#endif
