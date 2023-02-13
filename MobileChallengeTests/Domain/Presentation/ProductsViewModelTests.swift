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
    
    // MARK: - Load
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
        XCTAssertFalse(firstItem?.showSpecialPrice ?? true)
        XCTAssertNil(firstItem?.formattedSpecialPrice)
        XCTAssertEqual(firstItem?.totalDiscounts, 0.0)
        XCTAssertEqual(firstItem?.totalPrice, 0.0)
        XCTAssertEqual(firstItem?.formattedTotalPrice, "0.00€")
        XCTAssertNil(firstItem?.totalPriceWithDiscounts)
        XCTAssertNil(firstItem?.formattedTotalPriceWithDiscounts)
        let secondItem = cart?.items[1]
        XCTAssertEqual(secondItem?.productType, .tShirt)
        XCTAssertEqual(secondItem?.name, "Cabify T-Shirt")
        XCTAssertTrue(secondItem?.showDiscountBadge ?? false)
        XCTAssertEqual(secondItem?.cartQuantity, 0)
        XCTAssertEqual(secondItem?.formattedPrice, "20.00€")
        XCTAssertFalse(secondItem?.showSpecialPrice ?? true)
        XCTAssertNil(secondItem?.formattedSpecialPrice)
        XCTAssertEqual(secondItem?.totalDiscounts, 0.0)
        XCTAssertEqual(secondItem?.totalPrice, 0.0)
        XCTAssertEqual(secondItem?.formattedTotalPrice, "0.00€")
        XCTAssertNil(secondItem?.totalPriceWithDiscounts)
        XCTAssertNil(secondItem?.formattedTotalPriceWithDiscounts)
        let thirdItem = cart?.items[2]
        XCTAssertEqual(thirdItem?.productType, .mug)
        XCTAssertEqual(thirdItem?.name, "Cabify Coffee Mug")
        XCTAssertFalse(thirdItem?.showDiscountBadge ?? true)
        XCTAssertEqual(thirdItem?.cartQuantity, 0)
        XCTAssertEqual(thirdItem?.formattedPrice, "7.50€")
        XCTAssertFalse(thirdItem?.showSpecialPrice ?? true)
        XCTAssertNil(thirdItem?.formattedSpecialPrice)
        XCTAssertEqual(thirdItem?.totalDiscounts, 0.0)
        XCTAssertEqual(thirdItem?.totalPrice, 0.0)
        XCTAssertEqual(thirdItem?.formattedTotalPrice, "0.00€")
        XCTAssertNil(thirdItem?.totalPriceWithDiscounts)
        XCTAssertNil(thirdItem?.formattedTotalPriceWithDiscounts)
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
