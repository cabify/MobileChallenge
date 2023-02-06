//
//  CartRepository.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine

protocol CartRepository {
    @discardableResult
    func addItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error>
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error>
    func clearCart() -> AnyPublisher<Cart, Error>
}
