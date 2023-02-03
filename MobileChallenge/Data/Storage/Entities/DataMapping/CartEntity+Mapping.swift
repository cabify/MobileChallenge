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
        let items = items?.compactMap({ item -> Cart.Item? in
            guard let anItem = item as? CartItemEntity else { return nil }
            return .init(code: Int(anItem.code), quantity: Int(anItem.quantity))
        }) ?? []
        
        return .init(createdAt: TimeInterval(integerLiteral: createdAt), items: items)
    }
}
