//
//  SheetModifier.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI

struct SheetModifier<Item: Identifiable, Destination: View>: ViewModifier {
    
    private let item: Binding<Item?>
    private let destination: (Item) -> Destination
    
    init(item: Binding<Item?>, @ViewBuilder content: @escaping (Item) -> Destination) {
        self.item = item
        self.destination = content
    }
    
    func body(content: Content) -> some View {
        content.sheet(item: item, content: destination)
    }
}
