//
//  DependenciesiOS.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit

public class DependenciesiOS: SharedDependencies {
    public let window: UIWindow
    
    public init(window: UIWindow) {
        self.window = window
    }
    
    public lazy var factory: Factory = {
        return Factory()
    }()
    
    // MARK: - Wireframes
    
    public lazy var rootWireframe: RootWireframe = {
        RootWireframeiOS(window: self.window, dependencies: self)
    }()
    
    // MARK: - ViewControllers
    
    public func homeViewController() -> HomeViewController {
        let vc = factory.createVC() as HomeViewController
        let presenter = super.homePresenter(view: vc,
                                           interactor: super.homeInteractor as HomeInteractorInputProtocol,
                                           wireframe: rootWireframe)
        
        vc.presenter = presenter
        super.homeInteractor.presenter = presenter
        
        return vc
    }
    
    public func checkoutViewController(shoppingCart: ShoppingCart) -> CheckoutViewController {
        let vc = factory.createVC() as CheckoutViewController
        let presenter = super.checkoutPresenter(view: vc,
                                            interactor: super.checkoutInteractor as CheckoutInteractorInputProtocol,
                                            wireframe: rootWireframe,
                                            shoppingCart: shoppingCart)
        
        vc.presenter = presenter
        super.checkoutInteractor.presenter = presenter
        
        return vc
    }
}
