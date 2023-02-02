//
//  LoadableState.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import Foundation

enum LoadableState<Value> {
    case idle
    case loading
    case failed(Error)
    case loaded(Value)
}
