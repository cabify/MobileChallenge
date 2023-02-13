//
//  GetCartUseCaseTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class GetCartUseCaseTests: XCTestCase {
    
    // Success
    func testGetCartUseCase_whenSuccessfullyFetchesCart_thenValidateCartAndItems() throws {
        // Given
        let mockedRepository = MockedCartRepository()
        let getCartUseCase = DefaultGetCartUseCase(cartRepository: mockedRepository)
        
        // When
        let cart = try awaitPublisher(getCartUseCase.getCart())
        
        // Then
        XCTAssertEqual(cart.items.count, 3)
        // First product
        XCTAssertEqual(cart.items.first?.code, 0)
        XCTAssertEqual(cart.items.first?.name, "Cabify Voucher")
        XCTAssertEqual(cart.items.first?.quantity, 0)
        XCTAssertEqual(cart.items.first?.price, 5.0)
        // Last product
        XCTAssertEqual(cart.items.last?.code, 2)
        XCTAssertEqual(cart.items.last?.name, "Cabify Coffee Mug")
        XCTAssertEqual(cart.items.last?.quantity, 0)
        XCTAssertEqual(cart.items.last?.price, 7.5)
    }
}
