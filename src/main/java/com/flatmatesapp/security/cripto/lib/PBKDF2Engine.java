/**
 * 
 */
package com.flatmatesapp.security.cripto.lib;

import java.io.UnsupportedEncodingException;

/**
 * Copied from Matthias Gartner's PKCS#5 implementation - see
 * http://rtner.de/software/PBKDF2.html
 * 
 * <p>
 * Request for Comments: 2898 PKCS #5: Password-Based Cryptography Specification
 * <p>
 * Version 2.0
 * 
 * <p>
 * PBKDF2 (P, S, c, dkLen)
 * 
 * <p>
 * Options:
 * <ul>
 * <li>PRF underlying pseudorandom function (hLen denotes the length in octets
 * of the pseudorandom function output). PRF is pluggable.</li>
 * </ul>
 * 
 * <p>
 * Input:
 * <ul>
 * <li>P password, an octet string</li>
 * <li>S salt, an octet string</li>
 * <li>c iteration count, a positive integer</li>
 * <li>dkLen intended length in octets of the derived key, a positive integer,
 * at most (2^32 - 1) * hLen</li>
 * </ul>
 * 
 * <p>
 * Output:
 * <ul>
 * <li>DK derived key, a dkLen-octet string</li>
 * </ul>
 * 
 * <hr />
 * <p>
 * A free Java implementation of Password Based Key Derivation Function 2 as
 * defined by RFC 2898. Copyright (c) 2007 Matthias G&auml;rtner
 * </p>
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * </p>
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * </p>
 * <p>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * </p>
 * <p>
 * For Details, see <a
 * href="http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html"
 * >http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html</a>.
 * </p>
 * 
 * @see <a href="http://tools.ietf.org/html/rfc2898">RFC 2898</a>
 * @author Matthias G&auml;rtner
 * @version 1.0
 */
public class PBKDF2Engine implements PBKDF2 {
    
    protected PBKDF2Parameters parameters;

    protected PRF              prf;

    /**
     * Constructor for PBKDF2 implementation object. PBKDF2 parameters must be
     * passed later.
     */
    public PBKDF2Engine() {
        this.parameters = null;
        this.prf = null;
    }

    /**
     * Constructor for PBKDF2 implementation object. PBKDF2 parameters are
     * passed so that this implementation knows iteration count, method to use
     * and String encoding.
     * 
     * @param parameters Data holder for iteration count, method to use et
     * cetera.
     */
    public PBKDF2Engine(PBKDF2Parameters parameters) {
        this.parameters = parameters;
        this.prf = null;
    }

