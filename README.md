# IPFSNative [![Swift](https://img.shields.io/badge/swift-4.1-orange.svg?style=flat)](#) [![Kotlin](https://img.shields.io/badge/kotlin-1.2.41-orange.svg?style=flat)](#) [![Platform](https://img.shields.io/badge/platform-iOS-lightgrey.svg?style=flat)](#) [![Platform](https://img.shields.io/badge/platform-android-lightgrey.svg?style=flat)](#) [![License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat)](https://opensource.org/licenses/MIT)

<img src="Resources/logo.png" width=300 alt="Render" align=right />

This project implements one single file upload and display using the new hypermedia distribution protocol called [the InterPlanetary File System](https://github.com/ipfs/ipfs).

*IPFSNative* is a **ReactNative** project, and binding following two native libraries:  

* **ipfs-api-kotlin:** Provide API for kotlin to access a IPFS node via http.
* **swift-ipfs-api:** The Swift IPFS API shell/client is an asynchronous library that provides native calls to an IPFS node.

Tips from [Harry](https://harryyan.iceloof.com/):

>  The github address of installing Swift IPFS dependency is wrong in **swift-ipfs-api** repo. The correct line you need add to your `Cartfile` is `github "NeoTeo/swift-ipfs-api"` and then run this command in your terminal: `carthage update --platform iOS.`
> 
>
>  Similar issue happen to Android as well. When you add **ipfs-api-kotlin** dependency to gradle, please use following line: `implementation 'com.github.ligi:ipfs-api-kotlin:0.12'`, otherwise you can't install it. Don't forget add kotlin support in your module's gradle: `apply plugin: 'kotlin-android'`

### How to run this project

I don't checkin the **node_modules** folder to this repo, you need run `npm install` after you checkout this project.

Next, run `npm start` to start a package, and then run `ipfs daemon` to start IPFS Api Server.

Finally open Xcode or Android Studio IDE and click **Run** button to launch the app.

>  You can also run `react-native run-ios` or `react-native run-android` to lauch the app after you start IPFS Api Server.


### ScreenShots

#### iOS

<div style="text-align:center" markdown="1">
  <p align="left">
	<img src="Resources/IPFSiOS_before.png" width=300 alt="Render" align=center />
  </p>
  <p align="right">
	<img src="Resources/IPFSiOS_after.png" width=300 alt="Render" align=center />
  </p>
</div>

<br />

#### Android

<img src="Resources/IPFSAndroid_before.png" width=300 alt="Render" align=center />
<img src="Resources/IPFSAndroid_after.png" width=300 alt="Render" align=center />

### How to implement it

The main idea is to create independent framework(*iOS*) and modules(*Android*) on native side, which is easy to maintain, migrate and extend.

Following is the iOS project hierarchy:
<p align="center">
	<img src="Resources/project_iOS.png" width=300 alt="Render" align=center />
</p>

I created an **IPFSClient** framework, and add it to the main target called **IPFSiOS**. The main target doesn't need to know how to upload and fetch data from IPFS Api Server, it just cares about the response, and pass it to React Native page(*NativeModule* does it). Using this way, we can easily separate bussiness logic and UI logic, and easy to add more IPFS commands support without affecting other targets, also quite easy when migrating.

Android project has one more module called **nativemodules**(*communicate with IPFSClient*). Because the communication setup between android and react native is more complicated than iOS, which needs more classes such as **UploadFileModule**, **UploadReactPackage**, and **MainApplication**. In this way, we can not only separate **IPFSClient** module, but also separate native service components, which means we can maintain this module as a SDK, and apply it to different projects.

<p align="center">
	<img src="Resources/project_android.png" width=300 alt="Render" align=center />
</p>


# Issues I found:
The Swift and Kotlin IPFS Api Client Library is quite incompleted. For example, the command: `ipfs files ls`, which can list all the files user uploaded to the IPFS Server, but both Swift and Kotlin api don't support this command. This obstruct features implementation on both sides.

Also there are big differences between Swfit and Kotlin IPFS Api Library. For instance, Swift library provides `ls` functionality, which can show all files for specific component such as :

`ipfs file ls /ipfs/QmQLXHs7K98JNQdWrBB2cQLJahPhmupbDjRuH1b9ibmwVa`

However, there is no such functionality in Android Library.

So finally, I just implemented the common function supported by both channel, that is, `add` and `cat`.