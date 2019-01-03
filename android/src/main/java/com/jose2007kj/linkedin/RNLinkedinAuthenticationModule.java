
package com.jose2007kj.linkedin;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
//new code
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.AccessToken;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
//new import ends here

public class RNLinkedinAuthenticationModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private ArrayList<Scope.LIPermission> _availablePermissions;

  public RNLinkedinAuthenticationModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    reactContext.addActivityEventListener(this);

        _availablePermissions = new ArrayList<Scope.LIPermission>();
        _availablePermissions.add(Scope.R_BASICPROFILE);
        _availablePermissions.add(Scope.R_CONTACTINFO);
        _availablePermissions.add(Scope.R_EMAILADDRESS);
        _availablePermissions.add(Scope.R_FULLPROFILE);
        _availablePermissions.add(Scope.RW_COMPANY_ADMIN);
_availablePermissions.add(Scope.W_SHARE);
  }

  @Override
  public String getName() {
    return "RNLinkedinAuthentication";
  }
  @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        Activity _activity = this.getCurrentActivity();

        LISessionManager.getInstance(this.reactContext).onActivityResult(_activity, requestCode, resultCode, data);
    }

    @Override
    public void onNewIntent(Intent intent) {

    }



    

    /**
     * Makes an API request
     * @param url complete url for the api request without session information,
     *            ex: https://api.linkedin.com/v1/people/~:(id,first-name,last-name,industry,email-address)
     */
    @ReactMethod
    public void getRequest(final String url) {

        Activity _activity = this.getCurrentActivity();
        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                APIHelper.getInstance(this.reactContext).getRequest(this.reactContext, url, new ApiListener() {

                    @Override
                    public void onApiSuccess(ApiResponse apiResponse) {

                        

                        JSONObject dataAsJson = apiResponse.getResponseDataAsJson();
                        try {
                            // Log.i(TAG, dataAsJson.toString(4));


                        } catch (JSONException e) {
                            // Log.e(TAG, e.getMessage());
                        }

                        WritableMap params = Arguments.createMap();
                        params.putString("data",  dataAsJson.toString());

                        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("linkedinGetRequest", params);

                    }

                    @Override
                    public void onApiError(LIApiError apiError) {
                        // Log.e(TAG, apiError.toString());
                        WritableMap params = Arguments.createMap();
                        params.putString("error",  apiError.toString());

                        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("linkedinGetRequestError", params);
                    }

                });
            }

        });
    }

    /**
     *
     * logs in the user
     *
     * @param scopes array of available permissions
     */
    @ReactMethod
    public void login(final ReadableArray scopes) {

        final Activity _activity = this.getCurrentActivity();

        _activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
                // Log.d(TAG, "signIn");
                LISessionManager.getInstance(this.reactContext).init(_activity, Scope.build(Scope.R_BASICPROFILE, Scope.R_EMAILADDRESS), new AuthListener() {
                    @Override
                    public void onAuthSuccess() {
                        // Authentication was successful.  You can now do
                        // other calls with the SDK.

                        // Log.d(TAG, "onAuthSuccess");


                        String accessToken = LISessionManager.getInstance(this.reactContext).getSession().getAccessToken().getValue();
                        // Log.d(TAG, "onAuthSuccess: " + accessToken);

                        WritableMap params = Arguments.createMap();
                        params.putString("accessToken", LISessionManager.getInstance(this.reactContext).getSession().getAccessToken().getValue());
                        params.putDouble("expiresOn", LISessionManager.getInstance(this.reactContext).getSession().getAccessToken().getExpiresOn());

                        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("linkedinLogin", params);

                    }

                    @Override
                    public void onAuthError(LIAuthError error) {
                        // Handle authentication errors

                        // Log.e(TAG, "onAuthError: " + error.toString());

                        WritableMap params = Arguments.createMap();
                        params.putString("description", error.toString());

                        this.reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                                .emit("linkedinLoginError", params);
                    }
                }, true);
            }

            
        });
    }

   


    
}