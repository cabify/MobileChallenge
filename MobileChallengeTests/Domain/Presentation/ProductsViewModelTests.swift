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
                XCTAssertEqual(cart.formattedSubtotal, "90.00€")
                XCTAssertEqual(cart.discounts.count, 2)
                XCTAssertEqual(cart.discounts.first?.text, "Buy 2 and get 1 free")
                XCTAssertEqual(cart.discounts.first?.formattedValue, "5.00€")
                XCTAssertEqual(cart.discounts.last?.text, "€1 discount per unit for 3+")
                XCTAssertEqual(cart.discounts.last?.formattedValue, "4.00€")
                XCTAssertEqual(cart.formattedTotal, "81.00€")
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
