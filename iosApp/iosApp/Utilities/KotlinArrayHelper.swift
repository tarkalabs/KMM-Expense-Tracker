//
//  Utlilities.swift
//  iosApp
//
//  Created by Ibrahim Hassan on 08/02/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import shared

class KotlinArrayHelper<T> where T: AnyObject {
    static func convert(_ kotlinArray: KotlinArray<T>) -> [T] {
        let size = kotlinArray.size

        var swiftArray: [T] = []

        for i in 0 ..< size {
            if let item = kotlinArray.get(index: i) {
               swiftArray.append(item)
           }
        }

        return swiftArray
    }
}
