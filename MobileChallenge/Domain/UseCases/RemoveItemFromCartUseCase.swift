//
//  RemoveItemFromCartUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine

protocol RemoveItemFromCartUseCase {
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error>
}

final class DefaultRemoveItemFromCartUseCase: RemoveItemFromCartUseCase {
    
    private let cartRepository: CartRepository
    
    init(cartRepository: CartRepository) {
        self.cartRepository = cartRepository
    }
    
    func removeItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error> {
        return cartRepository.removeItem(item)
    }
}

#if DEBUG
extension DefaultRemoveItemFromCartUseCase {
    static var preview: Self {
        .init(cartRepository: DefaultCartRepository.preview)
    }
}
#endif
