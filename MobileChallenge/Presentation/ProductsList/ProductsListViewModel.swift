//
//  ProductsListViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Foundation
import Combine

final class ProductsListViewModel: LoadableObject {
    
    typealias Output = [SingleCartItemViewModel]
    
    private unowned let coordinator: ProductsListCoordinator
    private let getProductsListUseCase: GetProductsListUseCase
    private let getCartUseCase: GetCartUseCase
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    private var cancellables = Set<AnyCancellable>()
    @Published var state: LoadableState<[SingleCartItemViewModel]> = .idle
    var emptyStateType: EmptyStateView.EmptyType { .products }
    
    init(coordinator: ProductsListCoordinator, getProductsListUseCase: GetProductsListUseCase, getCartUseCase: GetCartUseCase, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase) {
        self.coordinator = coordinator
        self.getProductsListUseCase = getProductsListUseCase
        self.getCartUseCase = getCartUseCase
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
    }
    
    func load() {
        state = .loading
        
        productsListUseCase.getProductsList()
            .map { productList in
                let products = productList.products.compactMap({ SingleCartItem(product: $0) })
                return .loaded(products)
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
        
    }
}

#if DEBUG
extension ProductsListViewModel {
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
