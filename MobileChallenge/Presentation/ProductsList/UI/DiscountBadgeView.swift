//
//  DiscountBadgeView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct DiscountBadgeView: View {
    
    var badgeText: String
    
    var body: some View {
        HStack {
            Text(badgeText)
                .font(.system(size: 12))
                .padding(.all, 6)
                .foregroundColor(.white)
                .background(.purple)
                .cornerRadius(8.0)
            Spacer()
        }
    }
}

// MARK: - Preview
#if DEBUG
struct DiscountBadgeView_Previews: PreviewProvider {
    static var previews: some View {
        DiscountBadgeView(badgeText: ProductsListViewModel.SingleCartItem.defaultType.discountBadgeText ?? "")
    }
}
#endif
