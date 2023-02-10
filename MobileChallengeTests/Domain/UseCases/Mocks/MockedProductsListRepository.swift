//
//  MockedProductsListRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Combine

final class MockedProductsListRepository {
    
    private let error: Error?
    
    init(error: Error? = nil) {
        self.error = error
    }
}

extension MockedProductsListRepository: ProductsListRepository {
    func fetchProductsList() -> AnyPublisher<ProductsList, Error> {
        Future { promise in
            if let anError = self.error {
                promise(.failure(anError))
            } else {
                promise(.success(ProductsList.preview))
            }
        }
        .eraseToAnyPublisher()
    }
}
