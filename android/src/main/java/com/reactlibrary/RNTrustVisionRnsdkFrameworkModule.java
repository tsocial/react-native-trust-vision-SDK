
package com.reactlibrary;

import android.app.Activity;
import android.text.TextUtils;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.gson.JsonArray;
import com.trustingsocial.apisdk.utils.GsonUtils;
import com.trustingsocial.tvsdk.TVDetectionError;
import com.trustingsocial.tvsdk.TVSDKConfiguration;
import com.trustingsocial.tvsdk.TVSDKUtil;
import com.trustingsocial.tvsdk.internal.TVCardType;
import com.trustingsocial.tvsdk.internal.TrustVisionSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

public class RNTrustVisionRnsdkFrameworkModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNTrustVisionRnsdkFrameworkModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNTrustVisionRnsdkFramework";
    }

    @ReactMethod
    public void initializeWithAcessKeyId(String accessKeyId, String accessKeySecret, Boolean isForce, final Promise promise) {
        Activity activity = getCurrentActivity();
        TrustVisionSDK.TVInitializeListener listener = new TrustVisionSDK.TVInitializeListener() {
            @Override
            public void onInitSuccess() {
                promise.resolve("initSuccess");
            }

            @Override
            public void onInitError(TVDetectionError tvDetectionError) {
                promise.reject(String.valueOf(tvDetectionError.getErrorCode()), tvDetectionError.getErrorDescription());
            }
        };

        TrustVisionSDK.init(activity, accessKeyId, accessKeySecret, listener);
    }

    @ReactMethod
    public void init(String accessKeyId, String accessKeySecret, final Promise promise) {
        Activity activity = getCurrentActivity();
        TrustVisionSDK.TVInitializeListener listener = new TrustVisionSDK.TVInitializeListener() {
            @Override
            public void onInitSuccess() {
                promise.resolve("initSuccess");
            }

            @Override
            public void onInitError(TVDetectionError tvDetectionError) {
                promise.reject(String.valueOf(tvDetectionError.getErrorCode()), tvDetectionError.getErrorDescription());
            }
        };

        if (TextUtils.isEmpty(accessKeyId) || TextUtils.isEmpty(accessKeySecret)) {
            TrustVisionSDK.init(activity, listener);
        } else {
            TrustVisionSDK.init(activity, accessKeyId, accessKeySecret, listener);
        }
    }

    @ReactMethod
    public void getCardTypes(Promise promise) {
        List<TVCardType> cardTypes = TrustVisionSDK.getCardTypes();
        try {
            WritableArray array = RNTrustVisionUtils.toWritableArray(cardTypes);
            promise.resolve(array);
        } catch (JSONException e) {
            e.printStackTrace();
            promise.reject("error", "parse array error");
        }
    }

    @ReactMethod
    public void startFlowWithConfig(ReadableMap config, Promise promise) {
        try {
            TVSDKConfiguration configuration = RNTrustVisionUtils.convertConfigFromMap(config);
            TrustVisionSDK.startTrustVisionSDK(getCurrentActivity(), configuration);
        } catch (Exception ex) {
            promise.reject(ex);
        }
    }
}
