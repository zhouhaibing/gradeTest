package com.zhb.db.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import brut.androlib.Androlib;
import brut.androlib.ApkDecoder;
import brut.common.BrutException;

public class AndroidCompressTest {

	
	public static void compress() throws BrutException{
		File originFile = new File("C:\\Users\\zhouhaibing\\.xgpack\\works\\20170301165057\\tmp\\uc");
		File outputFile = new File("C:\\Users\\zhouhaibing\\.xgpack\\works\\20170301165057\\output\\ddd_uc.apk");
		Androlib androlib = new Androlib();
		androlib.build(originFile, outputFile);
	}
	
	public static void compress1() throws BrutException, IOException{
		File apkFile = new File("C:\\Users\\zhouhaibing\\.xgpack\\works\\20170302115145\\output\\unitydemo0301000_uc.apk");
		File outFile = new File("C:\\Users\\zhouhaibing\\.xgpack\\works\\20170302115145\\output\\tmp");
		if(!outFile.exists()){
			outFile.mkdir();
		}
		ApkDecoder decoder = new ApkDecoder();
		//decoder.setDebugMode(debugMode);
		decoder.setBaksmaliDebugMode(true);
		decoder.setForceDelete(true);
		decoder.setDecodeSources((short)0);
		decoder.setApkFile(apkFile);
		decoder.setOutDir(outFile);		
		decoder.decode();
	}
	
	public static void compress2() throws BrutException, IOException{
		File apkFile = new File("C:\\Users\\zhouhaibing\\.xgpack\\works\\20170302115145\\tmp\\uc");
		File outFile = new File("C:\\Users\\zhouhaibing\\.xgpack\\works\\20170302115145\\output\\jxsj_uc_recompress.apk");
		
		Androlib androlib = new Androlib();
		androlib.build(apkFile, outFile);
	}
	
	public static void main(String[] args) throws BrutException, IOException {
		//compress1();
		compress();
		
		/*File file = new File("F:\\temp\\text.txt");
		if(file.exists()){
			System.out.println("file exist");
		}
		
		File file2 = new File("text.txt");
		if(file2.exists()){
			System.out.println("file2 exist");
		}
		
		File file3 = new File(".");
		System.out.println(file3.getAbsolutePath()); C:\zhouhbFile\myCodes\gradleTest*/
		//InputStream input = AndroidCompressTest.class.getResourceAsStream("")
		
		
	}
}
