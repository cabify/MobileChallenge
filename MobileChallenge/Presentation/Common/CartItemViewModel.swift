//
//  CartItemViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct CartItemViewModel: Identifiable {
    
    static let defaultType: ProductType = .voucher
    
    typealias Identifier = UUID
    
    let id = Identifier()
    private(set) var productType: ProductType
    let name: String
    var showDiscountBadge: Bool {
        return productType.discountBadgeText != nil
    }
    private let price: Double
    let formattedPrice: String
    private(set) var cartQuantity: Int
    var showSpecialPrice: Bool {
        return self.specialPrice != nil
    }
    var formattedSpecialPrice: String? {
        guard let specialPrice = self.specialPrice?.currency else { return nil }
        return specialPrice
    }
    }
    var domainObject: Cart.Item {
        return .init(code: productType.intValue, name: name, quantity: cartQuantity, price: price)
    }
    
    init?(product: ProductsList.Product, cartQuantity: Int = 0) {
        guard let productType = ProductType(code: product.code) else { return nil }
        self.productType = productType
        self.name = product.name
        self.price = product.price
        self.formattedPrice = product.price.currency
        self.cartQuantity = cartQuantity
    }
    
    mutating func updateCartQuantity(_ cartQuantity: Int) {
        self.cartQuantity = cartQuantity
    }
    
    #if DEBUG
    static var preview: [Self] {
        ProductsList.preview.products.compactMap({ .init(product: $0) })
    }
    #endif
}
