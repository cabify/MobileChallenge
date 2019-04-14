//
//  HomeInteractor.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import RxCocoa
import RxSwift

final class HomeInteractor {
    weak var presenter: HomeInteractorOutputProtocol?
    private var localDatamanager: HomeLocalDataManagerInputProtocol?
    private var remoteDatamanager: HomeRemoteDataManagerInputProtocol?
    private var disposeBag = DisposeBag()
    
    init(local: HomeLocalDataManagerInputProtocol?, remote: HomeRemoteDataManagerInputProtocol?) {
        self.localDatamanager = local
        self.remoteDatamanager = remote
    }
}

extension HomeInteractor: HomeInteractorInputProtocol {
    
    func getProducts(){
        remoteDatamanager?.getProductList().subscribe(onNext: { [weak self] response in
            guard let strongSelf = self else { return }
            print(response.productList)
            strongSelf.presenter?.showProducts(response.productList)
            
            }, onError: { [weak self] error in
                guard let strongSelf = self else { return }
                strongSelf.presenter?.showError(error.localizedDescription)
        }).disposed(by: disposeBag)
    }
}
