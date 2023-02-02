//
//  EmptyStateView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI

struct EmptyStateView: View {
    
    enum EmptyType {
        case products
        case cart
        case error(Error)
        
        var imageNamed: String {
            switch self {
            case .products: return "tray.fill"
            case .cart: return "cart.fill"
            case .error(let error):
                switch error {
                case ConnectionError.noNetworkAvailable: return "wifi.slash"
                default: return "icloud.slash.fill"
                }
            }
        }
        
        var emptyText: String {
            switch self {
            case .products: return "Ops... we are updating our stocks!"
            case .cart: return "Your cart is empty. Go enjoy our discounts!"
            case .error(let error): return error.localizedDescription
            }
        }
        
        var retryButtonText: String? {
            switch self {
            case .cart: return "Continue shopping"
            case .error: return "Retry"
            default: return nil
            }
        }
    }
    
    var emptyType: EmptyType
    var onRetryAction: (() -> Void)?
    
    var body: some View {
        VStack {
            Image(systemName: emptyType.imageNamed)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 100, height: 100)
                .padding(.all, 20)
                .foregroundColor(.purple)
            
            Text(emptyType.emptyText)
                .multilineTextAlignment(.center)
                .foregroundColor(.purple)
            
            Spacer()
            
            if let retryButtonText = emptyType.retryButtonText,
               let onRetryAction = onRetryAction {
                PrimaryButtonView(buttonText: retryButtonText, onTapAction: onRetryAction)
            }
        }
        .padding(.all, 40)
    }
}

// MARK: - Preview
#if DEBUG
struct EmptyStateView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            EmptyStateView(emptyType: .products)
            EmptyStateView(emptyType: .cart, onRetryAction: { })
        }
    }
}
#endif
