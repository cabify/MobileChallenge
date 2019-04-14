//
//  RootWireframe.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation

public protocol RootWireframe: Wireframe {
    func updateGlobalNetworkActivity(visible: Bool)
    func presentMainView()
    func close()
    func popTwoLastViews()
    func popLastView()
    func gotoCheckOutView(shoppingCart: ShoppingCart)
}
