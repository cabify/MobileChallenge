//
//  DefaultCartRepository.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine
import CoreData

final class DefaultCartRepository {
    
    private let cartRepository: CoreDataRepository<CartEntity>
    private let cartItemRepository: CoreDataRepository<CartItemEntity>
    
    init(context: NSManagedObjectContext) {
        self.cartRepository = CoreDataRepository<CartEntity>(context: context)
        self.cartItemRepository = CoreDataRepository<CartItemEntity>(context: context)
    }
}

extension DefaultCartRepository: CartRepository {
    
    private func cartOrNew() -> AnyPublisher<CartEntity, Error> {
        return cartRepository.fetch()
            .flatMap { Just($0.first) }
            .flatMap { cartEntity in
                self.cartRepository.create(cartEntity)
            }
            .eraseToAnyPublisher()
    }
    
    func getCart() -> AnyPublisher<Cart, Error> {
        return cartOrNew()
            .flatMap { Just($0.domainObject) }
            .eraseToAnyPublisher()
    }
    
    private func cartItemOrNew(cartEntity: CartEntity, item: Cart.Item) -> AnyPublisher<CartItemEntity, Error> {
        let predicate = NSPredicate(format: "self.cart == %@ AND self.code == %d", cartEntity, item.code)
        return cartItemRepository.fetch(predicate: predicate)
            .flatMap { Just($0.first) }
            .flatMap { cartItemQuantity in
                self.cartItemRepository.create(cartItemQuantity, body: { newItem in
                    newItem.cart = cartEntity
                    newItem.code = Int16(item.code)
                    newItem.name = item.name
                    newItem.price = item.price
                    cartEntity.addToItems(newItem)
                })
            }
            .eraseToAnyPublisher()
    }
    
    private func updateItem(_ item: Cart.Item, increase: Bool = true) -> AnyPublisher<Int, Error> {
        return cartOrNew()
            .flatMap { cartEntity in
                self.cartItemOrNew(cartEntity: cartEntity, item: item)
            }
            .flatMap { cartItemEntity in
                self.cartItemRepository.update(cartItemEntity, body: { updatedItem in
                    if increase {
                        updatedItem.quantity += 1
                    } else {
                        updatedItem.quantity -= 1
                    }
                })
                .compactMap { Int($0.quantity)}
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
        return cartOrNew()
            .compactMap { cartEntity in
                cartEntity.itemEntities.forEach { _ = self.cartItemRepository.delete($0) }
                cartEntity.items = nil
                
                _ = self.cartRepository.update(cartEntity)
                return cartEntity.domainObject
            }
            .eraseToAnyPublisher()
    }
}

#if DEBUG
extension DefaultCartRepository {
    static var preview: Self {
        .init(context: CoreDataStorage.preview.context!)
    }
}
#endif

