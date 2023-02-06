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
    
    private func addNewItem(_ item: Cart.Item, toCart cartEntity: CartEntity) -> AnyPublisher<Cart, Error> {
        return cartItemRepository.create { newItem in
            newItem.cart = cartEntity
            newItem.code = Int16(item.code)
            newItem.quantity = Int16(item.quantity)
            
            cartEntity.addToItems(newItem)
        }
        .tryMap { _ in cartEntity.domainObject }
        .eraseToAnyPublisher()
    }
    
    private func updateItem(_ item: Cart.Item, increase: Bool = true) -> AnyPublisher<Cart, Error> {
        return cartOrNew()
            .compactMap { cartEntity -> Cart in
                if let existingItem = cartEntity.itemEntities.first(where: { $0.code == item.code }) {
                    if increase {
                        existingItem.quantity += 1
                    } else {
                        existingItem.quantity -= 1
                    }
                    _ = self.cartItemRepository.update(existingItem)
                    
                } else if increase {
                    _ = self.cartItemRepository.create(body: { newItem in
                        newItem.cart = cartEntity
                        newItem.code = Int16(item.code)
                        newItem.quantity = 1
                        
                        cartEntity.addToItems(newItem)
                        _ = self.cartRepository.update(cartEntity)
                    })
                }
                
                return cartEntity.domainObject
            }
            .eraseToAnyPublisher()
    }
    
    func addItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error> {
        return updateItem(item)
            .eraseToAnyPublisher()
    }
    
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error> {
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

