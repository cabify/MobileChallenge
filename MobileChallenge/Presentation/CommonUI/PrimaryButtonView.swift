//
//  PrimaryButtonView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI

struct PrimaryButtonView: View {
    
    var buttonText: String
    var onTapAction: (() -> Void)
    
    var body: some View {
        HStack {
            Button(buttonText) {
                onTapAction()
            }
            .font(.system(size: 20, weight: .medium))
            .padding(EdgeInsets(top: 10, leading: 20, bottom: 10, trailing: 20))
            .foregroundColor(.white)
            .background(.purple)
            .cornerRadius(8.0)
        }
    }
}

// MARK: - Preview
#if DEBUG
struct PrimaryButtonView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            PrimaryButtonView(buttonText: "Continue shopping", onTapAction: { })
        }
    }
}
#endif
