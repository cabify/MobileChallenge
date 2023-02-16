//
//  ProductListCellTests.swift
//  MobileChallengeUITests
//
//  Created by thomas pereira on 16/02/2023.
//

import XCTest

// MARK: - ProductListCellTests
extension BaseUITests {
    
    func test01_ProductListCellTests_whenLoadProducts_thenShowProducts() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        
        let firstCell = listCells.cells.element(boundBy: 0)
        let firstTexts = firstCell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Name)
        let firstProductName = firstTexts.element(boundBy: 0)
        XCTAssertEqual(firstProductName.label, "Cabify Voucher")
        
        let secondCell = listCells.cells.element(boundBy: 1)
        let secondTexts = secondCell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Name)
        let secondProductName = secondTexts.element(boundBy: 0)
        XCTAssertEqual(secondProductName.label, "Cabify T-Shirt")
        
        let thirdCell = listCells.cells.element(boundBy: 2)
        let thirdTexts = thirdCell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Name)
        let thirdProductName = thirdTexts.element(boundBy: 0)
        XCTAssertEqual(thirdProductName.label, "Cabify Coffee Mug")
    }
}

// MARK: - DiscountBadgeViewTests
extension BaseUITests {
    
    func test06_DiscountBadgeView_whenBagdeIsShown_thenConfirmText() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        let cell = listCells.cells.element(boundBy: 0)
        
        let discountBadgeView = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.DiscountBadge)
        let discountBadgeText = discountBadgeView.element(boundBy: 0)
        XCTAssertTrue(discountBadgeText.exists)
        XCTAssertEqual(discountBadgeText.label, "Buy 2 and get 1 free")
    }
}

// MARK: - CartQuantityViewTests
extension BaseUITests {
    
    func test07_CartQuantityView_whenNoItemsAdd_thenConfirmState() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        let cell = listCells.cells.element(boundBy: 0)
        
        let texts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Quantity)
        let quantityText = texts.element(boundBy: 0)
        XCTAssertEqual(quantityText.label, "0")
        
        let buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        let minusButton = buttons.element(boundBy: 0)
        XCTAssertFalse(minusButton.isEnabled)
        let plusButton = buttons.element(boundBy: 1)
        XCTAssertTrue(plusButton.isEnabled)
    }
    
    func test08_CartQuantityView_whenTapOnIncrease_thenConfirmState() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        let cell = listCells.cells.element(boundBy: 0)
        
        var texts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Quantity)
        var quantityText = texts.element(boundBy: 0)
        XCTAssertEqual(quantityText.label, "0")
        
        var buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        var minusButton = buttons.element(boundBy: 0)
        XCTAssertFalse(minusButton.isEnabled)
        let plusButton = buttons.element(boundBy: 1)
        XCTAssertTrue(plusButton.isEnabled)
        plusButton.tap()
        
        sleep(1)
        
        texts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Quantity)
        quantityText = texts.element(boundBy: 0)
        XCTAssertEqual(quantityText.label, "1")
        
        buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        minusButton = buttons.element(boundBy: 0)
        XCTAssertTrue(minusButton.isEnabled)
        
        sleep(1)
    }
    
    func test09_CartQuantityView_whenTapOnDecrease_thenConfirmState() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        let cell = listCells.cells.element(boundBy: 0)
        
        var buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        var minusButton = buttons.element(boundBy: 0)
        XCTAssertTrue(minusButton.isEnabled)
        let plusButton = buttons.element(boundBy: 1)
        XCTAssertTrue(plusButton.isEnabled)
        
        var texts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Quantity)
        var quantityText = texts.element(boundBy: 0)
        XCTAssertEqual(quantityText.label, "1")
        
        buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        minusButton = buttons.element(boundBy: 0)
        XCTAssertTrue(minusButton.isEnabled)
        minusButton.tap()
        
        sleep(1)
        
        texts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Quantity)
        quantityText = texts.element(boundBy: 0)
        XCTAssertEqual(quantityText.label, "0")
        
        buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        minusButton = buttons.element(boundBy: 0)
        XCTAssertFalse(minusButton.isEnabled)
        
        sleep(1)
    }
}

// MARK: - PriceViewTests
extension BaseUITests {
    
    func test10_PriceView_whenNoItemsAdd_thenShowPrice() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        let cell = listCells.cells.element(boundBy: 2)
        
        let texts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Prices)
        let priceText = texts.element(boundBy: 0)
        XCTAssertEqual(priceText.label, "7.50€")
    }
    
    func test11_PriceView_whenAddThreeTshirts_thenShowPriceAndSpecialPrice() throws {
        
        let listCells = app.collectionViews.containing(.collectionView, identifier: AccessibilityID.Products.ProductList)
        let cell = listCells.cells.element(boundBy: 1)
        
        var priceTexts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Prices)
        var priceText = priceTexts.element(boundBy: 0)
        XCTAssertEqual(priceText.label, "20.00€")
        
        let buttons = cell.buttons.containing(.button, identifier: AccessibilityID.Products.Cell.Quantity)
        let minusButton = buttons.element(boundBy: 0)
        let plusButton = buttons.element(boundBy: 1)
        XCTAssertTrue(plusButton.isEnabled )
        plusButton.tap()
        plusButton.tap()
        plusButton.tap()
        
        sleep(1)
        
        let quantityTexts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Quantity)
        let quantityText = quantityTexts.element(boundBy: 0)
        XCTAssertEqual(quantityText.label, "3")
        
        priceTexts = cell.staticTexts.containing(.staticText, identifier: AccessibilityID.Products.Cell.Prices)
        let specialPriceText = priceTexts.element(boundBy: 0)
        XCTAssertEqual(specialPriceText.label, "19.00€")
        priceText = priceTexts.element(boundBy: 1)
        XCTAssertEqual(priceText.label, "20.00€")
        
        minusButton.tap()
        
        sleep(1)
    }
}
