import SwiftUI
import shared

struct ContentView: View {
    let viewModel: AddExpenseViewModel = Koin.instance.get()
    var body: some View {
        Text("Hello World")
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
		ContentView()
    }
}
