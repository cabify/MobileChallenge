//
//  ProductsViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Foundation
import Combine

final class ProductsViewModel: ObservableObject {
    
    enum State {
        case idle
        case loading
        case failed(Error)
        case loaded(cart: CartLayoutViewModel, filteredItems: [CartLayoutViewModel]? = nil)
    }
    
    private unowned let coordinator: ProductsListCoordinator
    private let getProductsListUseCase: GetProductsListUseCase
    private let getCartUseCase: GetCartUseCase
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    @Published private(set) var state = State.idle
    private var cancellables = Set<AnyCancellable>()
    
    init(coordinator: ProductsListCoordinator, getProductsListUseCase: GetProductsListUseCase, getCartUseCase: GetCartUseCase, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase) {
        self.coordinator = coordinator
        self.getProductsListUseCase = getProductsListUseCase
        self.getCartUseCase = getCartUseCase
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
    }
    
    // MARK: - Fetch
    func load() {
        state = .loading
        
        getCartUseCase.getCart()
            .flatMap { cart in
                self.getProductsListUseCase.getProductsList()
                    .map { productList in
                        let products = productList.products.compactMap({ product -> Cart.Item? in
                            guard let productType = ProductType.init(code: product.code) else { return nil }
                            let cartQuantity = cart.items.first(where: { cartItem in cartItem.code == productType.intValue })?.quantity ?? 0
                            return Cart.Item(code: productType.intValue, name: product.name, quantity: cartQuantity, price: product.price)
                        })
                        return .loaded(cart: CartLayoutViewModel(cart: Cart(items: products)))
                    }
            }
            .catch { error in
                Just(.failed(error))
            }
            .sink { [weak self] state in
                self?.state = state
            }
            .store(in: &cancellables)
    }
    
    // MARK: - Add/remove items
    private func updateItem(_ item: CartLayoutViewModel.CartItem, newCartQuantity: Int) {
        guard case .loaded(let cart) = self.state else { return }
        
        var updatedCart = cart
        updatedCart.updateItem(item, newCartQuantity: newCartQuantity)
        
        self.state = .loaded(cart: updatedCart)
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
}

#if DEBUG
extension ProductsViewModel {
    static var preview: Self {
        .init(
            coordinator: ProductsListCoordinator.preview,
            getProductsListUseCase: DefaultGetProductsListUseCase.preview,
            getCartUseCase: DefaultGetCartUseCase.preview,
            addItemToCartUseCase: DefaultAddItemToCartUseCase.preview,
            removeItemToCartUseCase: DefaultRemoveItemFromCartUseCase.preview
        )
    }
}
#endif
