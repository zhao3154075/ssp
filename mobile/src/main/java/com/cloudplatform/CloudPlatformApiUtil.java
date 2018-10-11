package com.cloudplatform;

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import sun.misc.BASE64Decoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 与云服务平台相关的api
 */
public class CloudPlatformApiUtil {
    public static final String[] AUTHORIZE_PARAM_NAMES="appId,nonceStr,timeStamp,user_id".split(",");
    public static final String AUTHOR_KEY="be9e804494c566b27d246c3729bef8ce";
    /**
     * 生成跟在url后面的授权加密串
     * @return
     */
    public static String makeEncryptUrlParams(Map<String, Object> tmpMap){
        tmpMap.put("timeStamp",System.currentTimeMillis()/1000+"");
        String encryptTempString=makeEncryptTempString(tmpMap,AUTHOR_KEY);
        String sign=makeSignString(encryptTempString);
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, Object> item : tmpMap.entrySet()){
            buf.append(item.getKey()).append("=").append(item.getValue()).append("&");
        }
        buf.append("sign=").append(sign);
        return buf.toString();
    }
    /**
     * 验证授权加密
     */
    public static boolean checkEncrypt(String sign,Map<String, Object> tmpMap,String key){
        return makeSignString(makeEncryptTempString(tmpMap,key)).equals(sign);
    }

    /**
     * 根据传入的键值对按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串
     * @param tmpMap
     * @return
     */
    public static String makeEncryptTempString(Map<String, Object> tmpMap,String appkey){
        List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(tmpMap.entrySet());
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>()
        {

            public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2)
            {
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });
        StringBuilder buf = new StringBuilder();
        for (Map.Entry<String, Object> item : infoIds)
        {
            if (StringUtils.isNotBlank(item.getKey()))
            {
                String key = item.getKey();
                String val = item.getValue()+"";
                buf.append(key + "=" + val);
                buf.append("&");
            }

        }
        buf.append("key="+appkey);
        String result= buf.toString();
        return result;
    }

    /**
     * 生成加密串
     * @param temp
     * @return
     */
    public static String makeSignString(String temp){
        return MD5(temp).toUpperCase();
    }

    /**
     * AES256解密
     * @param data
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String desEncrypt(String data,String key,String iv) {
        try
        {
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);
            // setup cipher parameters with key and IV
            KeyParameter keyParam = new KeyParameter(key.getBytes());
            CipherParameters params = new ParametersWithIV(keyParam, iv.getBytes());

            // setup AES cipher in CBC mode with zero padding
            BlockCipherPadding padding = new ZeroBytePadding();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                    new CBCBlockCipher(new RijndaelEngine(256)),padding);
            cipher.reset();
            cipher.init(false, params);
            // create a temporary buffer to decode into (it'll include padding)
            byte[] plainTemp = new byte[cipher.getOutputSize(encrypted1.length)];
            int offset = cipher.processBytes(encrypted1, 0, encrypted1.length, plainTemp, 0);
            int last = cipher.doFinal(plainTemp, offset);
            byte[] plain = new byte[offset + last];
            System.arraycopy(plainTemp, 0, plain, 0, plain.length);
            return new String(plain,"utf-8");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES256加密
     * @param data
     * @param key
     * @param iv
     * @return
     * @throws Exception
     */
    public static String encrypt(String data,String key,String iv) {
        try
        {
            byte[] encrypted1 = data.getBytes();
            // setup cipher parameters with key and IV
            KeyParameter keyParam = new KeyParameter(key.getBytes());
            CipherParameters params = new ParametersWithIV(keyParam, iv.getBytes());

            // setup AES cipher in CBC mode with zero padding
            BlockCipherPadding padding = new ZeroBytePadding();
            BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(
                    new CBCBlockCipher(new RijndaelEngine(256)),padding);
            cipher.reset();
            cipher.init(true, params);
            // create a temporary buffer to decode into (it'll include padding)
            byte[] plainTemp = new byte[cipher.getOutputSize(encrypted1.length)];
            int offset = cipher.processBytes(encrypted1, 0, encrypted1.length, plainTemp, 0);
            int last = cipher.doFinal(plainTemp, offset);
            byte[] plain = new byte[offset + last];
            System.arraycopy(plainTemp, 0, plain, 0, plain.length);
            return new sun.misc.BASE64Encoder().encode(plain).replaceAll("\r\n", "").replaceAll("\r", "")
                    .replaceAll("\n", "");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
    //生成32位随机码
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    //生成8位uuid
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
}
