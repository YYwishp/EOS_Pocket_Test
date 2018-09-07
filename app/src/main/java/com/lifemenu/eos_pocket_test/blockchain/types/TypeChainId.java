package com.lifemenu.eos_pocket_test.blockchain.types;

import com.lifemenu.eos_pocket_test.blockchain.cypto.digest.Sha256;

/**
 * Created by swapnibble on 2017-09-12.
 */
public class TypeChainId {
    private final Sha256 mId;

    public TypeChainId() {
        mId = Sha256.ZERO_HASH;
    }

    byte [] getSha256FromHexStr(String str){
        int len = str.length();
        byte [] bytes = new byte[32];
        for(int i=0;i<len;i+=2){
            String strIte = str.substring(i, i+2);
            Integer n = Integer.parseInt(strIte, 16) & 0xFF;;
            bytes[i/2] = n.byteValue();
        }
        return bytes;
    }
    public TypeChainId(String str){
        mId = new Sha256(getSha256FromHexStr(str));
    }

    public byte[] getBytes() {
        return mId.getBytes();
    }
}