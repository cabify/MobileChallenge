//
//  MockedCartRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Foundation
import Combine

final class MockedCartRepository {
    
    static let mockedModel: Cart = .init(items: [
        .init(code: 0, name: "Cabify Voucher", quantity: 0, price: 5),
        .init(code: 1, name: "Cabify T-Shirt", quantity: 0, price: 20),
        .init(code: 2, name: "Cabify Coffee Mug", quantity: 0, price: 7.5)
    ])
    
    private var mockedCart: Cart!
    private let error: LocalizedError?
    
    init(error: LocalizedError? = nil) {
        self.mockedCart = MockedCartRepository.mockedModel
        self.error = error
    }
}

extension MockedCartRepository: CartRepository {
    
    func getCart() -> AnyPublisher<Cart, Error> {
        Future { promise in
            promise(.success(MockedCartRepository.mockedModel))
        }
        .eraseToAnyPublisher()
    }
    
    private func updateItem(_ item: Cart.Item, increase: Bool = true) -> AnyPublisher<Int, Error> {
        Future { promise in
            
            guard let updatedItem = self.mockedCart.items.first(where: { $0.code == item.code }) else {
                return promise(.failure(TestsError.itemNotFound))
            }
            
            var newQuantity = updatedItem.quantity + 1
            if !increase {
                newQuantity = updatedItem.quantity - 1
                
                if newQuantity < 0 {
                    return promise(.failure(TestsError.insufficientQuantity))
                }
            }
            
            let updatedItems = self.mockedCart.items.enumerated().compactMap { anIndex, anItem -> Cart.Item in
                if anItem.code == updatedItem.code {
                    return .init(code: anItem.code, name: anItem.name, quantity: newQuantity, price: anItem.price)
                }
                    
                return .init(code: anItem.code, name: anItem.name, quantity: anItem.quantity, price: anItem.price)
            }
            self.mockedCart = Cart(items: updatedItems)
            promise(.success(newQuantity))
        }
        .eraseToAnyPublisher()
    }
    
    func addItem(_ item: Cart.Item) -> AnyPublisher<Int, Error> {
        return updateItem(item)
            .eraseToAnyPublisher()
    }
    
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Int, Error> {
        return updateItem(item, increase: false)
            .eraseToAnyPublisher()
    }
    
    func clearCart() -> AnyPublisher<Cart, Error> {
        Future { promise in
            let cartItems = self.mockedCart.items.compactMap {
                Cart.Item(code: $0.code, name: $0.name, quantity: 0, price: $0.price)
            }
            promise(.success(Cart(items: cartItems)))
        }
        .eraseToAnyPublisher()
    }
}
