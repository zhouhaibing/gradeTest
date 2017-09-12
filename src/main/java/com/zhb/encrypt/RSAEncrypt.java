package com.zhb.encrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAEncrypt {

	// 默认公钥(openssl)

	public static final String DEFAULT_PUBLIC_KEY =
					"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZnI5rdEvm4U6oS63RapR7vPM8SsqbKRO/0M7GfYq4Ri3eSco2ZOMjF26/kHhGbVRSqEIBa5IoA2mZ9HYrbsJJ6K7tCxJogi9tjMrcwxC29PLoGnwZFza72GJltGwqw4fEmBWvvaMRusXCs3xacmHrNA3hZeP9wqg6SpVvIAk16QIDAQAB";
	public static final String DEFAULT_PRIVATE_KEY =
					"MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANmcjmt0S+bhTqhLrdFqlHu88zxKypspE7/QzsZ9irhGLd5JyjZk4yMXbr+QeEZtVFKoQgFrkigDaZn0dituwknoru0LEmiCL22MytzDELb08ugafBkXNrvYYmW0bCrDh8SYFa+9oxG6xcKzfFpyYes0DeFl4/3CqDpKlW8gCTXpAgMBAAECgYEA0X46TZAkEh0NkE9rApRMZeM9dum5qy4caSkoJ0Zs6YIGXl1DYqI1WG8SmzEF2ScfaoO5J4zbo9HFNAV4Tc/PEXhVagL+npLBiHSjOhuDXwAaeJ2AJ4f0ciVMCQzz7u6ZNQOvCRi6jQ9VuAMdL/F8wd2D/DtahJnjjNldKe9tJtECQQD+EM1fc+vNAmOzKkHDHzV9Z/onPY6kPJKP5hNqkUqDp87Rcqsgmt+9Jpn+u08H59CtaOmN2E0kGL1ZgO97dbVTAkEA20Szn7QZeGSlhaMT2v9Ot5CsDi70oIkgCoJ2k0r+wIw+9F+cTWyKLabrcx0wCWrI7Vu2vzwSkUBF8yplZWlkUwJAVec6eY7JaMzpBvg/ugjAXkGc3E29AB0W9R626+5qQm+nxzu9Ts3u3pbBtNaCV0rzTN7PMU20b4MJ6sxkrNrUzwJBANfLpohvpH2TeD+sfpcSm3MXo+2VktYH/ou5WF+f+Fah8DdrfZXJU7iYRbMWL7ek67iGgbV1tsRTIESwHfiPn9cCQQD1nsvuwawQ5HWzNCWaPPbckBRYFHcBw6bxmg9JhaDr2/DTPaMa7i++R4wlVxeHxEO8nTBlfVXRGMQUyXSvMEpT";

	/**
	 * 私钥
	 */
	private RSAPrivateKey privateKey;

	/**
	 * 公钥
	 */
	private RSAPublicKey publicKey;

	/**
	 * 字节数据转字符串专用集合
	 */
	private static final char[] HEX_CHAR = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 获取私钥
	 * 
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 * 
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 随机生成密钥对
	 */
	public void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(String publicKeyStr) throws Exception {
		try {
			/* BASE64Decoder base64Decoder= new BASE64Decoder();  
			 byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);*/
			byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	public void loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("私钥非法");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 公钥加密过程
	 * 
	 * @param publicKey1
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey1, byte[] plainTextData) throws Exception {
		if (publicKey1 == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey1);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 公钥解密过程
	 * 
	 * @param publicKey1
	 *            公钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPublicKey publicKey1, byte[] cipherData) throws Exception {
		if (publicKey1 == null) {
			throw new Exception("解密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, publicKey1);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 私钥解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

	/**
	 * 私钥加密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData) throws Exception {
		if (privateKey == null) {
			throw new Exception("加密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			// 使用默认RSA
			cipher = Cipher.getInstance("RSA");
			// cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 字节数据转十六进制字符串
	 * 
	 * @param data
	 *            输入数据
	 * @return 十六进制内容
	 */
	public static String byteArrayToString(byte[] data) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			// 取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
			stringBuilder.append(HEX_CHAR[(data[i] & 0xf0) >>> 4]);
			// 取出字节的低四位 作为索引得到相应的十六进制标识符
			stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
			if (i < data.length - 1) {
				stringBuilder.append(' ');
			}
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		RSAEncrypt rsaEncrypt = new RSAEncrypt();
		
		String onePublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZnI5rdEvm4U6oS63RapR7vPM8" 
+ "SsqbKRO/0M7GfYq4Ri3eSco2ZOMjF26/kHhGbVRSqEIBa5IoA2mZ9HYrbsJJ6K7t"
+ "CxJogi9tjMrcwxC29PLoGnwZFza72GJltGwqw4fEmBWvvaMRusXCs3xacmHrNA3h"
+ "ZeP9wqg6SpVvIAk16QIDAQAB";

		String onePrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANmcjmt0S+bhTqhL"
						+ "rdFqlHu88zxKypspE7/QzsZ9irhGLd5JyjZk4yMXbr+QeEZtVFKoQgFrkigDaZn0"
						+ "dituwknoru0LEmiCL22MytzDELb08ugafBkXNrvYYmW0bCrDh8SYFa+9oxG6xcKz"
						+ "fFpyYes0DeFl4/3CqDpKlW8gCTXpAgMBAAECgYEA0X46TZAkEh0NkE9rApRMZeM9"
						+ "dum5qy4caSkoJ0Zs6YIGXl1DYqI1WG8SmzEF2ScfaoO5J4zbo9HFNAV4Tc/PEXhV"
						+ "agL+npLBiHSjOhuDXwAaeJ2AJ4f0ciVMCQzz7u6ZNQOvCRi6jQ9VuAMdL/F8wd2D"
						+ "/DtahJnjjNldKe9tJtECQQD+EM1fc+vNAmOzKkHDHzV9Z/onPY6kPJKP5hNqkUqD"
						+ "p87Rcqsgmt+9Jpn+u08H59CtaOmN2E0kGL1ZgO97dbVTAkEA20Szn7QZeGSlhaMT"
						+ "2v9Ot5CsDi70oIkgCoJ2k0r+wIw+9F+cTWyKLabrcx0wCWrI7Vu2vzwSkUBF8ypl"
						+ "ZWlkUwJAVec6eY7JaMzpBvg/ugjAXkGc3E29AB0W9R626+5qQm+nxzu9Ts3u3pbB"
						+ "tNaCV0rzTN7PMU20b4MJ6sxkrNrUzwJBANfLpohvpH2TeD+sfpcSm3MXo+2VktYH"
						+ "/ou5WF+f+Fah8DdrfZXJU7iYRbMWL7ek67iGgbV1tsRTIESwHfiPn9cCQQD1nsvu"
						+ "wawQ5HWzNCWaPPbckBRYFHcBw6bxmg9JhaDr2/DTPaMa7i++R4wlVxeHxEO8nTBl"
						+ "fVXRGMQUyXSvMEpT";

		
		// encrypt decrypt test
		/*String plainText = "hello";
		try {
			rsaEncrypt.loadPublicKey(DEFAULT_PUBLIC_KEY);
			byte[] plainData = rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), plainText.getBytes());
			rsaEncrypt.loadPrivateKey(onePrivateKey);
			byte[] data = RSAEncrypt.decrypt(rsaEncrypt.getPrivateKey(), plainData);
			System.out.println(new String(data));

		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		//sign test
		// this test indicate that LF(line feed is no effect).
		
		String plainText = "hello";
		String sign = RSASignature.sign(plainText, onePrivateKey);
		System.out.println(RSASignature.doCheck(plainText, sign, DEFAULT_PUBLIC_KEY));

	}
}