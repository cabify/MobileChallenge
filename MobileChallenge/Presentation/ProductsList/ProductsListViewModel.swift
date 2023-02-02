//
//  ProductsListViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Foundation
import Combine

final class ProductsListViewModel: ObservableObject {
    
    // ViewModel of single product
    struct SingleProduct: Identifiable {
        
        static let defaultType: ProductType = .voucher
        // Types
        enum ProductType: String {
            case voucher = "VOUCHER"
            case shirt = "TSHIRT"
            case mug = "MUG"
            
            var discountBadgeText: String? {
                switch self {
                case .voucher: return "Buy 2 and get 1 free"
                case .shirt: return "€1 discount per unit for 3+"
                default: return nil
                }
            }
        }
        
        typealias Identifier = UUID
        
        let id = Identifier()
        let productType: ProductType
        let name: String
        let formattedPrice: String
        let showSpecialPrice: Bool
        let formattedSpecialPrice: String?
        let showDiscountBadge: Bool
        let cartCount: Int
        
        init?(product: ProductsList.Product) {
            guard let productType = ProductType(rawValue: product.code) else { return nil }
            self.productType = productType
            self.name = product.name
            self.formattedPrice = String(format: "%.2f€", product.price)
            self.showSpecialPrice = false
            self.formattedSpecialPrice = String(format: "%.2f€", product.price)
            self.showDiscountBadge = productType.discountBadgeText != nil
            self.cartCount = 0
        }
        
        #if DEBUG
        static var preview: [Self] {
            ProductsList.preview.products.compactMap({ .init(product: $0) })
        }
        #endif
    }
    
    private unowned let coordinator: ProductsListCoordinator
    private let productsListUseCase: FetchProductsListUseCase
    private var cancellables = Set<AnyCancellable>()
    @Published var products = [SingleProduct]()
    @Published var isPresented: Bool = false
    
    init(coordinator: ProductsListCoordinator, productsListUseCase: FetchProductsListUseCase) {
        self.coordinator = coordinator
        self.productsListUseCase = productsListUseCase
    }
    
    func fetchProducts() {
        productsListUseCase.getProductsList()
            .sink(receiveCompletion: { _ in }, receiveValue: { [weak self] productList in
                self?.products = productList.products.compactMap({ SingleProduct(product: $0) })
            })
            .store(in: &cancellables)
    }
    
    func openCart() {
        self.coordinator.openCart()
    }
}

#if DEBUG
extension ProductsListViewModel {
    static var preview: Self {
        .init(coordinator: ProductsListCoordinator.preview, productsListUseCase: DefaultFetchProductsListUseCase.preview)
    }
}
#endif
