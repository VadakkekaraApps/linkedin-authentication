
# react-native-linkedin-authentication

## Getting started

`$ npm install git+https://git@github.com/VadakkekaraApps/linkedin-authentication.git --save`

### Mostly automatic installation

`$ react-native link react-native-linkedin-authentication`

### Manual installation


#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.jose2007kj.reactnative.RNLinkedinAuthenticationPackage;` to the imports at the top of the file
  - Add `new RNLinkedinAuthenticationPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-linkedin-authentication'
  	project(':react-native-linkedin-authentication').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-linkedin-authentication/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-linkedin-authentication')
  	```


## Usage
```javascript
import RNLinkedinAuthentication from 'react-native-linkedin-authentication';

// TODO: What to do with the module?
RNLinkedinAuthentication;
```
  
