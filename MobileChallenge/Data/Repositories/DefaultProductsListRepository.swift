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
    let productsListRequest: ProductsListRequest
    
    init(productsListRequest: any RequestConvertable = ProductsListRequest()) {
        self.apiClient = ApiClient()
        self.productsListRequest = productsListRequest as! ProductsListRequest
    }
}

extension DefaultProductsListRepository: ProductsListRepository {
    func fetchProductsList() -> AnyPublisher<ProductsList, Error> {
        return apiClient.executeRequest(request: productsListRequest)
            .tryMap { $0.domainObject }
            .eraseToAnyPublisher()
    }
}

#if DEBUG
extension DefaultProductsListRepository {
    static var preview: Self {
        .init(productsListRequest: ProductsListRequest.preview)
    }
}
#endif

