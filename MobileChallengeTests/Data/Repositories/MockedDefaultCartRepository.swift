//
//  MockedDefaultCartRepository.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 15/02/2023.
//

import Foundation
import Combine
import CoreData
@testable import MobileChallenge

final class MockedDefaultCartRepository {
    static let mockedModel: Cart = .init(items: [
        .init(code: 0, name: "Cabify Voucher", quantity: 0, price: 5),
        .init(code: 1, name: "Cabify T-Shirt", quantity: 0, price: 20),
        .init(code: 2, name: "Cabify Coffee Mug", quantity: 0, price: 7.5)
    ])
    
    static func repository(backgroundContext: NSManagedObjectContext) -> DefaultCartRepository {
        return DefaultCartRepository(backgroundContext: backgroundContext)
    }
}
