//
//  CheckoutLocalDataManager.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 14/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation


final class CheckoutLocalDataManager {
}

// MARK: - Extensions -

extension CheckoutLocalDataManager: CheckoutLocalDataManagerInputProtocol {
    
    func getDiscounts() -> Dictionary<String, Discount>  {
        let discounts: Dictionary = [
            "VOUCHER": Discount.twoXone,
            "TSHIRT": Discount.reducedPrice,
            "MUG": Discount.none
        ]
        return discounts
    }
}
