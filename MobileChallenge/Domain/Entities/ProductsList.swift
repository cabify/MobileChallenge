//
//  ProductsList.swift
//  MobileChallenge
//
//  Created by thomas pereira on 27/01/2023.
//

import Foundation

struct ProductsList: Equatable {
    
    struct Product: Equatable, Identifiable {
        typealias Identifier = UUID
        
        let id: Identifier = Identifier()
        let code: String
        let name: String
        let price: Double
    }
    
    let products: [Product]
}
