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
    
    public static let baseURL = "http://localhost:8081/index.bundle?platform=ios"
    
    private let host = "127.0.0.1"
    private let port = 5001
    
    public func add(_ callBack: @escaping ((String?, String?) -> Void)) {
        let fileManager = FileManager.default
        let file = "Test.txt"
        let text = "The Sylo Protocol acts as the confidential networking layer for all integrated decentralised applications," +
                    "creating peer-to-peer (P2P) connections and providing an efficient way for users to interact and exchange" +
                    "data confidentially over the network. The Sylo Protocol consists of client-side APIs and services that allow" +
                    "Connected Applications to confidentially perform communication functions with other users on the network." +
                    "\n\nSylo Signalling will be a decentralised service run by resources on the Sylo Network. Sylo Signalling will" +
                    "provide the ability for peers to connect and is used to send messages and connectivity requests to enable reliable" +
                    "communication. Sylo Signalling Nodes will be remunerated in SYLOs in exchange for providing this service to the network."
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
            catch {
                print("Unexpected error: \(error).")
            }
            
            do {
                let path = fileURL.absoluteString
                let api = try? IpfsApi(host: host, port: port)
                try? api?.add(path, completionHandler: { nodes in
                    let multihash = nodes.first?.hash
                    let hashString = nodes.first?.hash?.string()
                    try? api?.cat(multihash!, completionHandler: { (data) in
                        let data = Data.withBytes(data)
                        content = String(data: data, encoding: .utf8)
                        callBack(hashString, content)
                    })
                })
            }
        }
    }
}
