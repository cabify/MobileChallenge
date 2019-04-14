//
//  Checkout.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 13/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation

class Checkout {
    private var _name = ""
    private var _totalPrice: Float = 0.0
    private var _count = 0
    
    var totalPrice : Float{
        return _totalPrice
    }
    
    var name : String{
        return _name
    }
    
    var count : Int{
        return _count
    }
    
    init?(name: String, count: Int, totalPrice: Float){
        _name       = name
        _count      = count
        _totalPrice = totalPrice;
    }
}
