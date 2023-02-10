//
//  GetProductsListUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 27/01/2023.
//

import Foundation
import Combine

protocol GetProductsListUseCase {
    func getProductsList() -> AnyPublisher<ProductsList, Error>
}

final class DefaultGetProductsListUseCase: GetProductsListUseCase {
    
    private let productsListRepository: ProductsListRepository
    
    init(productsListRepository: ProductsListRepository) {
        self.productsListRepository = productsListRepository
    }
    
    func getProductsList() -> AnyPublisher<ProductsList, Error> {
        return productsListRepository.fetchProductsList()
    }
}

#if DEBUG && TESTING
extension DefaultGetProductsListUseCase {
    static var preview: Self {
        .init(productsListRepository: DefaultProductsListRepository.preview)
    }
}
#endif
