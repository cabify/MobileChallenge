//
//  CheckoutInteractor.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import RxCocoa
import RxSwift

final class CheckoutInteractor {
    weak var presenter: CheckoutInteractorOutputProtocol?
    private var localDatamanager: CheckoutLocalDataManagerInputProtocol?
    private var remoteDatamanager: CheckoutRemoteDataManagerInputProtocol?
    private var disposeBag = DisposeBag()
    
    init(local: CheckoutLocalDataManagerInputProtocol?, remote: CheckoutRemoteDataManagerInputProtocol?) {
        self.localDatamanager = local
        self.remoteDatamanager = remote
    }
}

extension CheckoutInteractor: CheckoutInteractorInputProtocol {
    func getCheckout(request: CheckoutModels.getCheckoput.Request) {
        if let discounts = localDatamanager?.getDiscounts() {
            let checkouts = request.shoppingCart.totalCost(discount: discounts)
            let response = CheckoutModels.getCheckoput.Response(checkout: checkouts)
            presenter?.presentCheckout(response: response)
        }
    }
}
