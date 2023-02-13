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
    
    private var coordinator: ProductsListCoordinator?
    private var cancellables: Set<AnyCancellable> = []
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        // Coordinator with empty cart
        self.coordinator = ProductsListCoordinator(
            productsListRepository: MockedProductsListRepository(),
            cartRepository: MockedCartRepository()
        )
    }
    
    override func tearDownWithError() throws {
        self.coordinator = nil
        
        try super.tearDownWithError()
    }
}

// MARK: - Load
extension ProductsViewModelTests {
    // Success
    func testProductsViewModel_whenSuccessfullyLoadsContent_thenShowProductsList() throws {
        // Given
        let expectation = XCTestExpectation(description: "View model fetches products")
        var cart: CartLayoutViewModel?
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectation.fulfill()
            }
        }.store(in: &cancellables)
        
        // When
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
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
        // Custom coordinator with mocked error for repository
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
    // Add
    func testProductsViewModel_whenSuccessfullyAddProductToCart_thenShowProductsUpdated() throws {
        // Given
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        var cart: CartLayoutViewModel?
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoad.fulfill()
            }
        }.store(in: &cancellables)
        
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        
        wait(for: [expectationLoad], timeout: 0.5)
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        
        // When
        let productType = ProductType.voucher
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let expectationAddProduct = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddProduct.fulfill()
            }
        }.store(in: &cancellables)
        viewModel?.addItemToCart(addProduct)
        
        wait(for: [expectationAddProduct], timeout: 0.5)
        
        // Then
        XCTAssertFalse(cart?.cartItems.isEmpty ?? true)
        let firstItem = cart?.cartItems.first
        XCTAssertEqual(firstItem?.productType, productType)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 1)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
    }
    
    // Add and see special price
    func testProductsViewModel_whenSuccessfullyAddManyProductsToCart_thenShowProductsUpdatedWithSpecialPrice() throws {
        // Given
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        var cart: CartLayoutViewModel?
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoad.fulfill()
            }
        }.store(in: &cancellables)
        
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        
        wait(for: [expectationLoad], timeout: 0.5)
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        
        // When
        let productType = ProductType.tShirt
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        // Add 1
        let expectationAddProduct1 = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddProduct1.fulfill()
            }
        }.store(in: &cancellables)
        viewModel?.addItemToCart(addProduct)
        // Add 2
        let expectationAddProduct2 = XCTestExpectation(description: "View model adds one more from same product to cart")
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddProduct2.fulfill()
            }
        }.store(in: &cancellables)
        viewModel?.addItemToCart(addProduct)
        // Add 3
        let expectationAddProduct3 = XCTestExpectation(description: "View model adds one more from same product to cart")
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddProduct3.fulfill()
            }
        }.store(in: &cancellables)
        viewModel?.addItemToCart(addProduct)
        
        wait(for: [expectationAddProduct1, expectationAddProduct2, expectationAddProduct3], timeout: 0.5)
        
        // Then
        XCTAssertFalse(cart?.cartItems.isEmpty ?? true)
        let firstItem = cart?.cartItems.first
        XCTAssertEqual(firstItem?.productType, productType)
        XCTAssertEqual(firstItem?.name, "Cabify T-Shirt")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 3)
        XCTAssertEqual(firstItem?.formattedPrice, "20.00€")
        XCTAssertTrue(firstItem?.showSpecialPrice ?? false)
        XCTAssertEqual(firstItem?.formattedSpecialPrice, "19.00€")
    }
    
    // Remove
    func testProductsViewModel_whenSuccessfullyRemoveProductFromCart_thenShowProductsUpdated() throws {
        // Given
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        var cart: CartLayoutViewModel?
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoad.fulfill()
            }
        }.store(in: &cancellables)
        
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        
        wait(for: [expectationLoad], timeout: 0.5)
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        let expectationAddProduct = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddProduct.fulfill()
            }
        }.store(in: &cancellables)
        viewModel?.addItemToCart(addProduct)
        wait(for: [expectationAddProduct], timeout: 0.5)
        
        // When
        let removeProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        let expectationRemoveProduct = XCTestExpectation(description: "View model removes new product to cart")
        self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationRemoveProduct.fulfill()
            }
        }.store(in: &cancellables)
        viewModel?.removeItemFromCart(removeProduct)
        
        wait(for: [expectationRemoveProduct], timeout: 0.5)
        
        // Then
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
    }
}
