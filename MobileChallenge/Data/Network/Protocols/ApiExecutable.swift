//
//  ApiExecutable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation
import Combine

// Declare the abstraction for the request executor implementation
protocol ApiExecutable {
    func executeRequest<R>(request: R) -> AnyPublisher<R.Response, Error> where R: RequestConvertable
}
