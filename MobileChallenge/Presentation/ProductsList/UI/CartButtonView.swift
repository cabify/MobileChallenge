//
//  CartButtonView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI

struct CartButtonView: View {
    
    private let onTapAction: () -> Void
    
    init(onTapAction: @escaping () -> Void) {
        self.onTapAction = onTapAction
    }
    
    var body: some View {
        Button {
            onTapAction()
        } label: {
            Image(systemName: "cart")
        }
        .tint(.purple)
        .accessibilityIdentifier(AccessibilityID.Navigation.CartButton)
    }
}

// MARK: - Preview
#if DEBUG && TESTING
struct OpenCartButtonView_Previews: PreviewProvider {
    static var previews: some View {
        CartButtonView(onTapAction: { })
    }
}
#endif
