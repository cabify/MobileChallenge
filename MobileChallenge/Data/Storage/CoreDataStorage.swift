//
//  CoreDataStorage.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import CoreData

protocol CoreDataStorable {
    var context: NSManagedObjectContext? { get }
    init(configuration: CoreDataStorage.Configuration)
}

final class CoreDataStorage: CoreDataStorable {
    
    public enum Configuration {
        case basic(identifier: String)
        case inMemory(identifier: String? = nil)
        
        var identifier: String? {
            switch self {
            case .basic(let identifier): return identifier
            case .inMemory(let identifier): return identifier
            }
        }
    }
    
    var context: NSManagedObjectContext?
    
    init(configuration: CoreDataStorage.Configuration) {
        switch configuration {
        case .basic:
            initDB(modelName: configuration.identifier, storeType: .sqLiteStoreType)
            
        case .inMemory:
            initDB(storeType: .inMemoryStoreType)
        }
    }
    
    private func initDB(modelName: String? = nil, storeType: StorageStoreCoordinator.StoreType) {
        let coordinator = StorageStoreCoordinator.persistentStoreCoordinator(modelName: modelName, storeType: storeType)
        self.context = NSManagedObjectContext(concurrencyType: .mainQueueConcurrencyType)
        self.context?.persistentStoreCoordinator = coordinator
    }
}

#if DEBUG && TESTING
extension CoreDataStorage {
    static var preview: Self {
        .init(configuration: .basic(identifier: "MobileChallenge"))
    }
}
#endif
