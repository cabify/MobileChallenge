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
        let quantity: Int
    }
    
    let items: [Item]
}

#if DEBUG
extension Cart {
    static var preview: Self {
        .init(
            items: [
                .init(code: ProductType.voucher().intValue, quantity: 0),
                .init(code: ProductType.tShirt().intValue, quantity: 0),
                .init(code: ProductType.mug().intValue, quantity: 0)
            ]
        )
    }
}
#endif
