//
//  ProductsListView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct ProductsListView: View {
    
    @ObservedObject var viewModel: ProductsListViewModel
    @State var isPresented: Bool = false
    
    var body: some View {
        List {
            Text("Product 1")
            Text("Product 2")
            Text("Product 3")
        }
        .navigationTitle(Text("Products list"))
        .toolbar {
            CartButtonView(tapAction: {
                isPresented.toggle()
            })
            .sheet(isPresented: $isPresented) {
                CartView(viewModel: viewModel.openCart())
            }
            .onChange(of: isPresented) { isPresented in
                viewModel.isPresented = isPresented
            }
        }
        }
    }
}

// MARK: - Preview
#if DEBUG
struct ProductsListView_Previews: PreviewProvider {
    static var previews: some View {
        ProductsListView(viewModel: ProductsListViewModel.preview)
    }
}
#endif
