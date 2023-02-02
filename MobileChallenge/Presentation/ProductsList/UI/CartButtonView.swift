//
//  CartButtonView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI

struct CartButtonView: View {
    
    var onTapAction: () -> Void
    
    var body: some View {
        Button {
            onTapAction()
        } label: {
            Image(systemName: "cart")
        }
        .tint(.purple)
    }
}

// MARK: - Preview
#if DEBUG
struct OpenCartButtonView_Previews: PreviewProvider {
    static var previews: some View {
        CartButtonView(onTapAction: { })
    }
}
#endif
