//
//  HomeView.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct HomeView: View {
    @State private var showingSheet = false
    let expenseRepository: ExpenseRepository = Koin.instance.get()
    @State private var expenses: [Expense] = []
    private var categoryEmojis = ["🛒", "🧾", "🏠", "🚗", "🍽️", "🕹"]
    
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
            
            List(expenses) { item in
                HStack {
                    Text(categoryEmojis[Int(item.category.ordinal)])
                        .font(.largeTitle)
                        .padding()
                        .background {
                            Circle().foregroundColor(Color.teal)
                        }
                        
                    VStack(alignment: .leading, spacing: 5) {
                        Text("\(item.category.getFormattedName())")
                            .font(.callout)
                            .bold()
                        
                        Text("$ \(item.amount)")
                        
                        Text("\(item.expenseDate)")
                            .font(.caption)
                        
                        Text(item.note ?? "")
                            .padding(.horizontal)
                            .background {
                                Capsule().fill(Color.mint)
                            }
                    }
                }
            }.refreshable {
                getExpenses()
            }
        }.onAppear {
            getExpenses()
        }
    }
    
    private func getExpenses() {
        expenses = expenseRepository.getExpenseSync().sorted(by: { $0.expenseDate > $1.expenseDate })
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
