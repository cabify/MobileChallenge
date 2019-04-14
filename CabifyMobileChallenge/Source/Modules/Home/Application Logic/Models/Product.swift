//
//  Product.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import ObjectMapper

class Product: Mappable {
    private var _code = ""
    private var _name = ""
    private var _price: Float = 0.0
    
    var price : Float{
        return _price
    }
    
    var name : String{
        return _name
    }
    
    var code : String{
        return _code
    }
    
    required public init?(map: Map){}
    
    public func mapping(map: Map){
        _code     <- map["code"]
        _name     <- map["name"]
        _price    <- map["price"]
    }
}
