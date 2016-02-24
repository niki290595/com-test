package com.outlook.nikitin_ilya.cryptography;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

public class HashText {

    public static String sha256(String pass, String salt) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(salt.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(pass.getBytes()));
            return hash;
        }
        catch (Exception e){
            System.out.println("Error");
        }
        return null;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(sha256("123", "qvf99jc958hmi4s471qcjuiq6a"));
    }
}
