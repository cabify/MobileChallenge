//
//  ProductType.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

// Product types
enum ProductType: Int {
    
    case voucher = 0
    case tShirt
    case mug
    
    // MARK: - String
    // Init with string
    init?(stringValue: String) {
        switch stringValue {
        case "VOUCHER": self = .voucher
        case "TSHIRT": self = .tShirt
        case "MUG": self = .mug
        default: return nil
        }
    }
    
    var discountBadgeText: String? {
        switch self {
        case .voucher: return "Buy 2 and get 1 free"
        case .tShirt: return "€1 discount per unit for 3+"
        default: return nil
        }
    }
}
