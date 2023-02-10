//
//  RemoveItemFromCartUseCaseTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class RemoveItemFromCartUseCaseTests: XCTestCase {
    
    // Success
    func testRemoveItemFromCart_whenSuccessfullyRemovesItemFromCart_thenValidateNewQuantity() throws {
        // Given
        let mockedRepository = MockedCartRepository()
        let getCartUseCase = DefaultGetCartUseCase(cartRepository: mockedRepository)
        let removeItemFromCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: mockedRepository)
        
        // When
        let cart = try awaitPublisher(getCartUseCase.getCart())
        if let item = cart.items.first(where: { ProductType.init(rawValue: $0.code) == .voucher }) {
            XCTAssertEqual(item.quantity, 2)
            
            let newQuantity = try awaitPublisher(removeItemFromCartUseCase.removeItem(item))
            XCTAssertEqual(newQuantity, 1)
        }
    }
    
    // Fail
    func testRemoveItemFromCart_whenFailedRemovesItemFromCart_thenThrowAnError() throws {
        // Given
        let mockedRepository = MockedCartRepository()
        let getCartUseCase = DefaultGetCartUseCase(cartRepository: mockedRepository)
        let removeItemFromCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: mockedRepository)
        
        let cart = try awaitPublisher(getCartUseCase.getCart())
        if let item = cart.items.first(where: { ProductType.init(rawValue: $0.code) == .mug }) {
            XCTAssertEqual(item.quantity, 0)
            
            do {
                // When
                _ = try awaitPublisher(removeItemFromCartUseCase.removeItem(item))
                
            } catch let error {
                // Then
                XCTAssertEqual(error as? TestsError, .insufficientQuantity)
            }
        }
    }
}
