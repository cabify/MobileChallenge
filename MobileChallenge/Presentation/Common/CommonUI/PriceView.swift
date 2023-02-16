//
//  PriceView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct PriceView: View {
    
    // Properties
    private let price: String
    private let specialPrice: String?
    private let showSpecialPrice: Bool
    private let showPriceInline: Bool
    
    // Init
    init(price: String, specialPrice: String? = nil, inline: Bool = false) {
        self.price = price
        self.specialPrice = specialPrice
        self.showSpecialPrice = specialPrice != nil
        self.showPriceInline = inline
    }
    
    var body: some View {
        if showPriceInline {
            HStack(alignment: .firstTextBaseline, spacing: 10) {
                // Price
                priceLabel
                
                // Special price
                if let specialPrice = self.specialPrice,
                   showSpecialPrice {
                    specialPriceLabel(specialPrice)
                }
            }
            
        } else {
            VStack(alignment: .trailing) {
                // Special price
                if let specialPrice = self.specialPrice,
                   showSpecialPrice {
                    specialPriceLabel(specialPrice)
                }
                
                // Price
                priceLabel
            }
        }
    }
    
    @ViewBuilder
    private var priceLabel: some View {
        let fontSize: CGFloat = showSpecialPrice ? 14 : 16
        let opacity = showSpecialPrice ? 0.8 : 1
        
        Text(price)
            .font(.system(size: fontSize, weight: .medium))
            .foregroundColor(.black.opacity(opacity))
            .strikethrough(showSpecialPrice)
    }
    
    @ViewBuilder
    private func specialPriceLabel(_ specialPrice: String, inline: Bool = false) -> some View {
        Text(specialPrice)
            .font(.system(size: inline ? 20 : 18, weight: .bold))
            .foregroundColor(.purple)
    }
}

// MARK: - Preview
#if DEBUG && TESTING
struct PriceView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            PriceView(price: "20€", specialPrice: nil)
            PriceView(price: "20€", specialPrice: "19€")
            PriceView(price: "20€", specialPrice: "19€", inline: true)
        }
    }
}
#endif
