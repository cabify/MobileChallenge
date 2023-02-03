//
//  StorageProtocol.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine
import CoreData

public struct StorageSort {
    var key: String
    var ascending: Bool = true
}

protocol StorageProtocol {
    associatedtype Entity = Storable
    
    // Fetch list
    func fetch(predicate: NSPredicate?, sorted: [StorageSort]) -> AnyPublisher<[Entity], Error>
    // Fetch single object
    func objectWith(primaryKey: String) -> AnyPublisher<Entity?, Error>
    // Create
    func create(_ body: @escaping (inout Entity) -> Void) -> AnyPublisher<Entity, Error>
    // Update
    func update(_ entity: Entity) -> AnyPublisher<Void, Error>
    // Delete
    func delete(_ entity: Entity) -> AnyPublisher<Void, Error>
}
