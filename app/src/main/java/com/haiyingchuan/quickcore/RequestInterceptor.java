package com.haiyingchuan.quickcore;

import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZQXQ-Developer on 2017/7/25.
 */

public class RequestInterceptor implements Interceptor {
    public static boolean outSign;
    public static String extraName;
    public static String extraValue;
    public static Map<String, String> extraMap = new HashMap<>();
    /**
     * 请求头 key
     */
    private static final String HEADER_KEY_PUBLIC_KEY = "public_key";//公钥
    private static final String HEADER_KEY_APP_KEY = "app_key";//app 标识
    private static final String HEADER_KEY_SOURCE = "source";// 用户来源
    private static final String HEADER_KEY_CLIENT_ID = "client_id";//用户手机唯一标识
    private static final String HEADER_KEY_CHANNEL = "channel";//打包渠道
    private static final String HEADER_KEY_APP_VERSION = "app_version";//app 客户端版本号
    private static final String HEADER_KEY_TIMESTAMP = "timestamp";//客户端时间戳
    private static final String HEADER_KEY_SIGNATURE = "signature";//签名信息

    /**
     * 请求头 value
     */
    private static final String HEADER_VALUE_PUBLIC_KEY = "040CD66A84025B94CEAC3EC1B49C92D0";
    private static final String HEADER_VALUE_APP_KEY = "ZQXQ-SYD";
    private static final String HEADER_VALUE_SOURCE = "1";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String app_key = request.header("app_key");
        if (app_key != null && app_key.length() > 1) {

        } else {
            app_key = HEADER_VALUE_APP_KEY;
        }
        Log.i("result", "intercept: " + app_key);
        //请求体解析
        RequestBody rb = request.body();
        Map<String, String> bodyMap = new HashMap<>();
        if (rb instanceof FormBody) {
            FormBody fb = (FormBody) rb;
            for (int i = 0; i < fb.size(); i++) {
                String name = fb.encodedName(i);
                String value = java.net.URLEncoder.encode(fb.value(i), "UTF-8");
                bodyMap.put(name, value);
            }
        } else if (rb instanceof MultipartBody) {
            if (outSign) {
                for (String key : extraMap.keySet()) {
                    bodyMap.put(key, java.net.URLEncoder.encode(extraMap.get(key), "UTF-8"));
                }
                outSign = false;
                extraName = null;
                extraValue = null;
                extraMap.clear();
            }
        }
        String HEADER_VALUE_CHANNEL = "OPPO";
        String HEADER_VALUE_APP_VERSION = "1.0.0";

        //客户端时间戳
        String HEADER_VALUE_TIMESTAMP = String.valueOf(System.currentTimeMillis());
        //签名信息
        String HEADER_VALUE_SIGNATURE = doMD5(
                HEADER_VALUE_PUBLIC_KEY
                        + app_key
                        + HEADER_VALUE_SOURCE
                        + HEADER_VALUE_CHANNEL
                        + "0000000000"
                        + HEADER_VALUE_APP_VERSION
                        + getParmStr(bodyMap).toString()
                        + HEADER_VALUE_TIMESTAMP
        );

        Request newRequest = request.newBuilder()
                //公钥
                .addHeader(HEADER_KEY_PUBLIC_KEY, HEADER_VALUE_PUBLIC_KEY)
                //app 标识
                .addHeader(HEADER_KEY_APP_KEY, app_key)
                //用户来源
                .addHeader(HEADER_KEY_SOURCE, HEADER_VALUE_SOURCE)
                //用户手机唯一标识
                .addHeader(HEADER_KEY_CLIENT_ID, "000000000")
                //打包渠道名
                .addHeader(HEADER_KEY_CHANNEL, HEADER_VALUE_CHANNEL)
                //app 客户端版本号
                .addHeader(HEADER_KEY_APP_VERSION, HEADER_VALUE_APP_VERSION)
                //客户端时间戳
                .addHeader(HEADER_KEY_TIMESTAMP, HEADER_VALUE_TIMESTAMP)
                //签名信息
                .addHeader(HEADER_KEY_SIGNATURE, HEADER_VALUE_SIGNATURE)
                .build();
        return chain.proceed(newRequest);
    }

    /**
     * @param string 需要加密的字符串
     * @return 加密后的字符串
     */
    private String doMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }

        return hex.toString();
    }

    public StringBuffer getParmStr(Map<String, String> parms) {
        StringBuffer sb = new StringBuffer();
        ArrayList<String> aa = new ArrayList<String>(parms.keySet());
        Collections.sort(aa);

        for (String key : aa) {
            try {
                sb.append(key).append("=").append((parms.get(key))).append("&");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }
}
