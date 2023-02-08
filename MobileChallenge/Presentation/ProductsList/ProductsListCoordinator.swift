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
    
    init(productsListRepository: ProductsListRepository, cartRepository: CartRepository) {
        let defaultGetProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: productsListRepository)
        let defaultGetCartUseCase = DefaultGetCartUseCase(cartRepository: cartRepository)
        let defaultAddItemToCartUseCase = DefaultAddItemToCartUseCase(cartRepository: cartRepository)
        let defaultRemoveItemToCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: cartRepository)
        let defaultClearCartUseCase = DefaultClearCartUseCase(cartRepository: cartRepository)
        
        self.productsListViewModel = ProductsListViewModel(
            coordinator: self,
            getProductsListUseCase: defaultGetProductsListUseCase,
            getCartUseCase: defaultGetCartUseCase,
            addItemToCartUseCase: defaultAddItemToCartUseCase,
            removeItemToCartUseCase: defaultRemoveItemToCartUseCase,
            clearCartUseCase: defaultClearCartUseCase
        )
    }
}

#if DEBUG
extension ProductsListCoordinator {
    static var preview: Self {
        .init(productsListRepository: DefaultProductsListRepository.preview, cartRepository: DefaultCartRepository.preview)
    }
}
#endif
