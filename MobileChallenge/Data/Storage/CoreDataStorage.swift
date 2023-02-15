//
//  CoreDataStorage.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import CoreData

protocol CoreDataStorable {
    var persistentContainer: NSPersistentContainer! { get }
    var mainContext: NSManagedObjectContext { get }
    var backgroundContext: NSManagedObjectContext { get }
    
    init(configuration: CoreDataStorage.Configuration, onConfigureCompletionBlock: (() -> Void)?)
    func setup(onConfigureCompletionBlock: (() -> Void)?)
}

final class CoreDataStorage: CoreDataStorable {
    
    // MARK: - Configuration types
    public enum Configuration {
        case basic(identifier: String)
        case inMemory(identifier: String)
        
        var identifier: String {
            switch self {
            case .basic(let identifier): return identifier
            case .inMemory(let identifier): return identifier
            }
        }
        
        var storeType: String {
            switch self {
            case .basic: return NSSQLiteStoreType
            case .inMemory: return NSInMemoryStoreType
            }
        }
    }
    
    // MARK: - Stack
    // Container
    lazy var persistentContainer: NSPersistentContainer! = {
        let persistentContainer = NSPersistentContainer(name: storageConfiguration.identifier)
        let description = persistentContainer.persistentStoreDescriptions.first
        description?.type = storageConfiguration.storeType
        return persistentContainer
    }()
    // Main context
    lazy var mainContext: NSManagedObjectContext = {
        let context = self.persistentContainer.viewContext
        context.automaticallyMergesChangesFromParent = true
        return context
    }()
    // Background context
    lazy var backgroundContext: NSManagedObjectContext = {
        let context = self.persistentContainer.newBackgroundContext()
        context.mergePolicy = NSMergeByPropertyObjectTrumpMergePolicy
        return context
    }()
    
    // MARK: - Properties
    private let storageConfiguration: Configuration
    
    // MARK: - Init
    init(configuration: CoreDataStorage.Configuration, onConfigureCompletionBlock: (() -> Void)? = nil) {
        self.storageConfiguration = configuration
        self.setup(onConfigureCompletionBlock: onConfigureCompletionBlock)
    }
    
    // MARK: - Setup
    func setup(onConfigureCompletionBlock: (() -> Void)? = nil) {
        loadPersistentStore(onConfigureCompletionBlock: onConfigureCompletionBlock)
    }
    
    private func loadPersistentStore(onConfigureCompletionBlock: (() -> Void)? = nil) {
        persistentContainer.loadPersistentStores { description, error in
            guard error == nil else {
                fatalError("Was unable to load store \(error!)")
            }
            onConfigureCompletionBlock?()
        }
    }
}

#if DEBUG && TESTING
extension CoreDataStorage {
    static var preview: Self {
        .init(configuration: .basic(identifier: "MobileChallenge"))
    }
}
#endif
