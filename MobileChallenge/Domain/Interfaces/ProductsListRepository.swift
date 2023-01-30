//
//  ProductsListRepository.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation
import Combine

protocol ProductsListRepository {
    @discardableResult
    func fetchProductsList() -> AnyPublisher<ProductsList, Error>
}
