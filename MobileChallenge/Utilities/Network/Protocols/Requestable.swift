//
//  Requestable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation

// A generic type protocol that defines the abstraction for Parser.
protocol ResponseParserType {
    associatedtype Response
    func parse(data: Data) throws -> Response?
}

// A protocol defining the abstraction for Error parser.
public typealias JSONDictionary = [String : Any]
protocol ErrorParserType {
    func parse(data: JSONDictionary) -> Error?
}

// An AssociatedType Protocol: Request type to be implemented by Concrete Requests
protocol Requestable {
    // A Response Type constrained to be decodable
    associatedtype Response: Decodable
    // Response parser contrained to be confirming to ResponseParserType
    associatedtype ResponseParser: ResponseParserType where ResponseParser.Response == Response
    
    // Basic properties required by a request
    var baseURL: String { get }
    var path: String { get }
    var method: String { get }
    var header: [String: String]? { get }
    var parser: ResponseParser? { get }
    var errorParser: ErrorParserType? { get }
    func parameter() -> [String: Any]?
}

// Default values
extension Requestable {
    var baseURL: String { ApiClientConstants.baseURL }
}
