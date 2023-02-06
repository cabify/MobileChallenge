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
        return .init(createdAt: TimeInterval(integerLiteral: createdAt), items: items)
    }
    
    var itemEntities: [CartItemEntity] {
        guard let itemEntities = Array(arrayLiteral: items) as? [CartItemEntity] else { return [] }
        return itemEntities
    }
}

extension CartItemEntity {
    var domainObject: Cart.Item {
        return .init(code: Int(code), quantity: Int(quantity))
    }
}
