package com.zhb.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;

public class CommonUtils {
	
	// 
	public static void appendFileContent(String srcPath, String destPath){
		File srcFile = new File(srcPath);
		File destFile = new File(destPath);
		if (!srcFile.exists() || !destFile.exists()) {
			return;
		}
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(new File(srcPath)));
			//br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFile))); 也可以使用这种方式
			
			// FileWriter writer  = new FileWriter(destPath, true);  
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath, true)));
			String line = "";
			while((line = br.readLine()) != null) {
				bw.write("\n");
				bw.write(line);
			}
			bw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (br != null){
					br.close();
				}
				if (bw != null){
					bw.close();
				}
			} catch (IOException e) {     
	            e.printStackTrace();     
	        }  
		}
		  
	  }
	
	
	
	/**   
     * 追加文件：使用RandomAccessFile   
     *    
     * @param fileName 文件名   
     * @param content 追加的内容   
     */    
    public static void method3(String fileName, String content) {   
        RandomAccessFile randomFile = null;  
        try {     
            // 打开一个随机访问文件流，按读写方式     
            randomFile = new RandomAccessFile(fileName, "rw");     
            // 文件长度，字节数     
            long fileLength = randomFile.length();     
            // 将写文件指针移到文件尾。     
            randomFile.seek(fileLength);     
            randomFile.writeBytes(content);      
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally{  
            if(randomFile != null){  
                try {  
                    randomFile.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }    
}
