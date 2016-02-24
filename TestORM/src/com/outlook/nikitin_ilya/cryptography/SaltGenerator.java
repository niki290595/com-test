package com.outlook.nikitin_ilya.cryptography;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Ilya on 18.02.2016.
 */
public class SaltGenerator {

    public static String generate() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) System.out.println(generate());
    }
}
