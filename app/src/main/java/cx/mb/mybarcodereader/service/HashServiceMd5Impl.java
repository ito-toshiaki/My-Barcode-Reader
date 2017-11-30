package cx.mb.mybarcodereader.service;

import timber.log.Timber;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Calculate MD5 hash string.
 */
public class HashServiceMd5Impl implements HashService {

    @Override
    public String calculate(String source) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(source.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            Timber.e(e);
            throw new RuntimeException(e);
        }
    }
}
