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
        HStack(alignment: .firstTextBaseline, spacing: 10) {
            // Product name, badge and cart counting
            VStack(alignment: .leading, spacing: 5) {
                // Product name and cart counting
                HStack {
                    // Name
                    Text(product.name)
                        .font(.system(size: 16, weight: .medium))
                        .foregroundColor(.black)
                    
                    Spacer()
                    
                    // Cart counting
                    CartQuantityView(cartQuantity: product.cartCount)
                }
                
                // Badge
                if let badgeText = product.productType.discountBadgeText {
                    DiscountBadgeView(badgeText: badgeText)
                }
            }
            
            // Product price
            PriceView(price: product.formattedPrice, specialPrice: product.formattedSpecialPrice)
                .frame(minWidth: 60, alignment: .trailing)
        }
        .padding(EdgeInsets(top: 10, leading: 0, bottom: 10, trailing: 0))
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
