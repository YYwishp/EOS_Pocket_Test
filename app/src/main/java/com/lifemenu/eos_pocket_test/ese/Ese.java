package com.lifemenu.eos_pocket_test.ese;



/**
 * Ese
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Ese {

	/**
	 * parseTransferData
	 * 
	 * @param quantity
	 * @return
	 */
	public static String parseTransferData(String from, String to, String quantity, String memo) {
		DataParam[] datas = new DataParam[] { new DataParam(from, DataType.name, Action.transfer),
				new DataParam(to, DataType.name, Action.transfer),
				new DataParam(quantity, DataType.asset, Action.transfer),
				new DataParam(memo, DataType.string, Action.transfer), };
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = concat(allbyte, value.seria());
		}
		// final byte [] b = allbyte.clone();
		// int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
		// for(int i=1;i<=a.length;i++) {
		// System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
		// }
		return bytesToHexString(allbyte);
	}
	public static byte[] concat(byte[] a, byte[] b) {
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}

	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
