//
//  DefaultProductsListRepository.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation
import Combine

final class DefaultProductsListRepository {
    
    private let apiClient: ApiExecutable
    private let productsListRequest: any RequestConvertable
    
    init(apiClient: ApiExecutable = ApiClient(), productsListRequest: any RequestConvertable = ProductsListRequest()) {
        self.apiClient = apiClient
        self.productsListRequest = productsListRequest
    }
}

extension DefaultProductsListRepository: ProductsListRepository {
    func fetchProductsList() -> AnyPublisher<ProductsList, Error> {
        guard let aProductsListRequest = productsListRequest as? ProductsListRequest else {
            return Fail(error: RequestableError.invalidURL()).eraseToAnyPublisher()
        }
        
        return apiClient.executeRequest(request: aProductsListRequest)
            .tryMap { $0.domainObject }
            .eraseToAnyPublisher()
    }
}

#if DEBUG && TESTING
extension DefaultProductsListRepository {
    static var preview: Self {
        .init(productsListRequest: ProductsListRequest.preview)
    }
}
#endif

