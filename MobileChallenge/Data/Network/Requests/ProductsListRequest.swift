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
    
    var baseURL: String
    var path: String
    var method: String
    var header: [String : String]?
    var parser: ResponseParser?
    var errorParser: ErrorParserType?
    var parameter: [String: Any]?
    
    init(baseURL: String = ApiClientConstants.baseURL, path: String = "/Products.json", method: String = "GET", header: [String : String]? = nil, parser: ResponseParser? = Parser(), errorParser: ErrorParserType? = nil, parameter: [String: Any]? = nil) {
        self.baseURL = baseURL
        self.path = path
        self.method = method
        self.header = header
        self.parser = parser
        self.errorParser = errorParser
        self.parameter = parameter
    }
}

#if DEBUG && TESTING
extension ProductsListRequest {
    static var preview: Self {
        .init()
    }
}
#endif
