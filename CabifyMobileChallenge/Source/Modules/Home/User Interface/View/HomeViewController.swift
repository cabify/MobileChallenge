//
//  HomeViewController.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit
import RxSwift
import RxCocoa

public class HomeViewController: BaseViewController {

    @IBOutlet weak var tableView: UITableView!
    
    var presenter: HomePresenterProtocol!
    
    var products :Variable<[Product]> = Variable([])
    private let disposeBag = DisposeBag()
    private var viewModel: HomeTableViewViewModel!
    private let cellIdentifier = ProductInfoTableViewCell.ID
    
    override public func viewDidLoad() {
        super.viewDidLoad()
        setupViewModel()
        setupTableView()
        setupDoneButton()
        setupTableViewBinding()
        setupChoiceTableViewObserver()
        presenter.viewDidLoad()
    }
    
    override public func viewWillAppear(_ animated: Bool) {
        presenter.viewWillAppear(animated: animated)
    }
    
    private func setupViewModel() {
        self.viewModel = HomeTableViewViewModel()
    }
    
    private func setupDoneButton() {
        let doneButton = UIBarButtonItem(barButtonSystemItem: .done, target: self, action: #selector(HomeViewController.doneTapped))
        navigationItem.setRightBarButton(doneButton, animated: true)
    }
    
    private func setupTableView() {
        tableView.delegate = nil
        tableView.dataSource = nil
        tableView.tableFooterView = UIView()
    }
    
    private func setupTableViewBinding() {
        
        viewModel.dataSource
            .bind(to: tableView.rx.items(cellIdentifier: cellIdentifier, cellType: ProductInfoTableViewCell.self)) {  row, element, cell in
                cell.configure(with: element)
            }
            .disposed(by: disposeBag)
    }
    
    private func setupChoiceTableViewObserver() {
        Observable
            .zip(tableView.rx.itemSelected, tableView.rx.modelSelected(Product.self))
            .bind { [unowned self] indexPath, product in
                self.presenter.appendToShoopingCart(product)
                if let selectedRowIndexPath = self.tableView.indexPathForSelectedRow {
                    self.tableView.deselectRow(at: selectedRowIndexPath, animated: true)
                }
            }
            .disposed(by: disposeBag)
    }
    
    @objc func doneTapped() {
        presenter.gotoCheckOut()
    }
}

// MARK: - Extensions -

extension HomeViewController: HomeViewProtocol {
    
    func show(productList: [Product]) {
        viewModel.update(dataSource: productList)
    }
    
    func update(productsCount: Int) {
        self.title = "\(productsCount) products selected"
    }
}
