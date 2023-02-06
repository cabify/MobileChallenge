//
//  ProductsListRequest.swift
//  MobileChallenge
//
//  Created by thomas pereira on 06/02/2023.
//

import Foundation

struct ProductsListRequest: RequestConvertable {
    
    struct Parser: ResponseParserType {
        typealias Response = ProductsListDTO
        
        func parse(data: Data) throws -> ProductsListDTO? {
            try JSONDecoder().decode(Response.self, from: data)
        }
    }
    
    typealias Response = ProductsListDTO
    typealias ResponseParser = Parser
    
    var path: String = "/Products.json"
    var method: String = "GET"
    var header: [String : String]?
    var parser: ResponseParser? = Parser()
    var errorParser: ErrorParserType?
    var parameter: [String: Any]? { nil }
}

#if DEBUG
extension ProductsListRequest {
    static var preview: Self {
        .init()
    }
}
#endif
