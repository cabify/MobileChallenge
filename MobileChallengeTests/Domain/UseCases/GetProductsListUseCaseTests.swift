//
//  GetProductsListUseCaseTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class GetProductsListUseCaseTests: XCTestCase {
    
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
}

// MARK: - Success tests
extension GetProductsListUseCaseTests {
    
    func testGetProductsListUseCase_whenSuccessfullyFetchesProductsList_thenValidateProducts() throws {
        
        // Given
        let mockedRequest = ProductsListRequest()
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        // When
        let productList = try awaitPublisher(getProductsListUseCase.getProductsList())
        
        // Then
        XCTAssertEqual(productList.products.count, 3)
        // First product
        XCTAssertEqual(productList.products.first?.code, "VOUCHER")
        XCTAssertEqual(productList.products.first?.name, "Cabify Voucher")
        XCTAssertEqual(productList.products.first?.price, 5.0)
        // Last product
        XCTAssertEqual(productList.products.last?.code, "MUG")
        XCTAssertEqual(productList.products.last?.name, "Cabify Coffee Mug")
        XCTAssertEqual(productList.products.last?.price, 7.5)
    }
}

// MARK: - Error tests
extension GetProductsListUseCaseTests {
    
    // Invalid URL
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowInvalidURLError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest(baseURL: "Invalid Base URL", path: "Invalid Path")
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, RequestableError.invalidURL().errorDescription)
        }
    }
    
    // Invalid response
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowInvalidResponseError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest()
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel,
            statusCode: -1
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.invalidResponse().errorDescription)
        }
    }
    
    // No data
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowNoDataError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest()
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Data("{ }".utf8)
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.noData.errorDescription)
        }
    }
    
    // Parser error
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowParserError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest(parser: nil)
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Data("{ }".utf8)
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.parseError().errorDescription)
        }
    }
    
    // Not found
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowBadRequestError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest(parser: nil)
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel,
            statusCode: 400
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.badRequest().errorDescription)
        }
    }
    
    // Not found
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowNotFoundError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest(parser: nil)
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel,
            statusCode: 404
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.notFound().errorDescription)
        }
    }
    
    // Server error
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowServerError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest(parser: nil)
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel,
            statusCode: 500
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.serverError().errorDescription)
        }
    }
    
    // Unknown error
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenThrowUnknownError() throws {
        // Given
        let mockedRequest: any RequestConvertable = ProductsListRequest(parser: nil)
        let mockedSessionConfig = MockedURLProtocol.configureMockedURLSession(
            url: Self.mockedURL(base: mockedRequest.baseURL, path: mockedRequest.path),
            encoded: Self.encodedMockedModel,
            statusCode: 600
        )
        
        let mockedApiClient: ApiExecutable = ApiClient(sessionConfiguration: mockedSessionConfig)
        let mockedRepository = DefaultProductsListRepository(apiClient: mockedApiClient, productsListRequest: mockedRequest)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, APIError.unknown().errorDescription)
        }
    }
}
