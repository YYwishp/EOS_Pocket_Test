package com.quincysx.crypto;

import com.quincysx.crypto.utils.Base64;
import com.quincysx.crypto.utils.HexUtils;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

        BigInteger bigInteger = new BigInteger("2222");
        String s = Base64.encode(bigInteger);

        BigInteger bigInteger1 = new BigInteger(1,HexUtils.fromHex(s));

        assertEquals(bigInteger, bigInteger1);

    }
}