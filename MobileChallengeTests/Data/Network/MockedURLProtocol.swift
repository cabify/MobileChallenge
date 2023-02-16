//
//  MockedURLProtocol.swift
//  MobileChallengeTests
//
//  Created by thomas pereira on 13/02/2023.
//

import Foundation
@testable import MobileChallenge

class MockedURLProtocol: URLProtocol {
    
    static private var testURLs = [URL: Data]()
    static private var response: URLResponse?
    static private var error: LocalizedError?
    
    static func configureMockedURLSession(url: URL? = nil, encoded: Data? = nil, statusCode: Int? = 200, error: LocalizedError? = nil) -> URLSessionConfiguration {
        
        if let anUrl = url, let anEncoded = encoded {
            MockedURLProtocol.testURLs = [
                anUrl: anEncoded
            ]
        }
        
        if let aStatusCode = statusCode {
            if aStatusCode < 0 {
                // Invalid response
                MockedURLProtocol.response = URLResponse(
                    url: URL(string: "http://localhost:8080")!,
                    mimeType: nil,
                    expectedContentLength: 0,
                    textEncodingName: nil
                )
            } else  {
                // Valid response
                MockedURLProtocol.response = HTTPURLResponse(
                    url: URL(string: "http://localhost:8080")!,
                    statusCode: aStatusCode,
                    httpVersion: nil,
                    headerFields: nil
                )
            }
        }
        
        MockedURLProtocol.error = error
        
        let mockedSessionConfig = URLSessionConfiguration.ephemeral
        mockedSessionConfig.protocolClasses = [MockedURLProtocol.self]
        return mockedSessionConfig
    }
    
    override class func canInit(with request: URLRequest) -> Bool {
        return true
    }
    
    // ignore this method; just send back what we were given
    override class func canonicalRequest(for request: URLRequest) -> URLRequest {
        return request
    }
    
    override func startLoading() {
        if let url = request.url, let data = MockedURLProtocol.testURLs[url] {
            self.client?.urlProtocol(self, didLoad: data)
        }
        
        if let response = MockedURLProtocol.response {
            self.client?.urlProtocol(
                self,
                didReceive: response,
                cacheStoragePolicy: .notAllowed
            )
        }
        
        if let error = MockedURLProtocol.error {
            self.client?.urlProtocol(self, didFailWithError: error)
        }
        
        self.client?.urlProtocolDidFinishLoading(self)
    }
    
    override func stopLoading() { }
}
