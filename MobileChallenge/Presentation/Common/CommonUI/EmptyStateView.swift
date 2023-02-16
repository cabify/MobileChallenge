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
            case .error: return "icloud.slash.fill"
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
            case .cart: return AccessibilityID.CartDetailView.EmptyCartButton
            case .error: return "Retry"
            default: return nil
            }
        }
    }
    
    private let emptyType: EmptyType
    private let onRetryAction: (() -> Void)?
    
    init(emptyType: EmptyType, onRetryAction: (() -> Void)? = nil) {
        self.emptyType = emptyType
        self.onRetryAction = onRetryAction
    }
    
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
#if DEBUG && TESTING
struct EmptyStateView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            EmptyStateView(emptyType: .products)
            EmptyStateView(emptyType: .cart, onRetryAction: { })
        }
    }
}
#endif
