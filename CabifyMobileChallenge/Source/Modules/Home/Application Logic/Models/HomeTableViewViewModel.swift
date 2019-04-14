//
//  TableViewViewModel.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 13/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import RxSwift

class HomeTableViewViewModel {
    
    // MARK: Private properties
    var privateDataSource: Variable<[Product]> = Variable([])
    private let disposeBag = DisposeBag()
    
    // MARK: Outputs
    public var dataSource: Observable<[Product]>
    
    func update(dataSource: [Product]) {
        self.privateDataSource.value = dataSource
    }
    
    init() {
        self.dataSource = privateDataSource.asObservable()
    }
}
