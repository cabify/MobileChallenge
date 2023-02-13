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
    
    private var coordinator: ProductsListCoordinator!
    private var cancellables: Set<AnyCancellable> = []
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        self.coordinator = ProductsListCoordinator(
            productsListRepository: MockedProductsListRepository(),
            cartRepository: MockedCartRepository()
        )
    }
    
    override func tearDownWithError() throws {
        self.coordinator = nil
        try super.tearDownWithError()
    }
    
    // MARK: - Load
    // Success
    func testProductsViewModel_whenSuccessfullyLoadsContent_thenShowProductsList() throws {
        // Given
        let expectation = XCTestExpectation(description: "View model fetches products")
        
        self.coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let cart):
                // Summary
                XCTAssertEqual(cart.formattedSubtotal, "0.00€")
                XCTAssertFalse(cart.showDiscounts)
                XCTAssertEqual(cart.discounts.count, 0)
                XCTAssertNil(cart.discounts.first?.formattedValue)
                XCTAssertTrue(cart.discounts.isEmpty)
                XCTAssertEqual(cart.formattedTotal, "0.00€")
                
                // Items
                XCTAssertEqual(cart.items.count, 3)
                // First
                let firstItem = cart.items[0]
                XCTAssertEqual(firstItem.productType, .voucher)
                XCTAssertEqual(firstItem.name, "Cabify Voucher")
                XCTAssertTrue(firstItem.showDiscountBadge)
                XCTAssertEqual(firstItem.cartQuantity, 0)
                XCTAssertEqual(firstItem.formattedPrice, "5.00€")
                XCTAssertFalse(firstItem.showSpecialPrice)
                XCTAssertNil(firstItem.formattedSpecialPrice)
                XCTAssertEqual(firstItem.totalDiscounts, 0.0)
                XCTAssertEqual(firstItem.totalPrice, 0.0)
                XCTAssertEqual(firstItem.formattedTotalPrice, "0.00€")
                XCTAssertNil(firstItem.totalPriceWithDiscounts)
                XCTAssertNil(firstItem.formattedTotalPriceWithDiscounts)
                
                // Second
                let secondItem = cart.items[1]
                XCTAssertEqual(secondItem.productType, .tShirt)
                XCTAssertEqual(secondItem.name, "Cabify T-Shirt")
                XCTAssertTrue(secondItem.showDiscountBadge)
                XCTAssertEqual(secondItem.cartQuantity, 0)
                XCTAssertEqual(secondItem.formattedPrice, "20.00€")
                XCTAssertFalse(secondItem.showSpecialPrice)
                XCTAssertNil(secondItem.formattedSpecialPrice)
                XCTAssertEqual(secondItem.totalDiscounts, 0.0)
                XCTAssertEqual(secondItem.totalPrice, 0.0)
                XCTAssertEqual(secondItem.formattedTotalPrice, "0.00€")
                XCTAssertNil(secondItem.totalPriceWithDiscounts)
                XCTAssertNil(secondItem.formattedTotalPriceWithDiscounts)
                
                // Third
                let thirdItem = cart.items[2]
                XCTAssertEqual(thirdItem.productType, .mug)
                XCTAssertEqual(thirdItem.name, "Cabify Coffee Mug")
                XCTAssertFalse(thirdItem.showDiscountBadge)
                XCTAssertEqual(thirdItem.cartQuantity, 0)
                XCTAssertEqual(thirdItem.formattedPrice, "7.50€")
                XCTAssertFalse(thirdItem.showSpecialPrice)
                XCTAssertNil(thirdItem.formattedSpecialPrice)
                XCTAssertEqual(thirdItem.totalDiscounts, 0.0)
                XCTAssertEqual(thirdItem.totalPrice, 0.0)
                XCTAssertEqual(thirdItem.formattedTotalPrice, "0.00€")
                XCTAssertNil(thirdItem.totalPriceWithDiscounts)
                XCTAssertNil(thirdItem.formattedTotalPriceWithDiscounts)
            }
            expectation.fulfill()
            
        }.store(in: &cancellables)
        
        let viewModel = self.coordinator.productsViewModel
        XCTAssertEqual(self.coordinator.viewState.state, .idle)
        viewModel?.load()
    }
    
    // Fail
    func testProductsViewModel_whenFailedLoadsContent_thenThrowAnError() throws {
        
    }
}
