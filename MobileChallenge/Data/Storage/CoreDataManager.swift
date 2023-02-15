//
//  CoreDataManager.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import CoreData

final class CoreDataManager {
    
    // MARK: - Static
    // Shared
    static var shared = CoreDataManager()
    
    // Setup
    func setup(
        with storageConfig: CoreDataStorage.Configuration = .basic(identifier: "MobileChallenge"),
        onConfigureCompletionBlock: (() -> Void)? = nil) {
            
        self.coreDataStorage = CoreDataStorage(
            configuration: storageConfig,
            onConfigureCompletionBlock: onConfigureCompletionBlock
        )
    }
    
    // MARK: - Properties
    private var coreDataStorage: (any CoreDataStorable)?
    private var mainCoreDataStorage: CoreDataStorage {
        guard let mainCoreDataStorage = self.coreDataStorage as? CoreDataStorage else {
            fatalError("Error - you must call setup to configure the StoreContext before accessing any dao")
        }
        return mainCoreDataStorage
    }
    // PersistentContainer
    var persistentContainer: NSPersistentContainer {
        return mainCoreDataStorage.persistentContainer
    }
    // Main context
    var mainContext: NSManagedObjectContext {
        return mainCoreDataStorage.mainContext
    }
    // Background context
    var backgroundContext: NSManagedObjectContext {
        return mainCoreDataStorage.backgroundContext
    }
}
