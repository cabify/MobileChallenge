//
//  ParameterEncodable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation

public typealias Parameters = [String: Any]

protocol ParameterEncodable {
    func encode(_ urlRequest: URLRequest, with parameters: Parameters?) throws -> URLRequest
}

struct JSONEncoding: ParameterEncodable {
    
    public static var `default`: URLEncoding { return URLEncoding() }
    
    func encode(_ urlRequest: URLRequest, with parameters: Parameters?) throws -> URLRequest {
        var urlRequest = urlRequest
        
        guard let parameters = parameters else { return urlRequest }
        
        do {
            let data = try JSONSerialization.data(withJSONObject: parameters, options: [])
            
            if urlRequest.value(forHTTPHeaderField: "Content-Type") == nil {
                urlRequest.setValue("application/json", forHTTPHeaderField: "Content-Type")
            }
            
            urlRequest.httpBody = data
            
        } catch {
            throw RequestableError.parameterEncodingFailed(error, "JSON encoding failed")
        }
        
        return urlRequest
    }
}

struct URLEncoding: ParameterEncodable {
    
    public static var `default`: URLEncoding { return URLEncoding() }
    
    func encode(_ urlRequest: URLRequest, with parameters: Parameters?) throws -> URLRequest {
        var urlRequest = urlRequest
        
        guard let parameters = parameters else { return urlRequest }
        
        if urlRequest.httpMethod != nil {
            guard let url = urlRequest.url else { throw RequestableError.parameterEncodingFailed(nil, "URL not found") }
            
            if var urlComponents = URLComponents(url: url, resolvingAgainstBaseURL: false), !parameters.isEmpty {
                urlComponents.queryItems = parameters.map { (key, value) -> URLQueryItem in
                    return URLQueryItem(name: key, value: "\(value)".addingPercentEncoding(withAllowedCharacters: .urlHostAllowed))
                }
                urlRequest.url = urlComponents.url
            }
            
        } else {
            if urlRequest.value(forHTTPHeaderField: "Content-Type") == nil {
                urlRequest.setValue("application/x-www-form-urlencoded; charset=utf-8", forHTTPHeaderField: "Content-Type")
            }
        }
        
        return urlRequest
    }
}
