//
//  MobileChallengeApp.swift
//  MobileChallenge
//
//  Created by thomas pereira on 26/01/2023.
//

import SwiftUI

@main
struct MobileChallengeApp: App {
    
    @StateObject var coordinator = ProductsListCoordinator(
        productsListRepository: DefaultProductsListRepository(
            productsListRequest: DefaultProductsListRepository.ProductsListRequest()
        )
    )
    let persistenceController = PersistenceController.shared
    
    var body: some Scene {
        WindowGroup {
            ProductsListCoordinatorView(coordinatorObject: coordinator)
                .environment(\.managedObjectContext, persistenceController.container.viewContext)
        }
    }
}
