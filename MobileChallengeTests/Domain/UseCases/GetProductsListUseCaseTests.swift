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
    
    private var cancellables: Set<AnyCancellable>!
    
    override func setUp() {
        super.setUp()
        self.cancellables = []
    }
    
    // Success
    func testGetProductsListUseCase_whenSuccessfullyFetchesProductsList_thenValidateProducts() throws {
        // Given
        let mockedRepository = MockedProductsListRepository()
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
    
    // Failed
    func testGetProductsListUseCase_whenFailedFetchesProductsList_thenProductsListIsEmpty() throws {
        // Given
        let mockedRepository = MockedProductsListRepository(error: APIError.noData)
        let getProductsListUseCase = DefaultGetProductsListUseCase(productsListRepository: mockedRepository)
        
        do {
            // When
            _ = try awaitPublisher(getProductsListUseCase.getProductsList())
            
        } catch let error {
            // Then
            XCTAssertEqual(error.localizedDescription, "Could not received data from the server. Please retry.")
        }
    }

}
