//
//  ClearCartUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine

protocol ClearCartUseCase {
    func clearCart() -> AnyPublisher<Cart, Error>
}

final class DefaultClearCartUseCase: ClearCartUseCase {
    
    private let cartRepository: CartRepository
    
    init(cartRepository: CartRepository) {
        self.cartRepository = cartRepository
    }
    
    func clearCart() -> AnyPublisher<Cart, Error> {
        return cartRepository.clearCart()
    }
}

#if DEBUG && TESTING
extension DefaultClearCartUseCase {
    static var preview: Self {
        .init(cartRepository: DefaultCartRepository.preview)
    }
}
#endif
