//
//  ProductsList.swift
//  MobileChallenge
//
//  Created by thomas pereira on 27/01/2023.
//

import Foundation

struct ProductsList: Equatable {
    
    struct Product: Equatable {
        let code: String
        let name: String
        let price: Double
    }
    
    let products: [Product]
}
