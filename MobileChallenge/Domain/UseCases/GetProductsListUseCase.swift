//
//  GetProductsListUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 27/01/2023.
//

import Foundation
import Combine

protocol GetProductsListUseCase {
    func getProductsList() -> AnyPublisher<ProductsList, Error>
}

final class DefaultGetProductsListUseCase: GetProductsListUseCase {
    
    private let productsListRepository: ProductsListRepository
    
    init(productsListRepository: ProductsListRepository) {
        self.productsListRepository = productsListRepository
    }
    
    func getProductsList() -> AnyPublisher<ProductsList, Error> {
        let testingUI = ProcessInfo.processInfo.arguments.contains("IS_UI_TESTING")
        guard !testingUI else {
            let mockedModel: ProductsListDTO = .init(products: [
                .init(code: "VOUCHER", name: "Cabify Voucher", price: 5),
                .init(code: "TSHIRT", name: "Cabify T-Shirt", price: 20),
                .init(code: "MUG", name: "Cabify Coffee Mug", price: 7.5)
            ])
            return Just(mockedModel.domainObject)
                .mapError { _ in APIError.parseError() }
                .eraseToAnyPublisher()
        }
        
        return productsListRepository.fetchProductsList()
    }
}

#if DEBUG && TESTING
extension DefaultGetProductsListUseCase {
    static var preview: Self {
        .init(productsListRepository: DefaultProductsListRepository.preview)
    }
}
#endif
