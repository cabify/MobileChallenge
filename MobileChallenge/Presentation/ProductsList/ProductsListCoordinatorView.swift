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
            ProductsView(viewModel: coordinatorObject.productsViewModel)
            .navigationTitle(Text("Products list"))
            .navigationBarTitleDisplayMode(.large)
            .toolbar {
                CartButtonView(onTapAction: {
                    self.showingSheet.toggle()
                    coordinatorObject.openCart()
                })
                .sheet(isPresented: $showingSheet) {
                    CartDetailView(viewModel: coordinatorObject.cartDetailViewModel!)
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

