//
//  CheckoutModels.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 13/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation

enum CheckoutModels
{
    // MARK: Use cases
    
    enum getCheckoput
    {
        struct Request
        {
            var shoppingCart: ShoppingCart
        }
        
        struct Response
        {
            var checkout: [Checkout]
        }
        
        struct ViewModel
        {
            var checkout: [Checkout]
            var totalPrice: Float
        }
    }
}
