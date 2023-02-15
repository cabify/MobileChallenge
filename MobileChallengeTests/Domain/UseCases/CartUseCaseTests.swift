//
//  CartUseCaseTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class CartUseCasesTests: XCTestCase {
    
    private var mockedCoreDataStorage: MockedCoreDataStorage!
    private var mockedRepository: CartRepository!
    private var getCartUseCase: GetCartUseCase!
    private var addItemToCartUseCase: AddItemToCartUseCase!
    private var removeItemFromCartUseCase: RemoveItemFromCartUseCase!
    private var clearCartUseCase: ClearCartUseCase!
    private var cancellables: Set<AnyCancellable>! = []
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        self.mockedCoreDataStorage = MockedCoreDataStorage()
        self.mockedRepository = DefaultCartRepository(backgroundContext: self.mockedCoreDataStorage.backgroundContext)
        self.getCartUseCase = DefaultGetCartUseCase(cartRepository: self.mockedRepository)
        self.addItemToCartUseCase = DefaultAddItemToCartUseCase(cartRepository: self.mockedRepository)
        self.removeItemFromCartUseCase = DefaultRemoveItemFromCartUseCase(cartRepository: self.mockedRepository)
        self.clearCartUseCase = DefaultClearCartUseCase(cartRepository: self.mockedRepository)
    }
    
    override func tearDownWithError() throws {
        self.getCartUseCase = nil
        self.addItemToCartUseCase = nil
        self.removeItemFromCartUseCase = nil
        self.clearCartUseCase = nil
        self.mockedRepository = nil
        self.mockedCoreDataStorage = nil
        self.cancellables = nil
        
        try super.tearDownWithError()
    }
}

// MARK: - Get cart
extension CartUseCasesTests {
    
    func testGetCartUseCase_whenSuccessFetchesEmptyCart_thenValidateEmptyCart() throws {
        // Given
        let expectationSave = XCTestExpectation(description: "Background perform and wait")
        self.mockedCoreDataStorage.backgroundContext.expectation = expectationSave
        
        // When
        let cart = try awaitPublisher(self.getCartUseCase.getCart())
        wait(for: [expectationSave], timeout: 0.5)
        
        // Then
        XCTAssertNotNil(cart)
        XCTAssertTrue(cart.items.isEmpty)
        XCTAssertTrue(self.mockedCoreDataStorage.backgroundContext.saveWasCalled)
    }
}

// MARK: - Add item
extension CartUseCasesTests {
    
