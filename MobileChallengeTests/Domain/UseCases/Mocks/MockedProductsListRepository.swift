//
//  MockedProductsListRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Foundation
import Combine

final class MockedProductsListRepository {
    
    private let error: LocalizedError?
    
    init(error: LocalizedError? = nil) {
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
