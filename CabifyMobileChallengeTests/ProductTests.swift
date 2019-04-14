//
//  ProductTests.swift
//  CabifyMobileChallengeTests
//
//  Created by Jesús Emilio Fernández de Frutos on 14/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//
@testable import CabifyMobileChallenge
import XCTest

class ProductTests: XCTestCase {

    override func setUp() {
    }

    override func tearDown() {
    }

    func testParseObjects() {

        let productVoucher=Product(JSONString: "{\"code\":\"VOUCHER\",\"name\":\"Cabify Voucher\",\"price\":5}")
        XCTAssertEqual(productVoucher?.code, "VOUCHER")
        XCTAssertEqual(productVoucher?.name, "Cabify Voucher")
        XCTAssertEqual(productVoucher?.price, 5)
        
        let productTshirt=Product(JSONString: "{\"code\":\"TSHIRT\",\"name\":\"Cabify T-Shirt\",\"price\":20}")
        XCTAssertEqual(productTshirt?.code, "TSHIRT")
        XCTAssertEqual(productTshirt?.name, "Cabify T-Shirt")
        XCTAssertEqual(productTshirt?.price, 20)
        
        let productMug=Product(JSONString: "{\"code\":\"MUG\",\"name\":\"Cabify Coffee Mug\",\"price\":7.5}")
        XCTAssertEqual(productMug?.code, "MUG")
        XCTAssertEqual(productMug?.name, "Cabify Coffee Mug")
        XCTAssertEqual(productMug?.price, 7.5)
    }
}
