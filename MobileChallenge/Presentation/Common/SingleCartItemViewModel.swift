//
//  SingleCartItemViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct SingleCartItemViewModel: Identifiable {
    
    static let defaultType: ProductType = .voucher(quantity: 0, price: 0)
    static let priceFormat: String = "%.2f€"
    
    typealias Identifier = UUID
    
    let id = Identifier()
    private(set) var productType: ProductType
    let name: String
    private let price: Double
    let formattedPrice: String
    private(set) var cartQuantity: Int
    var showSpecialPrice: Bool {
        return productType.specialPrice != nil
    }
    var formattedSpecialPrice: String? {
        guard let specialPrice = productType.specialPrice else { return nil }
        return String(format: SingleCartItemViewModel.priceFormat, specialPrice)
    }
    var showDiscountBadge: Bool {
        return productType.discountBadgeText != nil
    }
    var domainObject: Cart.Item {
        return .init(code: productType.intValue, quantity: cartQuantity)
    }
    
    init?(product: ProductsList.Product, cartQuantity: Int = 0) {
        guard let productType = ProductType(code: product.code, quantity: cartQuantity, price: product.price) else { return nil }
        self.productType = productType
        self.name = product.name
        self.price = product.price
        self.formattedPrice = String(format: SingleCartItemViewModel.priceFormat, product.price)
        self.cartQuantity = cartQuantity
    }
    
    mutating func updateCartQuantity(_ cartQuantity: Int) {
        self.productType = ProductType(code: productType.stringValue, quantity: cartQuantity, price: price) ?? productType
        self.cartQuantity = cartQuantity
    }
    
    #if DEBUG
    static var preview: [Self] {
        ProductsList.preview.products.compactMap({ .init(product: $0) })
    }
    #endif
}
