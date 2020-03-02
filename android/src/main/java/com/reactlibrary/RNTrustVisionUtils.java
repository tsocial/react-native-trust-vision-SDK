package com.reactlibrary;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.trustingsocial.apisdk.utils.GsonUtils;
import com.trustingsocial.tvsdk.TVSDKConfiguration;
import com.trustingsocial.tvsdk.TVSDKConfiguration.TVActionMode;
import com.trustingsocial.tvsdk.TVSDKConfiguration.TVLivenessMode;
import com.trustingsocial.tvsdk.internal.TVCardType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

class RNTrustVisionUtils {

    static <T> WritableArray toWritableArray(List<T> objects) throws JSONException {
        WritableArray array = new WritableNativeArray();
        for (T element : objects) {
            WritableMap map = convertJsonToMap(new JSONObject(GsonUtils.toJson(element)));
            array.pushMap(map);
        }
        return array;
    }

    static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String) {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }

    static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String) {
                array.pushString((String) value);
            } else {
                array.pushString(value.toString());
            }
        }
        return array;
    }

    static TVSDKConfiguration convertConfigFromMap(ReadableMap map) {
        TVSDKConfiguration.Builder configuration = new TVSDKConfiguration.Builder();

        if (map.hasKey("isEnableSound")) {
            configuration.setEnableSound(map.getBoolean("isEnableSound"));
        }

        if (map.hasKey("livenessMode")) {
            configuration.setLivenessMode(TVLivenessMode.values()[map.getInt("livenessMode")]);
        }

        if (map.hasKey("actionMode")) {
            configuration.setActionMode(TVActionMode.values()[map.getInt("actionMode")]);
        }

        if (map.hasKey("cardType")) {
            String json = map.getString("cardType");
            configuration.setCardType(GsonUtils.fromJson(json, TVCardType.class));
        }

        if (map.hasKey("cameraOption")) {
            configuration.setCameraOption(TVSDKConfiguration.TVCameraOption.values()[map.getInt("cameraOption")]);
        }

        if (map.hasKey("isEnableIDSanityCheck")) {
            configuration.setEnableIDSanityCheck(map.getBoolean("isEnableIDSanityCheck"));
        }

        if (map.hasKey("isEnableSelfieSanityCheck")) {
            configuration.setEnableSelfieSanityCheck(map.getBoolean("isEnableSelfieSanityCheck"));
        }

        return configuration.build();
    }


}
