//
//  CartSummaryView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 08/02/2023.
//

import SwiftUI

struct CartSummaryView: View {
    
    private let cartViewModel: CartLayoutViewModel
    
    init(cartViewModel: CartLayoutViewModel) {
        self.cartViewModel = cartViewModel
    }
    
    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            // Subtotal
            summaryItem(
                titleText: "Subtotal",
                valueText: cartViewModel.formattedSubtotal
            )
            
            // Discounts
            if cartViewModel.showDiscounts {
                VStack(alignment: .leading, spacing: 5) {
                    ForEach(cartViewModel.discounts) { aDiscount in
                        summaryItem(
                            titleText: aDiscount.text,
                            valueText: aDiscount.formattedValue,
                            isDiscount: true
                        )
                    }
                }
            }
            
            Divider()
            
            // Total
            summaryItem(
                titleText: "Total",
                valueText: cartViewModel.formattedTotal,
                isHighlighted: true
            )
        }
        .padding(EdgeInsets(top: 20, leading: 20, bottom: 20, trailing: 20))
    }
    
    @ViewBuilder
    private func summaryItem(titleText: String, valueText: String, isHighlighted: Bool = false, isDiscount: Bool = false) -> some View {
        let fontSize: CGFloat = isDiscount ? 16 : isHighlighted ? 22 : 18
        let fontWeight: Font.Weight = isDiscount ? .regular : .medium
        let textColor: Color = isDiscount ? .purple : .black
        
        HStack(alignment: .center, spacing: 10) {
            Text(titleText)
                .font(.system(size: fontSize, weight: fontWeight))
                .foregroundColor(textColor)
                .multilineTextAlignment(.leading)
            
            Spacer()
            
            Text(valueText)
                .font(.system(size: fontSize, weight: fontWeight))
                .foregroundColor(textColor)
                .multilineTextAlignment(.trailing)
        }
    }
}

// MARK: - Preview
#if DEBUG
struct CartSummaryView_Previews: PreviewProvider {
    static var previews: some View {
        CartSummaryView(cartViewModel: CartLayoutViewModel.preview)
    }
}
#endif
