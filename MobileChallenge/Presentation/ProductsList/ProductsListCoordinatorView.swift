//
//  ProductsListCoordinatorView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct ProductsListCoordinatorView: View {
    
    @ObservedObject var coordinatorObject: ProductsListCoordinator
    
    var body: some View {
        NavigationView {
            ProductsListView(viewModel: coordinatorObject.productsListViewModel)
        }
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsListCoordinatorView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListCoordinatorView(coordinatorObject: ProductsListCoordinator.preview)
    }
}
#endif

