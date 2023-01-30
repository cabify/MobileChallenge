//
//  DefaultProductsListRepository.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation
import Combine

final class DefaultProductsListRepository {
    
    fileprivate struct ProductsListResponseParser: ResponseParserType {
        typealias Response = ProductsListDTO
        
        func parse(data: Data) throws -> ProductsListDTO? {
            try JSONDecoder().decode(Response.self, from: data)
        }
    }
    
    fileprivate struct ProductsListRequest: RequestConvertable {
        typealias Response = ProductsListDTO
        typealias ResponseParser = ProductsListResponseParser
        
        var path: String = "/Products.json"
        var method: String = "GET"
        var header: [String : String]?
        var parser: ResponseParser? = ProductsListResponseParser()
        var errorParser: ErrorParserType?
        var parameter: [String: Any]? { nil }
    }
    
    private let apiClient: ApiExecutable
    fileprivate let productsListRequest: ProductsListRequest
    
    init(apiClient: ApiExecutable = ApiClient()) {
        self.apiClient = apiClient
        self.productsListRequest = ProductsListRequest()
    }
}

extension DefaultProductsListRepository: ProductsListRepository {
    
    func fetchProductsList() -> AnyPublisher<ProductsList, Error> {
        return apiClient.executeRequest(request: productsListRequest)
            .tryMap { $0.domainObject }
            .eraseToAnyPublisher()
    }
}

