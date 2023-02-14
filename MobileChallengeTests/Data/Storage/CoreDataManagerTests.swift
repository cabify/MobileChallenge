//
//  CoreDataManagerTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 14/02/2023.
//

import XCTest
import Combine
@testable import MobileChallenge

final class CoreDataManagerTests: XCTestCase {
    
    func testCoreDataManager_whenSetup_thenValidateManagedObjectContext() throws {
        
        // Given
        let configuration: StorageConfiguration = .inMemory(identifier: "MobileChallangeTests")
        
        // When
        CoreDataManager.setup(with: configuration)
        
        // Then
        XCTAssertNotNil(CoreDataManager.shared.context)
    }
}
