//
//  CartDetailViewTests.swift
//  MobileChallengeUITests
//
//  Created by thomas pereira on 16/02/2023.
//

import XCTest

// MARK: - CartDetailViewTests
extension BaseUITests {
    
    func test03_CartDetailView_whenCartIsShown_thenShowEmptyCart() throws {
        
        let emptyStateImages = app.images.containing(.image, identifier: AccessibilityID.CartDetailView.EmptyCart)
        let emptyStateImage = emptyStateImages.element(boundBy: 0)
        XCTAssertEqual(emptyStateImage.label, "Shopping Cart")

        let emptyStateTexts = app.staticTexts.containing(.staticText, identifier: AccessibilityID.CartDetailView.EmptyCart)
        let emptyStateText = emptyStateTexts.element(boundBy: 0)
        XCTAssertEqual(emptyStateText.label, "Your cart is empty. Go enjoy our discounts!")

        let emptyStateButtons = app.buttons.containing(.button, identifier: AccessibilityID.CartDetailView.EmptyCart)
        let emptyStateButton = emptyStateButtons.element(boundBy: 0)
        XCTAssertEqual(emptyStateButton.label, "Continue shopping")
        
        sleep(1)
    }
    
    func test12_CartDetailView_whenCartIsShown_thenShowSummary() throws {
        
        try test08_CartQuantityView_whenTapOnIncrease_thenConfirmState()
        try test02_CartButtonView_whenTapButton_thenShowCart()
        
        // Summary texts
        let cartSummaryTexts = app.staticTexts.containing(.staticText, identifier: AccessibilityID.CartDetailView.Summary)
        // Subtotal
        let subtotalText = cartSummaryTexts.element(boundBy: 0)
        let subtotalValueText = cartSummaryTexts.element(boundBy: 1)
        XCTAssertEqual(subtotalText.label, "Subtotal")
        XCTAssertEqual(subtotalValueText.label, "45.00€")
        // Total
        let totalText = cartSummaryTexts.element(boundBy: 2)
        let totalValueText = cartSummaryTexts.element(boundBy: 3)
        XCTAssertEqual(totalText.label, "Total")
        XCTAssertEqual(totalValueText.label, "45.00€")
        
        // Cart items
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.CartDetailView.ItemList)
        let firstCell = listCells.cells.element(boundBy: 0)
        let firstTexts = firstCell.staticTexts.containing(.staticText, identifier: AccessibilityID.CartDetailView.Cell.Name)
        let firstProductName = firstTexts.element(boundBy: 0)
        XCTAssertEqual(firstProductName.label, "Cabify Voucher")
        
        let secondCell = listCells.cells.element(boundBy: 1)
        let secondTexts = secondCell.staticTexts.containing(.staticText, identifier: AccessibilityID.CartDetailView.Cell.Name)
        let secondProductName = secondTexts.element(boundBy: 0)
        XCTAssertEqual(secondProductName.label, "Cabify T-Shirt")
        
        sleep(1)
    }
    
    func test13_CartDetailView_whenCartIsShown_thenShowSummary() throws {
        
        // Cart items
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.CartDetailView.ItemList)
        let firstCell = listCells.cells.element(boundBy: 0)
        let firstButtons = firstCell.buttons.containing(.button, identifier: AccessibilityID.CartDetailView.Cell.Quantity)
        let firstPlusButton = firstButtons.element(boundBy: 1)
        firstPlusButton.tap()
        
        let secondCell = listCells.cells.element(boundBy: 1)
        let secondButtons = secondCell.buttons.containing(.button, identifier: AccessibilityID.CartDetailView.Cell.Quantity)
        let secondPlusButton = secondButtons.element(boundBy: 1)
        secondPlusButton.tap()
        
        sleep(1)
        
        // Summary texts
        let cartSummaryTexts = app.staticTexts.containing(.staticText, identifier: AccessibilityID.CartDetailView.Summary)
        // Subtotal
        let subtotalText = cartSummaryTexts.element(boundBy: 0)
        let subtotalValueText = cartSummaryTexts.element(boundBy: 1)
        XCTAssertEqual(subtotalText.label, "Subtotal")
        XCTAssertEqual(subtotalValueText.label, "70.00€")
        // Discounts
        let firstDiscountText = cartSummaryTexts.element(boundBy: 2)
        let firstDiscountValueText = cartSummaryTexts.element(boundBy: 3)
        XCTAssertEqual(firstDiscountText.label, "Buy 2 and get 1 free")
        XCTAssertEqual(firstDiscountValueText.label, "5.00€")
        let secondDiscountText = cartSummaryTexts.element(boundBy: 4)
        let secondDiscountValueText = cartSummaryTexts.element(boundBy: 5)
        XCTAssertEqual(secondDiscountText.label, "€1 discount per unit for 3+")
        XCTAssertEqual(secondDiscountValueText.label, "3.00€")
        // Total
        let totalText = cartSummaryTexts.element(boundBy: 6)
        let totalValueText = cartSummaryTexts.element(boundBy: 7)
        XCTAssertEqual(totalText.label, "Total")
        XCTAssertEqual(totalValueText.label, "62.00€")
        
        sleep(1)
    }
    
    func test14_CartDetailView_whenCartIsShown_thenClearCart() throws {
        
        // Cart items
        let buttons = app.buttons.containing(.button, identifier: AccessibilityID.CartDetailView.ItemList)
        let clearCartButton = buttons.element(boundBy: 0)
        XCTAssertEqual(clearCartButton.label, AccessibilityID.CartDetailView.ClearCart)
        clearCartButton.tap()
        
        sleep(1)
    }
}
