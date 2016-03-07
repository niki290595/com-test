package com.outlook.nikitin_ilya.cryptography;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Ilya on 07.03.2016.
 */
public class SaltGenerator {

    public static String generate() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

}
