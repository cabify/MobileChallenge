//
//  MobileChallengeApp.swift
//  MobileChallenge
//
//  Created by thomas pereira on 26/01/2023.
//

import SwiftUI

@main
struct AppLauncher {
    static func main() throws {
        if NSClassFromString("XCTestCase") == nil {
            MobileChallengeApp.main()
        } else {
            TestApp.main()
        }
    }
}

struct MobileChallengeApp: App {
    
    @StateObject var coordinator = ProductsListCoordinator(
        productsListRepository: DefaultProductsListRepository(),
        cartRepository: DefaultCartRepository(context: CoreDataManager.shared.mainContext)
    )
    
    init() {
        let configuration: CoreDataStorage.Configuration = .basic(identifier: "MobileChallenge")
        CoreDataManager.shared.setup(with: configuration)
    }
    
    var body: some Scene {
        WindowGroup {
            ProductsListCoordinatorView(coordinatorObject: coordinator)
        }
    }
}

struct TestApp: App {
    var body: some Scene {
        WindowGroup { Text("Running Unit Tests") }
    }
}
