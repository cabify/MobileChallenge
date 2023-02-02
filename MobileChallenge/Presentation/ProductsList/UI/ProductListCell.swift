//
//  ProductListCell.swift
//  MobileChallenge
//
//  Created by thomas pereira on 01/02/2023.
//

import SwiftUI

struct ProductListCell: View {
    
    // Properties
    var product: ProductsListViewModel.SingleProduct
    
    var body: some View {
        VStack(spacing: 5) {
            // Product info
            HStack(spacing: 10) {
                Text(product.name)
                    .font(.system(size: 16, weight: .medium))
                    .foregroundColor(.black)
                Spacer()
                CartQuantityView(cartQuantity: 1)
                PriceView(price: product.formattedPrice)
                    .frame(width: 60, alignment: .trailing)
            }
            
            // Discount badge
            if let badgeText = product.productType.discountBadgeText {
                DiscountBadgeView(badgeText: badgeText)
            }
        }
        .padding(.bottom, 10)
    }
}

// MARK: - Preview
#if DEBUG
struct ProductListCell_Previews: PreviewProvider {
    static var previews: some View {
        List(ProductsListViewModel.SingleProduct.preview) { aProduct in
            ProductListCell(product: aProduct)
        }
    }
}
#endif
