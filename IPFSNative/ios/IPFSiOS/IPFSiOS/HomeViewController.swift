//
//  ViewController.swift
//  IPFSiOS
//
//  Created by Harry on 15/09/18.
//  Copyright © 2018 com.nz.IPFS. All rights reserved.
//

import UIKit
import React
import IPFSClient

class HomeViewController: UIViewController {
    
    private let moduleName = "Home"
    
    // MARK: - Life cycle
    
    override func loadView() {
        let jsCodeLocation = URL(string: IPFSClient.baseURL)
        
        if let rootView = RCTRootView(bundleURL: jsCodeLocation, moduleName: moduleName, initialProperties: nil, launchOptions: nil) {
            view = rootView
        } else {
            view = UIView()
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        title = "IPFSiOS"
        
        setUpNavigationBar()
    }
    
    // MARK: - Set up navigation bar
    
    private func setUpNavigationBar() {
        navigationController?.navigationBar.titleTextAttributes = [NSAttributedStringKey(rawValue: NSAttributedStringKey.foregroundColor.rawValue): UIColor.white]
        navigationController?.navigationBar.barTintColor = UIColor(red: 46/255, green: 146/255, blue: 152/255, alpha: 1)
    }
}

