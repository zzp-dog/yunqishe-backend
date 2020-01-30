package com.zx.yunqishe.trunk.other.utils;


import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 加解密工具
 */
public class EncryptUtil {

    /** 字符编码集 */
    public static final String ENCODING = "UTF-8";
    /** AES算法 默认 "AES/EBC/PKCS5Padding"*/
    public static final String ALGORITHM = "AES";
    /** 带偏移向量的AES算法 */
    public static final String CBC_ALGORITHM = "AES/CBC/PKCS7Padding"; // 和AES/EBC/PKCS5Padding效果一样（我也测了一遍）
    /** rsa公钥和密钥对 */
    public static final Map<String, Object> RSA_KEY_MAP = EncryptUtil.genKeyPair();
    /**
     * 获取应用启动后的rsa公钥
     * @return PublicKey
     */
    public static PublicKey getAppPk() {
        return (PublicKey) RSA_KEY_MAP.get("pk");
    }
    /**
     * 获取应用启动后的rsa密钥
     * @return PrivateKey
     */
    public static PrivateKey getAppSk() {
        return (PrivateKey) RSA_KEY_MAP.get("sk");
    }
    static {
        //如果是PKCS7Padding填充方式，则必须加上下面这行
        Security.addProvider(new BouncyCastleProvider());
    }
    /**
     * base64解码，base64字符串变普通字符串
     * @param base64str base64字符串
     * @return 解码后的字符串
     */
    public static String base64str2str(String base64str) throws Exception {
        return new String(Base64.decodeBase64(base64str), ENCODING);
    }

    /**
     * base64编码，普通字符串变base64字符串
     * @param str 普通字符串
     * @return base64字符串
     */
    public static String str2base64str(String str) {
        return Base64.encodeBase64String(str.getBytes());
    }

    /**
     * base64编码，普通字符串的字节数组变为base64字符串
     * @param bytes 字节数组
     * @return 编码后的字符
     */
    public static String byte2base64str(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base64解码，base64字符串字节数组变普通字符串
     * @param bytes base64字符串字节数组
     * @return 普通字符串
     * @throws Exception
     */
    public static String base64byte2str(byte[] bytes) throws Exception {
        return new String(Base64.decodeBase64(bytes),ENCODING);
    }

    public static Map<String, Object> genKeyPair() {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("密钥协商失败，网络错误");
        }
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024,new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 将公钥和私钥保存到Map
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("pk", publicKey);  //pk表示公钥
        map.put("sk",privateKey);  //sk表示私钥
        return map;
    }

    /**
     * RSA加密，将明文变为密文base64字符串
     * @param mes 明文
     * @param key 公钥
     * @return 密文base64字符串
     * @throws Exception
     */
    public static String  RSAEncrypt(String mes,  PublicKey key) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return  Base64.encodeBase64String(cipher.doFinal(mes.getBytes()));
    }

    /**
     * RSA解密，将密文base64字符串变为明文
     * @param cipBase64str 密文base64字符串
     * @param key 密钥
     * @return 明文
     * @throws Exception
     */
    public static String RSADecrypt(String cipBase64str,  PrivateKey key) throws Exception {
        byte[] cipBytes = Base64.decodeBase64(cipBase64str);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(cipBytes), ENCODING);
    }

    /**
     * 带偏移量的AES加密（偏移量和AES密钥相同）,将明文变为密文base64字符串
     * @param mes 明文
     * @param key 密钥str
     * @return 密文base64字符串
     * @throws Exception
     */
    public static String AESEncrypt(String mes, String key) throws Exception{
        Cipher cipher = Cipher.getInstance(CBC_ALGORITHM,"BC");
        byte[] bytes = key.getBytes(ENCODING);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(bytes, ALGORITHM), new IvParameterSpec(bytes));
        return Base64.encodeBase64String(cipher.doFinal(mes.getBytes()));
    }

    /**
     * AES解密，将密文的base64字符串变为明文
     * @param cipBase64str 密文的base64字符串
     * @param key 密钥str
     * @return 明文
     * @throws Exception
     */
    public static String AESDecrypt(String cipBase64str, String key)throws Exception{
        byte[] cipBytes = Base64.decodeBase64(cipBase64str);
        Cipher cipher = Cipher.getInstance(CBC_ALGORITHM, "BC");
        byte[] bytes = key.getBytes(ENCODING);
        IvParameterSpec iv = new IvParameterSpec(bytes);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(bytes, ALGORITHM), iv);
        return new String(cipher.doFinal(cipBytes), ENCODING);
    }

    public static void main(String[] args) throws Exception{
        // 测试字符串编解码
        System.out.println("测试字符串编解码-start");
        String str = "进击的巨人";
        System.out.println("原字符串：");
        System.out.println(str);
        System.out.println("base64编码：");
        String base64str = str2base64str(str);
        System.out.println(base64str);
        System.out.println("base64解码：");
        System.out.println(base64str2str(base64str));
        System.out.println("测试字符串编解码-end");
        // 测试字节编解码
        System.out.println("测试字符串字节编解码-start");
        String str2 = "罪恶王冠";
        // String str2 = str;
        byte[] bytes = str2.getBytes();
        System.out.println("base64编码：");
        String str3 = byte2base64str(bytes);
        System.out.println(str3);
        System.out.println("base64解码：");
        System.out.println(base64byte2str(str3.getBytes()));
        System.out.println("测试字符串字节编解码-start");
        System.out.println("AES加解密");
        String str4 = "{\"account\":\"\",\"passowrd\":\"\"}";
        System.out.println("原来的字符串：" + str4);
        String key = "aa5PHBTXQ82lZPU8";
        String encrpyt = AESEncrypt(str4, key);
        System.out.println("AES加密后：" + encrpyt);
        System.out.println("AES解密后：" + AESDecrypt(encrpyt, key));

    }
}
