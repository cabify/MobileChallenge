//
//  NSManagedObjectContextSpy.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 15/02/2023.
//

import XCTest
import CoreData
@testable import MobileChallenge

class NSManagedObjectContextSpy: NSManagedObjectContext {
    
    var expectation: XCTestExpectation?
    var saveWasCalled = false
    
    // MARK: - Perform
    override func performAndWait(_ block: () -> Void) {
        super.performAndWait(block)
        
        expectation?.fulfill()
    }
    
    // MARK: - Save
    override func save() throws {
        try super.save()
        
        saveWasCalled = true
    }
}

