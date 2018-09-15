//
//  Data_Bytes.swift
//  IPFSClient
//
//  Created by Harry on 15/09/18.
//  Copyright © 2018 com.nz.IPFS. All rights reserved.
//

import Foundation

extension Data {
    func bytes() -> [UInt8] {
        let count = self.count / MemoryLayout<UInt8>.size
        var bytesArray = [UInt8](repeating: 0, count: count)
        (self as NSData).getBytes(&bytesArray, length:count * MemoryLayout<UInt8>.size)
        return bytesArray
    }
    
    static public func withBytes(_ bytes: [UInt8]) -> Data {
        return Data(bytes: UnsafePointer<UInt8>(bytes), count: bytes.count)
    }
}
