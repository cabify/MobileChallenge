//
//  BaseNetworkRequest.swift
//  CabifyMobileChallenge
//
//  Created by Jesús Emilio Fernández de Frutos on 07/04/2019.
//  Copyright © 2019 Jesús Emilio Fernández de Frutos. All rights reserved.
//

import Foundation
import Alamofire
import RxAlamofire
import RxSwift
import ObjectMapper

class BaseNetworkRequest<T: Mappable>: NSObject{
    
    public override init() {
        super.init()
    }
    
    func getEncoding() -> ParameterEncoding{
        if (getMethodType() == .post || getMethodType() == .put){
            return JSONEncoding.default
        }
        return URLEncoding.default
    }
    
    func getMethodType() -> HTTPMethod {
        return .get
    }
    
    func getHeaders() -> HTTPHeaders {
        let headers: HTTPHeaders = [
            "Content-Type": "application/json"
        ]
        return headers
    }
    
    func getParameters() -> Parameters {
        return [:]
    }
 
    public func getResponseObject(url: String) -> Observable<T>{
        let urlStr = url.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed)
        let url = URL(string: urlStr!)
        let alamofireRequest = RxAlamofire.request(.get,
                                                   url!,
                                                   parameters: getParameters(),
                                                   encoding: getEncoding(),
                                                   headers: getHeaders())
        
        let observable = alamofireRequest.flatMap{
            $0.rx_responseObject(type: T.self)
        }
        return observable
        
    }
}
