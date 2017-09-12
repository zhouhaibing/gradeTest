package com.zhb.db.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/*import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;*/
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;



public class DBTest {
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws Exception{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","xgsdk_zhb", "xgsdk_zhb");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select xg_custom_info from recharge_log_3");
			while(rs.next()){
				System.out.println(rs.getString("xg_custom_info"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb?useUnicode=true&characterEncoding=UTF-8","xgsdk_zhb", "xgsdk_zhb");
			
			stmt = (PreparedStatement) conn.prepareStatement("insert into recharge_log_3(charge_log_id,trade_no,role_name,xg_custom_info) values(4,'2223',?,?)");
			Statement stmtt = conn.createStatement();
			stmtt.executeUpdate("SET NAMES 'utf8mb4'");
			stmt.setString(1, "yue🎵");
			stmt.setString(2, "oooyue🎵");
			stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(stmt != null){
				stmt.close();
			}
			if(conn != null){
				conn.close();
			}
			
		}*/
		
		// test ci db
		/*try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://10.20.71.103:3306/xgsdk2_sdkserver?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false","xgsdk2_sdkserver", "xgsdk2_sdkserver");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select name from cfg_product where id = 1");
			while(rs.next()){
				System.out.println(rs.getString("name"));
			}
			
			int result = stmt.executeUpdate("insert into cfg_product_warse(product_id,warse_id,name,paid_amount,game_coin) values(2,'hello','world',23,34)");
			System.out.println(result);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}*/
		
		/*Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://42.62.96.68:3306/xgsdk2_qa?","xgsdk2_qa", "g7jmpzkdbn");
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery("select file from cfg_sdk_plan_channel_resource where id = 1");
		while(rs.next()){
			BASE64Encoder base64Encoder = new BASE64Encoder();
			//System.out.println(base64Encoder.encode(rs.getBytes("file")));
			System.out.println(Base64.getEncoder().encodeToString(rs.getBytes("file")));
		}*/
		
		String imgPath = "images/njuhu.png";
		File file = new File(imgPath);
		System.out.println(file.exists());
		System.out.println(getImageBase64Str(imgPath));
		generateImage(getImageBase64Str(imgPath), "images/nnnn.png");
	}
	
	public static String getImageBase64Str(String imgFilePath){
		byte[] data = null;
		if(StringUtils.isEmpty(imgFilePath)){
			return null;
		}
		try {
			InputStream in = new FileInputStream(new File(imgFilePath));
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Base64.getEncoder().encodeToString(data);
	}
	
	public static boolean generateImage(String imgStr,String imgFilePath){
		if(StringUtils.isEmpty(imgStr))
			return false;
		try{
			byte[] bytes = Base64.getDecoder().decode(imgStr);
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(bytes);
			out.flush();
			out.close();
			return true;
		} catch (IOException e){
			e.printStackTrace();
			return false;
		}
	}

}
