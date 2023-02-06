//
//  CartViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class CartViewModel: ObservableObject, Identifiable {
    
    private unowned let coordinator: ProductsListCoordinator
    private let getCartUseCase: GetCartUseCase
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    private let clearCartUseCase: ClearCartUseCase
    
    init(coordinator: ProductsListCoordinator, getCartUseCase: GetCartUseCase, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase, clearCartUseCase: ClearCartUseCase) {
        self.coordinator = coordinator
        self.getCartUseCase = getCartUseCase
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
        self.clearCartUseCase = clearCartUseCase
    }
}

#if DEBUG
extension CartViewModel {
    static var preview: Self {
        .init(
            coordinator: ProductsListCoordinator.preview,
            getCartUseCase: DefaultGetCartUseCase.preview,
            addItemToCartUseCase: DefaultAddItemToCartUseCase.preview,
            removeItemToCartUseCase: DefaultRemoveItemFromCartUseCase.preview,
            clearCartUseCase: DefaultClearCartUseCase.preview
        )
    }
}
#endif
