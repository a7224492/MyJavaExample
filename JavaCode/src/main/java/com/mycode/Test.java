package com.mycode;

import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author jiangzhen
 * @date 2019/3/29 13:58
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> m = new HashMap<>();
        m.put("jiangzhen", 25);
        m.put("chuangwang", 25);
        m.put("shafuzi", 26);
        m.entrySet();
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
