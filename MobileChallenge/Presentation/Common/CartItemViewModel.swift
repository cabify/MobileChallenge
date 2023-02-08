//
//  CartItemViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct CartItemViewModel: Identifiable {
    
    static let defaultType: ProductType = .voucher
    
    // MARK: - Properties
    // ID
    typealias Identifier = UUID
    let id = Identifier()
    // Main info
    private(set) var productType: ProductType
    let name: String
    var showDiscountBadge: Bool {
        return productType.discountBadgeText != nil
    }
    private(set) var cartQuantity: Int
    // Prices
    // Regular price
    private let price: Double
    let formattedPrice: String
    // Special price
    private var specialPrice: Double? {
        switch self.productType {
        case .tShirt: return self.cartQuantity < 3 ? nil : self.price - 1.0
        default: return nil
        }
    }
    var showSpecialPrice: Bool {
        return self.specialPrice != nil
    }
    var formattedSpecialPrice: String? {
        guard let specialPrice = self.specialPrice?.currency else { return nil }
        return specialPrice
    }
    // Discounts
    var totalDiscounts: Double {
        switch self.productType {
        case .voucher:
            guard self.cartQuantity >= 2 else { return 0.0 }
            if self.cartQuantity % 2 == 0 {
                return (Double(self.cartQuantity) / 2.0) * self.price
            }
            return ((Double(self.cartQuantity) / 2.0) * self.price) - (Double(self.cartQuantity % 2) * (self.price / 2.0))
            
        case .tShirt: return self.cartQuantity < 3 ? 0.0 : Double(self.cartQuantity) * 1.0
        default: return 0.0
        }
    }
    }
    // To domain
    var domainObject: Cart.Item {
        return .init(code: productType.intValue, name: name, quantity: cartQuantity, price: price)
    }
    
    // MARK: - Init
    // From product
    init?(product: ProductsList.Product, cartQuantity: Int = 0) {
        guard let productType = ProductType(code: product.code) else { return nil }
        self.productType = productType
        self.name = product.name
        self.price = product.price
        self.formattedPrice = product.price.currency
        self.cartQuantity = cartQuantity
    }
    
    // MARK: - Setter
    mutating func updateCartQuantity(_ cartQuantity: Int) {
        self.cartQuantity = cartQuantity
    }
    
    #if DEBUG
    static var preview: [Self] {
        ProductsList.preview.products.compactMap({ .init(product: $0) })
    }
    #endif
}
