//
//  ProductsViewModelTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class ProductsViewModelTests: XCTestCase {
    
    private var cancellables: Set<AnyCancellable> = []
    
    override func setUpWithError() throws {
        try super.setUpWithError()
    }
    
    override func tearDownWithError() throws {
        try super.tearDownWithError()
    }
    
}

// MARK: - Load
extension ProductsViewModelTests {
    // Success
    func testProductsViewModel_whenSuccessfullyLoadsContent_thenShowProductsList() throws {
        // Given
        let expectation = XCTestExpectation(description: "View model fetches products")
        let coordinator = ProductsListCoordinator(
            productsListRepository: MockedProductsListRepository(),
            cartRepository: MockedCartRepository()
        )
        var cart: CartLayoutViewModel?
        coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectation.fulfill()
            }
        }.store(in: &cancellables)
        
        // When
        let viewModel = coordinator.productsViewModel
        XCTAssertEqual(coordinator.viewState.state, .idle)
        viewModel?.load()
        
        wait(for: [expectation], timeout: 0.5)
        
        // Then
        let firstItem = cart?.items[0]
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 0)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
        let secondItem = cart?.items[1]
        XCTAssertEqual(secondItem?.productType, .tShirt)
        XCTAssertEqual(secondItem?.name, "Cabify T-Shirt")
        XCTAssertTrue(secondItem?.showDiscountBadge ?? false)
        XCTAssertEqual(secondItem?.cartQuantity, 0)
        XCTAssertEqual(secondItem?.formattedPrice, "20.00€")
        let thirdItem = cart?.items[2]
        XCTAssertEqual(thirdItem?.productType, .mug)
        XCTAssertEqual(thirdItem?.name, "Cabify Coffee Mug")
        XCTAssertFalse(thirdItem?.showDiscountBadge ?? true)
        XCTAssertEqual(thirdItem?.cartQuantity, 0)
        XCTAssertEqual(thirdItem?.formattedPrice, "7.50€")
    }
    
    // Fail
    func testProductsViewModel_whenFailedLoadsContent_thenThrowAnError() throws {
        // Given
        let expectation = XCTestExpectation(description: "View model fails to fetch products")
        let coordinator = ProductsListCoordinator(
            productsListRepository: MockedProductsListRepository(error: APIError.unknown(nil, "Unknown error")),
            cartRepository: MockedCartRepository()
        )
        var errorMessage: String?
        coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .loaded: return
            case .failed(let error):
                errorMessage = (error as? APIError)?.errorDescription
                expectation.fulfill()
            }
        }.store(in: &cancellables)
        
        // When
        let viewModel = coordinator.productsViewModel
        XCTAssertEqual(coordinator.viewState.state, .idle)
        viewModel?.load()
        
        wait(for: [expectation], timeout: 0.5)
        
        // Then
        XCTAssertEqual(errorMessage, "Unknown error")
    }
}

// MARK: - Add/Remove products from cart
extension ProductsViewModelTests {
    // Success
    func testProductsViewModel_whenSuccessfullyAddItemToCart_thenShowProductsUpdated() throws {
        // Given
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        let coordinator = ProductsListCoordinator(
            productsListRepository: MockedProductsListRepository(),
            cartRepository: MockedCartRepository()
        )
        
        var cart: CartLayoutViewModel?
        coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoad.fulfill()
            }
        }.store(in: &cancellables)
        
        let viewModel = coordinator.productsViewModel
        XCTAssertEqual(coordinator.viewState.state, .idle)
        viewModel?.load()
        
        wait(for: [expectationLoad], timeout: 0.5)
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        
        // When
        let expectationAddItem = XCTestExpectation(description: "View model adds new product to cart")
        coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddItem.fulfill()
            }
        }.store(in: &cancellables)
        
        let addItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        viewModel?.addItemToCart(addItem)
        
        wait(for: [expectationAddItem], timeout: 0.5)
        
        // Then
        XCTAssertFalse(cart?.cartItems.isEmpty ?? true)
        let firstItem = cart?.items.first
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 1)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
        XCTAssertFalse(firstItem?.showSpecialPrice ?? true)
    }
}
