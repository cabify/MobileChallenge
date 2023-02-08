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
    @State private var showingSheet = false
    
    var body: some View {
        NavigationView {
            ProductsListView(viewModel: coordinatorObject.productsListViewModel)
            .navigationTitle(Text("Products list"))
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                CartButtonView(onTapAction: {
                    self.showingSheet.toggle()
                })
                .sheet(isPresented: $showingSheet) {
                    CartDetailView(viewModel: coordinatorObject.productsListViewModel)
                }
            }
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

