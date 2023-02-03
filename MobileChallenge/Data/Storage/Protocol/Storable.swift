//
//  Storable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

protocol Storable {
    static var predicateFormat: String { get }
    var primaryKey: String { get }
}
