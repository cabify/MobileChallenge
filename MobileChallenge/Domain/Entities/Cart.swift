//
//  Cart.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

struct Cart {
    
    struct Item {
        let code: Int
        let name: String
        let quantity: Int
        let price: Double
    }
    
    let items: [Item]
}

#if DEBUG
extension Cart {
    static var preview: Self {
        .init(items: [
            .init(code: 0, name: "Cabify Voucher", quantity: 2, price: 5),
            .init(code: 1, name: "Cabify T-Shirt", quantity: 4, price: 20),
            .init(code: 2, name: "Cabify Coffee Mug", quantity: 0, price: 7.5)
        ])
    }
}
#endif
