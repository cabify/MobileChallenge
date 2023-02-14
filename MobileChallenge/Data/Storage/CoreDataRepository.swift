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
    
    var context: NSManagedObjectContext
    
    init(context: NSManagedObjectContext) {
        self.context = context
    }
}

extension CoreDataRepository: StorageProtocol where Entity: StorableObject {
    
    func fetch(predicate: NSPredicate? = nil, sorted: [StorageSort] = []) -> AnyPublisher<[Entity], Error> {
        Deferred { [context] in
            Future { promise in
                context.perform {
                    let request = Entity.fetchRequest()
                    request.sortDescriptors = sorted.compactMap({ NSSortDescriptor(key: $0.key, ascending: $0.ascending) })
                    request.predicate = predicate
                    
                    guard let results = try? context.fetch(request) as? [Entity] else {
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
        Deferred { [context] in
            Future { promise in
                if let anEntity = entity {
                    promise(.success(anEntity))
                    return
                }
                
                context.perform {
                    var entity = Entity(context: context)
                    body?(&entity)
                    do {
                        try context.save()
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
        Deferred { [context] in
            Future { promise in
                context.perform {
                    var updatedEntity = entity
                    body?(&updatedEntity)
                    do {
                        try context.save()
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
        Deferred { [context] in
            Future { promise in
                context.perform {
                    do {
                        context.delete(entity)
                        try context.save()
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
