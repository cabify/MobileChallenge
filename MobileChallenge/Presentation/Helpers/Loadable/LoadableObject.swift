//
//  LoadableObject.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import Foundation

protocol LoadableObject: ObservableObject {
    associatedtype Output
    var state: LoadableState<Output> { get }
    func load()
}
