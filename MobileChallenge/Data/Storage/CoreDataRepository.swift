//
//  CoreDataRepository.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine
import CoreData

final class CoreDataRepository<Entity: NSManagedObject> {
    
    private let backgroundContext: NSManagedObjectContext
    
    init(backgroundContext: NSManagedObjectContext) {
        self.backgroundContext = backgroundContext
    }
}

extension CoreDataRepository: StorageProtocol where Entity: StorableObject {
    
    func fetch(predicate: NSPredicate? = nil, sorted: [StorageSort] = []) -> AnyPublisher<[Entity], Error> {
        Deferred { [backgroundContext] in
            Future { promise in
                backgroundContext.performAndWait {
                    let request = Entity.fetchRequest()
                    request.sortDescriptors = sorted.compactMap({ NSSortDescriptor(key: $0.key, ascending: $0.ascending) })
                    request.predicate = predicate
                    
                    guard let results = try? backgroundContext.fetch(request) as? [Entity] else {
                        promise(.success([]))
                        return
                    }
                    promise(.success(results))
                }
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
    
    func create(_ entity: Entity? = nil, body: ((inout Entity) -> Void)? = nil) -> AnyPublisher<Entity, Error> {
        Deferred { [backgroundContext] in
            Future { promise in
                if let anEntity = entity {
                    promise(.success(anEntity))
                    return
                }
                
                backgroundContext.performAndWait {
                    var entity = Entity(context: backgroundContext)
                    body?(&entity)
                    do {
                        try backgroundContext.save()
                        promise(.success(entity))
                    } catch {
                        promise(.failure(error))
                    }
                }
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
    
    func update(_ entity: Entity, body: ((inout Entity) -> Void)? = nil) -> AnyPublisher<Entity, Error> {
        Deferred { [backgroundContext] in
            Future { promise in
                backgroundContext.performAndWait {
                    var updatedEntity = entity
                    body?(&updatedEntity)
                    do {
                        try backgroundContext.save()
                        promise(.success(updatedEntity))
                    } catch {
                        promise(.failure(error))
                    }
                }
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
    
    func delete(_ entity: Entity) -> AnyPublisher<Void, Error> {
        Deferred { [backgroundContext] in
            Future { promise in
                backgroundContext.performAndWait {
                    do {
                        backgroundContext.delete(entity)
                        try backgroundContext.save()
                        promise(.success(()))
                    } catch {
                        promise(.failure(error))
                    }
                }
            }
        }
        .receive(on: DispatchQueue.main)
        .eraseToAnyPublisher()
    }
}
