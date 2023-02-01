//
//  HomeView.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct HomeView: View {
    @State private var showingSheet = false
    
    var body: some View {
        VStack(alignment: .center, spacing: 16) {
            HStack {
                Spacer().frame(width: 8)
                
                VStack(alignment: .leading) {
                    Text("Expense Tracker")
                        .font(.title2.bold())
                        .frame(maxWidth: .infinity, alignment: .leading)
                    
                    Text("Track your expenses")
                        .font(.caption)
                        .fontWeight(.semibold)
                        .foregroundColor(.gray)
                }
                
                Spacer()
                
                Button {
                    showingSheet.toggle()
                } label: {
                    Image(systemName: "plus.circle.fill")
                        .resizable()
                        .frame(width: 32, height: 32)
                        .foregroundColor(Color.teal)
                }
                .fullScreenCover(isPresented: $showingSheet) {
                    AddExpenseView()
                }
                
                Spacer().frame(width: 16)
            }
            
            ExpenseCardView()
                .offset(x: 8, y: 0)
        }.background {
            Text("Show the expenses list here")
                .font(.title3.bold())
                .frame(maxHeight: .infinity)
                .foregroundColor(.black)
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
