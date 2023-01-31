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
