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
        var items: [Cart.Item] = []
        
        if let setItems = Array(arrayLiteral: items) as? [CartItemEntity] {
            items = setItems.compactMap({ $0.domainObject })
        }
        
        return .init(createdAt: TimeInterval(integerLiteral: createdAt), items: items)
    }
}

extension CartItemEntity {
    var domainObject: Cart.Item {
        return .init(code: Int(code), quantity: Int(quantity))
    }
}
