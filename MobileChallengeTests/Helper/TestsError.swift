//
//  TestsError.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Foundation
@testable import MobileChallenge

enum TestsError: Error {
    static let itemNotFoundMessage = "Can't find the item on the list"
    static let insufficientQuantityMessage = "Can't remove product without quantity"
    static let noDataMessage = "Could not received data from the server. Please retry."
    case itemNotFound
    case insufficientQuantity
}

extension TestsError: CustomStringConvertible {
    public var description: String {
        switch self {
        case .itemNotFound: return TestsError.itemNotFoundMessage
        case .insufficientQuantity: return TestsError.insufficientQuantityMessage
        }
    }
}

extension TestsError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .itemNotFound: return TestsError.itemNotFoundMessage
        case .insufficientQuantity: return TestsError.insufficientQuantityMessage
        }
    }
}
