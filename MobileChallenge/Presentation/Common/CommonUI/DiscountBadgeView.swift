//
//  DiscountBadgeView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct DiscountBadgeView: View {
    
    private let badgeText: String
    
    init(badgeText: String) {
        self.badgeText = badgeText
    }
    
    var body: some View {
        HStack {
            Text(badgeText)
                .font(.system(size: 12))
                .padding(.all, 6)
                .foregroundColor(.white)
                .background(.purple)
                .cornerRadius(8.0)
        }
    }
}

// MARK: - Preview
#if DEBUG && TESTING
struct DiscountBadgeView_Previews: PreviewProvider {
    static var previews: some View {
        DiscountBadgeView(badgeText: CartLayoutViewModel.CartItem.defaultType.discountBadgeText ?? "")
    }
}
#endif
