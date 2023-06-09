//
//  ProductListViewController.swift
//  DemoCabify
//
//  Created by admin on 8/06/23.
//

import UIKit

final class ProductListViewController: UIViewController {

    private let viewModel = ProductListViewModel()

    override func viewDidLoad() {
        super.viewDidLoad()
        viewModel.onViewDidLoad()
    }

}
