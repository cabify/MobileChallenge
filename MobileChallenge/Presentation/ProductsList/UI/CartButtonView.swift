//
//  CartButtonView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI

struct CartButtonView: View {
    
    var tapAction: () -> Void
    
    var body: some View {
        Button {
            tapAction()
        } label: {
            Image(systemName: "cart")
        }
    }
}

// MARK: - Preview
#if DEBUG
struct OpenCartButtonView_Previews: PreviewProvider {
    static var previews: some View {
        CartButtonView(tapAction: { })
    }
}
#endif
