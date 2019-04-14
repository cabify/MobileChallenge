//
//  HomePresenter.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit
import RxSwift
import RxCocoa

final public class HomePresenter {
    
    private weak var view: HomeViewProtocol?
    private let interactor: HomeInteractorInputProtocol
    private let wireframe: RootWireframe
    private let shoppingCart = ShoppingCart()
    private let disposeBag = DisposeBag()
    
    init(view: HomeViewProtocol, interactor: HomeInteractorInputProtocol, wireframe: RootWireframe) {
        self.view = view
        self.interactor = interactor
        self.wireframe = wireframe
    }
    
    private func setupShoppingCartObserver() {
        shoppingCart.products.asObservable()
            .subscribe(onNext: {
                products in
                self.view?.update(productsCount: products.count)
            })
            .disposed(by: disposeBag)
    }
}

extension HomePresenter: HomePresenterProtocol {
    public func viewDidLoad() {
        setupShoppingCartObserver()
        interactor.getProducts()
    }
    
    public func viewWillAppear(animated: Bool) {
        shoppingCart.products.value.removeAll()
    }
    
    func appendToShoopingCart(_ product: Product) {
        shoppingCart.products.value.append(product)
    }
}

extension HomePresenter: HomeInteractorOutputProtocol {
    
    func showProducts(_ productList: [Product] ) {
        view?.show(productList: productList)
    }
    
    func gotoCheckOut() {
        wireframe.gotoCheckOutView(shoppingCart: shoppingCart)
    }
    
    func showError(_ error: String) {
        let alert = UIAlertController(title: "Alert",
                                      message: "Message",
                                      preferredStyle: UIAlertController.Style.alert)
        alert.addAction(UIAlertAction(title: "Click",
                                      style: UIAlertAction.Style.default, handler: nil))
        (view as! UIViewController).present(alert,
                                            animated: true,
                                            completion: nil)
    }
}
