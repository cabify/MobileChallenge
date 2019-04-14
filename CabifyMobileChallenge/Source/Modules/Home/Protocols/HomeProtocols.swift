//
//  HomeProtocols.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import RxSwift

//MARK: ViewController -
protocol HomeViewProtocol: ViewProtocol {
    
    /* Presenter -> ViewController */
    func update(productsCount: Int)
    func show(productList: [Product])
}

//MARK: Presenter -
protocol HomePresenterProtocol: PresenterProtocol {
    
    /* ViewController -> Presenter */
    func gotoCheckOut()
    func appendToShoopingCart(_ product: Product)
}


//MARK: Interactor -
protocol HomeInteractorInputProtocol: InteractorInputProtocol {
    
    /* Presenter -> Interactor */
    func getProducts()

}

protocol HomeInteractorOutputProtocol: InteractorOutputProtocol {

    /* Interactor -> Presenter */
    func showError(_ error: String)
    func showProducts(_ productList: [Product] )
}

//MARK: DataManager -
protocol HomeLocalDataManagerInputProtocol: LocalDataManagerInputProtocol {

    /* Interactor -> LocalDatamanager */
}

protocol HomeRemoteDataManagerInputProtocol: RemoteDataManagerInputProtocol {

    /* Interactor -> RemoteDatamanager */
    func getProductList() -> Observable<ProductResponse>
}
