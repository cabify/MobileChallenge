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
    var emptyStateType: EmptyStateView.EmptyType { get }
    func load()
}
