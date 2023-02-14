//
//  StorageConfiguration.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation

public enum StorageConfiguration {
    case basic(identifier: String)
    case inMemory(identifier: String? = nil)
    
    var identifier: String? {
        switch self {
        case .basic(let identifier): return identifier
        case .inMemory(let identifier): return identifier
        }
    }
}
