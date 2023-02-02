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
        
        var imageNamed: String {
            switch self {
            case .products: return "tray.fill"
            case .cart: return "cart.fill"
            }
        }
        
        var emptyText: String {
            switch self {
            case .products: return "Ops... we are updating our stocks!"
            case .cart: return "Your cart is empty. Go enjoy our discounts!"
            }
        }
    }
    
    var emptyType: EmptyType
    
    var body: some View {
        VStack(alignment: .center) {
            Image(systemName: emptyType.imageNamed)
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 100, height: 100)
                .padding(.all, 20)
                .foregroundColor(.purple)
            
            Text(emptyType.emptyText)
                .multilineTextAlignment(.center)
                .foregroundColor(.purple)
        }
        .padding(.all, 20)
    }
}

// MARK: - Preview
#if DEBUG
struct EmptyStateView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            EmptyStateView(emptyType: .products)
            EmptyStateView(emptyType: .cart)
        }
    }
}
#endif
