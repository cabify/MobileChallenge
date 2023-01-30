//
//  ApiError.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation

public enum APIError: LocalizedError {
    case noData
    case parseError(Error? = nil, String? = "There was an error parsing the data.")
    
    // Fall under 400..499
    case badRequest(Error? = nil, String? = "Invalid request")
    case unauthorised(Error? = nil, String? = "Unautorised request, Please login.")
    case notFound(Error? = nil, String? = "Data not found, Please retry after some time.")
    case invalidResponse(Error? = nil, String? = "Invalid response. Please retry after some time.")
    case conflictError(Error? = nil, String? = "Already exist.")
    
    // 500 & above
    case serverError(Error? = nil, String? = "Services are down, Please retry after some time.")
    
    // Some unknown Error
    case `unknown`(Error? = nil, String? = "Some error occured, Please try again.")
    
    public var errorDescription: String? {
        switch self {
        case .noData: return "Could not received data from the server. Please retry."
        case .parseError(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .badRequest(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .unauthorised(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .notFound(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .invalidResponse(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .conflictError(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .serverError(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .unknown(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        }
    }
}

public enum ConnectionError: LocalizedError {
    
    case noNetworkAvailable(Error? = nil, String? = "Oops, looks like you are offline. Please check your network connection and try again.")
    
    public var errorDescription: String? {
        switch self {
        case .noNetworkAvailable(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        }
    }
}

public enum RequestableError: LocalizedError {
    
    case invalidURL(Error? = nil, String? = "Invalid Url")
    case parameterEncodingFailed(Error? = nil, String? = "Encoding Failed")
    
    public var errorDescription: String? {
        switch self {
        case .invalidURL(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        case .parameterEncodingFailed(let underlyingError, let errorMessage): return underlyingError?.localizedDescription ?? errorMessage
        }
    }
}
