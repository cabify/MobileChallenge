//
//  CartView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct CartView: View {
    
    @Environment(\.dismiss) var dismiss
    @ObservedObject var viewModel: CartViewModel
    
    var body: some View {
        NavigationView {
            List {
                Text("Cart 1")
                Text("Cart 2")
                Text("Cart 3")
                Text("Cart 4")
                
            }
            .navigationTitle(Text("Products list"))
            .toolbar {
                Button("Close") {
                    dismiss()
                }
            }
        }
    }
}

// MARK: - Preview
#if DEBUG
struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        CartView(viewModel: CartViewModel(coordinator: ProductsListCoordinator()))
    }
}
#endif
