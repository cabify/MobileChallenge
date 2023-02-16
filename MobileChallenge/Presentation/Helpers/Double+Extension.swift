//
//  Double+Extension.swift
//  MobileChallenge
//
//  Created by thomas pereira on 07/02/2023.
//

import Foundation

extension Double {
    var currency: String {
        return String(format: "%.2f€", self)
    }
}
