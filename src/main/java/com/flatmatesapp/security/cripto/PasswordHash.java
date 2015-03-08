package com.flatmatesapp.security.cripto;

import com.flatmatesapp.security.cripto.lib.BinTools;
import com.flatmatesapp.security.cripto.lib.PBKDF2;
import com.flatmatesapp.security.cripto.lib.PBKDF2Engine;
import com.flatmatesapp.security.cripto.lib.PBKDF2Parameters;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility for hashing passwords, by following the application protocol.
 * 
 * @version 1.6.0
 * @since 1.6.0
 * @author ostanciu
 * 
 */
public final class PasswordHash {

    private static final Logger LOG                 = LoggerFactory.getLogger(PasswordHash.class);

    // prime
    private static final int    ENCODING_ITERATIONS = 10009;

    private static final String HASH_ALGORITHM      = "HmacSHA1";
    private static final String CHARSET             = "ISO-8859-1";

    /**
     * Private constructor
     */
    private PasswordHash() {
        // do not instantiate
    }

    /**
     * @param password
     * @param salt
     * @return
     */
    public static String getPasswordHash(String password, String salt) {
        String passwordKey = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(password) && StringUtils.isNotBlank(salt)) {
            try {
                passwordKey = createPasswordKey(password.toCharArray(), salt.getBytes(CHARSET), ENCODING_ITERATIONS);
            } catch (GeneralSecurityException | UnsupportedEncodingException e) {
                LOG.error("getPasswordHash: ", e);
            }
        }

        return passwordKey;
    }

    /**
     * @param password
     * @param salt
     * @param iterations
     * @return
     * @throws GeneralSecurityException
     */
    private static String createPasswordKey(char[] password, byte[] salt, int iterations)
            throws GeneralSecurityException {

        PBKDF2Parameters params = new PBKDF2Parameters(HASH_ALGORITHM, CHARSET, salt, iterations);
        PBKDF2 pbkdf2 = new PBKDF2Engine(params);
        return BinTools.bin2hex(pbkdf2.deriveKey(new String(password)));
    }
}
