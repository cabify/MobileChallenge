//
//  ProductsListCoordinator.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class ProductsListCoordinator: ObservableObject {
    
    // MARK: - Properties
    @Published private(set) var productsListViewModel: ProductsListViewModel!
    @Published var cartViewModel: CartViewModel?
    private let cartRepository: CartRepository
    
    init(productsListRepository: ProductsListRepository, cartRepository: CartRepository) {
        self.cartRepository = cartRepository
        
        let addItemToCartUseCase = DefaultAddItemToCartUseCase(cartRepository: cartRepository)
        let removeItemToCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: cartRepository)
        let defaultGetProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: productsListRepository)
        
        self.productsListViewModel = ProductsListViewModel(
            coordinator: self,
            addItemToCartUseCase: addItemToCartUseCase,
            removeItemToCartUseCase: removeItemToCartUseCase
        )
    }
    
    func openCart() {
        self.cartViewModel = CartViewModel(
            coordinator: self,
            getCartUseCase: DefaultGetCartUseCase(cartRepository: cartRepository),
            addItemToCartUseCase: DefaultAddItemToCartUseCase(cartRepository: cartRepository),
            removeItemToCartUseCase: DefaultRemoveItemFromCartUseCase(cartRepository: cartRepository),
            clearCartUseCase: DefaultClearCartUseCase(cartRepository: cartRepository)
        )
    }
    
    func closeCart() {
        self.cartViewModel = nil
    }
}

#if DEBUG
extension ProductsListCoordinator {
    static var preview: Self {
        .init(productsListRepository: DefaultProductsListRepository.preview, cartRepository: DefaultCartRepository.preview)
    }
}
#endif
