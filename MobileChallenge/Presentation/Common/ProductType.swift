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
    init?(code: String) {
        switch code {
        case "VOUCHER": self = .voucher
        case "TSHIRT": self = .tShirt
        case "MUG": self = .mug
        default: return nil
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
}

extension ProductType: Equatable {
    static func == (lhs: Self, rhs: Self) -> Bool {
        switch (lhs, rhs) {
        case (.voucher, .voucher): return true
        case (.tShirt, .tShirt): return true
        case (.mug, .mug): return true
        default: return false
        }
    }
}
