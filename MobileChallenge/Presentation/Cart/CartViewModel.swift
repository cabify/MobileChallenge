//
//  CartViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

struct CartViewModel {
    
    // Subtotal
    private var subtotal: Double {
        return self.items.compactMap { $0.totalPrice }.reduce(0, +)
    }
    var formattedSubtotal: String {
        return self.subtotal.currency
    }
    
    // Discounts
    // UI representation
    struct Discount: Identifiable {
        let id = UUID()
        let text: String
        let value: Double
        var formattedValue: String {
            return value.currency
        }
    }
    // Show discounts
    var showDiscounts: Bool {
        return !self.discounts.isEmpty
    }
    // Discounts list
    var discounts: [Discount] {
        return items.compactMap { anItem -> Discount? in
            guard let discountText = anItem.productType.discountBadgeText, anItem.totalDiscounts > 0 else { return nil }
            return .init(text: discountText, value: anItem.totalDiscounts)
        }
    }
    
    // Total
    var total: Double {
        return items.compactMap { $0.totalPriceWithDiscounts ?? $0.totalPrice }.reduce(0, +)
    }
    var formattedTotal: String {
        return self.total.currency
    }
    private(set) var items: [CartLayoutViewModel.CartItem]
    
    init(cart: Cart) {
        let items: [CartLayoutViewModel.CartItem] = cart.items
            .filter { $0.quantity > 0 }
            .compactMap { .init(cartItem: $0) }
        
        // Items
        self.items = items
    }
    
    // MARK: - Setter
    mutating func updateItem(_ item: CartLayoutViewModel.CartItem, newCartQuantity: Int) {
        guard let indexOf = self.items.firstIndex(where: { $0.productType == item.productType }) else { return }
        
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
