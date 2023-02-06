//
//  ProductsListViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Foundation
import Combine

final class ProductsListViewModel: LoadableObject {
    
    typealias Output = [SingleCartItem]
    
    private unowned let coordinator: ProductsListCoordinator
    private let productsListUseCase: GetProductsListUseCase
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    private var cancellables = Set<AnyCancellable>()
    @Published var state: LoadableState<[SingleCartItem]> = .idle
    var emptyStateType: EmptyStateView.EmptyType { .products }
    
    init(coordinator: ProductsListCoordinator, productsListUseCase: GetProductsListUseCase, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase) {
        self.coordinator = coordinator
        self.productsListUseCase = productsListUseCase
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
    }
    
    func load() {
        state = .loading
        
        productsListUseCase.getProductsList()
            .map { productList in
                let products = productList.products.compactMap({ SingleCartItem(product: $0) })
                return .loaded(products)
            }
            .catch { error in
                Just(LoadableState.failed(error))
            }
            .sink { [weak self] state in
                self?.state = state
            }
            .store(in: &cancellables)
    }
    
    func openCart() {
        self.coordinator.openCart()
    }
}

// MARK: - ViewModel of a single product
extension ProductsListViewModel {
    
    struct SingleCartItem: Identifiable {
        
        static let defaultType: ProductType = .voucher
        
        typealias Identifier = UUID
        
        let id = Identifier()
        let productType: ProductType
        let name: String
        let formattedPrice: String
        let showSpecialPrice: Bool
        let formattedSpecialPrice: String?
        let showDiscountBadge: Bool
        let quantity: Int
        
        init?(product: ProductsList.Product) {
            guard let productType = ProductType(rawValue: product.code) else { return nil }
            self.productType = productType
            self.name = product.name
            self.quantity = 0
            self.formattedPrice = String(format: "%.2f€", product.price)
            self.showSpecialPrice = false // true
            self.formattedSpecialPrice = nil // String(format: "%.2f€", 19.0)
            self.showDiscountBadge = productType.discountBadgeText != nil
        }
        }
        
        #if DEBUG
        static var preview: [Self] {
            ProductsList.preview.products.compactMap({ .init(product: $0) })
        }
        #endif
    }
}

#if DEBUG
extension ProductsListViewModel {
    static var preview: Self {
        .init(
            coordinator: ProductsListCoordinator.preview,
            productsListUseCase: DefaultGetProductsListUseCase.preview,
            addItemToCartUseCase: DefaultAddItemToCartUseCase.preview,
            removeItemToCartUseCase: DefaultRemoveItemFromCartUseCase.preview
        )
    }
}
#endif
