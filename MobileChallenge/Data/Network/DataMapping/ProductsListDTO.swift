//
//  ProductsListDTO.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation

// MARK: - Data Transfer Object - DTO
struct ProductsListDTO: Equatable, Decodable {
    
    struct Product: Equatable, Decodable {
        let code: String
        let name: String
        let price: Double
    }
    
    let products: [Product]
}

// MARK: - Mapping to Domain
extension ProductsListDTO {
    var domainObject: ProductsList {
        let products: [ProductsList.Product] = products.compactMap { .init(code: $0.code, name: $0.name, price: $0.price) }
        
        return .init(products: products)
    }
}
