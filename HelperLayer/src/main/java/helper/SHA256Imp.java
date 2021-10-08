package helper;


import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


@Component
public
class SHA256Imp {
    public static String getSHA256(String messageToHash) {
        try {
            byte[] shaValue = getSHA(messageToHash);
            if(shaValue != null)
                return toHexString(shaValue);
            return null;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("HASHING HATASI");
        }
    }

    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        if(input != null) {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input.getBytes(StandardCharsets.UTF_8));
        }
        return null;
    }

    private static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash);

        StringBuilder hexString = new StringBuilder(number.toString(16));

        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }
        return hexString.toString();
    }

}