//
//  CoreDataStorage.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import CoreData

final class CoreDataStorage {
    
    var context: NSManagedObjectContext?
    
    required init(configuration: StorageConfiguration = .basic(identifier: "MobileChallenge")) {
        switch configuration {
        case .basic:
            initDB(modelName: configuration.identifier(), storeType: .sqLiteStoreType)
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
