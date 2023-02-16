//
//  NSPersistentContainer+Extension.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 15/02/2023.
//

import Foundation
import CoreData
@testable import MobileChallenge

extension NSPersistentContainer {
    
    func destroyPersistentStore() {
        
        guard let storeURL = persistentStoreDescriptions.first?.url,
              let storeType = persistentStoreDescriptions.first?.type else {
            return
        }
        
        do {
            let persistentStoreCoordinator = NSPersistentStoreCoordinator(managedObjectModel: NSManagedObjectModel())
            try persistentStoreCoordinator.destroyPersistentStore(at: storeURL, ofType: storeType, options: nil)
        } catch let error {
            print("failed to destroy persistent store at \(storeURL), error: \(error)")
        }
    }
}
