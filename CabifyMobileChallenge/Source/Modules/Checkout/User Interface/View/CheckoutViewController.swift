//
//  CheckoutController.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit
import RxSwift
import RxCocoa

public class CheckoutViewController: BaseViewController {

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var totalLabel: UILabel!
    
    var presenter: CheckoutPresenterProtocol!
    
    var products :Variable<[Checkout]> = Variable([])
    private let disposeBag = DisposeBag()
    
    private var viewModel: CheckoutTableViewViewModel!
    private let cellIdentifier = CheckoutInfoTableViewCell.ID
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        setupViewModel()
        setupTableView()
        setupTableViewBinding()
        presenter.viewDidLoad()
    }
    
    private func setupViewModel() {
        self.viewModel = CheckoutTableViewViewModel()
    }
    
    private func setupTableView() {
        tableView.delegate = nil
        tableView.dataSource = nil
    }
    
    private func setupTableViewBinding() {
        
        viewModel.dataSource
            .bind(to: tableView.rx.items(cellIdentifier: cellIdentifier, cellType: CheckoutInfoTableViewCell.self)) {  row, element, cell in
                cell.configure(with: element)
            }
            .disposed(by: disposeBag)
    }
}

// MARK: - Extensions -

extension CheckoutViewController: CheckoutViewProtocol {
    
    func update(productsCount: Int) {
        print(productsCount)
        self.title = "\(productsCount) products selected"
    }
    
    func displayCheckoutDetail(viewModel: CheckoutModels.getCheckoput.ViewModel) {
        self.viewModel.update(dataSource: viewModel.checkout)
        self.totalLabel.text = "TOTAL: \(viewModel.totalPrice)"
    }
}
