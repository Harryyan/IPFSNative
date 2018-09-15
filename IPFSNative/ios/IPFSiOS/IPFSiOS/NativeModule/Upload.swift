//
//  Upload.swift
//  IPFSiOS
//
//  Created by Harry on 15/09/18.
//  Copyright © 2018 com.nz.IPFS. All rights reserved.
//

import Foundation
import IPFSClient

@objc(Upload)
public class Upload: NSObject {
    
    static let hashKey = "hashKey"
    static let contentKey = "contentKey"
    
    @objc func uploadFile(callback: @escaping RCTResponseSenderBlock) {
        DispatchQueue.main.async {
            let client = IPFSClient()
            client.add() { (hash, content) in
                let callbackInfo = [Upload.hashKey: hash, Upload.contentKey: content]
                callback([callbackInfo])
            }
        }
    }
}
