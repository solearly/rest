package rest.helpers;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class Crypto {
    private final String key = "12345678901234567890123456789012";
    private final int wrapLength = 16;

    private String randomString(int length) {
        String value = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = (int)Math.floor(value.length() * Math.random());
            sb.append(value.charAt(idx));
        }
        return sb.toString();
    }

    private String wrapText(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append(randomString(wrapLength))
                .append(text)
                .append(randomString(wrapLength));
        return sb.toString();
    }

    private String unwrapText(String text) {
        return text.substring(wrapLength, text.length() - wrapLength);
    }

    private Cipher getCipher(int mode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(mode, skeySpec);
        return cipher;
    }

    public String encrypt(String text) {
        byte[] encrypted;
        try {
            Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);
            encrypted = Base64.getEncoder().encode(cipher.doFinal(wrapText(text).getBytes()));
        }
        catch (Exception e) {
            encrypted = new byte[] {};
        }
        return new String(encrypted);
    }

    public String decrypt(String text) {
        String decryptedValue;
        try {
            Cipher cipher = getCipher(Cipher.DECRYPT_MODE);
            byte[] decodedValue = Base64.getDecoder().decode(text);
            byte[] decValue = cipher.doFinal(decodedValue);
            decryptedValue = unwrapText(new String(decValue));
        }
        catch (Exception e) {
            decryptedValue = null;
        }
        return decryptedValue;
    }
}
