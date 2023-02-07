//
//  ProductType.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

// Product types
enum ProductType: Equatable {
    case voucher(quantity: Int = 0, price: Double = 0)
    case tShirt(quantity: Int = 0, price: Double = 0)
    case mug(quantity: Int = 0, price: Double = 0)
    
    // MARK: - Int
    // Value
    var intValue: Int {
        switch self {
        case .voucher: return 0
        case .tShirt: return 1
        case .mug: return 2
        }
    }
    
    // MARK: - String
    // Init with string
    init?(code: String, quantity: Int = 0, price: Double = 0) {
        switch code {
        case "VOUCHER": self = .voucher(quantity: quantity, price: price)
        case "TSHIRT": self = .tShirt(quantity: quantity, price: price)
        case "MUG": self = .mug(quantity: quantity, price: price)
        default: return nil
        }
    }
    // Value
    var stringValue: String {
        switch self {
        case .voucher: return "VOUCHER"
        case .tShirt: return "TSHIRT"
        case .mug: return "MUG"
        }
    }
    
    // MARK: - Custom infos
    // Badge discount text
    var discountBadgeText: String? {
        switch self {
        case .voucher: return "Buy 2 and get 1 free"
        case .tShirt: return "€1 discount per unit for 3+"
        default: return nil
        }
    }
    
    // Special price
    var specialPrice: Double? {
        switch self {
        case .tShirt(let quantity, let price): return quantity < 3 ? nil : price - 1.0
        default: return nil
        }
    }
}
