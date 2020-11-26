
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
import com.trustingsocial.tvsdk.external.TVCaptureError;
import com.trustingsocial.tvsdk.external.TVCapturingCallBack;
import com.trustingsocial.tvsdk.external.TVEncryptedImage;
import com.trustingsocial.tvsdk.external.TVGestureImage;
import com.trustingsocial.tvsdk.external.TVIDConfiguration;
import com.trustingsocial.tvsdk.external.TVSelfieCapturingCallback;
import com.trustingsocial.tvsdk.external.TVSelfieConfiguration;
import com.trustingsocial.tvsdk.internal.TrustVisionSDK;

import java.util.List;

public class RNTrustVisionRnsdkFrameworkModule extends ReactContextBaseJavaModule {
    private static String INTERNAL_ERROR = "internal_error";
    private static String SDK_CANCELED = "sdk_canceled";

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
    public void startIdCapturing(ReadableMap config, String languageCode, final Promise promise) {
        try {
            TVIDConfiguration configuration = RNTrustVisionUtils.convertIdConfigFromMap(config);
            TrustVisionSDK.startIDCapturing(getCurrentActivity(), languageCode, configuration, new TVCapturingCallBack() {
                @Override
                public void onError(TVCaptureError tvCaptureError) {
                    promise.reject(Integer.toString(tvCaptureError.getErrorCode()), tvCaptureError.getErrorDescription());
                }

                @Override
                public void onSuccess(TVEncryptedImage tvEncryptedImage, TVEncryptedImage tvEncryptedImage1) {
                    try {
                        promise.resolve(convertResult(tvEncryptedImage, tvEncryptedImage1));
                    } catch (Exception e) {
                        promise.reject(INTERNAL_ERROR, "Parse result error");
                    }
                }

                @Override
                public void onCanceled() {
                    promise.reject(SDK_CANCELED, "sdk is canceled by user");
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void startSelfieCapturing(ReadableMap config, String languageCode, final Promise promise) {
        try {
            TVSelfieConfiguration configuration = RNTrustVisionUtils.convertSelfieConfigFromMap(config);
            TrustVisionSDK.startSelfieCapturing(getCurrentActivity(), languageCode, configuration, new TVSelfieCapturingCallback() {
                @Override
                public void onError(TVCaptureError tvCaptureError) {
                    promise.reject(Integer.toString(tvCaptureError.getErrorCode()), tvCaptureError.getErrorDescription());
                }

                @Override
                public void onSuccess(List<TVGestureImage> list) {
                    try {
                        promise.resolve(convertResult(list));
                    } catch (Exception e) {
                        promise.reject(INTERNAL_ERROR, "Parse result error");
                    }
                }

                @Override
                public void onCanceled() {
                    promise.reject(SDK_CANCELED, "sdk is canceled by user");
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    private WritableMap convertResult(TVEncryptedImage bitmap, TVEncryptedImage bitmap1) throws Exception {
        WritableMap result = new WritableNativeMap();
        if (bitmap != null) {
            result.putMap("front_id_image", convertTvEncryptedImage(bitmap));
        }
        if (bitmap1 != null) {
            result.putMap("back_id_image", convertTvEncryptedImage(bitmap1));
        }
        return result;
    }

    private WritableMap convertResult(List<TVGestureImage> bitmapList) throws Exception {
        WritableMap result = new WritableNativeMap();
        WritableArray bitmapArray = new WritableNativeArray();
        for (TVGestureImage gestureImage: bitmapList) {
            if (gestureImage != null) {
                WritableMap selfieImageMap = new WritableNativeMap();
                selfieImageMap.putString("gesture_type", gestureImage.getGestureType().toString());
                selfieImageMap.putMap("frontal_image", convertTvEncryptedImage(gestureImage.getFrontalImage()));
                selfieImageMap.putMap("gesture_image", convertTvEncryptedImage(gestureImage.getGestureImage()));
                bitmapArray.pushMap(selfieImageMap);
            }
        }
        result.putArray("selfie_images", bitmapArray);

        return result;
    }

    private WritableMap convertTvEncryptedImage(TVEncryptedImage encryptedImage) {
        if (encryptedImage == null) { return null; }
        WritableMap result = new WritableNativeMap();
        Bitmap rawBitmap = encryptedImage.getRawImage();
        if (rawBitmap == null) { return null; }
        result.putString("raw_image_base64", RNTrustVisionUtils.convertBitmapToBase64(rawBitmap));
        return result;
    }
}