//
//  PriceView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct PriceView: View {
    
    // Constant
    static let fontSize: CGFloat = 16
    
    // Properties
    var price: String
    var specialPrice: String?
    private let showSpecialPrice: Bool
    
    // Init
    init(price: String, specialPrice: String? = nil) {
        self.price = price
        self.showSpecialPrice = specialPrice != nil
        self.specialPrice = specialPrice
    }
    
    var body: some View {
        HStack {
            // Price
            priceLabel
            
            // Special price
            if let specialPrice = self.specialPrice,
               showSpecialPrice {
                specialPriceLabel(specialPrice)
            }
        }
    }
    
    @ViewBuilder
    private var priceLabel: some View {
        let fontSize: CGFloat = showSpecialPrice ? 14 : PriceView.fontSize
        let opacity = showSpecialPrice ? 0.8 : 1
        
        Text(price)
            .font(.system(size: fontSize, weight: .medium))
            .foregroundColor(.black.opacity(opacity))
            .strikethrough(showSpecialPrice)
    }
    
    @ViewBuilder
    private func specialPriceLabel(_ specialPrice: String) -> some View {
        Text(specialPrice)
            .font(.system(size: PriceView.fontSize, weight: .bold))
            .foregroundColor(.green)
    }
}

// MARK: - Preview
#if DEBUG
struct PriceView_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            PriceView(price: "20€", specialPrice: nil)
            PriceView(price: "20€", specialPrice: "19€")
        }
    }
}
#endif
