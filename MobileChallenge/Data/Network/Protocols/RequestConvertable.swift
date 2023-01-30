//
//  RequestConvertable.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation

protocol RequestConvertable: Requestable {
    func asURLRequest() throws -> URLRequest
}

extension RequestConvertable {
    
    func asURLRequest() throws -> URLRequest {
        
        guard let baseURL = URL(string: ApiClientConstants.baseURL) else { throw RequestableError.invalidURL() }
        let completeURL = baseURL.appendingPathComponent(path)
        var urlRequest =  URLRequest(url: completeURL)
        
        urlRequest.httpMethod = method
        
        if let headers = header {
            for (key, value) in headers {
                urlRequest.setValue(value, forHTTPHeaderField: key)
            }
        }
        
        var param = parameter()
        
        switch method {
        case "POST": urlRequest = try JSONEncoding.default.encode(urlRequest, with: param)
        case "GET": urlRequest = try URLEncoding.default.encode(urlRequest, with: param)
        default: break
        }
        
        return urlRequest
    }
}
