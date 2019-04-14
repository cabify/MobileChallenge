//
//  SharedDependencies.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit

public class SharedDependencies {
    
    // MARK: - Interactors
    
    lazy var homeRemote: HomeRemoteDataManager = {
        return HomeRemoteDataManager()
    }()
    
    lazy var checkoutLocal: CheckoutLocalDataManager = {
        return CheckoutLocalDataManager()
    }()
    
    lazy var homeInteractor: HomeInteractor = {
        return HomeInteractor(local: nil, remote: homeRemote)
    }()
    
    lazy var checkoutInteractor: CheckoutInteractor = {
        return CheckoutInteractor(local: checkoutLocal, remote: nil)
    }()
    
    // MARK: - Default Presenters
    
    func homePresenter(view: HomeViewProtocol, interactor: HomeInteractorInputProtocol, wireframe: RootWireframe) -> HomePresenter {
        return HomePresenter(view: view, interactor: interactor, wireframe: wireframe)
    }
    
    func checkoutPresenter(view: CheckoutViewProtocol, interactor: CheckoutInteractorInputProtocol, wireframe: RootWireframe, shoppingCart: ShoppingCart) -> CheckoutPresenter {
        return CheckoutPresenter(view: view, interactor: interactor, wireframe: wireframe, shoppingCart: shoppingCart)
    }
}