    func testGetCartUseCase_whenAddNewItemToCart_thenShowCartWithOneItem() throws {
        // Given
        let addedItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 2 })!
        let addedQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedItem))
        
        // When
        let expectationSave = XCTestExpectation(description: "Background perform and wait")
        self.mockedCoreDataStorage.backgroundContext.expectation = expectationSave
        let updatedCart = try awaitPublisher(self.getCartUseCase.getCart())
        wait(for: [expectationSave], timeout: 0.5)
        
        // Then
        XCTAssertEqual(addedQuantity, 1)
        XCTAssertEqual(updatedCart.items.first?.code, 2)
        XCTAssertEqual(updatedCart.items.first?.name, "Cabify Coffee Mug")
        XCTAssertEqual(updatedCart.items.first?.quantity, 1)
        XCTAssertEqual(updatedCart.items.first?.price, 7.5)
        XCTAssertTrue(self.mockedCoreDataStorage.backgroundContext.saveWasCalled)
    }
    
    func testGetCartUseCase_whenAddItemTwiceToCart_thenShowCartWithOneItemAndTwoQuantities() throws {
        // Given
        let addedItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 2 })!
        let addedQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedItem))
        let addedMoreQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedItem))
        
        // When
        // Updated cart
        let expectationSave = XCTestExpectation(description: "Background perform and wait")
        self.mockedCoreDataStorage.backgroundContext.expectation = expectationSave
        let updatedCart = try awaitPublisher(self.getCartUseCase.getCart())
        wait(for: [expectationSave], timeout: 0.5)
        
        // Then
        XCTAssertEqual(addedQuantity, 1)
        XCTAssertEqual(addedMoreQuantity, 2)
        XCTAssertEqual(updatedCart.items.count, 1)
        XCTAssertEqual(updatedCart.items.first?.code, 2)
        XCTAssertEqual(updatedCart.items.first?.name, "Cabify Coffee Mug")
        XCTAssertEqual(updatedCart.items.first?.quantity, 2)
        XCTAssertEqual(updatedCart.items.first?.price, 7.5)
        XCTAssertTrue(self.mockedCoreDataStorage.backgroundContext.saveWasCalled)
    }
    
    func testGetCartUseCase_whenAddTwoItemsToCart_thenShowCartWithTwoItems() throws {
        // Given
        let addedFirstItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 2 })!
        let addedFirstQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedFirstItem))
        let addedLastItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 0 })!
        let addedLastQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedLastItem))
        
        // Updated cart
        let expectationSave = XCTestExpectation(description: "Background perform and wait")
        self.mockedCoreDataStorage.backgroundContext.expectation = expectationSave
        let updatedCart = try awaitPublisher(self.getCartUseCase.getCart())
        wait(for: [expectationSave], timeout: 0.5)
        
        // Then
        XCTAssertEqual(addedFirstQuantity, 1)
        XCTAssertEqual(addedLastQuantity, 1)
        XCTAssertEqual(updatedCart.items.count, 2)
        XCTAssertEqual(updatedCart.items.first?.code, 0)
        XCTAssertEqual(updatedCart.items.first?.name, "Cabify Voucher")
        XCTAssertEqual(updatedCart.items.first?.quantity, 1)
        XCTAssertEqual(updatedCart.items.first?.price, 5.0)
        XCTAssertEqual(updatedCart.items.last?.code, 2)
        XCTAssertEqual(updatedCart.items.last?.name, "Cabify Coffee Mug")
        XCTAssertEqual(updatedCart.items.last?.quantity, 1)
        XCTAssertEqual(updatedCart.items.last?.price, 7.5)
        XCTAssertTrue(self.mockedCoreDataStorage.backgroundContext.saveWasCalled)
    }
}

// MARK: - Remove item
extension CartUseCasesTests {
    
    func testGetCartUseCase_whenRemoveItemFromCart_thenShowEmptyCart() throws {
        // Given
        let anItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 2 })!
        let addedQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(anItem))
        let removedQuantity = try awaitPublisher(self.removeItemFromCartUseCase.removeItem(anItem))
        
        // When
        let expectationSave = XCTestExpectation(description: "Background perform and wait")
        self.mockedCoreDataStorage.backgroundContext.expectation = expectationSave
        let emptyCart = try awaitPublisher(self.getCartUseCase.getCart())
        wait(for: [expectationSave], timeout: 0.5)
        
        // Then
        XCTAssertEqual(addedQuantity, 1)
        XCTAssertEqual(removedQuantity, 0)
        XCTAssertEqual(emptyCart.items.first?.code, 2)
        XCTAssertEqual(emptyCart.items.first?.name, "Cabify Coffee Mug")
        XCTAssertEqual(emptyCart.items.first?.quantity, 0)
        XCTAssertEqual(emptyCart.items.first?.price, 7.5)
        XCTAssertTrue(self.mockedCoreDataStorage.backgroundContext.saveWasCalled)
    }
}

// MARK: - Clear cart
extension CartUseCasesTests {
    
    func testGetCartUseCase_whenClearCart_thenShowEmptyCart() throws {
        // Given
        let addedFirstItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 2 })!
        let addedFirstQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedFirstItem))
        let addedLastItem = MockedDefaultCartRepository.mockedModel.items.first(where: { $0.code == 0 })!
        let addedLastQuantity = try awaitPublisher(self.addItemToCartUseCase.addItem(addedLastItem))
        
        // When
        let expectationSave = XCTestExpectation(description: "Background perform and wait")
        self.mockedCoreDataStorage.backgroundContext.expectation = expectationSave
        let clearedCart = try awaitPublisher(self.clearCartUseCase.clearCart())
        wait(for: [expectationSave], timeout: 0.5)
        
        // Then
        XCTAssertEqual(addedFirstQuantity, 1)
        XCTAssertEqual(addedLastQuantity, 1)
        XCTAssertTrue(clearedCart.items.filter({ $0.quantity > 0}).isEmpty)
        XCTAssertTrue(self.mockedCoreDataStorage.backgroundContext.saveWasCalled)
    }
}
