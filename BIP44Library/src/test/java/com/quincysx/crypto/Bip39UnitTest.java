package com.quincysx.crypto;

import com.quincysx.crypto.bip39.MnemonicGenerator;
import com.quincysx.crypto.bip39.MnemonicValidator;
import com.quincysx.crypto.bip39.RandomSeed;
import com.quincysx.crypto.bip39.SeedCalculator;
import com.quincysx.crypto.bip39.WordCount;
import com.quincysx.crypto.bip39.validation.InvalidChecksumException;
import com.quincysx.crypto.bip39.validation.InvalidWordCountException;
import com.quincysx.crypto.bip39.validation.UnexpectedWhiteSpaceException;
import com.quincysx.crypto.bip39.validation.WordNotFoundException;
import com.quincysx.crypto.bip39.wordlists.English;
import com.quincysx.crypto.utils.HexUtils;

import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class Bip39UnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mnemonic() {
        byte[] random = RandomSeed.random(WordCount.TWELVE);
        List<String> mnemonic = new MnemonicGenerator(English.INSTANCE).createMnemonic(random);
        assertNotNull(mnemonic);
    }

    @Test
    public void mnemonicResetByThreeList() {
        List<String> mnemonicWordsInAList = new ArrayList<>();
        mnemonicWordsInAList.add("sponsor");
        mnemonicWordsInAList.add("thrive");
        mnemonicWordsInAList.add("twin");

        byte[] seed = new SeedCalculator()
                .withWordsFromWordList(English.INSTANCE)
                .calculateSeed(mnemonicWordsInAList, "");

        assertEquals(HexUtils.toHex(seed), "411b65ae261e383c5e3846e8290b693ae8a6c86aa55f87e946f940945f5e8476e722161351485c07fb6fe71229899d2565f114d4c84cf0dec714ee9dfb8f9bde");
    }

    @Test
    public void mnemonicResetByList() {
        List<String> mnemonicWordsInAList = new ArrayList<>();
        mnemonicWordsInAList.add("cupboard");
        mnemonicWordsInAList.add("shed");
        mnemonicWordsInAList.add("accident");
        mnemonicWordsInAList.add("simple");
        mnemonicWordsInAList.add("marble");
        mnemonicWordsInAList.add("drive");
        mnemonicWordsInAList.add("put");
        mnemonicWordsInAList.add("crew");
        mnemonicWordsInAList.add("marine");
        mnemonicWordsInAList.add("mistake");
        mnemonicWordsInAList.add("shop");
        mnemonicWordsInAList.add("chimney");
        mnemonicWordsInAList.add("plate");
        mnemonicWordsInAList.add("throw");
        mnemonicWordsInAList.add("cable");

        byte[] seed = new SeedCalculator()
                .withWordsFromWordList(English.INSTANCE)
                .calculateSeed(mnemonicWordsInAList, "");

        byte[] seed1 = new SeedCalculator()
                .calculateSeed(mnemonicWordsInAList, "");

        assertEquals(HexUtils.toHex(seed1), "165b063a8f7a58e3650534512f53ffeb2cdab1b73604ce631f5e340aa3ff266cb8811bd671ff6268d10bc64200ea671c94e35f413d130d3d9e7ee86b10021c54");
    }
}