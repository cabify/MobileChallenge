//
//  CartQuantityView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct CartQuantityView: View {
    
    @State var cartQuantity: Int
    
    var body: some View {
        HStack {
            // Minus button
            quantityButton(imageNamed: "minus.rectangle") {
                cartQuantity -= 1
            }.disabled(cartQuantity <= 0)
            
            // Current quantity
            Text("\(cartQuantity)")
                .font(.system(size: 16))
            
            // Plus button
            quantityButton(imageNamed: "plus.rectangle") {
                cartQuantity += 1
            }
        }
        .buttonStyle(BorderlessButtonStyle())
    }
    
    @ViewBuilder
    private func quantityButton(imageNamed: String, onTap: @escaping () -> Void) -> some View {
        Button {
            onTap()
        } label: {
            Image(systemName: imageNamed)
        }
        .tint(.black)
    }
}

// MARK: - Preview
#if DEBUG
struct CartQuantityView_Previews: PreviewProvider {
    static var previews: some View {
        CartQuantityView(cartQuantity: 0)
    }
}
#endif
