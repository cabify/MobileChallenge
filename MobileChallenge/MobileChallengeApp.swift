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
        let configuration: CoreDataStorageConfiguration = .basic(identifier: "MobileChallenge")
        CoreDataManager.setup(with: configuration)
    }
    
    var body: some Scene {
        WindowGroup {
            ProductsListCoordinatorView(coordinatorObject: coordinator)
        }
    }
}
