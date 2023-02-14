//
//  StorageStoreCoordinator.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import CoreData

final class StorageStoreCoordinator {
    
    public enum StoreType: String {
        case sqLiteStoreType
        case inMemoryStoreType
    }
    
    public enum CoordinatorError: Error {
        // .momd file not found
        case modelFileNotFound
        // NSManagedObjectModel creation fail
        case modelCreationError
        // Gettings document directory fail
        case storePathNotFound
    }
    
    static func persistentStoreCoordinator(modelName: String? = nil, storeType: StoreType = .sqLiteStoreType) -> NSPersistentStoreCoordinator? {
        do {
            return try NSPersistentStoreCoordinator.coordinator(modelName: modelName, storeType: storeType)
        } catch {
            print("CoreData: Unresolved error \(error)")
        }
        return nil
    }
}

extension NSPersistentStoreCoordinator {
    
    // Return NSPersistentStoreCoordinator object
    static func coordinator(modelName: String? = nil, storeType: StorageStoreCoordinator.StoreType) throws -> NSPersistentStoreCoordinator? {
        
        guard let modelURL = Bundle.main.url(forResource: modelName, withExtension: "momd") else {
            throw StorageStoreCoordinator.CoordinatorError.modelFileNotFound
        }
        
        guard let managedObjectModel = NSManagedObjectModel(contentsOf: modelURL) else {
            throw StorageStoreCoordinator.CoordinatorError.modelCreationError
        }
        
        let persistentContainer = NSPersistentStoreCoordinator(managedObjectModel: managedObjectModel)
        
        if storeType == .inMemoryStoreType {
            try persistentContainer.configureInMemoryStore()
            
        } else {
            try persistentContainer.configureSQLiteStore(name: modelName!)
        }
        
        return persistentContainer
    }
}

extension NSPersistentStoreCoordinator {
    
    func configureSQLiteStore(name: String) throws {
        guard let documents = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).last else {
            throw StorageStoreCoordinator.CoordinatorError.storePathNotFound
        }
        
        do {
            let url = documents.appendingPathComponent("\(name).sqlite")
            let options = [
                NSMigratePersistentStoresAutomaticallyOption: true,
                NSInferMappingModelAutomaticallyOption: true
            ]
            
            try self.addPersistentStore(ofType: NSSQLiteStoreType, configurationName: nil, at: url, options: options)
            
        } catch {
            throw error
        }
    }
    
    func configureInMemoryStore() throws {
        let description = NSPersistentStoreDescription()
        description.url = URL(fileURLWithPath: "/dev/null")
        description.shouldAddStoreAsynchronously = false
        
        self.addPersistentStore(with: description) { (description, error) in
            // Check if creating container wrong
            if let error = error {
                fatalError("Create an in-mem coordinator failed \(error)")
            }
        }
    }
    
}
