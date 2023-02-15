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
    private var mockedCoreDataStorage: MockedCoreDataStorage!
    private var mockedRepository: CartRepository!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        self.mockedCoreDataStorage = MockedCoreDataStorage()
        self.mockedRepository = MockedDefaultCartRepository.repository(backgroundContext: self.mockedCoreDataStorage.backgroundContext)
        
        // Coordinator with empty cart
        self.coordinator = ProductsListCoordinator(
            productsListRepository: MockedDefaultProductsListRepository.repository,
            cartRepository: self.mockedRepository
        )
    }
    
    override func tearDownWithError() throws {
        self.coordinator = nil
        self.mockedRepository = nil
        self.mockedCoreDataStorage = nil
        
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
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectation.fulfill()
            }
        }
        
        // When
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        wait(for: [expectation], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
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
            productsListRepository: MockedDefaultProductsListRepository.customRepository(statusCode: 600),
            cartRepository: MockedCartRepository.mockedDefaultRepository
        )
        var errorMessage: String?
        var cancellable: AnyCancellable? = coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .loaded: return
            case .failed(let error):
                errorMessage = (error as? APIError)?.errorDescription
                expectation.fulfill()
            }
        }
        
        // When
        let viewModel = coordinator.productsViewModel
        XCTAssertEqual(coordinator.viewState.state, .idle)
        viewModel?.load()
        wait(for: [expectation], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertEqual(errorMessage, "Some error occured, Please try again.")
    }
}

// MARK: - Add/Remove products from cart
extension ProductsViewModelTests {
    // Add
    func testProductsViewModel_whenSuccessfullyAddProductToCart_thenShowProductsUpdated() throws {
        // Given
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        let expectationAddProduct = XCTestExpectation(description: "View model adds a product to cart")
        var emptyCart: CartLayoutViewModel?
        var cart: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if emptyCart == nil {
                    emptyCart = loadedCart
                    expectationLoad.fulfill()
                    
                } else if cart == nil {
                    cart = loadedCart
                    expectationAddProduct.fulfill()
                }
            }
        }
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        wait(for: [expectationLoad], timeout: 0.5)
        
        // When
        let productType = ProductType.voucher
        let addProduct = try XCTUnwrap(emptyCart?.items.first(where: { $0.productType == productType }))
        viewModel?.addItemToCart(addProduct)
        wait(for: [expectationAddProduct], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertTrue(emptyCart?.cartItems.isEmpty ?? false)
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
        let expectationAddProduct = XCTestExpectation(description: "View model adds a product to cart")
        let expectationAddProductOneMore = XCTestExpectation(description: "View model adds one more of the same product to cart")
        let expectationAddProductOneMoreAgain = XCTestExpectation(description: "View model adds one more from same product to cart")
        var emptyCart: CartLayoutViewModel?
        var cart: CartLayoutViewModel?
        var cartOneMore: CartLayoutViewModel?
        var cartOneMoreAgain: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if emptyCart == nil {
                    emptyCart = loadedCart
                    expectationLoad.fulfill()
                    
                } else if cart == nil {
                    cart = loadedCart
                    expectationAddProduct.fulfill()
                    
                } else if cartOneMore == nil {
                    cartOneMore = loadedCart
                    expectationAddProductOneMore.fulfill()
                    
                } else if cartOneMoreAgain == nil {
                    cartOneMoreAgain = loadedCart
                    expectationAddProductOneMoreAgain.fulfill()
                }
                
                emptyCart = loadedCart
                expectationLoad.fulfill()
            }
        }
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        wait(for: [expectationLoad], timeout: 0.5)
        
        // When
        // Add product
        let productType = ProductType.tShirt
        let addProduct = try XCTUnwrap(emptyCart?.items.first(where: { $0.productType == productType }))
        viewModel?.addItemToCart(addProduct)
        wait(for: [expectationAddProduct], timeout: 0.5)
        // Add one more
        viewModel?.addItemToCart(addProduct)
        wait(for: [expectationAddProductOneMore], timeout: 0.5)
        // Add one more again
        viewModel?.addItemToCart(addProduct)
        wait(for: [expectationAddProductOneMoreAgain], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertFalse(emptyCart?.cartItems.isEmpty ?? true)
        let firstItem = cartOneMoreAgain?.cartItems.first
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
        let expectationAddProduct = XCTestExpectation(description: "View model adds a product to cart")
        let expectationRemoveProduct = XCTestExpectation(description: "View model removes the product from cart")
        var emptyCart: CartLayoutViewModel?
        var cart: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if emptyCart == nil {
                    emptyCart = loadedCart
                    expectationLoad.fulfill()
                    
                } else if cart == nil {
                    cart = loadedCart
                    expectationAddProduct.fulfill()
                    
                } else {
                    cart = loadedCart
                    expectationRemoveProduct.fulfill()
                }
            }
        }
        let viewModel = self.coordinator?.productsViewModel
        XCTAssertEqual(self.coordinator?.viewState.state, .idle)
        viewModel?.load()
        wait(for: [expectationLoad], timeout: 0.5)
        // Add
        let productType = ProductType.voucher
        let addProduct = try XCTUnwrap(emptyCart?.items.first(where: { $0.productType == productType }))
        viewModel?.addItemToCart(addProduct)
        wait(for: [expectationAddProduct], timeout: 0.5)
        
        // When
        let removeProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        viewModel?.removeItemFromCart(removeProduct)
        wait(for: [expectationRemoveProduct], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertTrue(emptyCart?.cartItems.isEmpty ?? false)
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
    }
}
