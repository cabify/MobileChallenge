//
//  CartViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class CartViewModel: ObservableObject, Identifiable {
    
    private unowned let coordinator: ProductsListCoordinator
    
    init(coordinator: ProductsListCoordinator) {
        self.coordinator = coordinator
    }
}

#if DEBUG
extension CartViewModel {
    static var preview: Self {
        .init(coordinator: ProductsListCoordinator.preview)
    }
}
#endif
