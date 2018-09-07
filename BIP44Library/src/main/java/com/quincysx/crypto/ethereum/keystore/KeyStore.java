package com.quincysx.crypto.ethereum.keystore;

import com.quincysx.crypto.bip32.ValidationException;
import com.quincysx.crypto.ethereum.EthECKeyPair;
import com.quincysx.crypto.utils.HexUtils;
import com.quincysx.crypto.utils.KECCAK256;

import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.spongycastle.crypto.generators.SCrypt;
import org.spongycastle.crypto.params.KeyParameter;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class KeyStore {

    private static final int N_LIGHT = 1 << 12;
    private static final int P_LIGHT = 6;

    private static final int N_STANDARD = 1 << 18;
    private static final int P_STANDARD = 1;

    private static final int R = 8;
    private static final int DKLEN = 32;

    private static final int CURRENT_VERSION = 3;

    private static final String CIPHER = "aes-128-ctr";
    static final String AES_128_CTR = "pbkdf2";
    static final String SCRYPT = "scrypt";

    private static SecureRandom secureRandom = new SecureRandom();

    public static KeyStoreFile createStandard(String password, EthECKeyPair ecKeyPair)
            throws CipherException {
        return create(password, ecKeyPair, N_STANDARD, P_STANDARD);
    }

    public static KeyStoreFile createLight(String password, EthECKeyPair ecKeyPair)
            throws CipherException {
        return create(password, ecKeyPair, N_LIGHT, P_LIGHT);
    }

    public static KeyStoreFile create(String password, EthECKeyPair ecKeyPair, int n, int p)
            throws CipherException {

        byte[] salt = generateRandomBytes(32);

        byte[] derivedKey = generateDerivedScryptKey(
                password.getBytes(Charset.forName("UTF-8")), salt, n, R, p, DKLEN);

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
        byte[] iv = generateRandomBytes(16);

//        byte[] privateKeyBytes =
//                Numeric.toBytesPadded(ecKeyPair.getPrivateKey(), Keys.PRIVATE_KEY_SIZE);
        byte[] privateKeyBytes = ecKeyPair.getRawPrivateKey();

        byte[] cipherText = performCipherOperation(
                Cipher.ENCRYPT_MODE, iv, encryptKey, privateKeyBytes);

        byte[] mac = generateMac(derivedKey, cipherText);

        return createWalletFile(ecKeyPair, cipherText, iv, salt, mac, n, p);
    }

    private static byte[] performCipherOperation(
            int mode, byte[] iv, byte[] encryptKey, byte[] text) throws CipherException {

        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");

            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptKey, "AES");
            cipher.init(mode, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(text);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidAlgorithmParameterException | InvalidKeyException
                | BadPaddingException | IllegalBlockSizeException e) {
            throw new CipherException("Error performing cipher operation", e);
        }
    }

    private static byte[] generateMac(byte[] derivedKey, byte[] cipherText) {
        byte[] result = new byte[16 + cipherText.length];

        System.arraycopy(derivedKey, 16, result, 0, 16);
        System.arraycopy(cipherText, 0, result, 16, cipherText.length);

        return KECCAK256.keccak256(result);
//        return Hash.sha3(result);
    }

    private static byte[] generateDerivedScryptKey(
            byte[] password, byte[] salt, int n, int r, int p, int dkLen) {
        return SCrypt.generate(password, salt, n, r, p, dkLen);
    }

    static byte[] generateRandomBytes(int size) {
        byte[] bytes = new byte[size];
        secureRandom.nextBytes(bytes);
        return bytes;
    }

    private static KeyStoreFile createWalletFile(
            EthECKeyPair ecKeyPair, byte[] cipherText, byte[] iv, byte[] salt, byte[] mac,
            int n, int p) {

        KeyStoreFile walletFile = new KeyStoreFile();
        walletFile.setAddress(ecKeyPair.getAddress());

        KeyStoreFile.Crypto crypto = new KeyStoreFile.Crypto();
        crypto.setCipher(CIPHER);
//        crypto.setCiphertext(Numeric.toHexStringNoPrefix(cipherText));
        crypto.setCiphertext(HexUtils.toHex(cipherText));
        walletFile.setCrypto(crypto);

        KeyStoreFile.CipherParams cipherParams = new KeyStoreFile.CipherParams();
//        cipherParams.setIv(Numeric.toHexStringNoPrefix(iv));
        cipherParams.setIv(HexUtils.toHex(iv));
        crypto.setCipherparams(cipherParams);

        crypto.setKdf(SCRYPT);
        KeyStoreFile.ScryptKdfParams kdfParams = new KeyStoreFile.ScryptKdfParams();
        kdfParams.setDklen(DKLEN);
        kdfParams.setN(n);
        kdfParams.setP(p);
        kdfParams.setR(R);
//        kdfParams.setSalt(Numeric.toHexStringNoPrefix(salt));
        kdfParams.setSalt(HexUtils.toHex(salt));
        crypto.setKdfparams(kdfParams);

//        crypto.setMac(Numeric.toHexStringNoPrefix(mac));
        crypto.setMac(HexUtils.toHex(mac));
        walletFile.setCrypto(crypto);
        walletFile.setId(UUID.randomUUID().toString());
        walletFile.setVersion(CURRENT_VERSION);

        return walletFile;
    }

    static void validate(KeyStoreFile walletFile) throws CipherException {
        KeyStoreFile.Crypto crypto = walletFile.getCrypto();

        if (walletFile.getVersion() != CURRENT_VERSION) {
            throw new CipherException("Wallet version is not supported");
        }

        if (!crypto.getCipher().equals(CIPHER)) {
            throw new CipherException("Wallet cipher is not supported");
        }

        if (!crypto.getKdf().equals(AES_128_CTR) && !crypto.getKdf().equals(SCRYPT)) {
            throw new CipherException("KDF type is not supported");
        }
    }

    public static EthECKeyPair decrypt(String password, KeyStoreFile walletFile)
            throws CipherException, ValidationException {

        validate(walletFile);

        KeyStoreFile.Crypto crypto = walletFile.getCrypto();

        byte[] mac = HexUtils.fromHex(crypto.getMac());
        byte[] iv = HexUtils.fromHex(crypto.getCipherparams().getIv());
        byte[] cipherText = HexUtils.fromHex(crypto.getCiphertext());

        byte[] derivedKey;

        KeyStoreFile.KdfParams kdfParams = crypto.getKdfparams();
        if (kdfParams instanceof KeyStoreFile.ScryptKdfParams) {
            KeyStoreFile.ScryptKdfParams scryptKdfParams =
                    (KeyStoreFile.ScryptKdfParams) crypto.getKdfparams();
            int dklen = scryptKdfParams.getDklen();
            int n = scryptKdfParams.getN();
            int p = scryptKdfParams.getP();
            int r = scryptKdfParams.getR();
            byte[] salt = HexUtils.fromHex(scryptKdfParams.getSalt());
//            derivedKey = generateDerivedScryptKey(password.getBytes(Charset.forName("UTF-8")),
//                    salt, n, r, p, dklen);
            derivedKey = com.lambdaworks.crypto.SCrypt.scryptN(password.getBytes(Charset.forName("UTF-8")), salt, n, r, p, dklen);
        } else if (kdfParams instanceof KeyStoreFile.Aes128CtrKdfParams) {
            KeyStoreFile.Aes128CtrKdfParams aes128CtrKdfParams =
                    (KeyStoreFile.Aes128CtrKdfParams) crypto.getKdfparams();
            int c = aes128CtrKdfParams.getC();
            String prf = aes128CtrKdfParams.getPrf();
            byte[] salt = HexUtils.fromHex(aes128CtrKdfParams.getSalt());

            derivedKey = generateAes128CtrDerivedKey(password.getBytes(Charset.forName("UTF-8")),
                    salt, c, prf);
        } else {
            throw new CipherException("Unable to deserialize params: " + crypto.getKdf());
        }

        byte[] derivedMac = generateMac(derivedKey, cipherText);

        if (!Arrays.equals(derivedMac, mac)) {
            throw new CipherException("Invalid password provided");
        }

        byte[] encryptKey = Arrays.copyOfRange(derivedKey, 0, 16);
        byte[] privateKey = performCipherOperation(Cipher.DECRYPT_MODE, iv, encryptKey, cipherText);
        return new EthECKeyPair(privateKey);
    }

    private static byte[] generateAes128CtrDerivedKey(
            byte[] password, byte[] salt, int c, String prf) throws CipherException {

        if (!prf.equals("hmac-sha256")) {
            throw new CipherException("Unsupported prf:" + prf);
        }

        // Java 8 supports this, but you have to convert the password to a character array, see
        // http://stackoverflow.com/a/27928435/3211687
        PKCS5S2ParametersGenerator gen = new PKCS5S2ParametersGenerator(new SHA256Digest());
        gen.init(password, salt, c);
        return ((KeyParameter) gen.generateDerivedParameters(256)).getKey();
    }
}
