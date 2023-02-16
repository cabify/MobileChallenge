//
//  ParameterEncodableTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 14/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class ParameterEncodableTests: XCTestCase {
    
    func testRequestConvertable_whenURLEncodeParameters_thenValidateURLWithParams() throws {
        
        // Given
        let mockedRequest = ProductsListRequest(parameter: ["sort": "ascending"])
        
        // When
        let urlRequest = try? mockedRequest.asURLRequest()
        
        // Then
        XCTAssertEqual(urlRequest?.httpMethod, "GET")
        XCTAssertEqual(urlRequest?.url?.absoluteString, "https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887/Products.json?sort=ascending")
    }
}
