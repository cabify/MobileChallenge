//
//  ShoppingCartTests.swift
//  CabifyMobileChallengeTests
//
//  Created by Jesús Emilio Fernández de Frutos on 14/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//
@testable import CabifyMobileChallenge
import XCTest

class ShoppingCartTests: XCTestCase {
    
    let productVoucher=Product(JSONString: "{\"code\":\"VOUCHER\",\"name\":\"Cabify Voucher\",\"price\":5}")
    let productTshirt=Product(JSONString: "{\"code\":\"TSHIRT\",\"name\":\"Cabify T-Shirt\",\"price\":20}")
    let productMug=Product(JSONString: "{\"code\":\"MUG\",\"name\":\"Cabify Coffee Mug\",\"price\":7.5}")

    override func setUp() {
    }

    override func tearDown() {
    }

    public func testTotal1() {
        let shoppingCart = ShoppingCart()
        
        shoppingCart.products.value.append(productVoucher!)
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productMug!)
        
        let discounts: Dictionary = [
            "VOUCHER": Discount.twoXone,
            "TSHIRT": Discount.reducedPrice,
            "MUG": Discount.none
        ]
        
        let checkouts = shoppingCart.totalCost(discount: discounts)
        let totalSum = checkouts.map({$0.totalPrice}).reduce(0, +)
        XCTAssertEqual(totalSum, 32.5)
    }
    
    public func testTotal2() {
        let shoppingCart = ShoppingCart()
        
        shoppingCart.products.value.append(productVoucher!)
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productVoucher!)
        
        let discounts: Dictionary = [
            "VOUCHER": Discount.twoXone,
            "TSHIRT": Discount.reducedPrice,
            "MUG": Discount.none
        ]
        
        let checkouts = shoppingCart.totalCost(discount: discounts)
        let totalSum = checkouts.map({$0.totalPrice}).reduce(0, +)
        XCTAssertEqual(totalSum, 25.0)
    }
    
    public func testTotal3() {
        let shoppingCart = ShoppingCart()
        
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productVoucher!)
        shoppingCart.products.value.append(productTshirt!)
        
        let discounts: Dictionary = [
            "VOUCHER": Discount.twoXone,
            "TSHIRT": Discount.reducedPrice,
            "MUG": Discount.none
        ]
        
        let checkouts = shoppingCart.totalCost(discount: discounts)
        let totalSum = checkouts.map({$0.totalPrice}).reduce(0, +)
        XCTAssertEqual(totalSum, 81.0)
    }
    
    public func testTotal4() {
        let shoppingCart = ShoppingCart()
        
        shoppingCart.products.value.append(productVoucher!)
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productVoucher!)
        shoppingCart.products.value.append(productVoucher!)
        shoppingCart.products.value.append(productMug!)
        shoppingCart.products.value.append(productTshirt!)
        shoppingCart.products.value.append(productTshirt!)
        
        let discounts: Dictionary = [
            "VOUCHER": Discount.twoXone,
            "TSHIRT": Discount.reducedPrice,
            "MUG": Discount.none
        ]
        
        let checkouts = shoppingCart.totalCost(discount: discounts)
        let totalSum = checkouts.map({$0.totalPrice}).reduce(0, +)
        XCTAssertEqual(totalSum, 74.5)
    }
}
