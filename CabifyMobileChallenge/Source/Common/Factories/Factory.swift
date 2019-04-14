//
//  Factory.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit

public class Factory {
    public init() {
    }
    
    public func createVC<VC>() -> VC where VC: UIViewController {
        let nibName = String(describing: VC.self)
        let storyboard = UIStoryboard(name: nibName, bundle: nil)
        let vc = storyboard.instantiateViewController(withIdentifier: nibName) as! VC
        
        return vc
    }
    
    public func createView<VM, V>(viewModel: VM) -> V where V: BaseView<VM> {
        let nibName = String(describing: V.self)
        guard let view: V = Bundle.main.loadNibNamed(nibName, owner: nil, options: nil)?.first as? V else {
            fatalError("Can not create view with nib name: \(nibName)")
        }

        return view
    }
}
