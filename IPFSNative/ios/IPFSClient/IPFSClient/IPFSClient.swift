//
//  IPFSClient.swift
//  IPFSClient
//
//  Created by Harry on 15/09/18.
//  Copyright © 2018 com.nz.IPFS. All rights reserved.
//

import Foundation
import SwiftIpfsApi

public final class IPFSClient: NSObject {
    
    private let host = "127.0.0.1"
    private let port = 5001
    
    func add(_ callBack: @escaping ((String?, String?) -> Void)) {
        let fileManager = FileManager.default
        let file = "Test.txt"
        let text = "In a world of mindless browsing and intrusive advertising, Sylo puts you back in control of your own digital world. It’s a community of like minded people and businesses, who want full control over how they communicate and the information they share online." + "In a world of mindless browsing and intrusive advertising, Sylo puts you back in control of your own digital world. It’s a community of like minded people and businesses, who want full control over how they communicate and the information they share online."
        
        var hash: String?
        var content: String?
        
        if let dir = FileManager.default.urls(for: .documentDirectory, in: .userDomainMask).first {
            let fileURL = dir.appendingPathComponent(file)
            let exist = fileManager.fileExists(atPath: fileURL.absoluteString)
            
            if exist {
                try? fileManager.removeItem(at: fileURL)
            }
            
            do {
                try text.write(to: fileURL, atomically: false, encoding: .utf8)
            }
            catch {/* error handling here */}
            do {
                let path = fileURL.absoluteString
                let api = try? IpfsApi(host: host, port: port)
                try? api?.add(path, completionHandler: { nodes in
                    let multihash = nodes.first?.hash
                    hash = nodes.first?.hash?.string()
                    try? api?.cat(multihash!, completionHandler: { (data) in
                        let test = Data.withBytes(data)
                        content = String(data: test, encoding: .utf8)
                        callBack(hash, content)
                    })
                })
            }
        }
    }
}
