//
//  Cart.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

struct Cart {
    
    struct CartItem {
        let code: Int
        let quantity: Int
    }
    
    let items: [CartItem]
}

#if DEBUG
extension Cart {
    static var preview: Self {
        .init(items: [
            .init(code: ProductType.voucher.hashValue, quantity: 0),
            .init(code: ProductType.tShirt.hashValue, quantity: 0),
            .init(code: ProductType.mug.hashValue, quantity: 0)
        ])
    }
}
#endif
