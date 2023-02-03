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
    func addItem(_ item: Cart.Item, toCart cart: Cart) -> AnyPublisher<Cart, Error>
    func removeItem(_ item: Cart.Item, fromCart cart: Cart) -> AnyPublisher<Cart, Error>
    func clearCart(_ cart: Cart) -> AnyPublisher<Cart, Error>
}
