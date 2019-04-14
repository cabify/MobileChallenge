//
//  AppDelegate.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

    var window: UIWindow?

    public static var shared: AppDelegate {
        guard let instance = UIApplication.shared.delegate as? AppDelegate else {
            fatalError("The Application delegate is not AppDelegate")
        }
        return instance
    }
    
    public lazy var dependencies: DependenciesiOS = {
        let rootWindow = UIWindow()
        rootWindow.makeKeyAndVisible()
        self.window = rootWindow
        
        return DependenciesiOS(window: rootWindow)
    }()
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        dependencies.rootWireframe.presentMainView()
        return true
    }
}

