package com.lifemenu.eos_pocket_test.blockchain.util;

import com.lifemenu.eos_pocket_test.blockchain.chain.PackedTransaction;
import com.lifemenu.eos_pocket_test.blockchain.chain.SignedTransaction;

/**
 * Created by swapnibble on 2017-09-12.
 */

public class EstimateRsc {

    private static final long DEFAULT_EXTRA_KCPU = 1000;

    private long tx_cf_cpu_usage;
    private long tx_net_usage;

//    public long estimateTrxCtxFreeKiloCpuUsage(final SignedTransaction txn, long extraKcpu){
//        // TODO add "tx_cf_cpu_usage" to preference!
//        if (tx_cf_cpu_usage != 0) {
//            return (tx_cf_cpu_usage + 1023 ) / 1024 ;
//        }
//
//        long estimated_per_action_usage = 1000 * 10;
//        return extraKcpu + ( txn.getContextFreeActionCount() * estimated_per_action_usage + 1023 ) / 1024 ;
//    }
//
//    public long estimateTrxNetUsageWords( final SignedTransaction txn, PackedTransaction.CompressType compressType, int keyCount ){
//        if (tx_net_usage != 0) {
//            return tx_net_usage / 8;
//        }
//
//        long sigs =  5 +  // the maximum encoded size of the unsigned_int for the size of the signature block
//                (keyCount * 72); // 72 == sizeof(signature_type)
//
//        long packed_size_drift = PackedTransaction.CompressType.none.equals( compressType ) ?
//                4 :  // there is 1 variably encoded ints we haven't set yet, this size it can grow by 4 bytes
//                256; // allow for drift in the compression due to new data
//
//        PackedTransaction packedTransaction = new PackedTransaction( txn );
//
//        long estimated_packed_size = packedTransaction.getDataSize() + packed_size_drift;
//
//        return (sigs + estimated_packed_size + txn.getCtxFreeDataCount() + 7) / 8;
//    }

    public SignedTransaction estimate(final SignedTransaction txn, PackedTransaction.CompressType compressType, int keyCount) {
        // TODO 2018-04-06
        txn.putKcpuUsage( 0 );
        txn.putNetUsageWords( 0 );

//        txn.putKcpuUsage( estimateTrxCtxFreeKiloCpuUsage( txn, DEFAULT_EXTRA_KCPU ) );
//        txn.putNetUsageWords( estimateTrxNetUsageWords( txn, compressType, keyCount) );

        return txn;
    }
}

