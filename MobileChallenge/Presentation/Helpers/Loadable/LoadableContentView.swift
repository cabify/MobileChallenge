//
//  LoadableContentView.swift
//  MobileChallenge
//
//  Created by thomas pereira on 02/02/2023.
//

import SwiftUI
import Combine

struct LoadableContentView<Source: LoadableObject, Content: View>: View {
    @ObservedObject var source: Source
    var content: (Source.Output) -> Content
    
    init(source: Source, @ViewBuilder content: @escaping (Source.Output) -> Content) {
        self.source = source
        self.content = content
    }
    
    var body: some View {
        switch source.state {
        case .idle:
            Color.clear.onAppear(perform: source.load)
            
        case .loading:
            ProgressView()
                .controlSize(.large)
                .tint(.purple)
            
        case .failed(let error): EmptyView() // TODO ErrorView(error: error, retryHandler: source.load)
        case .loaded(let output): content(output)
        }
    }
}
