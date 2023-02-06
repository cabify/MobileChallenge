//
//  AddItemToCartUseCase.swift
//  MobileChallenge
//
//  Created by thomas pereira on 03/02/2023.
//

import Foundation
import Combine

protocol AddItemToCartUseCase {
    func addItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error>
}

final class DefaultAddItemToCartUseCase: AddItemToCartUseCase {
    
    private let cartRepository: CartRepository
    
    init(cartRepository: CartRepository) {
        self.cartRepository = cartRepository
    }
    
    func addItem(_ item: Cart.Item) -> AnyPublisher<Cart, Error> {
        return cartRepository.addItem(item)
    }
}

#if DEBUG
extension DefaultAddItemToCartUseCase {
    static var preview: Self {
        .init(cartRepository: DefaultCartRepository.preview)
    }
}
#endif
