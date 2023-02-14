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
    // Custom configuration
    struct Config {
        let storageConfig: StorageConfiguration
    }
    private static var storageConfig: Config?
    // Shared
    static var shared = CoreDataManager()
    
    // Setup
    static func setup(with storageConfig: StorageConfiguration) {
        CoreDataManager.storageConfig = Config(storageConfig: storageConfig)
    }
    
    // MARK: - Properties
    // Private
    private var coreDataStorage: any CoreDataStorable
    var context: NSManagedObjectContext {
        guard let context = coreDataStorage.context else {
            fatalError("Error - you must call setup to configure the StoreContext before accessing any dao")
        }
        return context
    }
    
    // MARK: - Init
    private init() {
        guard let coreDataConfig = CoreDataManager.storageConfig else {
            fatalError("Error - you must call setup before accessing CoreDataManager.shared")
        }
        
        coreDataStorage = CoreDataStorage(configuration: coreDataConfig.storageConfig)
    }
}
