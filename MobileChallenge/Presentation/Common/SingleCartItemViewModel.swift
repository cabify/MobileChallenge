//
//  SingleCartItemViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct SingleCartItemViewModel: Identifiable {
    
    static let defaultType: ProductType = .voucher
    
    typealias Identifier = UUID
    
    let id = Identifier()
    let productType: ProductType
    let name: String
    let formattedPrice: String
    private(set) var cartQuantity: Int
    let showSpecialPrice: Bool
    let formattedSpecialPrice: String?
    let showDiscountBadge: Bool
    var domainObject: Cart.Item {
        return .init(code: productType.rawValue, quantity: cartQuantity)
    }
    
    init?(product: ProductsList.Product, cart: Cart? = nil) {
        guard let productType = ProductType(stringValue: product.code) else { return nil }
        self.productType = productType
        self.name = product.name
        self.formattedPrice = String(format: "%.2f€", product.price)
        
        // Cart info
        if let cartItem = cart?.items.first(where: { $0.code == productType.rawValue }) {
            self.cartQuantity = cartItem.quantity
            self.showSpecialPrice = false
            self.formattedSpecialPrice = nil
            
        } else {
            self.cartQuantity = 0
            self.showSpecialPrice = false
            self.formattedSpecialPrice = nil
        }
        
        self.showDiscountBadge = productType.discountBadgeText != nil
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
