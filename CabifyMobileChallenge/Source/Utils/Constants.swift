//
//  Constants.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

enum Servers{
    public static let DEV = "https://api.myjson.com/bins/"
}

class Constants{
    public static let BASE_URL = Servers.DEV
}

enum APIPath: String{
    case locations = "4bwec"
    
    var path: String{
        return Constants.BASE_URL + rawValue
    }
}
