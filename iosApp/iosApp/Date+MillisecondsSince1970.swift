//
//  Date+MillisecondsSince1970.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 01/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation

extension Date {
    var millisecondsSince1970: Int64 {
        Int64((self.timeIntervalSince1970 * 1000.0).rounded())
    }
}
