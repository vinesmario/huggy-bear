package com.vinesmario.microservice.server.common.util;

/**
 * @description 用于RC4加密，可将一个字符串，或一个字节数组加密，得到相同长度的密文
 * @version 1.0
 * @update 修改时间 2012-4-28 上午10:00:38
 */
public class RC4EncryptUtil {
	//是否启用加密
	private static boolean enableEncrypt=false;

	/**
	 * 加密一个字符串
	 * @param aInput
	 * @return 
	 * @description 
	 * @param
	 */
	public static String rc4(String key,String aInput) {
		int[] iS = prepareKey(key);
		int i = 0;
		int j = 0;
		char[] iInputChar = aInput.toCharArray();
		char[] iOutputChar = new char[iInputChar.length];
		for (short x = 0; x < iInputChar.length; x++) {
			i = (i + 1) % 256;
			j = (j + iS[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
			iOutputChar[x] = (char) (iInputChar[x] ^ iS[(iS[i] + iS[j]) % 256]);
		}
		return new String(iOutputChar);
	}

	/**
	 * @param key
	 * @return
	 * @description 准备密钥的方法，通过RC4加密码算法生成子密钥。加密前需要先调用此方法
	 * @param
	 */
	private static int[] prepareKey(String key) {
		int ucIndex1 = 0;
		int ucIndex2 = 0;
		int[] state = new int[256];
		for (int i = 0; i < state.length; i++) {
			state[i] = i;
		}
		for (int counter = 0; counter < state.length; counter++) {
			ucIndex2 = (key.charAt(ucIndex1) + state[counter] + ucIndex2) % 256;
			int temp = state[counter];
			state[counter] = state[ucIndex2];
			state[ucIndex2] = temp;
			ucIndex1 = (ucIndex1 + 1) % key.length();
		}
		return state;
	}
	/**
	 * 加密一个字节流
	 * @param aInput
	 * @return 
	 * @description 加密一个字节流
	 * @param
	 */
	public static byte[] rc4(String key,byte[] aInput) {
	    if(!enableEncrypt){
	        return aInput;
        }
		int[] iS = prepareKey(key);
		int i = 0;
		int j = 0;
		byte[] iOutputChar = new byte[aInput.length];
		for (int x = 0; x < aInput.length; x++) {
			i = (i + 1) % 256;
			j = (j + iS[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
			int iY = iS[(iS[i] + iS[j]) % 256];
			int aX = aInput[x] > 0 ? aInput[x] : 256 + aInput[x];
			iOutputChar[x] = (byte) (aX ^ iY);
		}
		return iOutputChar;
	}

//	public static void main(String[] args) {
//		String aa="aaa";
//		String key="111";
//
//		String ccc=rc4(key,aa);
//		System.out.println(ccc);
//		System.out.println(rc4(key,ccc));
//	}

}
