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
    func getCart() -> AnyPublisher<Cart, Error>
    func addItem(_ item: Cart.Item) -> AnyPublisher<Int, Error>
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Int, Error>
    func clearCart() -> AnyPublisher<Cart, Error>
}
