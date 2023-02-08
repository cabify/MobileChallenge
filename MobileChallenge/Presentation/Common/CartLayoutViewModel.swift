//
//  CartLayoutViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import Foundation

struct CartLayoutViewModel {
    
    // MARK:
    struct CartItem: Identifiable {
        
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
        // Total
        var totalPrice: Double {
            return self.price * Double(self.cartQuantity)
        }
        var formattedTotalPrice: String {
            return self.totalPrice.currency
        }
        var totalPriceWithDiscounts: Double? {
            if let specialPrice = self.specialPrice {
                return specialPrice * Double(self.cartQuantity)
            }
            return self.totalPrice - self.totalDiscounts
        }
        var formattedTotalPriceWithDiscounts: String? {
            return self.totalPriceWithDiscounts?.currency
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
        // From product
        init?(cartItem: Cart.Item) {
            guard let productType = ProductType(rawValue: cartItem.code) else { return nil }
            self.productType = productType
            self.name = cartItem.name
            self.price = cartItem.price
            self.formattedPrice = cartItem.price.currency
            self.cartQuantity = cartItem.quantity
        }
        
        // MARK: - Setter
        mutating func updateCartQuantity(_ cartQuantity: Int) {
            self.cartQuantity = cartQuantity
        }
    }
    
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
    private(set) var items: [CartItem]
    
    init(cart: Cart) {
        let items: [CartItem] = cart.items
            .filter { $0.quantity > 0 }
            .compactMap { .init(cartItem: $0) }
        
        // Items
        self.items = items
    }
    
    // MARK: - Setter
    mutating func updateItem(_ item: CartItem, newCartQuantity: Int) {
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
extension CartLayoutViewModel {
    static var preview: Self {
        .init(cart: Cart.preview)
    }
}

extension CartLayoutViewModel.CartItem {
    static var productsListPreview: [Self] {
        ProductsList.preview.products.compactMap({ .init(product: $0) })
    }
    
    static var cartItemsPreview: [Self] {
        Cart.preview.items.compactMap({ .init(cartItem: $0) })
    }
}
#endif
