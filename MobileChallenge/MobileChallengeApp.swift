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
        productsListRepository: DefaultProductsListRepository(),
        cartRepository: DefaultCartRepository(context: CoreDataManager.shared.context)
    )
    
    init() {
        CoreDataManager.setup(storage: CoreDataStorage())
    }
    
    var body: some Scene {
        WindowGroup {
            ProductsListCoordinatorView(coordinatorObject: coordinator)
        }
    }
}
