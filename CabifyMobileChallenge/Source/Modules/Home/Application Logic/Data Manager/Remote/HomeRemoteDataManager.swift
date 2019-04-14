//
//  HomeRemoteDataManager.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import RxSwift
import Alamofire

final class HomeRemoteDataManager {
    private let networkService = BaseNetworkRequest<ProductResponse>()
}

// MARK: - Extensions -

extension HomeRemoteDataManager: HomeRemoteDataManagerInputProtocol {
    func getProductList() -> Observable<ProductResponse> {
        return networkService.getResponseObject(url: APIPath.locations.path)
    }
}
