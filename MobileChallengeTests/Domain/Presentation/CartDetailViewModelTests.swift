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
    
    private var coordinator: ProductsListCoordinator?
    private var mockedCoreDataStorage: MockedCoreDataStorage!
    private var mockedRepository: CartRepository!
    
    override func setUpWithError() throws {
        try super.setUpWithError()
        
        self.mockedCoreDataStorage = MockedCoreDataStorage()
        self.mockedRepository = MockedDefaultCartRepository.repository(backgroundContext: self.mockedCoreDataStorage.backgroundContext)
        
        // Coordinator with pre-fetched cart
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
extension CartDetailViewModelTests {
    // Empty cart
    func testCartDetailViewModel_whenSuccessfullyLoadsEmptyCart_thenShowCartSummary() throws {
        // Given
        let coordinator = ProductsListCoordinator(
            productsListRepository: MockedDefaultProductsListRepository.repository,
            cartRepository: MockedCartRepository.mockedDefaultRepository
        )
        
        var cart: CartLayoutViewModel?
        let expectationLoad = XCTestExpectation(description: "View model fetches products")
        var cancellable: AnyCancellable? = coordinator.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                cart = loadedCart
                expectationLoad.fulfill()
            }
        }
        
        let productsListViewModel = coordinator.productsViewModel
        productsListViewModel?.load()
        wait(for: [expectationLoad], timeout: 0.5)
        
        // When
        coordinator.openCart()
        
        cancellable?.cancel()
        cancellable = nil
        
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        var cart: CartLayoutViewModel?
        var cartLoaded: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartLoaded == nil {
                    cartLoaded = loadedCart
                    expectationPreFetchedItem.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        // When
        coordinator?.openCart()
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertFalse(cartLoaded?.cartItems.isEmpty ?? true)
        let firstItem = cartLoaded?.cartItems.first
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        let expectationAddCartItem = XCTestExpectation(description: "View model adds new product to cart")
        var cart: CartLayoutViewModel?
        var cartPreFetched: CartLayoutViewModel?
        var cartNewItem: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartPreFetched == nil {
                    cartPreFetched = loadedCart
                    expectationPreFetchedItem.fulfill()
                    
                } else if cartNewItem == nil {
                    cartNewItem = loadedCart
                    expectationAddCartItem.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        self.coordinator?.openCart()
        
        // When
        let productType = ProductType.tShirt
        let addCartItem = try XCTUnwrap(cartPreFetched?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinator?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        wait(for: [expectationAddCartItem], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertEqual(cartNewItem?.cartItems.count, 2)
        let firstItem = cartNewItem?.cartItems.first
        XCTAssertEqual(firstItem?.productType, .voucher)
        XCTAssertEqual(firstItem?.name, "Cabify Voucher")
        XCTAssertTrue(firstItem?.showDiscountBadge ?? false)
        XCTAssertEqual(firstItem?.cartQuantity, 1)
        XCTAssertEqual(firstItem?.formattedPrice, "5.00€")
        
        let lastItem = cartNewItem?.cartItems.last
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        let expectationAddCartItem = XCTestExpectation(description: "View model adds new product to cart")
        var cart: CartLayoutViewModel?
        var cartPreFetched: CartLayoutViewModel?
        var cartNewItem: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartPreFetched == nil {
                    cartPreFetched = loadedCart
                    expectationPreFetchedItem.fulfill()
                    
                } else if cartNewItem == nil {
                    cartNewItem = loadedCart
                    expectationAddCartItem.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        self.coordinator?.openCart()
        
        // When
        let productType = ProductType.voucher
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinator?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        wait(for: [expectationAddCartItem], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertEqual(cartNewItem?.cartItems.count, 1)
        XCTAssertEqual(cartNewItem?.formattedSubtotal, "10.00€")
        XCTAssertTrue(cartNewItem?.showDiscounts ?? false)
        XCTAssertFalse(cartNewItem?.discounts.isEmpty ?? true)
        XCTAssertEqual(cartNewItem?.discounts.first?.text, "Buy 2 and get 1 free")
        XCTAssertEqual(cartNewItem?.discounts.first?.formattedValue, "5.00€")
        XCTAssertEqual(cartNewItem?.formattedTotal, "5.00€")
        let firstItem = cartNewItem?.cartItems.first
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        let expectationLastCartItem = XCTestExpectation(description: "View model adds new product to cart")
        var cart: CartLayoutViewModel?
        var cartPreFetched: CartLayoutViewModel?
        var cartOneMoreVoucher: CartLayoutViewModel?
        var cartBuklOfVoucher: CartLayoutViewModel?
        var cartLastVoucher: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartPreFetched == nil {
                    cartPreFetched = loadedCart
                    expectationPreFetchedItem.fulfill()
                    
                } else if cartOneMoreVoucher == nil {
                    cartOneMoreVoucher = loadedCart
                    
                } else if cartBuklOfVoucher == nil {
                    cartBuklOfVoucher = loadedCart
                    
                } else if cartLastVoucher == nil {
                    cartLastVoucher = loadedCart
                    expectationLastCartItem.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        self.coordinator?.openCart()
        
        // When
        let productType = ProductType.voucher
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinator?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        wait(for: [expectationLastCartItem], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertEqual(cartLastVoucher?.cartItems.count, 1)
        XCTAssertEqual(cartLastVoucher?.formattedSubtotal, "25.00€")
        XCTAssertTrue(cartLastVoucher?.showDiscounts ?? false)
        XCTAssertFalse(cartLastVoucher?.discounts.isEmpty ?? true)
        XCTAssertEqual(cartLastVoucher?.discounts.first?.text, "Buy 2 and get 1 free")
        XCTAssertEqual(cartLastVoucher?.discounts.first?.formattedValue, "10.00€")
        XCTAssertEqual(cartLastVoucher?.formattedTotal, "15.00€")
        let firstItem = cartLastVoucher?.cartItems.first
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        let expectationLastCartItem = XCTestExpectation(description: "View model adds new product to cart")
        var cart: CartLayoutViewModel?
        var cartPreFetched: CartLayoutViewModel?
        var cartOneMoreTshirt: CartLayoutViewModel?
        var cartBulkOfTshirts: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartPreFetched == nil {
                    cartPreFetched = loadedCart
                    expectationPreFetchedItem.fulfill()
                    
                } else if cartOneMoreTshirt == nil {
                    cartOneMoreTshirt = loadedCart
                    
                } else if cartBulkOfTshirts == nil {
                    cartBulkOfTshirts = loadedCart
                    expectationLastCartItem.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let productType = ProductType.tShirt
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        self.coordinator?.openCart()
        
        // When
        let addCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinator?.cartDetailViewModel
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        cartDetailViewModel?.addItemToCart(addCartItem)
        wait(for: [expectationLastCartItem], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertEqual(cartBulkOfTshirts?.cartItems.count, 1)
        XCTAssertEqual(cartBulkOfTshirts?.formattedSubtotal, "80.00€")
        XCTAssertTrue(cartBulkOfTshirts?.showDiscounts ?? false)
        XCTAssertFalse(cartBulkOfTshirts?.discounts.isEmpty ?? true)
        XCTAssertEqual(cartBulkOfTshirts?.discounts.last?.text, "€1 discount per unit for 3+")
        XCTAssertEqual(cartBulkOfTshirts?.discounts.last?.formattedValue, "4.00€")
        XCTAssertEqual(cartBulkOfTshirts?.formattedTotal, "76.00€")
        let tShirtCartItem = cartBulkOfTshirts?.cartItems.first
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        let expectationRemoveCartItem = XCTestExpectation(description: "View model removes cart item from cart")
        var cart: CartLayoutViewModel?
        var cartPreFetched: CartLayoutViewModel?
        var cartRemoved: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartPreFetched == nil {
                    cartPreFetched = loadedCart
                    expectationPreFetchedItem.fulfill()
                    
                } else if cartRemoved == nil {
                    cartRemoved = loadedCart
                    expectationRemoveCartItem.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        self.coordinator?.openCart()
        
        // When
        let productType = ProductType.voucher
        let removeCartItem = try XCTUnwrap(cart?.items.first(where: { $0.productType == productType }))
        let cartDetailViewModel = self.coordinator?.cartDetailViewModel
        cartDetailViewModel?.removeItemFromCart(removeCartItem)
        wait(for: [expectationRemoveCartItem], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
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
        let expectationLoadProducts = XCTestExpectation(description: "View model fetches products")
        let expectationPreFetchedItem = XCTestExpectation(description: "View model add pre-fetched item")
        let expectationClearCart = XCTestExpectation(description: "View model clears cart")
        var cart: CartLayoutViewModel?
        var cartPreFetched: CartLayoutViewModel?
        var cartCleared: CartLayoutViewModel?
        var cancellable: AnyCancellable? = self.coordinator?.viewState.$state.sink { state in
            switch state {
            case .idle, .loading, .failed: return
            case .loaded(let loadedCart):
                if cart == nil {
                    cart = loadedCart
                    expectationLoadProducts.fulfill()
                    
                } else if cartPreFetched == nil {
                    cartPreFetched = loadedCart
                    expectationPreFetchedItem.fulfill()
                    
                } else if cartCleared == nil {
                    cartCleared = loadedCart
                    expectationClearCart.fulfill()
                }
            }
        }
        let productsListViewModelPrefetchedCart = coordinator?.productsViewModel
        productsListViewModelPrefetchedCart?.load()
        wait(for: [expectationLoadProducts], timeout: 0.5)
        
        // Add pre-fetched item to cart
        let addProduct = try XCTUnwrap(cart?.items.first(where: { $0.productType == .voucher }))
        productsListViewModelPrefetchedCart?.addItemToCart(addProduct)
        wait(for: [expectationPreFetchedItem], timeout: 0.5)
        
        self.coordinator?.openCart()
        
        // When
        let cartDetailViewModel = self.coordinator?.cartDetailViewModel
        cartDetailViewModel?.clearCart()
        wait(for: [expectationClearCart], timeout: 0.5)
        
        cancellable?.cancel()
        cancellable = nil
        
        // Then
        XCTAssertTrue(cart?.cartItems.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedSubtotal, "0.00€")
        XCTAssertFalse(cart?.showDiscounts ?? true)
        XCTAssertTrue(cart?.discounts.isEmpty ?? false)
        XCTAssertEqual(cart?.formattedTotal, "0.00€")
    }
}
