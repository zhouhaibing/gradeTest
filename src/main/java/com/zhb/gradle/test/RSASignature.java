package com.zhb.gradle.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSASignature {
	private static byte[] encodeBase64Map = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_".getBytes();
	private static KeyFactory rsaKf = null;
	private static byte[] decodeBase64Map = new byte[256];
	public static enum SignatureAlgorithm{
		SHA1WITHRSA("SHA1WithRSA"),
		MD5WITHRSA("MD51WithRSA");
		private final String code;
		SignatureAlgorithm(String code){
			this.code = code;
		}
		public static SignatureAlgorithm fromCode(String code){
			for(SignatureAlgorithm signatureAlgorithm:values()){
				if(signatureAlgorithm.code.equals(code)){
					return signatureAlgorithm;
				}
			}
			return null;
		}
	}
	public static final SignatureAlgorithm DEFAULT_SIGNATURE_ALGORITHM = SignatureAlgorithm.SHA1WITHRSA;
	static{
		try {
			rsaKf = KeyFactory.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		for(int i=0;i<decodeBase64Map.length;i++){
			decodeBase64Map[i]=-1;
		}
		for(int i=0;i<encodeBase64Map.length;i++){
			decodeBase64Map[encodeBase64Map[i]]=(byte)i;
		}
		//for url unsafe
		decodeBase64Map['+']=decodeBase64Map['-'];
		decodeBase64Map['/']=decodeBase64Map['_'];
	}
	
	public static byte[] getBytesFromBase64(String base64String){
		byte[] in = base64String.replaceAll("[\\r\\n=]", "").getBytes();
		int totalTrunk = in.length/4;
		int outLength = totalTrunk*3;
		if(in.length-totalTrunk*4==3){
			outLength+=2;
		}else if(in.length-totalTrunk*4==2){
			outLength+=1;
		}
		byte[] out = new byte[outLength];
		int outOffset = 0;
		int inOffset = 0;
		while(inOffset<totalTrunk*4){
			out[outOffset++]=(byte) ((decodeBase64Map[0xff&(in[inOffset  ])]<<2)|(decodeBase64Map[0xff&(in[inOffset+1])]>>>4));
			out[outOffset++]=(byte) ((decodeBase64Map[0xff&(in[inOffset+1])]<<4)|(decodeBase64Map[0xff&(in[inOffset+2])]>>>2));
			out[outOffset++]=(byte) ((decodeBase64Map[0xff&(in[inOffset+2])]<<6)|(decodeBase64Map[0xff&(in[inOffset+3])]));
			inOffset+=4;
		}
		if(out.length-outOffset==2){
			out[outOffset++]=(byte) ((decodeBase64Map[0xff&(in[inOffset  ])]<<2)|(decodeBase64Map[0xff&(in[inOffset+1])]>>>4));
			out[outOffset++]=(byte) ((decodeBase64Map[0xff&(in[inOffset+1])]<<4)|(decodeBase64Map[0xff&(in[inOffset+2])]>>>2));
		}else if(out.length-outOffset==1){
			out[outOffset++]=(byte) ((decodeBase64Map[0xff&(in[inOffset  ])]<<2)|(decodeBase64Map[0xff&(in[inOffset+1])]>>>4));
		}
		return out;
	}
	
	public static PublicKey getPublicKeyFromBytes(byte[] pubKey){
		PublicKey publicKey = null;
		try {
			publicKey = rsaKf.generatePublic(new X509EncodedKeySpec(pubKey));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return publicKey;
	}
	
	public static PrivateKey getPrivateKeyFromBytes(byte[] priKey){
		PrivateKey privateKey = null;
		try {
			privateKey = rsaKf.generatePrivate(new PKCS8EncodedKeySpec(priKey));
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return privateKey;
	}
	
	public static byte[] decrypt(PrivateKey priKey,byte[] in){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			RSAPrivateKey pk = (RSAPrivateKey) priKey;
			int chunkSize = pk.getModulus().bitLength()/8;
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE,priKey);
			int inOffset = 0;
			while(inOffset<in.length){
				int copyLength = in.length-inOffset>chunkSize?chunkSize:in.length-inOffset;
				byte[] buffer = cipher.doFinal(in,inOffset,copyLength);
				baos.write(buffer);
				inOffset+=copyLength;
			}
			return baos.toByteArray();
		} catch (NoSuchAlgorithmException|NoSuchPaddingException|IOException|IllegalBlockSizeException|BadPaddingException|InvalidKeyException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] encrypt(PublicKey pubKey,byte[] in){
//		int chunkSize = keyPairSize/8-11;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			RSAPublicKey pk = (RSAPublicKey)pubKey;
			int chunkSize = pk.getModulus().bitLength()/8-11;
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);
			
			int inOffset = 0;
			while(inOffset<in.length){
				int copyLength = in.length-inOffset>chunkSize?chunkSize:in.length-inOffset;
				byte[] buffer = cipher.doFinal(in,inOffset,copyLength);
				baos.write(buffer);
				inOffset+=copyLength;
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
	public static boolean verify(PublicKey pubKey,byte[] content,byte[] sign,SignatureAlgorithm signatureAlgorithm){
		try {
			Signature signature = Signature.getInstance(signatureAlgorithm.code);
			signature.initVerify(pubKey);
			signature.update(content);
			return signature.verify(sign);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SignatureException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//解密
		{
			String strRaw = "SR_-TTQM2XgF5zrR6h0u2NgYMClu-qYtUz9qQHUNsz-4Z1xHeZBAhvfUmww0S2DoU5Le_GLDHVmOGtQKXFA4HA";
			String strPrivateKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEApcNM4921j9u2p3_mKYTOJKAPTvtxE3AZaCl41SDKEJjhTFBkaok3iDad9H38QR2Av6v7CIPfs8z8Mu_-uO3VOQIDAQABAkEAlzGxM3JphgLw8ozdYedpo8x-yhrcg89OzUxuk2-p_0IJ1mg2VwqFFobNa8m5lCt3mR7S5ka23v86AU-OVyu9AQIhAN8tB91MEz4H-VhEmqUHbt4W_ySY2Il6pe34e6NrLNFZAiEAviSRW8c1RLy21EyBe2wTO_kNm1g9rSTQbFX3vCa3xuECIHFVS9YcqPTqVbx2clX2FV4hLYZnPCdsqlDTy-2sIoFZAiEAqcUWsB5utl_U0js5BQ0bI1ocxw4Di5uqKICdgDYFsqECIQDSE4Ci3tyqPuIjgVOIERYl2AEsj2h38P8FZyBGgnKRuQ";
			PrivateKey priKey = getPrivateKeyFromBytes(getBytesFromBase64(strPrivateKey));
			byte[] decryptData = decrypt(priKey, getBytesFromBase64(strRaw));
			String result = new String(decryptData,"UTF-8");
			System.out.println(result);
		}
		//验签
		{		
			/*String strPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJXJVHxjMMGV9FXFqBDya2aiZ1mAnHGJZQsaWTId3ulw2aTfFJvuFw2vxA0maiae9ZacAEYVxIX7+gQ5lsb95PUCAwEAAQ==";
		
			String strContent = "appId2y2TAW4hjDwpcheckCodeGcyfCoTpheirLu4Mvhd_nscM-uORUpkyK9KkRvgw30lNVZT84QU74aVSACHp7nO6TiZ0RUzp_BJdkHEjc3t1sZzrv6AIhCG0Mhn5xXwvizVATiKFfk0L0e393rZJyvkYTc43GnqGClCtFtBrr5woLwF7f45a_Hr0Eg0QmT1w8ecdeliveryCodeYszvazOxjx6VLAspsQUNTtgcTxtdayaN2Vw0Gg4Ggl5K7nOkEvhbNpgCHbZ20lOweMTXkZeN91kqkHqaSMQaGOBnFCkKHDIi8l-z5lcfU126g8m47KxN_lJW1jZNqNDm_Syg0YtxaD5d-8-XtPtoidmhi5a879Ei5PbYv279PxkorderId1265paymentId201604211413010456519564price1.00productExtproductId1productName购买60元宝userIdAlsmPzB7mtrsversion1.0.0";
			String sign = "McdKjLnZt2nqq0jJOYQa-lmo4p1ZKBgWRYbeZ_hMiEX7EhKKxggvjwVnG1P8Zb99Za24Qr9HfZRG5pNb9OBb-g";
			
			PublicKey pubKey = getPublicKeyFromBytes(getBytesFromBase64(strPublicKey));
						
			boolean verifySuccess = verify(pubKey, strContent.getBytes("UTF-8"), getBytesFromBase64(sign), DEFAULT_SIGNATURE_ALGORITHM);
			System.out.println(verifySuccess);*/
			System.out.println("验签");
			String strPublicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJXJVHxjMMGV9FXFqBDya2aiZ1mAnHGJZQsaWTId3ulw2aTfFJvuFw2vxA0maiae9ZacAEYVxIX7+gQ5lsb95PUCAwEAAQ==";
			String strContent = "appIdQ0s6_NDsPxD_checkCodeiA_XkuSQo9vow9nyWH12v_QASnBPN3JrNPYXVCtkGkxM9B35IbkgVY8mwE9JRt1wzLohiP4LyTyp8QjHu6exswdeliveryCodecwhZILTdbY99XX4ep9ewhlFZhU2-wc4t3Hd87qFSB5J9AuHDLDOgtVEjFRlQjAw0flg2FuyJzKeqQYSVB82J2AorderId616q291000334702paymentId201606271752217701299286price1.00productExtproductId1productName元宝userIdDL2I6CRttkNYversion1.0.0";
			String sign = "RUmMs9DV608WRPDRwGF3380Nsgv_Y63N48BBBzRK1BUcf8jYyhJojF1moEOmclEFuhQiEVRAlXo49oziHpEtmA";
			PublicKey pubKey = getPublicKeyFromBytes(getBytesFromBase64(strPublicKey));
			
			boolean verifySuccess = verify(pubKey, strContent.getBytes("UTF-8"), getBytesFromBase64(sign), DEFAULT_SIGNATURE_ALGORITHM);
			System.out.println(verifySuccess);
			
		}
		//验证密钥对
		{
			String strPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDah31J6bOMANXcJqrI5B21kGAC+nHqhEeVkexakp8J/HOE3npIHzgM+IRrJPQ/H/B1y6OC4cse4IHR/0YWPUg3AdgNWWAuXO83BL9Kf1hgAfBqYjkIPmEWZ1Tto4mov1uzwJy0jqQZ8023JmKlkssk8Y3xn2bn8bJm5oeRUtFSkwIDAQAB";
			String strPrivateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANqHfUnps4wA1dwmqsjkHbWQYAL6ceqER5WR7FqSnwn8c4TeekgfOAz4hGsk9D8f8HXLo4Lhyx7ggdH/RhY9SDcB2A1ZYC5c7zcEv0p/WGAB8GpiOQg+YRZnVO2jiai/W7PAnLSOpBnzTbcmYqWSyyTxjfGfZufxsmbmh5FS0VKTAgMBAAECgYEAget5dl8Fyt+YRbmLYcIVU6ORIVQqM8YPFQJbZYG0Iei/+SeXxZch6Lw7ZcPjnj/r4ux9ustoymOpauL8lS475radtv+c+qPcP0ecekoYdGCzqCdIy7TU9BS6VTIWY+uBPGRMeShCL8WZVAFh68v0TaCq16sR8J6m2zWAdkBBdgECQQD3oDSmX5Qus9vsyJdZG8yaWPtgJZ7HxAKBgPYPNaCgQ/3ZQpc6QUiYTL5kLv85RrMND3PGZPruDq36jUmMiQ+TAkEA4etiKWFXnGrJQmWAqmuaaFjOXs9UiF5uHf0O+T6oiyCsVXO3Vw7Q24g0eMJPcdVhi7ZvkHk9FUBhOaQlF3KRAQJAeVBAWv3aT+MHrII9O4eV/kv3owvrNPz/KUjvw1XHgpyswYKRKhYPpaV7ZQNtQDc1wwE9/AU+td/1Nhl/3dzMBwJBAK0D5QMnxz7FESjGL0lUasXq7PDy+xwMpWxV9veuoskZ1qECo7AKaq0VnIm21byp27BxoPMVxk/WJB9OtBFG7gECQQDXX1DxK3Vfw0SWFys61/bhMyhZl7Ku4rR6Rg86m5Fan9zhb2VKRUxJFU8IZcBTKC/M3fneBr6u1/UIwrF2YLGI";
			
			PublicKey pubKey = getPublicKeyFromBytes(getBytesFromBase64(strPublicKey));
			PrivateKey priKey = getPrivateKeyFromBytes(getBytesFromBase64(strPrivateKey));
			String strContent = "codewwwkeyakeybsigntypersa";
			byte[] data = decrypt(priKey, encrypt(pubKey, strContent.getBytes("UTF-8")));
			System.out.println(new String(data,"UTF-8"));
			
		}
	}

}
