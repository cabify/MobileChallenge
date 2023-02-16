//
//  AccessibilityIdentifier+Helper.swift
//  MobileChallengeUITests
//
//  Created by thomas pereira on 16/02/2023.
//

import Foundation

enum AccessibilityID {
    struct Navigation {
        static let ProductsList = "Products list"
        static let CartButton = "CartButton"
        static let Cart = "Place your order"
    }
    
    struct Products {
        static let ProductList = "ProductList"
        
        struct Cell {
            static let Name = "ProductName"
            static let Quantity = "ProductQuantity"
            static let DiscountBadge = "ProductBadge"
            static let Prices = "ProductPrices"
        }
    }
    
    struct CartDetailView {
        static let CloseButton = "CloseButton"
        static let Summary = "Summary"
        static let EmptyCart = "EmptyCart"
        static let ItemList = "ItemList"
        
        struct Cell {
            static let Name = "ItemName"
            static let Quantity = "ItemQuantity"
            static let DiscountBadge = "ItemDiscountBadge"
            static let Prices = "ItemPrices"
        }
    }
}
