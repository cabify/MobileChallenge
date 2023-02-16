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
        let testingUI = ProcessInfo.processInfo.arguments.contains("IS_UI_TESTING")
        guard !testingUI else {
            let mockedModel: Cart = .init(items: [
                .init(code: 0, name: "Cabify Voucher", quantity: 0, price: 5),
                .init(code: 1, name: "Cabify T-Shirt", quantity: 0, price: 20),
                .init(code: 2, name: "Cabify Coffee Mug", quantity: 0, price: 7.5)
            ])
            return Just(mockedModel)
                .mapError { _ in APIError.parseError() }
                .eraseToAnyPublisher()
        }
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