    /**
     * Constructor for PBKDF2 implementation object. PBKDF2 parameters are
     * passed so that this implementation knows iteration count, method to use
     * and String encoding.
     * 
     * @param parameters Data holder for iteration count, method to use et
     * cetera.
     * @param prf Supply customer Pseudo Random Function.
     */
    public PBKDF2Engine(PBKDF2Parameters parameters, PRF prf) {
        this.parameters = parameters;
        this.prf = prf;
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#deriveKey(java.lang.String)
     */
    @Override
    public byte[] deriveKey(String inputPassword) {
        return deriveKey(inputPassword, 0);
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#deriveKey(java.lang.String, int)
     */
    @Override
    public byte[] deriveKey(String inputPassword, int dkLen) {
        byte[] r = null;
        byte[] p  = null;
        String charset = this.parameters.getHashCharset();
        String processedInputPassword = inputPassword;
        if (processedInputPassword == null) {
            processedInputPassword = "";
        }
        try {
            if (charset == null) {
                p = processedInputPassword.getBytes();
            } else {
                p = processedInputPassword.getBytes(charset);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        assertPRF(p);
        int processedDkLen = dkLen;
        if (processedDkLen == 0) {
            processedDkLen = this.prf.getHLen();
        }
        r = PBKDF2(this.prf, this.parameters.getSalt(), this.parameters.getIterationCount(), processedDkLen);
        return r;
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#verifyKey(java.lang.String)
     */
    @Override
    public boolean verifyKey(String inputPassword) {
        byte[] referenceKey = getParameters().getDerivedKey();
        if (referenceKey == null || referenceKey.length == 0) {
            return false;
        }
        byte[] inputKey = deriveKey(inputPassword, referenceKey.length);

        if (inputKey == null || inputKey.length != referenceKey.length) {
            return false;
        }
        for (int i = 0; i < inputKey.length; i++) {
            if (inputKey[i] != referenceKey[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Factory method. Default implementation is (H)MAC-based. To be overridden
     * in derived classes.
     * 
     * @param p User-supplied candidate password as array of bytes.
     */
    protected void assertPRF(byte[] p) {
        if (prf == null) {
            prf = new MacBasedPRF(parameters.getHashAlgorithm());
        }
        prf.init(p);
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#getPseudoRandomFunction()
     */
    @Override
    public PRF getPseudoRandomFunction() {
        return prf;
    }

    /**
     * Core Password Based Key Derivation Function 2.
     * 
     * @see <a href="http://tools.ietf.org/html/rfc2898">RFC 2898 5.2</a>
     * @param prf Pseudo Random Function (i.e. HmacSHA1)
     * @param s Salt as array of bytes. <code>null</code> means no salt.
     * @param c Iteration count (see RFC 2898 4.2)
     * @param dkLen desired length of derived key.
     * @return internal byte array
     */
    protected byte[] PBKDF2(PRF prf, byte[] s, int c, int dkLen) {
        byte[] processedSalt = s;
        if (s == null) {
            processedSalt = new byte[0];
        }
        int hLen = prf.getHLen();
        int l = ceil(dkLen, hLen);
        int r = dkLen - (l - 1) * hLen;
        byte[] t = new byte[l * hLen];
        int tiOffset = 0;
        for (int i = 1; i <= l; i++) {
            _F(t, tiOffset, prf, processedSalt, c, i);
            tiOffset += hLen;
        }
        if (r < hLen) {
            // Incomplete last block
            byte dk[] = new byte[dkLen];
            System.arraycopy(t, 0, dk, 0, dkLen);
            return dk;
        }
        return t;
    }

    /**
     * Integer division with ceiling function.
     * 
     * @see <a href="http://tools.ietf.org/html/rfc2898">RFC 2898 5.2 Step
     * 2.</a>
     * @param a
     * @param b
     * @return ceil(a/b)
     */
    protected int ceil(int a, int b) {
        int m = 0;
        if (a % b > 0) {
            m = 1;
        }
        return a / b + m;
    }

    /**
     * Function F.
     * 
     * @see <a href="http://tools.ietf.org/html/rfc2898">RFC 2898 5.2 Step
     * 3.</a>
     * @param dest Destination byte buffer
     * @param offset Offset into destination byte buffer
     * @param prf Pseudo Random Function
     * @param s Salt as array of bytes
     * @param c Iteration count
     * @param blockIndex
     */
    protected void _F(byte[] dest, int offset, PRF prf, byte[] s, int c, int blockIndex) {
        int hLen = prf.getHLen();
        byte uR[] = new byte[hLen];

        // u0 = s || intEnc (i);
        byte uI[] = new byte[s.length + 4];
        System.arraycopy(s, 0, uI, 0, s.length);
        intEnc(uI, s.length, blockIndex);

        for (int i = 0; i < c; i++) {
            uI = prf.doFinal(uI);
            xor(uR, uI);
        }
        System.arraycopy(uR, 0, dest, offset, hLen);
    }

    /**
     * Block-Xor. Xor source bytes into destination byte buffer. Destination
     * buffer must be same length or less than source buffer.
     * 
     * @param dest
     * @param src
     */
    protected void xor(byte[] dest, byte[] src) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] ^= src[i];
        }
    }

    /**
     * Four-octet encoding of the integer i, most significant octet first.
     * 
     * @see <a href="http://tools.ietf.org/html/rfc2898">RFC 2898 5.2 Step
     * 3.</a>
     * @param dest
     * @param offset
     * @param i
     */
    protected void intEnc(byte[] dest, int offset, int i) {
        dest[offset + 0] = (byte) (i / (256 * 256 * 256));
        dest[offset + 1] = (byte) (i / (256 * 256));
        dest[offset + 2] = (byte) (i / (256));
        dest[offset + 3] = (byte) (i);
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#getParameters()
     */
    @Override
    public PBKDF2Parameters getParameters() {
        return parameters;
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#setParameters(fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2Parameters)
     */
    @Override
    public void setParameters(PBKDF2Parameters parameters) {
        this.parameters = parameters;
    }

    /* 
     * (non-Javadoc)
     * @see fr.isiom.bpms.prestataire.kernel.crypto.lib.PBKDF2#setPseudoRandomFunction(fr.isiom.bpms.prestataire.kernel.crypto.lib.PRF)
     */
    @Override
    public void setPseudoRandomFunction(PRF prf) {
        this.prf = prf;
    }
}
