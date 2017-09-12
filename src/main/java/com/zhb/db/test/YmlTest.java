package com.zhb.db.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.yaml.snakeyaml.Yaml;

import com.alibaba.fastjson.JSON;

public class YmlTest {
	
	
	public static void main(String[] args) throws IOException{		
		Yaml yml = new Yaml();
		ApktoolModel model = yml.loadAs(YmlTest.class.getClassLoader().getResourceAsStream("yml/apktool.yml"), ApktoolModel.class);
		System.out.println(model);
		model.getDoNotCompress().add("assets/Bundle");
		
		yml.dump(model, new FileWriter("newapktool.yml",false));
	}
	
	

}

class ApktoolModel{
	public String apkFileName;
	public boolean compressionType;
	public List<String> doNotCompress;
	public String isFrameworkApk;
	public PackageInfo packageInfo;
	public SdkInfo sdkInfo;
	public boolean sharedLibrary;
	public Object unknownFiles;
	public UsesFramework usesFramework;
	public String version;
	public VersionInfo versionInfo;
	
	public ApktoolModel(){}
	public String getApkFileName() {
		return apkFileName;
	}
	public void setApkFileName(String apkFileName) {
		this.apkFileName = apkFileName;
	}
	public boolean getCompressionType() {
		return compressionType;
	}
	public void setCompressionType(boolean compressionType) {
		this.compressionType = compressionType;
	}
	public List<String> getDoNotCompress() {
		return doNotCompress;
	}
	public void setDoNotCompress(List<String> doNotCompress) {
		this.doNotCompress = doNotCompress;
	}
	public String getIsFrameworkApk() {
		return isFrameworkApk;
	}
	public void setIsFrameworkApk(String isFrameworkApk) {
		this.isFrameworkApk = isFrameworkApk;
	}
	
	/*@Override
	public String toString(){
		return JSON.toJSONString(this);
	}*/
}

class VersionInfo{
	public String versionCode;
	public String versionName;
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
	
}
class PackageInfo {
	public String forcedPackageId;
	public Object renameManifestPackage;
	public String getForcedPackageId() {
		return forcedPackageId;
	}
	public void setForcedPackageId(String forcedPackageId) {
		this.forcedPackageId = forcedPackageId;
	}
	public Object getRenameManifestPackage() {
		return renameManifestPackage;
	}
	public void setRenameManifestPackage(Object renameManifestPackage) {
		this.renameManifestPackage = renameManifestPackage;
	}
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}

class SdkInfo{
	public String minSdkVersion;
	public String targetSdkVersion;
	public String getMinSdkVersion() {
		return minSdkVersion;
	}
	public void setMinSdkVersion(String minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}
	public String getTargetSdkVersion() {
		return targetSdkVersion;
	}
	public void setTargetSdkVersion(String targetSdkVersion) {
		this.targetSdkVersion = targetSdkVersion;
	}
	
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}

class UsesFramework{
	public List<String> ids;
	public Object tag;
	public List<String> getIds() {
		return ids;
	}
	public void setIds(List<String> ids) {
		this.ids = ids;
	}
	public Object getTag() {
		return tag;
	}
	public void setTag(Object tag) {
		this.tag = tag;
	}
	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}
}