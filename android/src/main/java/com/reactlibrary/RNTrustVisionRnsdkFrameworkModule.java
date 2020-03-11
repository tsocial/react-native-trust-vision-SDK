
package com.reactlibrary;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Base64;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeMap;
import com.trustingsocial.apisdk.TVApi;
import com.trustingsocial.apisdk.data.TVApiError;
import com.trustingsocial.apisdk.data.TVCallback;
import com.trustingsocial.tvsdk.TVCapturingCallBack;
import com.trustingsocial.tvsdk.TVDetectionError;
import com.trustingsocial.tvsdk.TVDetectionResult;
import com.trustingsocial.tvsdk.TVIDConfiguration;
import com.trustingsocial.tvsdk.TVSDKCallback;
import com.trustingsocial.tvsdk.TVSDKConfiguration;
import com.trustingsocial.tvsdk.TVSelfieConfiguration;
import com.trustingsocial.tvsdk.TVTransactionData;
import com.trustingsocial.tvsdk.internal.TVCardType;
import com.trustingsocial.tvsdk.internal.TrustVisionSDK;

import org.json.JSONException;

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
            promise.reject(INTERNAL_ERROR, "get cardTypes error");
        }
    }

    @ReactMethod
    public void startFlowWithConfig(ReadableMap config, final Promise promise) {
        try {
            TVSDKConfiguration configuration = RNTrustVisionUtils.convertConfigFromMap(config);
            TrustVisionSDK.startTrustVisionSDK(getCurrentActivity(), configuration, new TVCapturingCallBack() {
                @Override
                public void onError(TVDetectionError tvDetectionError) {
                    promise.reject(tvDetectionError.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(tvDetectionError));
                }

                @Override
                public void onSuccess(TVDetectionResult tvDetectionResult) {
                    //convert to map
                    promise.resolve(RNTrustVisionUtils.convertResult(tvDetectionResult));
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void startIDCapturing(ReadableMap config, final Promise promise) {
        try {
            TVIDConfiguration configuration = RNTrustVisionUtils.convertIdConfigFromMap(config);
            TrustVisionSDK.startIDCapturing(getCurrentActivity(), configuration, new TVCapturingCallBack() {
                @Override
                public void onError(TVDetectionError tvDetectionError) {
                    promise.reject(tvDetectionError.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(tvDetectionError));
                }

                @Override
                public void onSuccess(TVDetectionResult tvDetectionResult) {
                    promise.resolve(RNTrustVisionUtils.convertResult(tvDetectionResult));
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
            TrustVisionSDK.startSelfieCapturing(getCurrentActivity(), configuration, new TVCapturingCallBack() {
                @Override
                public void onError(TVDetectionError tvDetectionError) {
                    promise.reject(tvDetectionError.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(tvDetectionError));
                }

                @Override
                public void onSuccess(TVDetectionResult tvDetectionResult) {
                    promise.resolve(RNTrustVisionUtils.convertResult(tvDetectionResult));
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void faceMatching(String imageId1, String imageId2, final Promise promise) {
        try {
            TrustVisionSDK.faceMatching(imageId1, imageId2, new TVCapturingCallBack() {
                @Override
                public void onError(TVDetectionError tvDetectionError) {
                    promise.reject(tvDetectionError.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(tvDetectionError));
                }

                @Override
                public void onSuccess(TVDetectionResult tvDetectionResult) {
                    promise.resolve(RNTrustVisionUtils.convertResult(tvDetectionResult));

                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void startTransaction(String referenceId, final Promise promise) {
        try {
            TrustVisionSDK.startTransaction(referenceId, new TVSDKCallback<TVTransactionData>() {
                @Override
                public void onSuccess(TVTransactionData data) {
                    promise.resolve(data);

                }

                @Override
                public void onError(TVDetectionError tvDetectionError) {
                    promise.reject(tvDetectionError.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(tvDetectionError));
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void endTransaction(final Promise promise) {
        try {
            TrustVisionSDK.endTransaction(getCurrentActivity(), new TVSDKCallback<String>() {
                @Override
                public void onSuccess(String transactionId) {
                    WritableMap data = new WritableNativeMap();
                    data.putString("transactionId", transactionId);
                    promise.resolve(data);
                }

                @Override
                public void onError(TVDetectionError tvDetectionError) {
                    promise.reject(tvDetectionError.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(tvDetectionError));
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void getLivenessOptions(Promise promise) {
        try {
            promise.resolve(TrustVisionSDK.getLivenessOptions());
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

    @ReactMethod
    public void downloadImage(String imageId, final Promise promise) {
        try {
            TVApi.getInstance().downloadImage(imageId, new TVCallback<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    promise.resolve(Base64.encode(bytes, Base64.NO_WRAP));
                }

                @Override
                public void onError(List<TVApiError> list) {
                    TVDetectionError error = new TVDetectionError(list.get(0));
                    promise.reject(error.getDetailErrorCode(), RNTrustVisionUtils.convertErrorString(error));
                }
            });
        } catch (Exception ex) {
            promise.reject(INTERNAL_ERROR, ex.getMessage());
        }
    }

}