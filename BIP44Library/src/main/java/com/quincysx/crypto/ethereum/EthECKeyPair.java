package com.quincysx.crypto.ethereum;

import com.quincysx.crypto.ECKeyPair;
import com.quincysx.crypto.Key;
import com.quincysx.crypto.bip32.ValidationException;
import com.quincysx.crypto.eip55.EthCheckAddress;
import com.quincysx.crypto.utils.HexUtils;
import com.quincysx.crypto.utils.KECCAK256;

import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.HMacDSAKCalculator;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author QuincySx
 * @date 2018/3/7 下午5:51
 */
public class EthECKeyPair extends ECKeyPair {
    public static EthECKeyPair parse(Key keyPair) {
        return new EthECKeyPair(keyPair);
    }

    public EthECKeyPair(byte[] p) throws ValidationException {
        super(p, true);
    }

    public EthECKeyPair(BigInteger priv) {
        super(priv, true);
    }

    public EthECKeyPair(Key keyPair) {
        super(keyPair);
    }

    @Override
    public byte[] getRawPrivateKey() {
        return super.getRawPrivateKey();
    }

    @Override
    public String getPrivateKey() {
        return HexUtils.toHex(getRawPrivateKey());
    }

    @Override
    public byte[] getRawPublicKey() {
        byte[] publicKey = new byte[pub.length - 1];
        System.arraycopy(pub, 1, publicKey, 0, publicKey.length);
        return publicKey;
    }

    @Override
    public String getPublicKey() {
        return HexUtils.toHex(getRawPublicKey());
    }

    @Override
    public byte[] getRawAddress() {
        byte[] byteAddress = KECCAK256.keccak256(getRawPublicKey());
        byte[] address = new byte[20];
        System.arraycopy(byteAddress, 12, address, 0, address.length);
        return address;
    }

    @Override
    public String getAddress() {
        return EthCheckAddress.toChecksumAddress(HexUtils.toHex(getRawAddress()));
    }

    public ECDSASignature doSign(byte[] input) {
        if (input.length != 32) {
            throw new IllegalArgumentException("Expected 32 byte input to ECDSA signature, not "
                    + input.length);
        }
        // No decryption of private key required.
        ECDSASigner signer = new ECDSASigner(new HMacDSAKCalculator(new SHA256Digest()));
        ECPrivateKeyParameters privKeyParams = new ECPrivateKeyParameters(priv, domain);
        signer.init(true, privKeyParams);
        BigInteger[] components = signer.generateSignature(input);
        return new ECDSASignature(components[0], components[1]).toCanonicalised();
    }

    public ECDSASignature sign(byte[] messageHash) {
        ECDSASignature sig = doSign(messageHash);
        // Now we have to work backwards to figure out the recId needed to recover the signature.
        int recId = -1;
        for (int i = 0; i < 4; i++) {
            byte[] k = recoverPubBytesFromSignature(i, sig, messageHash);
            if (k != null && java.util.Arrays.equals(k, pub)) {
                recId = i;
                break;
            }
        }
        if (recId == -1)
            throw new RuntimeException("Could not construct a recoverable key. This should never " +
                    "happen.");
        sig.v = (byte) (recId + 27);
        return sig;
    }

    public static byte[] recoverPubBytesFromSignature(int recId, ECDSASignature sig, byte[]
            messageHash) {
        check(recId >= 0, "recId must be positive");
        check(sig.r.signum() >= 0, "r must be positive");
        check(sig.s.signum() >= 0, "s must be positive");
        check(messageHash != null, "messageHash must not be null");
        // 1.0 For j from 0 to h   (h == recId here and the loop is outside this function)
        //   1.1 Let x = r + jn
        BigInteger n = CURVE.getN();  // Curve order.
        BigInteger i = BigInteger.valueOf((long) recId / 2);
        BigInteger x = sig.r.add(i.multiply(n));
        //   1.2. Convert the integer x to an octet string X of length mlen using the conversion
        // routine
        //        specified in Section 2.3.7, where mlen = ⌈(log2 p)/8⌉ or mlen = ⌈m/8⌉.
        //   1.3. Convert the octet string (16 set binary digits)||X to an elliptic curve point R
        // using the
        //        conversion routine specified in Section 2.3.4. If this conversion routine
        // outputs “invalid”, then
        //        do another iteration of Step 1.
        //
        // More concisely, what these points mean is to use X as a compressed public key.
        ECCurve.Fp curve = (ECCurve.Fp) CURVE.getCurve();
        BigInteger prime = curve.getQ();  // Bouncy Castle is not consistent about the letter it
        // uses for the prime.
        if (x.compareTo(prime) >= 0) {
            // Cannot have point co-ordinates larger than this as everything takes place modulo Q.
            return null;
        }
        // Compressed keys require you to know an extra bit of data about the y-coord as there
        // are two possibilities.
        // So it's encoded in the recId.
        ECPoint R = decompressKey(x, (recId & 1) == 1);
        //   1.4. If nR != point at infinity, then do another iteration of Step 1 (callers
        // responsibility).
        if (!R.multiply(n).isInfinity())
            return null;
        //   1.5. Compute e from M using Steps 2 and 3 of ECDSA signature verification.
        BigInteger e = new BigInteger(1, messageHash);
        //   1.6. For k from 1 to 2 do the following.   (loop is outside this function via
        // iterating recId)
        //   1.6.1. Compute a candidate public key as:
        //               Q = mi(r) * (sR - eG)
        //
        // Where mi(x) is the modular multiplicative inverse. We transform this into the following:
        //               Q = (mi(r) * s ** R) + (mi(r) * -e ** G)
        // Where -e is the modular additive inverse of e, that is z such that z + e = 0 (mod n).
        // In the above equation
        // ** is point multiplication and + is point addition (the EC group operator).
        //
        // We can find the additive inverse by subtracting e from zero then taking the mod. For
        // example the additive
        // inverse of 3 modulo 11 is 8 because 3 + 8 mod 11 = 0, and -3 mod 11 = 8.
        BigInteger eInv = BigInteger.ZERO.subtract(e).mod(n);
        BigInteger rInv = sig.r.modInverse(n);
        BigInteger srInv = rInv.multiply(sig.s).mod(n);
        BigInteger eInvrInv = rInv.multiply(eInv).mod(n);
        ECPoint.Fp q = (ECPoint.Fp) ECAlgorithms.sumOfTwoMultiplies(CURVE.getG(), eInvrInv, R,
                srInv);
        return q.getEncoded(/* compressed */ false);
    }

    private static ECPoint decompressKey(BigInteger xBN, boolean yBit) {
        X9IntegerConverter x9 = new X9IntegerConverter();
        byte[] compEnc = x9.integerToBytes(xBN, 1 + x9.getByteLength(CURVE.getCurve()));
        compEnc[0] = (byte) (yBit ? 0x03 : 0x02);
        return CURVE.getCurve().decodePoint(compEnc);
    }

    private static void check(boolean test, String message) {
        if (!test) throw new IllegalArgumentException(message);
    }
}
