//
//  CartEntity.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

// MARK: - Mapping database entity to domain
extension CartEntity {
    var domainObject: Cart {
        let items: [Cart.Item] = self.itemEntities.compactMap { $0.domainObject }
        return .init(items: items)
    }
    
    var itemEntities: [CartItemEntity] {
        guard let itemEntities = items?.allObjects as? [CartItemEntity] else { return [] }
        return itemEntities.sorted(by: { $0.code < $1.code })
    }
}

extension CartItemEntity {
    var domainObject: Cart.Item {
        return .init(code: Int(code), name: name!, quantity: Int(quantity), price: price)
    }
}
