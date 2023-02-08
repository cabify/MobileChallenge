//
//  CartViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct CartViewModel {
    
    struct Discount {
        let text: String
        let value: Double
        var formattedValue: String {
            return value.currency
        }
    }
    
    private let subtotal: Double
    let formattedSubtotal: String
    var showDiscounts: Bool = false
    var discounts: [Discount]
    private let total: Double
    let formattedTotal: String
    private(set) var items: [CartItemViewModel]
    
    init(cart: Cart) {
        let items: [CartItemViewModel] = cart.items
            .filter { $0.quantity > 0 }
            .compactMap { .init(cartItem: $0) }
        
        // Subtotal
        let subtotal = items.compactMap { $0.totalPrice }.reduce(0, +)
        self.subtotal = subtotal
        self.formattedSubtotal = subtotal.currency
        
        // Discounts
        self.discounts = items.compactMap { anItem -> Discount? in
            guard let discountText = anItem.productType.discountBadgeText, anItem.totalDiscounts > 0 else { return nil }
            return .init(text: discountText, value: anItem.totalDiscounts)
        }
        self.showDiscounts = !self.discounts.isEmpty
        
        // Total
        self.total = subtotal
        self.formattedTotal = subtotal.currency
        
        // Items
        self.items = items
    }
}

#if DEBUG
extension CartViewModel {
    static var preview: Self {
        .init(cart: Cart.preview)
    }
}
#endif
