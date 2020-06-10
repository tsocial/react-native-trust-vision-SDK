
package com.reactlibrary;

import android.graphics.Bitmap;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.trustingsocial.tvsdk.TVCaptureError;
import com.trustingsocial.tvsdk.TVCapturingCallBack;
import com.trustingsocial.tvsdk.TVIDConfiguration;
import com.trustingsocial.tvsdk.TVSelfieCapturingCallback;
import com.trustingsocial.tvsdk.TVSelfieConfiguration;
import com.trustingsocial.tvsdk.internal.TrustVisionSDK;

import java.util.List;

public class RNTrustVisionRnsdkFrameworkModule extends ReactContextBaseJavaModule {
    private static String INTERNAL_ERROR = "internal_error";

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
    public void startIdCapturing(ReadableMap config, final Promise promise) {
        try {
            TVIDConfiguration configuration = RNTrustVisionUtils.convertIdConfigFromMap(config);
            TrustVisionSDK.startIDCapturing(getCurrentActivity(), configuration, new TVCapturingCallBack() {
                @Override
                public void onError(TVCaptureError tvCaptureError) {
                    promise.reject(Integer.toString(tvCaptureError.getErrorCode()), tvCaptureError.getErrorDescription());
                }

                @Override
                public void onSuccess(Bitmap bitmap, Bitmap bitmap1) {
                    try {
                        promise.resolve(convertResult(bitmap, bitmap1));
                    } catch (Exception e) {
                        promise.reject(INTERNAL_ERROR, "Parse result error");
                    }
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void startSelfieCapturing(ReadableMap config, final Promise promise) {
        try {
            TVSelfieConfiguration configuration = RNTrustVisionUtils.convertSelfieConfigFromMap(config);
            TrustVisionSDK.startSelfieCapturing(getCurrentActivity(), configuration, new TVSelfieCapturingCallback() {
                @Override
                public void onError(TVCaptureError tvCaptureError) {
                    promise.reject(Integer.toString(tvCaptureError.getErrorCode()), tvCaptureError.getErrorDescription());
                }

                @Override
                public void onSuccess(List<Bitmap> list) {
                    try {
                        promise.resolve(convertResult(list));
                    } catch (Exception e) {
                        promise.reject(INTERNAL_ERROR, "Parse result error");
                    }
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    private WritableMap convertResult(Bitmap bitmap, Bitmap bitmap1) throws Exception {
        WritableMap result = new WritableNativeMap();
        if (bitmap != null) {
            result.putString("id_front_image", RNTrustVisionUtils.convertBitmapToBase64(bitmap));
        }
        if (bitmap1 != null) {
            result.putString("id_back_image", RNTrustVisionUtils.convertBitmapToBase64(bitmap1));
        }
        return result;
    }

    private WritableMap convertResult(List<Bitmap> bitmapList) throws Exception {
        WritableMap result = new WritableNativeMap();
        WritableArray bitmapArray = new WritableNativeArray();
        for (Bitmap bitmap: bitmapList) {
            if (bitmap != null) {
                bitmapArray.pushString(RNTrustVisionUtils.convertBitmapToBase64(bitmap));
            }
        }
        result.putArray("selfie_images", bitmapArray);

        return result;
    }
}