//
//  CartDetailViewModelTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 13/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class CartDetailViewModelTests: XCTestCase {
    
    private var coordinatorPrefetchedCart: ProductsListCoordinator!
    private var cancellables: Set<AnyCancellable> = []
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        // Coordinator with pre-fetched cart
        self.coordinatorPrefetchedCart = ProductsListCoordinator(
            productsListRepository: MockedDefaultProductsListRepository.mockedDefaultRepository,
            cartRepository: MockedCartRepository.mockedDefaultRepository
        )
        // Pre-fetched products list with empty cart
        var cart: CartLayoutViewModel?
        let expectationLoadProducts = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoadProducts.fulfill()
            }
        }.store(in: &cancellables)
        let productsListViewModelPrefetchedCart = coordinatorPrefetchedCart.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        // Wait for products load
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add a t-shirt product to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
    }
    
    override func tearDownWithError() throws {
        self.coordinatorPrefetchedCart = nil
        
        try super.tearDownWithError()
    }
}

// MARK: - Load
extension CartDetailViewModelTests {
    // Empty cart
    func testCartDetailViewModel_whenSuccessfullyLoadsEmptyCart_thenShowCartSummary() throws {
        // Given
        let coordinator = ProductsListCoordinator(
            productsListRepository: MockedDefaultProductsListRepository.mockedDefaultRepository,
            cartRepository: MockedCartRepository.mockedDefaultRepository
        )
        
        var cart: CartLayoutViewModel?
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoad.fulfill()
            }
        }.store(in: &cancellables)
        
        let productsListViewModel = coordinator.productsViewModel
        productsListViewModel?.load()
        wait(for: [expectationLoad], timeout: 0.5)
        
        // When
        coordinator.openCart()
        
        // Then
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedSubtotal, "0.00€")
        XCTAssertFalse(cart?.showDiscounts ?? true)
        XCTAssertTrue(cart?.discounts.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedTotal, "0.00€")
    }
    
    // With item
    func testCartDetailViewModel_whenSuccessfullyLoadsCartWithItem_thenShowCartSummaryAndItems() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        
        // When
        coordinatorPrefetchedCart?.openCart()
        
        // Then
        XCTAssertFalse(cart?.cartItems.isEmpty ?? true)
        let firstItem = cart?.cartItems.first
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 1)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
    }
}

// MARK: - Add items to cart
extension CartDetailViewModelTests {
    // Add new cart item to cart
    func testCartDetailViewModel_whenSuccessfullyAddItemToCart_thenShowCartSummaryWithNoDiscountsAndItems() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        self.coordinatorPrefetchedCart?.openCart()
        
