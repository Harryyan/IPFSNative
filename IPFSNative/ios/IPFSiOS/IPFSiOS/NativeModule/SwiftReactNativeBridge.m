//
//  SwiftReactNativeBridge.m
//  IPFSiOS
//
//  Created by Harry on 15/09/18.
//  Copyright © 2018 com.nz.IPFS. All rights reserved.
//

#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(Upload, NSObject)

RCT_EXTERN_METHOD(uploadFileWithCallBack:(RCTResponseSenderBlock *)callback)

@end
