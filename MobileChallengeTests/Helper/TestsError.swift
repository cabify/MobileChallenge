//
//  TestsError.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 10/02/2023.
//

import Foundation

enum TestsError: Error {
    static let noDataMessage = "Could not received data from the server. Please retry."
    case insufficientQuantity
}

extension TestsError: CustomStringConvertible {
    public var description: String {
        switch self {
        case .insufficientQuantity: return "Can't remove product without quantity"
        }
    }
}
