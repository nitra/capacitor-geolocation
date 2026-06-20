# 1.0.0 (2026-06-20)


### Bug Fixes

* add missing callbacks when calling clearWatch ([be9b246](https://github.com/nitra/capacitor-geolocation/commit/be9b246c2d956ae40baf2c782637b4d062ecfe22))
* Add play services location version for capacitor ([15170c2](https://github.com/nitra/capacitor-geolocation/commit/15170c2f5155f51f2ba364d67ccf6b015f3d2a5f))
* add returnType annotation to watchPosition function ([de2b2a5](https://github.com/nitra/capacitor-geolocation/commit/de2b2a560a3c402eb8dea1c5b8385fdf0933abb1))
* add success and error callbacks to clearWatch in outsystems-wrapper ([057ee1f](https://github.com/nitra/capacitor-geolocation/commit/057ee1fc4de58436fccfc16ac3fd6bf984ff26dc))
* add watchId to options before calling watchPosition ([2fefd65](https://github.com/nitra/capacitor-geolocation/commit/2fefd65748b42f6bdac71f7dba6c4be94fc4af99))
* **android:** AGP 9.0 no longer supporting `proguard-android.txt` ([#74](https://github.com/nitra/capacitor-geolocation/issues/74)) ([32961e1](https://github.com/nitra/capacitor-geolocation/commit/32961e1eb53106ba9004a9a1d0abb4b500a90dc8))
* **android:** Only request permissions that are defined in the manifest ([#85](https://github.com/nitra/capacitor-geolocation/issues/85)) ([c9c4c84](https://github.com/nitra/capacitor-geolocation/commit/c9c4c8446efb1dacd71eaab2e9d52844ee5df020))
* **android:** Properly parsing number parameters ([#24](https://github.com/nitra/capacitor-geolocation/issues/24)) ([cb605d8](https://github.com/nitra/capacitor-geolocation/commit/cb605d824c655fbba108c085f194e5e22464fc51))
* **android:** Properly parsing number parameters ([#24](https://github.com/nitra/capacitor-geolocation/issues/24)) ([75ffbd2](https://github.com/nitra/capacitor-geolocation/commit/75ffbd2c141eacf9a523f8e3d55782e2aff2d1c7))
* **android:** Return error when user rejects request to turn on location with `enableLocationManagerFallback=true` ([#86](https://github.com/nitra/capacitor-geolocation/issues/86)) ([1a2504a](https://github.com/nitra/capacitor-geolocation/commit/1a2504a2b50f1793089bdbf029b010980d6dbcfa))
* **android:** update Gradle and Kotlin configurations for compatibility with latest @capacitor/android ([#28](https://github.com/nitra/capacitor-geolocation/issues/28)) ([060d525](https://github.com/nitra/capacitor-geolocation/commit/060d525cd9b0492bcc921c64f09ea2a1b1de8173))
* **android:** use 'propName = value' assignment syntax in build.gradle files ([08f311a](https://github.com/nitra/capacitor-geolocation/commit/08f311a1f785f04eab8eec87a60cd5147488388b))
* Capacitor plugin name on sample app ([4b18fc9](https://github.com/nitra/capacitor-geolocation/commit/4b18fc98ef503068616454a1460a15e42ff98ab7))
* Capacitor sample app name ([6d47cd0](https://github.com/nitra/capacitor-geolocation/commit/6d47cd09d5dc582490bd5e37d5f1515358b59a02))
* Capacitor sample app name and package ([e45a172](https://github.com/nitra/capacitor-geolocation/commit/e45a1722cbb2cc1179e401d525cdc46cf71a5ce3))
* Continue with @capacitor/geolocation ([164aa60](https://github.com/nitra/capacitor-geolocation/commit/164aa609ec41716b51c20351718220cf9b93104f))
* convert position result for watchPosition like we do for getCurrentPosition ([a1490e8](https://github.com/nitra/capacitor-geolocation/commit/a1490e8432ad483e26a6566589f9bd93438aa260))
* convert position result for watchPosition like we do for getCurrentPosition ([ad8d264](https://github.com/nitra/capacitor-geolocation/commit/ad8d264990bde72e1166a3af40aa0f04ed9a16cb))
* coroutine scope lifecycle on cordova plugin ([719f87c](https://github.com/nitra/capacitor-geolocation/commit/719f87cbfd2049ce83298a728147a16508cf4313))
* correct iOS build scheme name in verify script ([48d41aa](https://github.com/nitra/capacitor-geolocation/commit/48d41aa3bb494b738ab280ff8a31347b3ffdbf9c))
* correctly pass options to clearWatch ([54a138c](https://github.com/nitra/capacitor-geolocation/commit/54a138c7bed3701eebb2e591d9b1dc45cdc055e7))
* correctly pass watchId to clearWatch ([741d72a](https://github.com/nitra/capacitor-geolocation/commit/741d72a05f47342e55dc50473a302d6aad8d6cfc))
* correctly pass watchId to clearWatch ([6ee6dc8](https://github.com/nitra/capacitor-geolocation/commit/6ee6dc88167a197cd1b4b70b49865e360a0cb034))
* directory for os-lib ([80d2eb5](https://github.com/nitra/capacitor-geolocation/commit/80d2eb5dc8a8c133b605690d55c7f0134e4b1f61))
* **docs:** improve formatting and clarity in README.md ([c08c8e3](https://github.com/nitra/capacitor-geolocation/commit/c08c8e3a33186badb7552686bb721049b67d19ad))
* fix directories ([68da3e5](https://github.com/nitra/capacitor-geolocation/commit/68da3e593307797b22a181d15c12e74946758ff3))
* fix prefix format for error codes ([f947fa2](https://github.com/nitra/capacitor-geolocation/commit/f947fa227b30cabc87c2e75437a003c117594ef3))
* fix type of success callback ([ff2a4c6](https://github.com/nitra/capacitor-geolocation/commit/ff2a4c6aec9c7bf4bfe610a2bcfaae11b61bfcc9))
* fixing function name for `watchPosition` and `id field for clearWatch ([351b570](https://github.com/nitra/capacitor-geolocation/commit/351b570926102b51dcb82a5c9214940834c2b6e4))
* fixing iOS file name ([b3b4026](https://github.com/nitra/capacitor-geolocation/commit/b3b40268135eaae90ed44cf75897a4fb4d71023c))
* fixing iOS library name in plugin.xml ([eaaee59](https://github.com/nitra/capacitor-geolocation/commit/eaaee591da946dec58ae76cffdbff3cbb9ab1d94))
* fixing library name in Azure pipeline ([7bef749](https://github.com/nitra/capacitor-geolocation/commit/7bef749c1806248a63205d3fc3e764d5170eb477))
* fixing typo in package ([a450280](https://github.com/nitra/capacitor-geolocation/commit/a450280f6dd89c1426c766bbeec5f92d88959cf2))
* include `watchId` in the parameters for `watchPosition in the cordova-plugin ([b39abe4](https://github.com/nitra/capacitor-geolocation/commit/b39abe40881e727e73eb90e406ceb1426d17226b))
* include `watchId` in the parameters for `watchPosition` in the cordova-plugin ([17f308a](https://github.com/nitra/capacitor-geolocation/commit/17f308a0d68b05038aa549ce1719c6e78873aff6))
* **ios:** added timeout implementation for both getCurrentPosition and watchPosition ([#55](https://github.com/nitra/capacitor-geolocation/issues/55)) ([4c22ac3](https://github.com/nitra/capacitor-geolocation/commit/4c22ac3c6facd4b628a38a668d66a7ea2f3ad44c))
* **ios:** Allow publisher re-subscribe on location error ([8705ea8](https://github.com/nitra/capacitor-geolocation/commit/8705ea89bce06b3f145ee8bb51cda9678f934543))
* **ios:** Allow publisher re-subscribe on location error ([960bfc4](https://github.com/nitra/capacitor-geolocation/commit/960bfc4efa2eb78cc903c2c108204543ca409d95))
* **ios:** fixes an issue where the plugin stops receiving location updates after calling the clearWatch method ([#38](https://github.com/nitra/capacitor-geolocation/issues/38)) ([d6f5266](https://github.com/nitra/capacitor-geolocation/commit/d6f5266fb8b8f382df9ca5d85ba8e9471643e063))
* **ios:** handle location watch callbacks recovery after backgrounding ([978bd50](https://github.com/nitra/capacitor-geolocation/commit/978bd505e532904cdcfb5b0486c8d55e20252bbe))
* **ios:** resolving requestPermissions ([#16](https://github.com/nitra/capacitor-geolocation/issues/16)) ([2586a9e](https://github.com/nitra/capacitor-geolocation/commit/2586a9e327e85c35b28932fbb829329efa32dc55))
* **ios:** Simplify SPM usage for native library ([#52](https://github.com/nitra/capacitor-geolocation/issues/52)) ([ad53dc6](https://github.com/nitra/capacitor-geolocation/commit/ad53dc654369db9c1c2ae57cefca798fc5283dc8))
* **ios:** watchPosition after an error occurs ([f851818](https://github.com/nitra/capacitor-geolocation/commit/f8518183186d812ea2d2cc89983332fa6d36e8d6))
* **ios:** watchPosition after an error occurs ([749ca11](https://github.com/nitra/capacitor-geolocation/commit/749ca11f20ee434b895f97d00462442f4bdbf370))
* local variable declarations ([f4b585f](https://github.com/nitra/capacitor-geolocation/commit/f4b585fc075d470e14a1b9e77080a783a19a4f64))
* make SPM use version 7 instead of main ([#18](https://github.com/nitra/capacitor-geolocation/issues/18)) ([9b4a7c6](https://github.com/nitra/capacitor-geolocation/commit/9b4a7c64bfeb576f624228db1e4d3e733de01479))
* make SPM use version 7 instead of main ([#18](https://github.com/nitra/capacitor-geolocation/issues/18)) ([d46adec](https://github.com/nitra/capacitor-geolocation/commit/d46adec2f292eb9bce52254160c3417e88355f35))
* match package.json description to match old plugin ([c942731](https://github.com/nitra/capacitor-geolocation/commit/c9427314f718b9b4f1743b45faaae02ee3856f88))
* Missing permissions in android app ([cf5ff7c](https://github.com/nitra/capacitor-geolocation/commit/cf5ff7c79f33ff8f8872f0fdf5f1ddda997a46f6))
* Missing speed prop in sample app ([b30564f](https://github.com/nitra/capacitor-geolocation/commit/b30564fe381330f79a6d2b938128678e2d7ecccb))
* peerDependency for pnpm compatibility ([a94839d](https://github.com/nitra/capacitor-geolocation/commit/a94839d1c51d7db2bdc39d123121920f8d0b883b))
* Podspec definition for ios plugin ([0e1706c](https://github.com/nitra/capacitor-geolocation/commit/0e1706c8f6562f9873d1f9a5ed79641b879a1aa0))
* properly get parameters for getCurrentPosition ([4cdef94](https://github.com/nitra/capacitor-geolocation/commit/4cdef94f9457d974236906b9cefec09eebd880b0))
* Re-add License file ([#23](https://github.com/nitra/capacitor-geolocation/issues/23)) ([3dd0acf](https://github.com/nitra/capacitor-geolocation/commit/3dd0acf80dd96e53b9bd1c15690e3d74fc1dbaaa))
* Re-add License file ([#23](https://github.com/nitra/capacitor-geolocation/issues/23)) ([fc287aa](https://github.com/nitra/capacitor-geolocation/commit/fc287aad7dded9b3b5fbeef651ba6bdda9d4f1e7))
* remove files that do not exist (yet) ([4fda383](https://github.com/nitra/capacitor-geolocation/commit/4fda38354a29e0c0b4d1dd7918ab4fe139fcd09e))
* restore timeout handling and options forwarding from upstream ([04b1ba2](https://github.com/nitra/capacitor-geolocation/commit/04b1ba2105ae9a6b9471fcfc86678c64d78055a4))
* set keepCallback to true when returning position for `watchPosition ([423ccd7](https://github.com/nitra/capacitor-geolocation/commit/423ccd7389cee383962f6041aedcb777ccb5498c))
* set peerDependency on @capacitor/core to `>=7.0.0` to match current version of plugin ([ab7ea49](https://github.com/nitra/capacitor-geolocation/commit/ab7ea49972506c46ba3385b4893ca362f1c8f44f))
* typos and remove unnecessary callback ([2fcd073](https://github.com/nitra/capacitor-geolocation/commit/2fcd073004e8d80f647b792fd9db9d40525780b4))
* Unit tests un-mocking ([3e24815](https://github.com/nitra/capacitor-geolocation/commit/3e24815b4b38ea7a4bfeb6862f10048c18eebb27))
* Update android and ios OS version targets ([6580cde](https://github.com/nitra/capacitor-geolocation/commit/6580cde165afd9f00c8c68f99095044af309c304))
* update IONGeolocationLib dependency and improve location handling in GeolocationPlugin ([d3d29b4](https://github.com/nitra/capacitor-geolocation/commit/d3d29b440aa272969cb2624c701a5d90dc5e111b))
* update IONGeolocationLib dependency version constraint to allow newer versions ([ab34684](https://github.com/nitra/capacitor-geolocation/commit/ab34684180f25d7e427fe29cde6dfe8ac0338d63))
* update location service methods to include options for improved functionality ([488b57d](https://github.com/nitra/capacitor-geolocation/commit/488b57df0c7b8b30e0cfcd21fc8a940922940b52))
* update repository URL in package.json and add id-token permission in release workflow ([c2d497e](https://github.com/nitra/capacitor-geolocation/commit/c2d497ec9a74487ce006914170b27a5e1d85751e))
* update test gradle dependencies ([551660d](https://github.com/nitra/capacitor-geolocation/commit/551660df2cdfe8e4b411cf5a4510be53cf33c78a))
* Urls on capacitor plugin's `package.json` ([5e95952](https://github.com/nitra/capacitor-geolocation/commit/5e959521b810d773831ef166416fd4f60eafa3d6))
* use `position.altitudeAccuracy ([e35bc43](https://github.com/nitra/capacitor-geolocation/commit/e35bc43fb49b46aec36da4d9d6640eb8d8860f31))
* use correct const variable ([324cf12](https://github.com/nitra/capacitor-geolocation/commit/324cf1283795e380540054b62383bbece83e38d0))
* use plugin.xml as standard file, not link ([c02588c](https://github.com/nitra/capacitor-geolocation/commit/c02588c4b0478f7c074c3642351e123b876360db))


### Features

* add altitudeAccuracy to result ([c2a5749](https://github.com/nitra/capacitor-geolocation/commit/c2a574977bac7bd14cccb0c40556ccac9f8de42a))
* add altitudeAccuracy to result ([0a44a66](https://github.com/nitra/capacitor-geolocation/commit/0a44a668ef747926db00a7e4e4cd54dd7e7aea9c))
* add barebones implementation of Android Capacitor Bridge ([e7146d3](https://github.com/nitra/capacitor-geolocation/commit/e7146d3fcf0f49a83f7cd17f0f818df5051a8beb))
* add barebones implementation of Android Cordova Bridge ([a7c7a7d](https://github.com/nitra/capacitor-geolocation/commit/a7c7a7d58677d6f3de82f7c6665120bbf0077623))
* add cordova and capacitor bridge methods ([43487c5](https://github.com/nitra/capacitor-geolocation/commit/43487c5ad13b6a590127dfb68e7425f1d2ef122f))
* add implementation for Capacitor bridge ([6a8f6f4](https://github.com/nitra/capacitor-geolocation/commit/6a8f6f47294f352c2763408da81aaf41f4ebedb1))
* add outsystems javascript wrapper for both capacitor and cordova plugins ([fc11136](https://github.com/nitra/capacitor-geolocation/commit/fc11136267e9c54d02d9e181decacf284039ff3b))
* add permission request to Cordova bridge ([9c5a05b](https://github.com/nitra/capacitor-geolocation/commit/9c5a05b9ba41d1dbedae601e3e8ac99de087694b))
* add support for heading ([#78](https://github.com/nitra/capacitor-geolocation/issues/78)) ([ed20f3e](https://github.com/nitra/capacitor-geolocation/commit/ed20f3e3b77c9178b5ba2372a9d98c1759dae65d))
* add wrapper as a package ([2a99be8](https://github.com/nitra/capacitor-geolocation/commit/2a99be8b8988c6202c893618c682af8803551e9b))
* addWatch+clearWatch for Android cordova bridge ([d6fe8bb](https://github.com/nitra/capacitor-geolocation/commit/d6fe8bb4e7490eae60662507cfeb03ecaedb03ec))
* **android:** Fallback option for no network or Play Services ([#53](https://github.com/nitra/capacitor-geolocation/issues/53)) ([09277b7](https://github.com/nitra/capacitor-geolocation/commit/09277b7cf458b1625db444f4eb0a40bd7c7b3265))
* **android:** New parameter `interval` in `watchPosition` ([#62](https://github.com/nitra/capacitor-geolocation/issues/62)) ([7fda0cf](https://github.com/nitra/capacitor-geolocation/commit/7fda0cf3f9de7254d67405689164c854ea09c84e))
* cap sample app watches and clear watch ([5934095](https://github.com/nitra/capacitor-geolocation/commit/593409589dd77d51a7756ca75b8a1c71aa8b8ba4))
* Capacitor 8 support ([6ead26a](https://github.com/nitra/capacitor-geolocation/commit/6ead26a06dbfa727551214a0a58469d5195d8657))
* **capacitor:** add pwa code ([8340740](https://github.com/nitra/capacitor-geolocation/commit/8340740441d0bc85de16120bbfa3f975f58adcb6))
* **capacitor:** add pwa getCurrentPosition ([02e5c0a](https://github.com/nitra/capacitor-geolocation/commit/02e5c0ad16f363ff2f81402a818be01a8d3f2adb))
* **ci:** add Maven Central publishing workflow and Android publishing scripts ([#72](https://github.com/nitra/capacitor-geolocation/issues/72)) ([281d64f](https://github.com/nitra/capacitor-geolocation/commit/281d64fd038fbdfea15aa7a484ee0fb33370e35b))
* **ci:** add publish-pod job to invoke CocoaPods publish workflow ([#70](https://github.com/nitra/capacitor-geolocation/issues/70)) ([e2387f4](https://github.com/nitra/capacitor-geolocation/commit/e2387f43c060ee5f1e5e7210fe9c3ef13237f2a4))
* **ci:** add publish-pod job to invoke CocoaPods publish workflow ([#71](https://github.com/nitra/capacitor-geolocation/issues/71)) ([f9a4eca](https://github.com/nitra/capacitor-geolocation/commit/f9a4ecae97b944f739736ecc04f90b4c372232a5))
* configura Kotlin version for Capacitor Plugin ([48b0cc2](https://github.com/nitra/capacitor-geolocation/commit/48b0cc2317f3f1f13d7742adaa366340eccc82b3))
* convert JSON result for format needed for Capacitor bridge ([8a36084](https://github.com/nitra/capacitor-geolocation/commit/8a36084b2872214f9d9d4ee23170e6c058ccf670))
* enhance location string representation with isMock and provider details ([4ae9d54](https://github.com/nitra/capacitor-geolocation/commit/4ae9d54f7322cf19d68f754b90318d87ebce166f))
* finish getLocation implementation on library ([6637f74](https://github.com/nitra/capacitor-geolocation/commit/6637f74eceab46b88c9dcaad52598bfb26f34f8b))
* first implementation of `getLocation ([abb24a7](https://github.com/nitra/capacitor-geolocation/commit/abb24a7c2928721f4c6cd07744e167aabdb48aab))
* get watchId from args ([3b72a49](https://github.com/nitra/capacitor-geolocation/commit/3b72a4993200e1711d40f431375e8d40730a1244))
* implement addWatch and clearWatch on Capacitor bridge ([a5a2ebf](https://github.com/nitra/capacitor-geolocation/commit/a5a2ebf77870d5ed5f1f7b60f30ddd5ea764aa1d))
* Implement addWatch+clearWatch for native Android ([1d4d426](https://github.com/nitra/capacitor-geolocation/commit/1d4d426f9c40deb574ba6fe1df5a4893ec198a92))
* Initial location example app code ([3363e74](https://github.com/nitra/capacitor-geolocation/commit/3363e7465cfb5900e47d2860061cea483a6ce67a))
* initial version for sample app watches ([a829efb](https://github.com/nitra/capacitor-geolocation/commit/a829efb9e2314a1a8e72fc28e54c529ff0b2b0af))
* properly handle error scenarios in Capacitor bridge and remove unused sealed class ([fe28149](https://github.com/nitra/capacitor-geolocation/commit/fe28149f0e9d00070eb3e9390a5a418e3c547bbe))
* properly handle error scenarios in Cordova bridge ([1da1205](https://github.com/nitra/capacitor-geolocation/commit/1da12059b1f9d110998ea5b48261c2fad6b9d500))
* request permissions before getting location ([fe32351](https://github.com/nitra/capacitor-geolocation/commit/fe323519d346738514d4490ee3f95604eb63144e))
* update dependency to OSGeolocationLib-Android in Cordova and Capacitor plugin, and remove local Android lib ([bb9b86d](https://github.com/nitra/capacitor-geolocation/commit/bb9b86d3c3f5e03d19c46846fc77beb17be74b0d))
* Update plugin name ([9948a41](https://github.com/nitra/capacitor-geolocation/commit/9948a41827faba2d4eefe2178f07443d3a427f2e))


### BREAKING CHANGES

* **android:** The `timeout` property now gets applied to all requests on Android on iOS, as opposed to just web and `getCurrentPosition` on Android. This aligns with what is documented in the plugin. If you are experiencing timeouts when requesting location in your app, consider using a higher `timeout` value. For `watchPosition` on Android, you may use the `interval` parameter introduced in version 8.0.0.
* Capacitor major version update requires major version update on the plugin.

# [8.2.0](https://github.com/ionic-team/capacitor-geolocation/compare/v8.1.0...v8.2.0) (2026-03-31)


### Features

* add support for heading ([#78](https://github.com/ionic-team/capacitor-geolocation/issues/78)) ([ed20f3e](https://github.com/ionic-team/capacitor-geolocation/commit/ed20f3e3b77c9178b5ba2372a9d98c1759dae65d))

# [8.1.0](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0...v8.1.0) (2026-02-11)


### Bug Fixes

* **android:** AGP 9.0 no longer supporting `proguard-android.txt` ([#74](https://github.com/ionic-team/capacitor-geolocation/issues/74)) ([32961e1](https://github.com/ionic-team/capacitor-geolocation/commit/32961e1eb53106ba9004a9a1d0abb4b500a90dc8))


### Features

* **ci:** add Maven Central publishing workflow and Android publishing scripts ([#72](https://github.com/ionic-team/capacitor-geolocation/issues/72)) ([281d64f](https://github.com/ionic-team/capacitor-geolocation/commit/281d64fd038fbdfea15aa7a484ee0fb33370e35b))
* **ci:** add publish-pod job to invoke CocoaPods publish workflow ([#70](https://github.com/ionic-team/capacitor-geolocation/issues/70)) ([e2387f4](https://github.com/ionic-team/capacitor-geolocation/commit/e2387f43c060ee5f1e5e7210fe9c3ef13237f2a4))
* **ci:** add publish-pod job to invoke CocoaPods publish workflow ([#71](https://github.com/ionic-team/capacitor-geolocation/issues/71)) ([f9a4eca](https://github.com/ionic-team/capacitor-geolocation/commit/f9a4ecae97b944f739736ecc04f90b4c372232a5))

# [8.0.0](https://github.com/ionic-team/capacitor-geolocation/compare/v7.1.6...v8.0.0) (2025-12-08)


### Bug Fixes

* **android:** use 'propName = value' assignment syntax in build.gradle files ([08f311a](https://github.com/ionic-team/capacitor-geolocation/commit/08f311a1f785f04eab8eec87a60cd5147488388b))
* **ios:** added timeout implementation for both getCurrentPosition and watchPosition ([#55](https://github.com/ionic-team/capacitor-geolocation/issues/55)) ([4c22ac3](https://github.com/ionic-team/capacitor-geolocation/commit/4c22ac3c6facd4b628a38a668d66a7ea2f3ad44c))
* peerDependency for pnpm compatibility ([a94839d](https://github.com/ionic-team/capacitor-geolocation/commit/a94839d1c51d7db2bdc39d123121920f8d0b883b))


### Features

* **android:** Fallback option for no network or Play Services ([#53](https://github.com/ionic-team/capacitor-geolocation/issues/53)) ([09277b7](https://github.com/ionic-team/capacitor-geolocation/commit/09277b7cf458b1625db444f4eb0a40bd7c7b3265))
* **android:** New parameter `interval` in `watchPosition` ([#62](https://github.com/ionic-team/capacitor-geolocation/issues/62)) ([7fda0cf](https://github.com/ionic-team/capacitor-geolocation/commit/7fda0cf3f9de7254d67405689164c854ea09c84e))
* Capacitor 8 support ([6ead26a](https://github.com/ionic-team/capacitor-geolocation/commit/6ead26a06dbfa727551214a0a58469d5195d8657))


### BREAKING CHANGES

* The `timeout` property now gets applied to all requests on Android on iOS, as opposed to just web and `getCurrentPosition` on Android. This aligns with what is documented in the plugin. If you are experiencing timeouts when requesting location in your app, consider using a higher `timeout` value. For `watchPosition` on Android, you may use the `interval` parameter introduced in version 8.0.0.
* Capacitor major version update requires major version update on the plugin.

# [8.0.0-next.6](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.5...v8.0.0-next.6) (2025-11-26)


### Features

* **android:** New parameter `interval` in `watchPosition` ([#62](https://github.com/ionic-team/capacitor-geolocation/issues/62)) ([7fda0cf](https://github.com/ionic-team/capacitor-geolocation/commit/7fda0cf3f9de7254d67405689164c854ea09c84e))


### BREAKING CHANGES

* **android:** The `timeout` property now gets applied to all requests on Android on iOS, as opposed to just web and `getCurrentPosition` on Android. This aligns with what is documented in the plugin. If you are experiencing timeouts when requesting location in your app, consider using a higher `timeout` value. For `watchPosition` on Android, you may use the `interval` parameter introduced in version 8.0.0.

## [7.1.6](https://github.com/ionic-team/capacitor-geolocation/compare/v7.1.5...v7.1.6) (2025-11-25)


### Bug Fixes

* **ios:** Simplify SPM usage for native library ([#52](https://github.com/ionic-team/capacitor-geolocation/issues/52)) ([ad53dc6](https://github.com/ionic-team/capacitor-geolocation/commit/ad53dc654369db9c1c2ae57cefca798fc5283dc8))

# [8.0.0-next.7](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.6...v8.0.0-next.7) (2025-11-21)


### Features

* **android:** New parameter `interval` in `watchPosition` ([#62](https://github.com/ionic-team/capacitor-geolocation/issues/62)) ([7fda0cf](https://github.com/ionic-team/capacitor-geolocation/commit/7fda0cf3f9de7254d67405689164c854ea09c84e))

### BREAKING CHANGES

* The `timeout` property now gets applied to all requests on Android on iOS, as opposed to just web and `getCurrentPosition` on Android. This aligns with what is documented in the plugin. If you are experiencing timeouts when requesting location in your app, consider using a higher `timeout` value. For `watchPosition` on Android, you may use the `interval` parameter introduced in version 8.0.0.

# [8.0.0-next.6](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.5...v8.0.0-next.6) (2025-11-17)


* **android:** Update gradle dependencies to latest versions ([#61](https://github.com/ionic-team/capacitor-geolocation/pull/61))
* **ios:** Minor updates to Package.swift ([#60](https://github.com/ionic-team/capacitor-geolocation/pull/60))

# [8.0.0-next.5](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.4...v8.0.0-next.5) (2025-11-10)


### Bug Fixes

* **android:** use 'propName = value' assignment syntax in build.gradle files ([08f311a](https://github.com/ionic-team/capacitor-geolocation/commit/08f311a1f785f04eab8eec87a60cd5147488388b))

# [8.0.0-next.4](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.3...v8.0.0-next.4) (2025-11-04)


### Bug Fixes

* **ios:** added timeout implementation for both getCurrentPosition and watchPosition ([#55](https://github.com/ionic-team/capacitor-geolocation/issues/55)) ([4c22ac3](https://github.com/ionic-team/capacitor-geolocation/commit/4c22ac3c6facd4b628a38a668d66a7ea2f3ad44c))

# [8.0.0-next.3](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.2...v8.0.0-next.3) (2025-10-06)


### Features

* **android:** Fallback option for no network or Play Services ([#53](https://github.com/ionic-team/capacitor-geolocation/issues/53)) ([09277b7](https://github.com/ionic-team/capacitor-geolocation/commit/09277b7cf458b1625db444f4eb0a40bd7c7b3265))

# [8.0.0-next.2](https://github.com/ionic-team/capacitor-geolocation/compare/v8.0.0-next.1...v8.0.0-next.2) (2025-10-03)


### Bug Fixes

* **ios:** Simplify SPM usage for native library ([#52](https://github.com/ionic-team/capacitor-geolocation/issues/52)) ([ad53dc6](https://github.com/ionic-team/capacitor-geolocation/commit/ad53dc654369db9c1c2ae57cefca798fc5283dc8))

# [8.0.0-next.1](https://github.com/ionic-team/capacitor-geolocation/compare/v7.1.5...v8.0.0-next.1) (2025-09-09)


### Bug Fixes

* peerDependency for pnpm compatibility ([a94839d](https://github.com/ionic-team/capacitor-geolocation/commit/a94839d1c51d7db2bdc39d123121920f8d0b883b))


### Features

* Capacitor 8 support ([6ead26a](https://github.com/ionic-team/capacitor-geolocation/commit/6ead26a06dbfa727551214a0a58469d5195d8657))


### BREAKING CHANGES

* Capacitor major version update requires major version update on the plugin.

## [7.1.5](https://github.com/ionic-team/capacitor-geolocation/compare/v7.1.4...v7.1.5) (2025-08-12)

### Fixes

- **ios:** fixes an issue where the plugin stops receiving location updates after calling the clearWatch method. ([#38](https://github.com/ionic-team/capacitor-geolocation/pull/38))

## [7.1.4](https://github.com/ionic-team/capacitor-geolocation/compare/v7.1.3...7.1.4) (2025-07-15)

### Fixes

- **ios:** Swift package name to be consistent with previous versions ([#34](https://github.com/ionic-team/capacitor-geolocation/pull/34))
- **android** update Gradle and Kotlin configurations for compatibility with latest @capacitor/android ([#28](https://github.com/ionic-team/capacitor-geolocation/pull/28))

### Docs

- **ios** Add note about background usage description ([#29](https://github.com/ionic-team/capacitor-geolocation/pull/29))

## [7.1.3](https://github.com/ionic-team/capacitor-geolocation/compare/7.1.2...v7.1.3) (2025-06-26)

### Fixes

- **ios:** handle location watch callbacks recovery after backgrounding. More info [here](https://github.com/ionic-team/capacitor-geolocation/issues/19).
- Set dependency on @capacitor/synapse to 1.0.3 to fix ssr environments 

## [7.1.2](https://github.com/ionic-team/capacitor-geolocation/compare/7.1.1...7.1.2) (2025-02-21)

### Fixes

- **ios:** watchPosition after an error occurs
- **android** properly parsing number parameters

## [7.1.1](https://github.com/ionic-team/capacitor-geolocation/compare/v7.1.0...7.1.1) (2025-02-21)

### Fixes

- **ios:** properly resolving requestPermissions

# [7.1.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@7.1.0...@nitra/geolocation@7.0.0) (2025-02-12)

### Chores

- Set peerDependency on @capacitor/core to >=7.0.0

### Features

- Revamp error messages and add error codes for better error-handling.
- Rewrite plugin using native Android and iOS libraries.

### Fixes

- **getCurrentPosition** on Android now uses uses the **timeout** parameter.
- **watchPosition** on Android now uses the **timeout** parameter to define the interval for position updates, and the **maximumAge** parameter for **setMaxUpdateAgeMillis**. More info [here](https://developers.google.com/android/reference/com/google/android/gms/location/LocationRequest.Builder#public-locationrequest.builder-setmaxupdateagemillis-long-maxupdateagemillis).

# [7.0.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@7.0.0-rc.0...@nitra/geolocation@7.0.0) (2025-01-20)

**Note:** Version bump only for package @nitra/geolocation

# [7.0.0-rc.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@7.0.0-alpha.2...@nitra/geolocation@7.0.0-rc.0) (2025-01-13)

**Note:** Version bump only for package @nitra/geolocation

# [7.0.0-alpha.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@7.0.0-alpha.1...@nitra/geolocation@7.0.0-alpha.2) (2024-12-19)

**Note:** Version bump only for package @nitra/geolocation

# [7.0.0-alpha.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.1...@nitra/geolocation@7.0.0-alpha.1) (2024-12-16)

### Features

- **geolocation:** add `minimumUpdateInterval` parameter for `startWatch` ([#2272](https://github.com/ionic-team/capacitor-plugins/issues/2272)) ([c6ddc53](https://github.com/ionic-team/capacitor-plugins/commit/c6ddc53efb7eb2b3fc04fc9f2dc9660c9db1a464))

## [6.0.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0...@nitra/geolocation@6.0.1) (2024-08-08)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0-rc.1...@nitra/geolocation@6.0.0) (2024-04-15)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0-rc.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0-rc.0...@nitra/geolocation@6.0.0-rc.1) (2024-03-25)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0-rc.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0-beta.1...@nitra/geolocation@6.0.0-rc.0) (2024-02-07)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0-beta.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0-beta.0...@nitra/geolocation@6.0.0-beta.1) (2023-12-14)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0-beta.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0-alpha.2...@nitra/geolocation@6.0.0-beta.0) (2023-12-13)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0-alpha.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@6.0.0-alpha.1...@nitra/geolocation@6.0.0-alpha.2) (2023-11-15)

**Note:** Version bump only for package @nitra/geolocation

# [6.0.0-alpha.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.6...@nitra/geolocation@6.0.0-alpha.1) (2023-11-08)

**Note:** Version bump only for package @nitra/geolocation

## [5.0.6](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.5...@nitra/geolocation@5.0.6) (2023-07-12)

**Note:** Version bump only for package @nitra/geolocation

## [5.0.5](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.4...@nitra/geolocation@5.0.5) (2023-06-29)

**Note:** Version bump only for package @nitra/geolocation

## [5.0.4](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.3...@nitra/geolocation@5.0.4) (2023-06-08)

**Note:** Version bump only for package @nitra/geolocation

## [5.0.3](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.2...@nitra/geolocation@5.0.3) (2023-06-08)

**Note:** Version bump only for package @nitra/geolocation

## [5.0.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.1...@nitra/geolocation@5.0.2) (2023-05-09)

**Note:** Version bump only for package @nitra/geolocation

## [5.0.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.0...@nitra/geolocation@5.0.1) (2023-05-05)

### Bug Fixes

- **android:** add appCompat libraries for maven releases ([#1577](https://github.com/ionic-team/capacitor-plugins/issues/1577)) ([8a2e0ea](https://github.com/ionic-team/capacitor-plugins/commit/8a2e0ea96538a46bde299a864dba760c6e2eba68))
- Use Capacitor 5 final ([#1574](https://github.com/ionic-team/capacitor-plugins/issues/1574)) ([139c18b](https://github.com/ionic-team/capacitor-plugins/commit/139c18b86a11d31246e952d1a74335ff8ce5dbc2))

# [5.0.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.0-beta.1...@nitra/geolocation@5.0.0) (2023-05-03)

**Note:** Version bump only for package @nitra/geolocation

# [5.0.0-beta.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.0-beta.0...@nitra/geolocation@5.0.0-beta.1) (2023-04-21)

### Features

- Update gradle to 8.0.2 and gradle plugin to 8.0.0 ([#1542](https://github.com/ionic-team/capacitor-plugins/issues/1542)) ([e7210b4](https://github.com/ionic-team/capacitor-plugins/commit/e7210b47867644f5983e37acdbf0247214ec232d))

# [5.0.0-beta.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@5.0.0-alpha.1...@nitra/geolocation@5.0.0-beta.0) (2023-03-31)

**Note:** Version bump only for package @nitra/geolocation

# [5.0.0-alpha.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@4.1.0...@nitra/geolocation@5.0.0-alpha.1) (2023-03-16)

### Bug Fixes

- **geolocation:** use LocationRequest builder instead of deprecated create ([#1483](https://github.com/ionic-team/capacitor-plugins/issues/1483)) ([7cfa12c](https://github.com/ionic-team/capacitor-plugins/commit/7cfa12c86807bd7434dbf907eb878f6796109fe9))

### Features

- **android:** Removing enableJetifier ([d66f9cb](https://github.com/ionic-team/capacitor-plugins/commit/d66f9cbd9da7e3b1d8c64ca6a5b45156867d4a04))

# [4.1.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.3.1...@nitra/geolocation@4.1.0) (2022-11-16)

## 4.0.1 (2022-07-28)

# 4.0.0 (2022-07-27)

# 4.0.0-beta.2 (2022-07-08)

# 4.0.0-beta.0 (2022-06-27)

### Bug Fixes

- **geolocation:** reject checkPermissions / requestPermissions if location services are disabled ([#1053](https://github.com/ionic-team/capacitor-plugins/issues/1053)) ([774ec6e](https://github.com/ionic-team/capacitor-plugins/commit/774ec6e941193b1b06d07d31e6672340de532385))
- **geolocation:** stop location requests on pause ([#1018](https://github.com/ionic-team/capacitor-plugins/issues/1018)) ([eb24f25](https://github.com/ionic-team/capacitor-plugins/commit/eb24f2521d05dd25a2087a1de2b3e0644568cda0))

### Features

- set targetSDK default value to 31 ([#824](https://github.com/ionic-team/capacitor-plugins/issues/824)) ([3ee10de](https://github.com/ionic-team/capacitor-plugins/commit/3ee10de98067984c1a4e75295d001c5a895c47f4))
- set targetSDK default value to 32 ([#970](https://github.com/ionic-team/capacitor-plugins/issues/970)) ([fa70d96](https://github.com/ionic-team/capacitor-plugins/commit/fa70d96f141af751aae53ceb5642c46b204f5958))
- Upgrade gradle to 7.4 ([#826](https://github.com/ionic-team/capacitor-plugins/issues/826)) ([5db0906](https://github.com/ionic-team/capacitor-plugins/commit/5db0906f6264287c4f8e69dbaecf19d4d387824b))
- Use java 11 ([#910](https://github.com/ionic-team/capacitor-plugins/issues/910)) ([5acb2a2](https://github.com/ionic-team/capacitor-plugins/commit/5acb2a288a413492b163e4e97da46a085d9e4be0))

## [4.0.1](https://github.com/ionic-team/capacitor-plugins/compare/4.0.0...4.0.1) (2022-07-28)

**Note:** Version bump only for package @nitra/geolocation

# [4.0.0](https://github.com/ionic-team/capacitor-plugins/compare/4.0.0-beta.2...4.0.0) (2022-07-27)

**Note:** Version bump only for package @nitra/geolocation

# [4.0.0-beta.2](https://github.com/ionic-team/capacitor-plugins/compare/4.0.0-beta.0...4.0.0-beta.2) (2022-07-08)

**Note:** Version bump only for package @nitra/geolocation

# 4.0.0-beta.0 (2022-06-27)

### Bug Fixes

- **geolocation:** reject checkPermissions / requestPermissions if location services are disabled ([#1053](https://github.com/ionic-team/capacitor-plugins/issues/1053)) ([774ec6e](https://github.com/ionic-team/capacitor-plugins/commit/774ec6e941193b1b06d07d31e6672340de532385))
- **geolocation:** stop location requests on pause ([#1018](https://github.com/ionic-team/capacitor-plugins/issues/1018)) ([eb24f25](https://github.com/ionic-team/capacitor-plugins/commit/eb24f2521d05dd25a2087a1de2b3e0644568cda0))
- add es2017 lib to tsconfig ([#180](https://github.com/ionic-team/capacitor-plugins/issues/180)) ([2c3776c](https://github.com/ionic-team/capacitor-plugins/commit/2c3776c38ca025c5ee965dec10ccf1cdb6c02e2f))
- correct addListeners links ([#655](https://github.com/ionic-team/capacitor-plugins/issues/655)) ([f9871e7](https://github.com/ionic-team/capacitor-plugins/commit/f9871e7bd53478addb21155e148829f550c0e457))
- inline source code in esm map files ([#760](https://github.com/ionic-team/capacitor-plugins/issues/760)) ([a960489](https://github.com/ionic-team/capacitor-plugins/commit/a960489a19db0182b90d187a50deff9dfbe51038))
- remove postpublish scripts ([#656](https://github.com/ionic-team/capacitor-plugins/issues/656)) ([ed6ac49](https://github.com/ionic-team/capacitor-plugins/commit/ed6ac499ebf4a47525071ccbfc36c27503e11f60))
- **geolocation:** Make getCurrentPosition return only once ([#470](https://github.com/ionic-team/capacitor-plugins/issues/470)) ([c5f1ceb](https://github.com/ionic-team/capacitor-plugins/commit/c5f1ceb790910b92e3f64d0b7fa8c85d48ea9841))
- **geolocation:** Replace deprecated call.save with new keepAlive API ([#375](https://github.com/ionic-team/capacitor-plugins/issues/375)) ([e4e7cf4](https://github.com/ionic-team/capacitor-plugins/commit/e4e7cf4afd4a70bf48359c625fa7a548211876d5))
- **geolocation:** return cached location if newer than maximumAge ([#639](https://github.com/ionic-team/capacitor-plugins/issues/639)) ([7b08eea](https://github.com/ionic-team/capacitor-plugins/commit/7b08eea9729bbf2b2b6b881cc81389cf108b3a2c))
- **geolocation:** Use the new APIs for handling/saving calls ([#374](https://github.com/ionic-team/capacitor-plugins/issues/374)) ([ebd5b52](https://github.com/ionic-team/capacitor-plugins/commit/ebd5b527cb7f8b6c0016e82d03a0e84287913d3e))
- support deprecated types from Capacitor 2 ([#139](https://github.com/ionic-team/capacitor-plugins/issues/139)) ([2d7127a](https://github.com/ionic-team/capacitor-plugins/commit/2d7127a488e26f0287951921a6db47c49d817336))

### Features

- set targetSDK default value to 31 ([#824](https://github.com/ionic-team/capacitor-plugins/issues/824)) ([3ee10de](https://github.com/ionic-team/capacitor-plugins/commit/3ee10de98067984c1a4e75295d001c5a895c47f4))
- set targetSDK default value to 32 ([#970](https://github.com/ionic-team/capacitor-plugins/issues/970)) ([fa70d96](https://github.com/ionic-team/capacitor-plugins/commit/fa70d96f141af751aae53ceb5642c46b204f5958))
- Upgrade gradle to 7.4 ([#826](https://github.com/ionic-team/capacitor-plugins/issues/826)) ([5db0906](https://github.com/ionic-team/capacitor-plugins/commit/5db0906f6264287c4f8e69dbaecf19d4d387824b))
- Use java 11 ([#910](https://github.com/ionic-team/capacitor-plugins/issues/910)) ([5acb2a2](https://github.com/ionic-team/capacitor-plugins/commit/5acb2a288a413492b163e4e97da46a085d9e4be0))
- **android:** implements Activity Result API changes for permissions and activity results ([#222](https://github.com/ionic-team/capacitor-plugins/issues/222)) ([f671b9f](https://github.com/ionic-team/capacitor-plugins/commit/f671b9f4b472806ef43db6dcf302d4503cf1828c))
- **geolocation:** Add new alias for coarse location ([#684](https://github.com/ionic-team/capacitor-plugins/issues/684)) ([7563040](https://github.com/ionic-team/capacitor-plugins/commit/7563040983ad397e28616246e7ed5ffce69727c2))
- **geolocation:** Error if Google Play Services are not available ([#709](https://github.com/ionic-team/capacitor-plugins/issues/709)) ([fc79c43](https://github.com/ionic-team/capacitor-plugins/commit/fc79c4319c54cbcd5dbbb7221dfdd03d0515805b))
- **geolocation:** Throw error if location is disabled ([#589](https://github.com/ionic-team/capacitor-plugins/issues/589)) ([14724c5](https://github.com/ionic-team/capacitor-plugins/commit/14724c5ec5b23bf94f6f3511bbe204482768d10f))
- add commonjs output format ([#179](https://github.com/ionic-team/capacitor-plugins/issues/179)) ([8e9e098](https://github.com/ionic-team/capacitor-plugins/commit/8e9e09862064b3f6771d7facbc4008e995d9b463))
- Geolocation plugin ([#13](https://github.com/ionic-team/capacitor-plugins/issues/13)) ([911ae71](https://github.com/ionic-team/capacitor-plugins/commit/911ae71e6aef4cfa9fb3ab5b0c13f3c06ef6b15c))

## [1.3.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.3.0...@nitra/geolocation@1.3.1) (2022-01-19)

### Bug Fixes

- inline source code in esm map files ([#760](https://github.com/ionic-team/capacitor-plugins/issues/760)) ([a960489](https://github.com/ionic-team/capacitor-plugins/commit/a960489a19db0182b90d187a50deff9dfbe51038))

# [1.3.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.2.0...@nitra/geolocation@1.3.0) (2021-12-08)

### Features

- **geolocation:** Error if Google Play Services are not available ([#709](https://github.com/ionic-team/capacitor-plugins/issues/709)) ([fc79c43](https://github.com/ionic-team/capacitor-plugins/commit/fc79c4319c54cbcd5dbbb7221dfdd03d0515805b))

# [1.2.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.1.3...@nitra/geolocation@1.2.0) (2021-11-17)

### Features

- **geolocation:** Add new alias for coarse location ([#684](https://github.com/ionic-team/capacitor-plugins/issues/684)) ([7563040](https://github.com/ionic-team/capacitor-plugins/commit/7563040983ad397e28616246e7ed5ffce69727c2))

## [1.1.3](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.1.2...@nitra/geolocation@1.1.3) (2021-11-03)

**Note:** Version bump only for package @nitra/geolocation

## [1.1.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.1.1...@nitra/geolocation@1.1.2) (2021-10-14)

### Bug Fixes

- remove postpublish scripts ([#656](https://github.com/ionic-team/capacitor-plugins/issues/656)) ([ed6ac49](https://github.com/ionic-team/capacitor-plugins/commit/ed6ac499ebf4a47525071ccbfc36c27503e11f60))

## [1.1.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.1.0...@nitra/geolocation@1.1.1) (2021-10-13)

### Bug Fixes

- correct addListeners links ([#655](https://github.com/ionic-team/capacitor-plugins/issues/655)) ([f9871e7](https://github.com/ionic-team/capacitor-plugins/commit/f9871e7bd53478addb21155e148829f550c0e457))
- **geolocation:** return cached location if newer than maximumAge ([#639](https://github.com/ionic-team/capacitor-plugins/issues/639)) ([7b08eea](https://github.com/ionic-team/capacitor-plugins/commit/7b08eea9729bbf2b2b6b881cc81389cf108b3a2c))

# [1.1.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.0.2...@nitra/geolocation@1.1.0) (2021-09-01)

### Features

- **geolocation:** Throw error if location is disabled ([#589](https://github.com/ionic-team/capacitor-plugins/issues/589)) ([14724c5](https://github.com/ionic-team/capacitor-plugins/commit/14724c5ec5b23bf94f6f3511bbe204482768d10f))

## [1.0.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.0.1...@nitra/geolocation@1.0.2) (2021-06-23)

### Bug Fixes

- **geolocation:** Make getCurrentPosition return only once ([#470](https://github.com/ionic-team/capacitor-plugins/issues/470)) ([c5f1ceb](https://github.com/ionic-team/capacitor-plugins/commit/c5f1ceb790910b92e3f64d0b7fa8c85d48ea9841))

## [1.0.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@1.0.0...@nitra/geolocation@1.0.1) (2021-06-09)

**Note:** Version bump only for package @nitra/geolocation

# [1.0.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.7...@nitra/geolocation@1.0.0) (2021-05-19)

**Note:** Version bump only for package @nitra/geolocation

## [0.4.7](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.6...@nitra/geolocation@0.4.7) (2021-05-11)

**Note:** Version bump only for package @nitra/geolocation

## [0.4.6](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.5...@nitra/geolocation@0.4.6) (2021-05-10)

**Note:** Version bump only for package @nitra/geolocation

## [0.4.5](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.4...@nitra/geolocation@0.4.5) (2021-05-07)

### Bug Fixes

- **geolocation:** Replace deprecated call.save with new keepAlive API ([#375](https://github.com/ionic-team/capacitor-plugins/issues/375)) ([e4e7cf4](https://github.com/ionic-team/capacitor-plugins/commit/e4e7cf4afd4a70bf48359c625fa7a548211876d5))
- **geolocation:** Use the new APIs for handling/saving calls ([#374](https://github.com/ionic-team/capacitor-plugins/issues/374)) ([ebd5b52](https://github.com/ionic-team/capacitor-plugins/commit/ebd5b527cb7f8b6c0016e82d03a0e84287913d3e))

## [0.4.4](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.3...@nitra/geolocation@0.4.4) (2021-04-29)

**Note:** Version bump only for package @nitra/geolocation

## [0.4.3](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.2...@nitra/geolocation@0.4.3) (2021-03-10)

**Note:** Version bump only for package @nitra/geolocation

## [0.4.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.1...@nitra/geolocation@0.4.2) (2021-03-02)

**Note:** Version bump only for package @nitra/geolocation

## [0.4.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.4.0...@nitra/geolocation@0.4.1) (2021-02-27)

**Note:** Version bump only for package @nitra/geolocation

# [0.4.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.3.2...@nitra/geolocation@0.4.0) (2021-02-10)

### Features

- **android:** implements Activity Result API changes for permissions and activity results ([#222](https://github.com/ionic-team/capacitor-plugins/issues/222)) ([f671b9f](https://github.com/ionic-team/capacitor-plugins/commit/f671b9f4b472806ef43db6dcf302d4503cf1828c))

## [0.3.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.3.1...@nitra/geolocation@0.3.2) (2021-02-05)

**Note:** Version bump only for package @nitra/geolocation

## [0.3.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.3.0...@nitra/geolocation@0.3.1) (2021-01-26)

**Note:** Version bump only for package @nitra/geolocation

# [0.3.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.2.0...@nitra/geolocation@0.3.0) (2021-01-14)

**Note:** Version bump only for package @nitra/geolocation

# [0.2.0](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.1.3...@nitra/geolocation@0.2.0) (2021-01-13)

### Bug Fixes

- add es2017 lib to tsconfig ([#180](https://github.com/ionic-team/capacitor-plugins/issues/180)) ([2c3776c](https://github.com/ionic-team/capacitor-plugins/commit/2c3776c38ca025c5ee965dec10ccf1cdb6c02e2f))

### Features

- add commonjs output format ([#179](https://github.com/ionic-team/capacitor-plugins/issues/179)) ([8e9e098](https://github.com/ionic-team/capacitor-plugins/commit/8e9e09862064b3f6771d7facbc4008e995d9b463))

## [0.1.3](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.1.2...@nitra/geolocation@0.1.3) (2021-01-13)

**Note:** Version bump only for package @nitra/geolocation

## [0.1.2](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.1.1...@nitra/geolocation@0.1.2) (2021-01-08)

**Note:** Version bump only for package @nitra/geolocation

## [0.1.1](https://github.com/ionic-team/capacitor-plugins/compare/@nitra/geolocation@0.1.0...@nitra/geolocation@0.1.1) (2020-12-27)

**Note:** Version bump only for package @nitra/geolocation

# 0.1.0 (2020-12-20)

### Bug Fixes

- support deprecated types from Capacitor 2 ([#139](https://github.com/ionic-team/capacitor-plugins/issues/139)) ([2d7127a](https://github.com/ionic-team/capacitor-plugins/commit/2d7127a488e26f0287951921a6db47c49d817336))

### Features

- Geolocation plugin ([#13](https://github.com/ionic-team/capacitor-plugins/issues/13)) ([911ae71](https://github.com/ionic-team/capacitor-plugins/commit/911ae71e6aef4cfa9fb3ab5b0c13f3c06ef6b15c))
