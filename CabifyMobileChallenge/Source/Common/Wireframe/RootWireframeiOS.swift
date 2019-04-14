//
//  RootWireframeiOS.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit

public class RootWireframeiOS: RootWireframe {
    
    private let window: UIWindow
    private let dependencies: DependenciesiOS
   
    private lazy var navController: UINavigationController = {
        return UINavigationController()
    }()
    
    public init(window: UIWindow, dependencies: DependenciesiOS) {
        self.window = window
        self.dependencies = dependencies
    }
    
    public func updateGlobalNetworkActivity(visible: Bool) {
        UIApplication.shared.isNetworkActivityIndicatorVisible = visible
    }
    
    public func presentMainView() {
        let HomeVC = dependencies.homeViewController()
        navController.viewControllers = [HomeVC]
        window.rootViewController = navController
    }
    
    public func close() {
        exit(0)
    }
    
    public func popTwoLastViews() {
        guard navController.viewControllers.count > 3 else {
            return
        }
        let beforeLastViewController = navController.viewControllers[navController.viewControllers.count - 3]
        navController.popToViewController(beforeLastViewController, animated: true)
    }
    
    public func popLastView() {
        navController.popViewController(animated: true)
    }
    
    public func gotoCheckOutView(shoppingCart: ShoppingCart) {
        let checkoutViewVC = dependencies.checkoutViewController(shoppingCart: shoppingCart)
        navController.pushViewController(checkoutViewVC, animated: true)
    }
}

