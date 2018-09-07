package com.quincysx.crypto;

import com.quincysx.crypto.utils.Base64;
import com.quincysx.crypto.utils.HexUtils;
import com.quincysx.crypto.utils.KECCAK256;
import com.quincysx.crypto.utils.RIPEMD160;
import com.quincysx.crypto.utils.SHA256;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HashUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testKeccak256() {
        byte[] bytes = KECCAK256.keccak256("12".getBytes());
        assertEquals(HexUtils.toHex(bytes),
                "7f8b6b088b6d74c2852fc86c796dca07b44eed6fb3daf5e6b59f7c364db14528");
    }

    @Test
    public void testSha256() {
        byte[] bytes = SHA256.sha256("12".getBytes());
        assertEquals(HexUtils.toHex(bytes),
                "6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918");

        byte[] bytes1 = SHA256.sha256("6b51d431df5d7f141cbececcf79edf3dd861c3b4069f0b11661a3eefacbba918".getBytes());
        assertEquals(HexUtils.toHex(bytes1),
                "ab2e30bf75636a9c97e05099d9ccc9b623c17a44f629e7ce7206dba683028db1");
    }

    @Test
    public void testDoubleSha256() {
        byte[] bytes = SHA256.doubleSha256("12".getBytes());
        assertEquals(HexUtils.toHex(bytes),
                "ab2e30bf75636a9c97e05099d9ccc9b623c17a44f629e7ce7206dba683028db1");
    }

    @Test
    public void testRipemd160() {
        byte[] bytes = RIPEMD160.ripemd160("12".getBytes());
        assertEquals(HexUtils.toHex(bytes),
                "58bd2c615ce3fbfa69b1e0e309b610e40cb4c83f");
    }

    @Test
    public void testHash160() {
        byte[] bytes = RIPEMD160.hash160("12".getBytes());
        assertEquals(HexUtils.toHex(bytes),
                "02668db46b805753c3cb0aaa3129af37d026e309");
    }

    @Test
    public void testBase64Encode() {
        String base64 = Base64.encode("58bd2c615ce3fbfa69b1e0e309b610e40cb4c83f".getBytes());
        assertEquals(base64,
                "NThiZDJjNjE1Y2UzZmJmYTY5YjFlMGUzMDliNjEwZTQwY2I0YzgzZg==");
    }

    @Test
    public void testBase64Decode() {
        String base64 = Base64.decode("NThiZDJjNjE1Y2UzZmJmYTY5YjFlMGUzMDliNjEwZTQwY2I0YzgzZg==");
        assertEquals(base64,
                "58bd2c615ce3fbfa69b1e0e309b610e40cb4c83f");
    }

}