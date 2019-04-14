//
//  CheckoutProtocols.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import RxSwift

//MARK: ViewController -
protocol CheckoutViewProtocol: ViewProtocol {
    
    /* Presenter -> ViewController */
    func displayCheckoutDetail(viewModel: CheckoutModels.getCheckoput.ViewModel)
}

//MARK: Presenter -
protocol CheckoutPresenterProtocol: PresenterProtocol {
    
    /* ViewController -> Presenter */
}


//MARK: Interactor -
protocol CheckoutInteractorInputProtocol: InteractorInputProtocol {
    
    /* Presenter -> Interactor */
    func getCheckout(request: CheckoutModels.getCheckoput.Request)
}

protocol CheckoutInteractorOutputProtocol: InteractorOutputProtocol {

    /* Interactor -> Presenter */
    func presentCheckout(response: CheckoutModels.getCheckoput.Response)
}

//MARK: DataManager -
protocol CheckoutLocalDataManagerInputProtocol: LocalDataManagerInputProtocol {

    /* Interactor -> LocalDatamanager */
    func getDiscounts() -> Dictionary<String, Discount>
}

protocol CheckoutRemoteDataManagerInputProtocol: RemoteDataManagerInputProtocol {

    /* Interactor -> RemoteDatamanager */
    
}
