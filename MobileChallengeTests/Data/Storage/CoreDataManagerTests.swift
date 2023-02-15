//
//  CoreDataManagerTests.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 14/02/2023.
//

import XCTest
import Combine
import CoreData
@testable import MobileChallenge

final class CoreDataManagerTests: XCTestCase {
    
    // MARK: - Persistent container
    func testCoreDataManager_whenSetup_thenFinalizeSetup() throws {
        
        // Given
        let expectation = XCTestExpectation(description: "Core Data loads persistent container")
        let coreDataManager = CoreDataManager()
        
        // When
        var finished = false
        let configuration: CoreDataStorage.Configuration = .inMemory(identifier: "MobileChallenge")
        coreDataManager.setup(with: configuration, onConfigureCompletionBlock: {
            finished = true
            expectation.fulfill()
        })
        wait(for: [expectation], timeout: 0.5)
        
        // Then
        XCTAssertTrue(finished)
    }
    
    func testCoreDataManager_whenSetup_thenPersistentStoreCreated() throws {
        
        // Given
        let expectation = XCTestExpectation(description: "Core Data loads persistent container")
        let coreDataManager = CoreDataManager()
        
        // When
        let configuration: CoreDataStorage.Configuration = .inMemory(identifier: "MobileChallenge")
        coreDataManager.setup(with: configuration, onConfigureCompletionBlock: {
            expectation.fulfill()
        })
        wait(for: [expectation], timeout: 0.5)
        
        // Then
        XCTAssertTrue(coreDataManager.persistentContainer.persistentStoreCoordinator.persistentStores.count > 0)
    }
    
    func testCoreDataManager_whenSetupOnDisk_thenPersistentStoreLoadedOnDisk() throws {
        
        // Given
        let expectation = XCTestExpectation(description: "Core Data loads persistent container")
        let coreDataManager = CoreDataManager()
        
        // When
        coreDataManager.setup(onConfigureCompletionBlock: {
            expectation.fulfill()
        })
        wait(for: [expectation], timeout: 0.5)
        
        // Then
        XCTAssertEqual(coreDataManager.persistentContainer.persistentStoreDescriptions.first?.type, NSSQLiteStoreType)
        coreDataManager.persistentContainer.destroyPersistentStore()
    }
    
    func testCoreDataManager_whenSetup_thenPersistentStoreLoadedOnMemory() throws {
        
        // Given
        let expectation = XCTestExpectation(description: "Core Data loads persistent container")
        let coreDataManager = CoreDataManager()
        
        // When
        let configuration: CoreDataStorage.Configuration = .inMemory(identifier: "MobileChallenge")
        coreDataManager.setup(with: configuration, onConfigureCompletionBlock: {
            expectation.fulfill()
        })
        wait(for: [expectation], timeout: 0.5)
        
        // Then
        XCTAssertEqual(coreDataManager.persistentContainer.persistentStoreDescriptions.first?.type, NSInMemoryStoreType)
        coreDataManager.persistentContainer.destroyPersistentStore()
    }
}
