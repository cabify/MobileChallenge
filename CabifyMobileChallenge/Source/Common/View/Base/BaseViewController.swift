//
//  BaseViewController.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit

public class BaseViewController/*<P: PresenterProtocol>*/: UIViewController {
    public required init?(coder aDecoder: NSCoder) {
        super.init(coder: aDecoder)
        
        print("🙌 init \(self)")
    }
    
    public var dependencies: DependenciesiOS {
        return AppDelegate.shared.dependencies
    }
    
    public override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    deinit {
        print("👋 deinit \(self)")
    }
}
