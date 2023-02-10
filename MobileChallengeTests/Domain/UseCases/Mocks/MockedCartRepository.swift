//
//  MockedCartRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Foundation
import Combine

final class MockedCartRepository { }

extension MockedCartRepository: CartRepository {
    
    func getCart() -> AnyPublisher<Cart, Error> {
        Future { promise in
            promise(.success(Cart.preview))
        }
        .eraseToAnyPublisher()
    }
    
    func addItem(_ item: Cart.Item) -> AnyPublisher<Int, Error> {
        Future { promise in
            if let anError = self.error {
                promise(.failure(anError))
                
            } else {
                promise(.success(item.quantity + 1))
            }
        }
        .eraseToAnyPublisher()
    }
    
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Int, Error> {
        Future { promise in
            if let anError = self.error {
                promise(.failure(anError))
                
            } else if item.quantity > 0 {
                promise(.success(item.quantity - 1))
                
            } else {
                promise(.failure(TestsError.insufficientQuantity))
            }
        }
        .eraseToAnyPublisher()
    }
    
    func clearCart() -> AnyPublisher<Cart, Error> {
        Future { promise in
            if let anError = self.error {
                promise(.failure(anError))
                
            } else {
                let cartItems = Cart.preview.items.compactMap {
                    Cart.Item(code: $0.code, name: $0.name, quantity: 0, price: $0.price)
                }
                promise(.success(Cart(items: cartItems)))
            }
        }
        .eraseToAnyPublisher()
    }
}
