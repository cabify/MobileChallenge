//
//  CartDetailViewModel.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import Combine

final class CartDetailViewModel: LoadableObject, Identifiable {
    
    typealias Output = [SingleCartItemViewModel]
    
    private unowned let coordinator: ProductsListCoordinator
    private let getCartUseCase: GetCartUseCase
    private let addItemToCartUseCase: AddItemToCartUseCase
    private let removeItemToCartUseCase: RemoveItemFromCartUseCase
    private let clearCartUseCase: ClearCartUseCase
    private var cancellables = Set<AnyCancellable>()
    @Published var state: LoadableState<[SingleCartItemViewModel]> = .idle
    var emptyStateType: EmptyStateView.EmptyType { .cart }
    
    init(coordinator: ProductsListCoordinator, getCartUseCase: GetCartUseCase, addItemToCartUseCase: AddItemToCartUseCase, removeItemToCartUseCase: RemoveItemFromCartUseCase, clearCartUseCase: ClearCartUseCase) {
        self.coordinator = coordinator
        self.getCartUseCase = getCartUseCase
        self.addItemToCartUseCase = addItemToCartUseCase
        self.removeItemToCartUseCase = removeItemToCartUseCase
        self.clearCartUseCase = clearCartUseCase
    }
    
    // MARK: - Fetch
    func load() {
        state = .loading
        
        getCartUseCase.getCart()
            .flatMap { cart in
                self.getProductsListUseCase.getProductsList()
                    .map { productList in
                        let products = productList.products.compactMap({ product in
                            let cartQuantity = cart.items.first(where: { cartItem in
                                cartItem.code == ProductType.init(code: product.code)?.intValue
                            })?.quantity ?? 0
                            return SingleCartItemViewModel(product: product, cartQuantity: cartQuantity)
                        })
                        return .loaded(products)
                    }
            }
            .catch { error in
                Just(LoadableState.failed(error))
            }
            .sink { [weak self] state in
                self?.state = state
            }
            .store(in: &cancellables)
    }
}

#if DEBUG
extension CartDetailViewModel {
    static var preview: Self {
        .init(
            coordinator: ProductsListCoordinator.preview,
            getCartUseCase: DefaultGetCartUseCase.preview,
            addItemToCartUseCase: DefaultAddItemToCartUseCase.preview,
            removeItemToCartUseCase: DefaultRemoveItemFromCartUseCase.preview,
            clearCartUseCase: DefaultClearCartUseCase.preview
        )
    }
}
#endif
