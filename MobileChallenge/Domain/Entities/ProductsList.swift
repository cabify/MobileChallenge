//
//  ProductsList.swift
//  MobileChallenge
//
//  Created by thomas pereira on 27/01/2023.
//

import Foundation

struct ProductsList: Equatable {
    
    struct Product: Equatable {
        let code: String
        let name: String
        let price: Double
    }
    
    let products: [Product]
}

#if DEBUG
extension ProductsList {
    static var preview: Self {
        .init(products: [
            .init(code: "VOUCHER", name: "Cabify Voucher", price: 5),
            .init(code: "TSHIRT", name: "Cabify T-Shirt", price: 20),
            .init(code: "MUG", name: "Cabify Coffee Mug", price: 7.5)
        ])
    }
}
#endif
