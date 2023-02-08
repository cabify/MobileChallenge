//
//  CartDetailViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class CartDetailViewModel: LoadableObject, Identifiable {
    
    typealias Output = CartViewModel
    
    private unowned let coordinator: ProductsListCoordinator
    private let getCartUseCase: GetCartUseCase
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    private let clearCartUseCase: ClearCartUseCase
    private var cancellables = Set<AnyCancellable>()
    @Published var state: LoadableState<CartViewModel> = .idle
    var emptyStateType: EmptyStateView.EmptyType { .cart }
    
    init(coordinator: ProductsListCoordinator, getCartUseCase: GetCartUseCase, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase, clearCartUseCase: ClearCartUseCase) {
        self.coordinator = coordinator
        self.getCartUseCase = getCartUseCase
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
        self.clearCartUseCase = clearCartUseCase
    }
    
    // MARK: - Fetch
    func load() {
        state = .loading
        
        getCartUseCase.getCart()
            .map { cart in
                let cart = CartViewModel(cart: cart)
                return .loaded(cart)
            }
            .catch { error in
                Just(LoadableState.failed(error))
            }
            .sink { [weak self] state in
                self?.state = state
            }
            .store(in: &cancellables)
    }
}

#if DEBUG
extension CartDetailViewModel {
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
