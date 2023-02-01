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
    
    init(productsListRepository: DefaultProductsListRepository) {
        let productsListUseCase = DefaultFetchProductsListUseCase(productsListRepository: productsListRepository)
        self.productsListViewModel = ProductsListViewModel(coordinator: self, productsListUseCase: productsListUseCase)
    }
    
    func openCart() -> CartViewModel {
        return CartViewModel(coordinator: self)
    }
}
