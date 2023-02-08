//
//  ExpenseCategory.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 09/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

typealias ExpenseCategory = shared.Category

extension ExpenseCategory {
    func getFormattedName() -> String {
        self.name.replacingOccurrences(of: "_", with: " ").capitalized
    }
    
    static func getCategoryValues() -> [ExpenseCategory] {
        Category.values().toArray().compactMap { $0 as? shared.Category }
    }
}
