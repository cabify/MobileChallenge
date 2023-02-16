//
//  CartButtonViewTests.swift
//  MobileChallengeUITests
//
//  Created by thomas pereira on 16/02/2023.
//

import XCTest

// MARK: - CartButtonViewTests
extension BaseUITests {
    
    func test02_CartButtonView_whenTapButton_thenShowCart() throws {
        
        let navigationBars = app.navigationBars.containing(.navigationBar, identifier: AccessibilityID.Navigation.ProductsList)
        let navigationBar = navigationBars.element(boundBy: 0)
        XCTAssert(navigationBar.exists)
        
        let navigationBarsButtons = navigationBar.buttons.containing(.button, identifier: AccessibilityID.Navigation.CartButton)
        let cartButtonView = navigationBarsButtons.element(boundBy: 0)
        XCTAssert(cartButtonView.exists)
        cartButtonView.tap()
        
        sleep(1)
    }
    
    func test04_CartButtonView_whenCartIsShown_thenDismissCart() throws {
        
        let cartNavigationBars = app.navigationBars.containing(.navigationBar, identifier: AccessibilityID.Navigation.Cart)
        let cartNavigationBar = cartNavigationBars.element(boundBy: 0)
        XCTAssert(cartNavigationBar.exists)
        
        let cartNavigationBarsButtons = cartNavigationBar.buttons.containing(.button, identifier: AccessibilityID.CartDetailView.CloseButton)
        let closeCartButtonView = cartNavigationBarsButtons.element(boundBy: 0)
        XCTAssert(closeCartButtonView.exists)
        closeCartButtonView.tap()
        
        sleep(1)
    }
    
    func test05_CartButtonView_whenCartIsShowAndTapContinueShopping_thenDismissCart() throws {
        
        try test02_CartButtonView_whenTapButton_thenShowCart()
        
        let buttons = app.buttons.containing(.button, identifier: AccessibilityID.CartDetailView.EmptyCart)
        let continueShoppingButton = buttons.element(boundBy: 0)
        XCTAssertEqual(continueShoppingButton.label, AccessibilityID.CartDetailView.EmptyCartButton)
        continueShoppingButton.tap()
        
        sleep(1)
    }
}
