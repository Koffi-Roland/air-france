package com.afklm.repind.msv.doctor.role.service.encoder.role;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class EncoderUtils {

    //Generate a random number based on gin for contract number using salt
    public static String generateRandomWithGinEncoder(String gin) throws NoSuchAlgorithmException {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[20];
        random.nextBytes(salt);
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] hashedGin = md.digest(gin.getBytes(StandardCharsets.UTF_8));
        String contractNumber = Base64.getEncoder().encodeToString(hashedGin);

        return contractNumber.replaceAll("[^a-zA-Z0-9]", "");
    }


}
