import SwiftUI

@main
struct iOSApp: App {
    
    init() {
//        test()
        Koin.start()
    }
    
	var body: some Scene {
		WindowGroup {
            HomeView()
		}
	}
}
