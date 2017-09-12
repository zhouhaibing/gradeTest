/**
 * 
 */
package com.zhb.encrypt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

/**
 * RSA签名验签类
 */
public class RSASignature {

	public static final String SIGN_ALGORITHMS = "SHA1WithRSA";
	public static final String CHARSET = "UTF-8";

	/**
	 * RSA签名
	 * 
	 * @param content
	 *            待签名数据
	 * @param privateKey
	 *            商户私钥
	 * @param encode
	 *            字符集编码
	 * @return 签名值
	 */
	public static String sign(String content, String privateKey, String charset)
	{
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(priPKCS8);
			java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(charset));
			byte[] signed = signature.sign();
			return new String(Base64.getEncoder().encode(signed), charset);
		} catch (Exception e) {
			//XGLog.supplementExceptionMessage(e);
		}
		return null;
	}

	public static String sign(String content, String privateKey)
	{
		return sign(content, privateKey, CHARSET);
	}

	public static String signWithCharSet(String content, String privateKey, String charSet)
	{
		if (StringUtils.isNotEmpty(charSet)) {
			return sign(content, privateKey, charSet);
		}
		return sign(content, privateKey, CHARSET);
	}

	/**
	 * RSA验签名检查
	 * 
	 * @param content
	 *            待签名数据
	 * @param sign
	 *            签名值
	 * @param publicKey
	 *            分配给开发商公钥
	 * @param encode
	 *            字符集编码
	 * @return 布尔值
	 */
	public static boolean doCheck(String content, String sign, String publicKey, String charset)
	{
		try
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			byte[] encodedKey = Base64.getDecoder().decode(publicKey);
			PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

			java.security.Signature signature = java.security.Signature
							.getInstance(SIGN_ALGORITHMS);

			signature.initVerify(pubKey);
			signature.update(content.getBytes(charset));

			boolean bverify = signature.verify(Base64.getDecoder().decode(sign));
			return bverify;

		} catch (Exception e)
		{
			//XGLog.supplementExceptionMessage(e);
		}

		return false;
	}

	public static boolean doCheck(String content, String sign, String publicKey)
	{
		return doCheck(content, sign, publicKey,CHARSET);
	}

	public static boolean doCheckWithCharSet(String content, String sign, String publicKey, String charSet) {
		if (StringUtils.isNotEmpty(charSet)) {
			return doCheck(content, sign, publicKey, charSet);
		}
		return doCheck(content, sign, publicKey, CHARSET);
	}

}
