//
//  MockedDefaultProductsListRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 14/02/2023.
//

import Foundation
import Combine
@testable import MobileChallenge

final class MockedDefaultProductsListRepository {
    
    static private func mockedURL(base: String?, path: String?) -> URL? {
        guard let aBase = base, let aPath = path else { return nil }
        var mockedURL = URLComponents(string: aBase)
        mockedURL?.path.append(aPath)
        return mockedURL?.url
    }

    static private var encodedMockedModel: Data? {
        let mockedModel: ProductsListDTO = .init(products: [
            .init(code: "VOUCHER", name: "Cabify Voucher", price: 5),
            .init(code: "TSHIRT", name: "Cabify T-Shirt", price: 20),
            .init(code: "MUG", name: "Cabify Coffee Mug", price: 7.5)
        ])
        return try? JSONEncoder().encode(mockedModel)
    }
    
    static var repository: DefaultProductsListRepository {
        
        let mockedRequest = ProductsListRequest()
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        return DefaultProductsListRepository(apiClient: mockedApiClient)
    }
    
    static func customRepository(request: any RequestConvertable = ProductsListRequest(), encodedData: Data? = MockedDefaultProductsListRepository.encodedMockedModel, statusCode: Int = 200) -> DefaultProductsListRepository {
        
        let mockedRequest = ProductsListRequest()
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: encodedData,
            statusCode: statusCode
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        return DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: request)
    }
}
