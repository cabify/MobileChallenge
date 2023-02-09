//
//  ProductsListCoordinator.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class ProductsListCoordinator: ObservableObject {
    
    final class ViewState: ObservableObject {
        enum State {
            case idle
            case loading
            case failed(Error)
            case loaded(cart: CartLayoutViewModel)
        }
        
        @Published private(set) var state: State
        
        init(state: State) {
            self.state = state
        }
        
        func setNew(_ state: State) {
            self.state = state
        }
    }
    
    // MARK: - Properties
    @Published private(set) var viewState: ViewState
    private(set) var productsViewModel: ProductsViewModel!
    private(set) var cartDetailViewModel: CartDetailViewModel?
    private let cartRepository: CartRepository
    
    init(productsListRepository: ProductsListRepository, cartRepository: CartRepository) {
        self.viewState = ViewState(state: .idle)
        self.cartRepository = cartRepository
        
        let defaultGetProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: productsListRepository)
        let defaultGetCartUseCase = DefaultGetCartUseCase(cartRepository: cartRepository)
        let defaultAddItemToCartUseCase = DefaultAddItemToCartUseCase(cartRepository: cartRepository)
        let defaultRemoveItemToCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: cartRepository)
        
        self.productsViewModel = ProductsViewModel(
            coordinator: self,
            getProductsListUseCase: defaultGetProductsListUseCase,
            getCartUseCase: defaultGetCartUseCase,
            addItemToCartUseCase: defaultAddItemToCartUseCase,
            removeItemToCartUseCase: defaultRemoveItemToCartUseCase
        )
    }
    
    func openCart() {
        let defaultAddItemToCartUseCase = DefaultAddItemToCartUseCase(cartRepository: cartRepository)
        let defaultRemoveItemToCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: cartRepository)
        let defaultClearCartUseCase = DefaultClearCartUseCase(cartRepository: cartRepository)
        
        self.cartDetailViewModel = CartDetailViewModel(
            coordinator: self,
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
