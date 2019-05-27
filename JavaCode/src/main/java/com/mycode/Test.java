package com.mycode;

import com.alibaba.fastjson.JSONObject;
import com.kodgames.billingutil.HttpClient;
import com.kodgames.message.protocol.PlatformProtocolsConfig;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

import static com.mycode.common.constants.Constants._1G;

/**
 * @author jiangzhen
 * @date 2019/3/29 13:58
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
//        String openId = "om-BY5A3p1wtQbLX9RGFIPYGtPBM";
//        String sessionKey = "MEmMOSvU+MoC59Yhw0Habw==";
//        String accessToken = "21_1kkktxBcJsd7JvEul5VGtVGzvMMuFDmPLC5BCnz8Wpzi_oFbgKaBuPAxkaB8Fug7U2hL0JmTjNK6mv8DjM3t2_hdkZZH0uJPyMNEUzIGjjqVhfbsyRuin48ADxNDGxo6qGYB_bPKog1OBFvQIIYbAIARRH";
//        String ts = String.valueOf(String.valueOf(System.currentTimeMillis()));
//
//        JSONObject body = new JSONObject();
//        body.put("openid", openId);
//        body.put("appid", "wxa7529ccfea8b759c");
//        body.put("offer_id", "1450020913");
//        body.put("ts", ts);
//        body.put("zone_id", "1");
//        body.put("pf", "android");
//
//        String sig = sig(openId, ts);
//        body.put("sig", sig);
//        body.put("mp_sig", mpSig(accessToken, openId, sig, ts, sessionKey));
//
//        HttpClient.doPostAsyn("https://api.weixin.qq.com/cgi-bin/midas/sandbox/getbalance?access_token="+accessToken, body.toJSONString(), HttpClient.FORM, new Callback()
//        {
//            @Override
//            public void onFailure(Call call, IOException e)
//            {
//                System.out.println(e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response)
//                    throws IOException
//            {
//                JSONObject result = JSONObject.parseObject(response.body().string());
//                System.out.println(result);
//                if (response.isSuccessful())
//                {
//
//                }
//                else
//                {
//                    System.out.println("response is not successful");
//                }
//            }
//        });

        double[] x = {1.0, 3.0, 4.0};
        int f = 1;
        System.out.println(f == x[0]);
    }

    public static String sig(String openId, String ts)
    {
        String stringA="appid=wxa7529ccfea8b759c&offer_id=1450020913&openid=" + openId + "&pf=android&ts=" + ts + "&zone_id=1";
        String stringSignTemp=stringA+"&org_loc=/cgi-bin/midas/getbalance&method=POST&secret=a822601c40ac534f204f0000e7df7c6f";
        return sha256_HMAC(stringSignTemp, "FbccTeIpKTWBbEqgv1zQKmCizEK0TjyX");
    }

    public static String mpSig(String accessToken, String openId, String sig, String ts, String sessionKey)
    {
        String stringA="access_token=ACCESSTOKEN&appid=wxa7529ccfea8b759c&offer_id=1450020913&openid=" + openId + "&pf=android&sig=" + sig + "&ts=" + ts + "&zone_id=1";
        String stringSignTemp=stringA+"&org_loc=/cgi-bin/midas/getbalance&method=POST&session_key=" + sessionKey;
        return sha256_HMAC(stringSignTemp, sessionKey);
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param b 字节数组
     * @return 字符串
     */
    public  static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b!=null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }
    /**
     * sha256_HMAC加密
     * @param message 消息
     * @param secret  秘钥
     * @return 加密后字符串
     */
    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }
}
