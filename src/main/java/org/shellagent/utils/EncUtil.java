package org.shellagent.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * 加密和解密字符串
 * */
public class EncUtil {
    public static String encode(@NotNull String origin) {
        byte[] enc1 = Base64Utils.encode(origin.getBytes());
        byte[] enc2 = Base64Utils.encode(enc1);
        return new String(enc2);
    }
    public static String decode(@NotNull String enc) {
        String origin = null;
        try {
            String origin1 = new String(Base64Utils.decode(enc.getBytes()), "UTF-8");
            String origin2 = new String(Base64Utils.decode(origin1.getBytes()), "UTF-8");
            origin = origin2;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return origin;
    }
    public static String decodeUserPass(@NotNull String password) {
        String origin = null;
        try {
            origin = new String(Base64Utils.decode(password.getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return origin;
    }
}
