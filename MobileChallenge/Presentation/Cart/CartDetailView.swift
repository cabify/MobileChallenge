//
//  CartDetailView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 31/01/2023.
//

import SwiftUI
import Combine

struct CartDetailView: View {
    
    @Environment(\.dismiss) var dismiss
    @ObservedObject var viewModel: CartDetailViewModel
    
    var body: some View {
        NavigationView {
            EmptyStateView(emptyType: .cart, onRetryAction: {
                dismiss()
            })
            .toolbar {
                Button("Close") {
                    dismiss()
                }
                .tint(.purple)
            }
        }
    }
}

// MARK: - Preview
#if DEBUG
struct CartView_Previews: PreviewProvider {
    static var previews: some View {
        CartDetailView(viewModel: CartDetailViewModel.preview)
    }
}
#endif
