//
//  FetchProductsListUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 27/01/2023.
//

import Foundation
import Combine

protocol FetchProductsListUseCase {
    func getProductsList() -> AnyPublisher<ProductsList, Error>
}

final class DefaultFetchProductsListUseCase: FetchProductsListUseCase {
    
    private let productsListRepository: ProductsListRepository
    
    init(productsListRepository: ProductsListRepository) {
        self.productsListRepository = productsListRepository
    }
    
    func getProductsList() -> AnyPublisher<ProductsList, Error> {
        return productsListRepository.fetchProductsList()
    }
}

#if DEBUG
extension DefaultFetchProductsListUseCase {
    static var preview: Self {
        .init(productsListRepository: DefaultProductsListRepository.preview)
    }
}
#endif
