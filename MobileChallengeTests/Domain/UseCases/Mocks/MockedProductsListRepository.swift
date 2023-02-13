//
//  MockedProductsListRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Foundation
import Combine

final class MockedProductsListRepository {
    
    static let productsList: ProductsList = .init(products: [
        .init(code: "VOUCHER", name: "Cabify Voucher", price: 5),
        .init(code: "TSHIRT", name: "Cabify T-Shirt", price: 20),
        .init(code: "MUG", name: "Cabify Coffee Mug", price: 7.5)
    ])
    
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
                promise(.success(MockedProductsListRepository.productsList))
            }
        }
        .eraseToAnyPublisher()
    }
}
