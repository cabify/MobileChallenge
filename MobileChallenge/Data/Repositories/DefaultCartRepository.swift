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
    
    init(backgroundContext: NSManagedObjectContext) {
        self.cartRepository = CoreDataRepository<CartEntity>(backgroundContext: backgroundContext)
        self.cartItemRepository = CoreDataRepository<CartItemEntity>(backgroundContext: backgroundContext)
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
                .compactMap { Int($0.quantity) }
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
            .flatMap { cartEntity in
                let updatedItems = cartEntity.itemEntities
                updatedItems.forEach { $0.quantity = Int16(0) }
                
                return self.cartRepository.update(cartEntity, body: { updatedCart in
                    updatedCart.items = NSSet(array: updatedItems)
                })
                .eraseToAnyPublisher()
                .compactMap { $0.domainObject }
            }
            .eraseToAnyPublisher()
    }
}

#if DEBUG && TESTING
extension DefaultCartRepository {
    static var preview: Self {
        .init(mainContext: CoreDataStorage.preview.backgroundContext)
    }
}
#endif

