//
//  ClearCartUseCaseTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class ClearCartUseCaseTests: XCTestCase {
    
    // Success
    func testClearCart_whenSuccessfullyClearsCart_thenValidateItemQuantities() throws {
        // Given
        let mockedRepository = MockedCartRepository()
        let getCartUseCase = DefaultGetCartUseCase(cartRepository: mockedRepository)
        let clearCartUseCase = DefaultClearCartUseCase(cartRepository: mockedRepository)
        
        // When
        let cart = try awaitPublisher(getCartUseCase.getCart())
        let cartItems = cart.items.filter { $0.quantity > 0 }
        
        // Then
        let clearedCart = try awaitPublisher(clearCartUseCase.clearCart())
        XCTAssertEqual(cartItems.count, 0)
        XCTAssertEqual(clearedCart.items.filter { $0.quantity > 0 }.count, 0)
    }
}
