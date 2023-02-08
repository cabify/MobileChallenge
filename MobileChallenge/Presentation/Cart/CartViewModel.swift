//
//  CartViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct CartViewModel {
    
    struct Discount: Identifiable {
        let id = UUID()
        let text: String
        let value: Double
        var formattedValue: String {
            return value.currency
        }
    }
    
    private let subtotal: Double
    var formattedSubtotal: String {
        return self.subtotal.currency
    }
    var showDiscounts: Bool = false
    var discounts: [Discount]
    private let total: Double
    var formattedTotal: String {
        return self.total.currency
    }
    private(set) var items: [CartItemViewModel]
    
    init(cart: Cart) {
        let items: [CartItemViewModel] = cart.items
            .filter { $0.quantity > 0 }
            .compactMap { .init(cartItem: $0) }
        
        // Subtotal
        let subtotal = items.compactMap { $0.totalPrice }.reduce(0, +)
        self.subtotal = subtotal
    // MARK: - Setter
    mutating func updateItem(_ item: CartItemViewModel, newCartQuantity: Int) {
        guard let indexOf = self.items.firstIndex(where: { $0.productType == item.productType }) else { return }
        
        // Discounts
        self.discounts = items.compactMap { anItem -> Discount? in
            guard let discountText = anItem.productType.discountBadgeText, anItem.totalDiscounts > 0 else { return nil }
            return .init(text: discountText, value: anItem.totalDiscounts)
        var updatedItems = self.items
        // Remove item when quantity reaches 0
        if newCartQuantity == 0 {
            updatedItems.remove(at: indexOf)
            
        } else {
            // Update item quantity
            var updatedItem = item
            updatedItem.updateCartQuantity(newCartQuantity)
            updatedItems[indexOf] = updatedItem
        }
        self.showDiscounts = !self.discounts.isEmpty
        
        // Total
        let total = items.compactMap { $0.totalPriceWithDiscounts ?? $0.totalPrice }.reduce(0, +)
        self.total = total
        
        // Items
        self.items = items
        self.items = updatedItems
    }
}

#if DEBUG
extension CartViewModel {
    static var preview: Self {
        .init(cart: Cart.preview)
    }
}
#endif
