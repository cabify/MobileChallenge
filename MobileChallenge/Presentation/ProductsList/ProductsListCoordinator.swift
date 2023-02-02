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
    
    init(productsListRepository: DefaultProductsListRepository) {
        let productsListUseCase = DefaultFetchProductsListUseCase(productsListRepository: productsListRepository)
        self.productsListViewModel = ProductsListViewModel(coordinator: self, productsListUseCase: productsListUseCase)
    }
    
    func openCart() {
        self.cartViewModel = CartViewModel(coordinator: self)
    }
    
    func closeCart() {
        self.cartViewModel = nil
    }
}

#if DEBUG
extension ProductsListCoordinator {
    static var preview: Self {
        .init(productsListRepository: DefaultProductsListRepository.preview)
    }
}
#endif
