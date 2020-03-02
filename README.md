
# react-native-trust-vision-rnsdk-framework

## Getting started

`$ npm install react-native-trust-vision-rnsdk-framework --save`

### Mostly automatic installation

`$ react-native link react-native-trust-vision-rnsdk-framework`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-trust-vision-rnsdk-framework` and add `RNTrustVisionRnsdkFramework.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNTrustVisionRnsdkFramework.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNTrustVisionRnsdkFrameworkPackage;` to the imports at the top of the file
  - Add `new RNTrustVisionRnsdkFrameworkPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-trust-vision-rnsdk-framework'
  	project(':react-native-trust-vision-rnsdk-framework').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-trust-vision-rnsdk-framework/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-trust-vision-rnsdk-framework')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNTrustVisionRnsdkFramework.sln` in `node_modules/react-native-trust-vision-rnsdk-framework/windows/RNTrustVisionRnsdkFramework.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Trust.Vision.Rnsdk.Framework.RNTrustVisionRnsdkFramework;` to the usings at the top of the file
  - Add `new RNTrustVisionRnsdkFrameworkPackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNTrustVisionRnsdkFramework from 'react-native-trust-vision-rnsdk-framework';

// TODO: What to do with the module?
RNTrustVisionRnsdkFramework;
```
  