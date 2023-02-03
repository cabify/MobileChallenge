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
    static func setup(storage: CoreDataStorage) {
        shared.storage = storage
    }
    
    // MARK: - Properties
    // Private
    private var storage: CoreDataStorage?
    var context: NSManagedObjectContext {
        guard let storage = self.storage, let context = storage.context else {
            fatalError("You must call setup to configure the StoreContext before accessing any dao")
        }
        return context
    }
    // Public
    lazy var cartRepository = CoreDataRepository<CartEntity>(context: context)
    
    // MARK: - Init
    private init() { }
    
    // Save
    func saveContext() {
        guard context.hasChanges else { return }
        try? context.save()
    }
}
