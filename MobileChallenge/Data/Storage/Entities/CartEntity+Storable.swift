//
//  CartEntity+Storable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

extension CartEntity: Storable {
    static var predicateFormat: String {
        return "createdAt = %@"
    }
    
    var primaryKey: String {
        return NSNumber(value: createdAt).stringValue
    }
}
