//
//  ExpenseCardView.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct ExpenseCardView: View {
    var body: some View {
        ZStack {
            GeometryReader { geometery in
                RoundedRectangle(cornerRadius: 20, style: .circular)
                    .fill(
                        .linearGradient(colors: [
                            Color.indigo, Color.teal, Color.red
                    ], startPoint: .topLeading, endPoint: .bottomTrailing
                   )
                )
                .frame(width: geometery.size.width - 16, height: 200)
            }
        }
    }
}
