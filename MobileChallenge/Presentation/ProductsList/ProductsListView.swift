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
        List(viewModel.products) { aProduct in
            ProductListCell(product: aProduct)
        }
        // Hack to disable row selection and allow
        // the tap on inner buttons
        .onTapGesture { return }
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
