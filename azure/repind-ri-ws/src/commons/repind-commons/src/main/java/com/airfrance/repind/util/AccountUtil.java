package com.airfrance.repind.util;

public class AccountUtil {

    public static boolean isCidSignature(String signature) {
        boolean isCidSign = false;
        String cidNoEmail = "CID_NO_EMAIL";
        String cid = "CID";

        if (signature != null) {
            if (signature.equalsIgnoreCase(cidNoEmail) || signature.equalsIgnoreCase(cid)) {
                isCidSign = true;
            }
        }

        return isCidSign;
    }
}
