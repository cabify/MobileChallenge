//
//  ApiClient.swift
//  MobileChallenge
//
//  Created by thomas pereira on 30/01/2023.
//

import Foundation
import Combine

enum ApiClientConstants {
    static let baseURL = "https://gist.githubusercontent.com/palcalde/6c19259bd32dd6aafa327fa557859c2f/raw/ba51779474a150ee4367cda4f4ffacdcca479887"
    static let reponseValidRange: ClosedRange<Int> = (200...399)
}

final class ApiClient: ApiExecutable {
    
    private var configuration: URLSessionConfiguration
    private var session: URLSession
    
    init(sessionConfiguration: URLSessionConfiguration = URLSessionConfiguration.default) {
        self.configuration = sessionConfiguration
        self.session = URLSession(configuration: sessionConfiguration)
    }
    
    func executeRequest<R>(request: R) -> AnyPublisher<R.Response, Error> where R : RequestConvertable {
        
        do {
            let urlRequest = try request.asURLRequest()
            return session.dataTaskPublisher(for: urlRequest)
                .tryMap { [weak self] data, response -> Data in
                    guard let httpResponse = response as? HTTPURLResponse else {
                        throw APIError.invalidResponse()
                    }
                    
                    guard ApiClientConstants.reponseValidRange.contains(httpResponse.statusCode) else {
                        throw self?.parseError(data: data, response: response, errorParser: request.errorParser) ?? APIError.unknown()
                    }
                    
                    return data
                }
                .tryMap { data in
                    if let parser = request.parser {
                        do {
                            guard let parsed = try parser.parse(data: data) else {
                                throw APIError.parseError()
                            }
                            return parsed
                            
                        } catch {
                            throw APIError.noData
                        }
                    }
                    
                    do {
                        return try JSONDecoder().decode(R.Response.self, from: data)
                        
                    } catch {
                        throw APIError.parseError()
                    }
                }
                .mapError { error in
                    if let error = error as? RequestableError {
                        return error
                        
                    } else if let error = error as? APIError {
                        return error
                    }
                    
                    return APIError.unknown(error)
                }
                .receive(on: RunLoop.main)
                .eraseToAnyPublisher()
            
        } catch {
            return Fail(error: error)
                .eraseToAnyPublisher()
        }
    }
    
    private func parseError(data: Data, response: URLResponse, errorParser: ErrorParserType?) -> Error {
        
        var errorToReturn: Error?
        
        guard let httpUrlResponse = response as? HTTPURLResponse else {
            return APIError.invalidResponse()
        }
        
        if let errorParser = errorParser,
           let json = try? JSONSerialization.jsonObject(with: data, options: []) as? JSONDictionary,
           let parsedError = errorParser.parse(data: json) {
            errorToReturn = parsedError
        }
        
        switch httpUrlResponse.statusCode {
        case 400: return APIError.badRequest(errorToReturn)
        case 404: return APIError.notFound(errorToReturn)
        case 500...599: return APIError.serverError()
        default: return APIError.unknown()
        }
    }
}
