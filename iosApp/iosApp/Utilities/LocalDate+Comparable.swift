//
//  LocalDate+Comparable.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 08/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

extension LocalDate: Comparable {
    public static func < (lhs: LocalDate, rhs: LocalDate) -> Bool {
        lhs.compareTo(other: rhs) != 0
    }
}
