//
//  ShoppingCart.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 13/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import RxSwift

public class ShoppingCart {
    
    let products: Variable<[Product]> = Variable([])
    
    func totalCost(discount: Dictionary<String, Discount>) -> [Checkout] {
        
        var checkouts = [Checkout]()
        
        let groupedByCode = Dictionary(grouping: products.value) { $0.code }

        groupedByCode.forEach({ (key: String, value: [Product]) in
            
            var price: Float
            switch discount[key] {
            case .some(.twoXone):
                let count: Int = (value.count > 1) ? value.count/2 + value.count%2 : value.count
                price = Float(count) * value[0].price
            case .some(.reducedPrice):
                price = ((value.count > 2) ? value[0].price - 1.00 : value[0].price) * Float(value.count)
            case .none,.some(.none):
                price = value[0].price * Float(value.count)
            }
            
            if let checkout = Checkout(name: key, count: value.count, totalPrice: price) {
                checkouts.append(checkout)
            }
        })
        
        return checkouts
    }
}
