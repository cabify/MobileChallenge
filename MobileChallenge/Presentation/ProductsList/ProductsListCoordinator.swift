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
    
    init() {
        self.productsListViewModel = ProductsListViewModel(coordinator: self)
    }
    
    func openCart() -> CartViewModel {
        return CartViewModel(coordinator: self)
    }
}
