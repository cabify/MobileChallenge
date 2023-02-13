//
//  AddItemToCartUseCaseTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class AddItemToCartUseCaseTests: XCTestCase {
    
    // Success
    func testAddItemToCart_whenSuccessfullyAddsItemToCart_thenValidateNewQuantity() throws {
        // Given
        let mockedRepository = MockedCartRepository()
        let getCartUseCase = DefaultGetCartUseCase(cartRepository: mockedRepository)
        let addItemToCartUseCase = DefaultAddItemToCartUseCase(cartRepository: mockedRepository)
        
        // When
        let cart = try awaitPublisher(getCartUseCase.getCart())
        if let item = cart.items.first(where: { ProductType.init(rawValue: $0.code) == .tShirt }) {
            XCTAssertEqual(item.quantity, 0)
            
            let newQuantity = try awaitPublisher(addItemToCartUseCase.addItem(item))
            XCTAssertEqual(newQuantity, 1)
        }
    }
}
