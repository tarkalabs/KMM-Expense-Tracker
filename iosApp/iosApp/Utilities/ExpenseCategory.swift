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
        name.replacingOccurrences(of: "_", with: " ").capitalized
    }
    
    static func getCategoryValues() -> [ExpenseCategory] {
        KotlinArrayHelper.convert(ExpenseCategory.values())
    }
}
