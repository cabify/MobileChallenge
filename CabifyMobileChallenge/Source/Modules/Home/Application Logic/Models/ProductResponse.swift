//
//  ProductResponse.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import ObjectMapper

public class ProductResponse: Mappable{
    
    private var _productList : [Product]?
    
    var productList : [Product]{
        return _productList ?? []
    }
    
    required public init?(map: Map){}
    
    public func mapping(map: Map){
        _productList <- map["products"]
    }
}