        // When
        let productType = ProductType.tShirt
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let expectationAddCartItem = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddCartItem.fulfill()
            }
        }.store(in: &cancellables)
        let cartDetailViewModel = self.coordinatorPrefetchedCart?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        
        wait(for: [expectationAddCartItem], timeout: 0.5)
        
        // Then
        XCTAssertEqual(cart?.cartItems.count, 2)
        let firstItem = cart?.cartItems.first
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 1)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
        
        let lastItem = cart?.cartItems.last
        XCTAssertEqual(lastItem?.productType, .tShirt)
        XCTAssertEqual(lastItem?.name, "Cabify T-Shirt")
        XCTAssertTrue(lastItem?.showDiscountBadge ?? false)
        XCTAssertEqual(lastItem?.cartQuantity, 1)
        XCTAssertEqual(lastItem?.formattedPrice, "20.00€")
        XCTAssertNil(lastItem?.formattedSpecialPrice)
        XCTAssertNil(lastItem?.formattedTotalPriceWithDiscounts)
    }
    
    // Add one more voucher and see discounts
    func testCartDetailViewModel_whenSuccessfullyAddOneMoreVoucherToCart_thenShowCartSummaryAndDiscounts() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        self.coordinatorPrefetchedCart?.openCart()
        
        // When
        let productType = ProductType.voucher
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let expectationAddCartItem = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationAddCartItem.fulfill()
            }
        }.store(in: &cancellables)
        let cartDetailViewModel = self.coordinatorPrefetchedCart?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        
        wait(for: [expectationAddCartItem], timeout: 0.5)
        
        // Then
        XCTAssertEqual(cart?.cartItems.count, 1)
        XCTAssertEqual(cart?.formattedSubtotal, "10.00€")
        XCTAssertTrue(cart?.showDiscounts ?? false)
        XCTAssertFalse(cart?.discounts.isEmpty ?? true)
        XCTAssertEqual(cart?.discounts.first?.text, "Buy 2 and get 1 free")
        XCTAssertEqual(cart?.discounts.first?.formattedValue, "5.00€")
        XCTAssertEqual(cart?.formattedTotal, "5.00€")
        let firstItem = cart?.cartItems.first
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 2)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
        XCTAssertEqual(firstItem?.formattedTotalPrice, "10.00€")
        XCTAssertEqual(firstItem?.formattedTotalPriceWithDiscounts, "5.00€")
    }
    
    // Add more vouchers cart items and finish with odd number
    func testCartDetailViewModel_whenSuccessfullyAddMoreVouchersToCart_thenShowCartSummaryAndDiscountsWithOddNumberOfVouchers() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        self.coordinatorPrefetchedCart?.openCart()
        
        // When
        let productType = ProductType.voucher
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinatorPrefetchedCart?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        
        let expectationLastCartItem = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLastCartItem.fulfill()
            }
        }.store(in: &cancellables)
        
        cartDetailViewModel?.addItemToCart(addCartItem)
        wait(for: [expectationLastCartItem], timeout: 0.5)
        
        // Then
        XCTAssertEqual(cart?.cartItems.count, 1)
        XCTAssertEqual(cart?.formattedSubtotal, "25.00€")
        XCTAssertTrue(cart?.showDiscounts ?? false)
        XCTAssertFalse(cart?.discounts.isEmpty ?? true)
        XCTAssertEqual(cart?.discounts.first?.text, "Buy 2 and get 1 free")
        XCTAssertEqual(cart?.discounts.first?.formattedValue, "10.00€")
        XCTAssertEqual(cart?.formattedTotal, "15.00€")
        let firstItem = cart?.cartItems.first
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 5)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
        XCTAssertEqual(firstItem?.totalPrice, 25.00)
        XCTAssertEqual(firstItem?.formattedTotalPrice, "25.00€")
        XCTAssertEqual(firstItem?.totalPriceWithDiscounts, 15.00)
        XCTAssertEqual(firstItem?.formattedTotalPriceWithDiscounts, "15.00€")
    }
    
    // Add many t-shirt cart items and visualize the prices with discount
    func testCartDetailViewModel_whenSuccessfullyAddManyTShirtsToCart_thenShowCartSummaryAndDiscountsPrices() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        self.coordinatorPrefetchedCart?.openCart()
        
        // When
        let productType = ProductType.tShirt
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinatorPrefetchedCart?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        
        let expectationLastCartItem = XCTestExpectation(description: "View model adds new product to cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLastCartItem.fulfill()
            }
        }.store(in: &cancellables)
        
        cartDetailViewModel?.addItemToCart(addCartItem)
        wait(for: [expectationLastCartItem], timeout: 0.5)
        
        // Then
        XCTAssertEqual(cart?.cartItems.count, 2)
        XCTAssertEqual(cart?.formattedSubtotal, "85.00€")
        XCTAssertTrue(cart?.showDiscounts ?? false)
        XCTAssertFalse(cart?.discounts.isEmpty ?? true)
        XCTAssertEqual(cart?.discounts.last?.text, "€1 discount per unit for 3+")
        XCTAssertEqual(cart?.discounts.last?.formattedValue, "4.00€")
        XCTAssertEqual(cart?.formattedTotal, "81.00€")
        let tShirtCartItem = cart?.cartItems.last
        XCTAssertEqual(tShirtCartItem?.productType, .tShirt)
        XCTAssertEqual(tShirtCartItem?.name, "Cabify T-Shirt")
        XCTAssertTrue(tShirtCartItem?.showDiscountBadge ?? false)
        XCTAssertEqual(tShirtCartItem?.cartQuantity, 4)
        XCTAssertEqual(tShirtCartItem?.formattedPrice, "20.00€")
        XCTAssertEqual(tShirtCartItem?.totalPrice, 80.0)
        XCTAssertEqual(tShirtCartItem?.formattedTotalPrice, "80.00€")
        XCTAssertEqual(tShirtCartItem?.totalPriceWithDiscounts, 76.00)
        XCTAssertEqual(tShirtCartItem?.formattedTotalPriceWithDiscounts, "76.00€")
    }
}

// MARK: - Remove items from cart
extension CartDetailViewModelTests {
    // Remove
    func testCartDetailViewModel_whenSuccessfullyRemovedItemFromCart_thenShowCartSummaryWithoutItems() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        self.coordinatorPrefetchedCart?.openCart()
        
        // When
        let productType = ProductType.voucher
        let removeCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let expectationRemoveCartItem = XCTestExpectation(description: "View model removes cart item from cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationRemoveCartItem.fulfill()
            }
        }.store(in: &cancellables)
        let cartDetailViewModel = self.coordinatorPrefetchedCart?.cartDetailViewModel
        cartDetailViewModel?.removeItemFromCart(removeCartItem)
        
        wait(for: [expectationRemoveCartItem], timeout: 0.5)
        
        // Then
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedSubtotal, "0.00€")
        XCTAssertFalse(cart?.showDiscounts ?? true)
        XCTAssertTrue(cart?.discounts.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedTotal, "0.00€")
    }
    
    // Remove
    func testCartDetailViewModel_whenSuccessfullyClearedCart_thenShowCartSummaryWithoutItems() throws {
        // Given
        var cart: CartLayoutViewModel?
        if case .loaded(let loadedCart) = self.coordinatorPrefetchedCart?.viewState.state {
            cart = loadedCart
        }
        self.coordinatorPrefetchedCart?.openCart()
        
        let productType = ProductType.tShirt
        let cartDetailViewModel = self.coordinatorPrefetchedCart?.cartDetailViewModel
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        cartDetailViewModel?.addItemToCart(addCartItem)
        
        // When
        let expectationClear = XCTestExpectation(description: "View model clears cart")
        self.coordinatorPrefetchedCart?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationClear.fulfill()
            }
        }.store(in: &cancellables)
        
        cartDetailViewModel?.clearCart()
        wait(for: [expectationClear], timeout: 0.5)
        
        // Then
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedSubtotal, "0.00€")
        XCTAssertFalse(cart?.showDiscounts ?? true)
        XCTAssertTrue(cart?.discounts.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedTotal, "0.00€")
    }
}
