
// import { NativeModules } from 'react-native';

// const { RNLinkedinAuthentication } = NativeModules.RNLinkedinAuthentication;
// class RNLinkedinAuthentication{

// }
// export default RNLinkedinAuthentication;
// 'use strict';

// const React = require('react-native');
// const {
//   Platform,
//   DeviceEventEmitter,
//   NativeModules
// } = React;
// const RNLinkedinAuthentication = NativeModules.LinkedinAuthentication;


// class LinkedinAuthentication {

//   _accessToken = null;
//   _expiresOn = null;

//   _scopes = null;

//   constructor() {
//   }

//   /**
//    * Initializes the Linkedin Login module
//    * 
//    * @param {any} scopes 
//    * @returns {LinkedinAuthentication} 
//    */
//   init(scopes) {

//     this._scopes = scopes;
//     return this;
//   }

//   /**
//    * Gets the Profile image
//    * @return {object} Returns the promise with the image
//    */
//   getProfileImages() {
//     const atoken = this._accessToken;

//     return new Promise((resolve, reject) => {
//       DeviceEventEmitter.addListener('linkedinGetRequest', (d) => {
//         const data = JSON.parse(d.data);

//         if (data.values) {
//           resolve(data.values);
//         } else {
//           reject('No profile image found');
//         }
//       });

//       DeviceEventEmitter.addListener('linkedinGetRequestError', (error) => {
//         reject(error);
//       });

//       const picstr = 'https://api.linkedin.com/v1/people/~/picture-urls::(original)';

//       RNLinkedinAuthentication.getRequest(picstr);
    
//     });
//   }

//   /**
//    * Gets the user profile
//    * @param {string} options Linkedin options for profile object 
//    * @return {object} Returns a promise with the user object or error
//    */
//   getProfile(options) {
//     const atoken = this._accessToken;

//     return new Promise((resolve, reject) => {
//       DeviceEventEmitter.addListener('linkedinGetRequest', (d) => {
//         const data = JSON.parse(d.data);
        
//         if (data) {
//           resolve(data);
//         }
//       });

//       DeviceEventEmitter.addListener('linkedinGetRequestError', (error) => {
//         reject(error);
//       });

//       const opt = options;
//       const profilestr = `https://api.linkedin.com/v1/people/~:(${opt})`;

//       RNLinkedinAuthentication.getRequest(profilestr);
//     });
//   }

//   /**
//   * Sets the Linkedin session
//   * @param  {string} accessToken Linkedin access token
//   * @param  {Number} expiresOn   The access token's expiration number
//   * @return {object} promise     Returns if access token is valid or not
//   */
//   setSession(accessToken, expiresOn) {
//     this._accessToken = accessToken;
//     this._expiresOn = expiresOn;
//   }

//   /**
//    * Logs the user in
//    * @return {promise} returns whether or not the user logged in successfully
//    */
//   login() {
//     return new Promise((resolve, reject) => {
//       DeviceEventEmitter.addListener('linkedinLoginError', (error) => {
//         reject(error);
//       });

//       DeviceEventEmitter.addListener('linkedinLogin', (data) => {
//         this._accessToken = data.accessToken;
//         this._expiresOn = data.expiresOn;

//         resolve(data);
//       });

//       RNLinkedinAuthentication.login(
//         this._scopes
//       );
//     });
//   }

//   logout() {
//     this._accessToken = null;
//   }
// }

// const linkedinAuthentication = new LinkedinAuthentication();

// module.exports = linkedinAuthentication;