//
//  CheckoutPresenter.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import UIKit
import RxSwift
import RxCocoa

final public class CheckoutPresenter {
    
    private weak var view: CheckoutViewProtocol?
    private let interactor: CheckoutInteractorInputProtocol
    private let wireframe: RootWireframe
    private let shoopintCart: ShoppingCart
    private let disposeBag = DisposeBag()
    
    init(view: CheckoutViewProtocol, interactor: CheckoutInteractorInputProtocol, wireframe: RootWireframe, shoppingCart: ShoppingCart) {
        self.view = view
        self.interactor = interactor
        self.wireframe = wireframe
        self.shoopintCart = shoppingCart
    }
}

extension CheckoutPresenter: CheckoutPresenterProtocol {
    
    public func viewDidLoad() {
        let request =  CheckoutModels.getCheckoput.Request(shoppingCart: shoopintCart)
        interactor.getCheckout(request: request)
    }
}

extension CheckoutPresenter: CheckoutInteractorOutputProtocol {
    func presentCheckout(response: CheckoutModels.getCheckoput.Response) {
        let totalSum = response.checkout.map({$0.totalPrice}).reduce(0, +)
        let viewModel = CheckoutModels.getCheckoput.ViewModel(checkout: response.checkout,
                                                              totalPrice: totalSum)
        view?.displayCheckoutDetail(viewModel: viewModel)
    }
}
