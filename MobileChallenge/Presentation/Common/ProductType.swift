//
//  ProductType.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

// Product types
enum ProductType: Int, RawRepresentable {
    
    case voucher = 0
    case shirt
    case mug
    
    // MARK: - RawRepresentable
    typealias RawValue = String
    // RawValue
    var rawValue: String {
        switch self {
        case .voucher: return "VOUCHER"
        case .shirt: return "TSHIRT"
        case .mug: return "MUG"
        }
    }
    // Init from RawValue
    init?(rawValue: String) {
        switch rawValue {
        case "VOUCHER": self = .voucher
        case "TSHIRT": self = .shirt
        case "MUG": self = .mug
        default: return nil
        }
    }
    
    var discountBadgeText: String? {
        switch self {
        case .voucher: return "Buy 2 and get 1 free"
        case .shirt: return "€1 discount per unit for 3+"
        default: return nil
        }
    }
}
