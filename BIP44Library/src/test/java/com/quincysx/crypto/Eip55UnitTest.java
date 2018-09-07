package com.quincysx.crypto;

import android.util.Log;

import com.quincysx.crypto.eip55.EthCheckAddress;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author QuincySx
 * @date 2018/3/2 下午5:29
 */
public class Eip55UnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        String s = EthCheckAddress.toChecksumAddress("0xD1220A0cf47c7B9Be7A2E6BA89F429762e7b9aDb");
        assertEquals(s, "0xD1220A0cf47c7B9Be7A2E6BA89F429762e7b9aDb");

        String s1 = EthCheckAddress.toChecksumAddress("0x5aAeb6053F3E94C9b9A09f33669435E7Ef1BeAed");
        assertEquals(s1, "0x5aAeb6053F3E94C9b9A09f33669435E7Ef1BeAed");

        String s2 = EthCheckAddress.toChecksumAddress("0xfB6916095ca1df60bB79Ce92cE3Ea74c37c5d359");
        assertEquals(s2, "0xfB6916095ca1df60bB79Ce92cE3Ea74c37c5d359");

        String s3 = EthCheckAddress.toChecksumAddress("0xdbF03B407c01E7cD3CBea99509d93f8DDDC8C6FB");
        assertEquals(s3, "0xdbF03B407c01E7cD3CBea99509d93f8DDDC8C6FB");


        boolean check1 = EthCheckAddress.checksumAddress("0xdbF03B407c01E7cD3CBea99509d93f8DDDC8C6FB");
        assertEquals(check1, true);

        boolean check2 = EthCheckAddress.checksumAddress("0xfB6916095ca1df60bB79Ce92cE3Ea74c37c5D359");
        assertEquals(check2, false);

        boolean check3 = EthCheckAddress.checksumAddress("0xfB6916095ca1df60bB79Ce92cE3Ea74c37c5d359");
        assertEquals(check3, true);

        boolean check4 = EthCheckAddress.checksumAddress("0xfb6916095ca1df60bb79ce92ce3ea74c37c5d359");
        assertEquals(check4, true);

        boolean check5 = EthCheckAddress.checksumAddress("fb6916095ca1df60bb79ce92ce3ea74c37c5d359");
        assertEquals(check5, false);

    }
}
