//
//  ProductsListViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class ProductsListViewModel: ObservableObject {
    
    private unowned let coordinator: ProductsListCoordinator
    @Published var isPresented: Bool = false
    
    init(coordinator: ProductsListCoordinator) {
        self.coordinator = coordinator
    }
    
    func openCart() -> CartViewModel {
        return self.coordinator.openCart()
    }
}

#if DEBUG
extension ProductsListViewModel {
    static var preview: Self {
        .init(coordinator: ProductsListCoordinator.preview, productsListUseCase: DefaultFetchProductsListUseCase.preview)
    }
}
#endif
