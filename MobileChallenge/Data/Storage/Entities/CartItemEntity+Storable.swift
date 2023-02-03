//
//  CartItemEntity+Storable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

extension CartItemEntity: Storable {
    static var predicateFormat: String {
        return "cart.createdAt = %@"
    }
    
    var primaryKey: String {
        return NSNumber(value: cart?.createdAt ?? Int64(Date().timeIntervalSince1970)).stringValue
    }
}
