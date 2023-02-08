import SwiftUI
import shared

struct AddExpenseView: View {
    @Environment(\.dismiss) var dismiss
    let viewModel: AddExpenseViewModel = Koin.instance.get()
    
    @State private var amount = ""
    @State private var category = "Select Category"
    @State private var revealCategories = false
    @State private var expenseDate = Date()
    @State private var remarkNote = ""
    @State private var selectCategoryIndex: Int?
        
    @ViewBuilder
    var addExpense: some View {
        Text("Add Expense")
            .font(.title2.bold())
            .foregroundColor(.white)
            .padding()
        
        TextField("0", text: $amount)
            .font(.system(size: 35))
            .foregroundColor(Color.black)
            .multilineTextAlignment(.center)
            .keyboardType(.numberPad)
            .overlay(alignment: .center) {
                if !amount.isEmpty {
                    let offsetAmount = amount.count
                    
                    Text("$")
                        .font(.system(size: 35))
                        .foregroundColor(Color.black)
                        .offset(x: -CGFloat((offsetAmount)) * CGFloat(11.5) - 5)
                }
            }
            .padding(.vertical, 10)
            .frame(maxWidth: .infinity)
            .background {
                RoundedRectangle(cornerRadius: 20, style: .continuous)
                    .fill(.white)
            }
            .padding(.horizontal, 20)
    }
    
    @ViewBuilder
    var addCategory: some View {
        GroupBox {
            DisclosureGroup(category, isExpanded: $revealCategories) {
                let categoryValues = Category.values().toArray().map { "\($0)" }
                let categoryValuesForDisplay = categoryValues.map { $0.replacingOccurrences(of: "_", with: " ").capitalized }
                
                ForEach(categoryValuesForDisplay, id: \.self) { value in
                    Text(value)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .padding(5)
                        .font(.body)
                        .gesture(
                            TapGesture()
                                .onEnded { _ in
                                    revealCategories.toggle()
                                    
                                    selectCategoryIndex = categoryValuesForDisplay.firstIndex(where: { $0 == value })
                                    category = value
                                }
                        )
                }
            }
        }
        .font(.title2.bold())
        .padding(.horizontal, 20)
        .foregroundColor(.black)
    }
    
    @ViewBuilder
    var addNotes: some View {
        Label {
            TextField("Remark", text: $remarkNote)
                .padding(.leading, 10)
        } icon: {
            Image(systemName: "list.bullet.rectangle.portrait.fill")
                .font(.title3)
                .foregroundColor(Color.gray)
        }
        .padding(.vertical, 20)
        .padding(.horizontal, 15)
        .background {
            RoundedRectangle(cornerRadius: 12, style: .continuous)
                .fill(.white)
        }
        .padding(.horizontal, 20)
    }
    
    @ViewBuilder
    var addDate: some View {
        Label {
            DatePicker("", selection: $expenseDate, displayedComponents: .date)
                .datePickerStyle(.compact)
                .labelsHidden()
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.leading, 10)
        } icon: {
            Image(systemName: "calendar")
                .font(.title3)
                .foregroundColor(Color.gray)
        }
        .padding(.vertical, 20)
        .padding(.horizontal, 15)
        .background {
            RoundedRectangle(cornerRadius: 12, style: .continuous)
                .fill(.white)
        }
        .padding(.horizontal, 20)
    }
    
    @ViewBuilder
    var submitButton: some View {
        Button {
            let categoryValues = Category.values().toArray().map { "\($0)" }
            
            if let selectCategoryIndex {
                viewModel.addExpense(amount: Float(amount) ?? 0.0, category: Category.companion.getCategoryFromName(name: categoryValues[selectCategoryIndex]), expenseDate: DateUtilsKt.toDefaultTzLocalDate(expenseDate), note: remarkNote) { error in
                    if error == nil {
                        dismiss()
                    }
                }
            }
        } label: {
            Text("Submit")
                .font(.title2.bold())
                .foregroundColor(.white)
                .padding(.vertical, 20)
                .padding(.horizontal, 15)
                .frame(maxWidth: .infinity)
                .background(RoundedRectangle(cornerRadius: 20, style: .continuous))
        }
        .padding()
    }
    
    var body: some View {
        VStack(spacing: 20) {
            addExpense
            
            addCategory
            
            addNotes
            
            addDate
            
            Spacer()
            
            submitButton
            
            Spacer()
        }.frame(maxWidth: .infinity, maxHeight: .infinity)
            .overlay(alignment: .topTrailing) {
                Button {
                    dismiss()
                } label: {
                    Image(systemName: "xmark")
                        .font(.title2)
                        .foregroundColor(.white)
                        .opacity(0.7)
                }
                .padding()
            }
            .background(.linearGradient(colors: [
                Color.indigo, Color.teal, Color.red
        ], startPoint: .topLeading, endPoint: .bottomTrailing
       ))
    }
}

struct AddExpenseView_Previews: PreviewProvider {
    static var previews: some View {
        AddExpenseView()
    }
}
