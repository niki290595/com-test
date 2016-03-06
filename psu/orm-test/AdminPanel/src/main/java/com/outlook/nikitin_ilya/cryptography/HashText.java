package com.outlook.nikitin_ilya.cryptography;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashText {

    public static String getHash(String text, String salt) {
        String s = text + salt;
        return hash(s);
    }

    private static String hash(String s) {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];

        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(s.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        BigInteger bigInt = new BigInteger(1, digest);
        String md5Hex = bigInt.toString();

        while (md5Hex.length() < 32) {
            md5Hex = "0" + md5Hex;
        }

        return md5Hex;
    }

    /*

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
    }*/
}
