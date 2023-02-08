//
//  Utlilities.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 08/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared
import Foundation

extension KotlinArray {
    @objc func toArray() -> [AnyObject] {
        let size = size
        var swiftArray: [AnyObject] = []

        for i in 0 ..< size {
            if let item = get(index: i) {
                swiftArray.append(item)
            }
        }

        return swiftArray
    }
}
