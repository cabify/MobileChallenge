//
//  GetCartUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 06/02/2023.
//

import Foundation
import Combine

protocol GetCartUseCase {
    func getCart() -> AnyPublisher<Cart, Error>
}

final class DefaultGetCartUseCase: GetCartUseCase {
    
    private let cartRepository: CartRepository
    
    init(cartRepository: CartRepository) {
        self.cartRepository = cartRepository
    }
    
    func getCart() -> AnyPublisher<Cart, Error> {
        return cartRepository.getCart()
    }
}

#if DEBUG && TESTING
extension DefaultGetCartUseCase {
    static var preview: Self {
        .init(cartRepository: DefaultCartRepository.preview)
    }
}
#endif
