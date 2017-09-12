package com.zhb.db.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HelloTest {
	public static final String KEY = "xgsdkNiuX";
	public static final String RFC_3339_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";// for 2014-12-12T00:00:00.000+09:00.or ISO 8601

	private static final int COUNT_BITS = Integer.SIZE - 3;
	private static final int CAPACITY = (1 << COUNT_BITS) - 1;

	public static void main(String[] args) throws Exception {
		
		
		String s = "{\"appId\":\"91000658\",\"mobile\":null,\"uid\":\"1\",\"serverId\":null}";
		
		JSONObject obj = JSONObject.parseObject(s);
		System.out.println(obj.getString("serverId"));
		
		/*System.out.println(URLEncoder.encode("ABC ABC"));


		Properties p = new Properties(System.getProperties());
		System.out.println(p.getProperty("path.separator"));
		writeFile();*/

		/*System.out.println(System.getProperty("xgpackType"));
		int value = 2;
		System.out.println(value <<= 1);
		
		System.out.println(64 >>> 3);
		
		int hashCode = "hello".hashCode();
		System.out.println(hashCode);
		System.out.println(Integer.toBinaryString(3));
		System.out.println(Integer.toBinaryString(hashCode));
		System.out.println(Integer.toBinaryString(hashCode >>> 16));
		
		//		System.out.println( Long.parseLong(Integer.toBinaryString(hashCode)) ^ Long.parseLong(Integer.toBinaryString(hashCode >>> 16)));
		System.out.println(hashCode ^ (hashCode >>> 16) );
		System.out.println(hash("hello"));
		
		System.out.println(hash("hello") & 22);*/
		/*
		
		Map<String,String> map2 = new ConcurrentHashMap<String, String>();
		
		System.out.println(7 >> 1);
		
		//result is 50,00,Locale.US result is 50.00
		System.out.println(String.format(Locale.FRANCE,"%.2f", 5000 / 100d));
		
		System.out.println((float)123333/10000);
		
		// Exception subclass
		try{
			sayHello();
		}catch(Throwable t){
			System.out.println(t.getMessage());
		}
		
		
		//hashmap toString
		Map<String,String> map = new HashMap<>();
		map.put("hello", "world");
		map.put("helloo", "world");
		System.out.println(map.toString());//{hello=world}
		
		//Properties properties = channel == null ? new Properties() : channel.getFeedbackConfigProperties(); is ok.
		System.out.println("helllo" + (null == null ? "hee":"oo"));// () is must + priority to == operator
		
		
		//System.out.println(Arrays.asList(null)); nullExcpetion

		// ComparableVersion.java test
		ComparableVersion cv = new ComparableVersion("2.2.3");
		System.out.println(cv.compareTo(new ComparableVersion("0")));// -1. compare to 2.2.5 // 1;

		ComparableVersion cv1 = new ComparableVersion("4.4.33_bate");
		System.out.println(cv1.compareTo(new ComparableVersion("4.4.33")));

		ComparableVersion cv2 = new ComparableVersion("2.2.10");
		System.out.println(cv2.compareTo(new ComparableVersion("2.3.0")));
		
		
		int[] ia = new int[] { 1, 2, 3, 4 };

		System.out.println(StringUtils.join(new String[] { "hh", "ww" }, ";"));
		
		*/

		// Optional and java8 list
		/*List<Integer> iis = Arrays.asList(1,2,3,4);
		Optional<Integer> io = iis.stream().filter(ii -> ii > 5).findFirst();
		System.out.println(io.isPresent());// when false then use get() will occur nosuchElement exception(no value present)
		int ir = iis.stream().filter(ii -> ii > 5).findFirst().get();
		
		
		List<String> ss = Arrays.asList(new String[]{"ee","rrr"});
		List<String> stooges = Arrays.asList("Larry", "Moe", "Curly");
		List<Integer> i1 = Arrays.asList(new Integer[]{1,2,3,4});*/
		// List<Integer> iis = Arrays.asList(new int[]{1,2,3,4}); error, because int can not be T generic type.
		/*int[] a = new int[] { 1, 2, 3, 4, 5 };
		List<int[]> list = Arrays.asList(a);
		System.out.println(Arrays.toString(a));// the right way to print arrays


		
		System.out.println(Arrays.toString("||ddd||".split("\\|")));//[, , ddd]
		System.out.println(Arrays.toString(StringUtils.split("||ddd||", "|")));//[ddd]
		System.out.println(2 * (Integer.MAX_VALUE / 3));

		System.out.println(System.currentTimeMillis());
		
		
		
		System.out.println(JSONObject.parseObject("{}", Person.class));*/

		// subString test
		/*String s1 = "uc__12345";
		String s2 = "12345";
		System.out.println(s2.lastIndexOf("_"));// -1 not contain str,then return -1.equal to indexOf
		System.out.println(s2.substring(s2.lastIndexOf("_") + 1));
		
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 1);
		System.out.println(cal.getTime());
		
		List<String> lists = new ArrayList<String>();
		lists.add("hello");
		lists.add("world");
		System.out.println(lists);
		
		
		System.out.println(Math.random());
		
		try {
			Scanner scan = new Scanner(new File("hello.txt"));
			System.out.println(scan.nextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

		// String s1 = "{\"body\":{\"result\":\"success\"},\"headers\":\"\",\"statusCode\":\"OK\"}";
		/*String s1 = "{\"body\":\"{\\\"result\\\":\\\"success\\\"}\",\"headers\":{},\"statusCode\":\"OK\"}";
		JSONObject obj = JSONObject.parseObject(s1);
		System.out.println(obj.getString("body"));
		System.out.println(obj.toJSONString());
		//System.out.println(obj.getJSONObject("body"));
		//System.out.println(JSON.toJSONString(obj.getString("body")));
		//System.out.println(JSON.toJSONString(obj.getJSONObject("body")));
		//System.out.println(JSON.toJSONString(obj));
		
		String s2 = "{\"response\":\"{\\\"body\\\":\\\"hello\\\"}\"}";
		JSONObject obj2 = JSONObject.parseObject(s2);
		System.out.println(obj2.getString("response"));
		
		
		List<String> list1 = new ArrayList<String>();
		for(String s : list1){
			System.out.println(s);
		}
		
		
		JSONObject obj1 = JSONObject.parseObject("{}");
		System.out.println(obj1.getString("hello"));
		
		System.out.println(AesUtil.decrypt("PjPxmWzQWzUNJOFEeRv2lQ==", KEY));*/

	}

	public static void sayHello() throws SubExcept {
		throw new SubExcept("Exception occur in hello");
	}

	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	public static void writeFile() {

		StringBuilder strB = new StringBuilder();
		File file = new File("hello.txt");
		File newFile = new File("world.txt");
		InputStream is = null;
		OutputStream outs = null;
		try {
			is = new FileInputStream(file);
			outs = new FileOutputStream(newFile, true);
			byte[] bytes = new byte[1024];
			int len = is.read(bytes);
			while (len != -1) {
				strB.append(new String(bytes, "UTF-8"));
				outs.write(bytes, 0, len);
				len = is.read(bytes);
			}
			outs.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (outs != null) {
					outs.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("exception in close streaming");
			}
		}

		System.out.println(strB.toString());

		FileWriter writer = null;
		try {
			writer = new FileWriter("worldd.txt");
			writer.write("good");
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("worldd.txt")));
			out.print("123456789");
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//use bit store int
	public void bitStoreInt(){
		int countBit = Integer.SIZE -3;
		int lowerBitCapacity = (1 << countBit) - 1;
		int value = -1 << countBit;//store in higher bit
		int value1 = 0 << countBit;
		AtomicInteger ai = new AtomicInteger(value);
		ai.getAndIncrement();
		//get lower bit value
		System.out.println(ai.get() & lowerBitCapacity);
		//get higher bit value
		System.out.println(value);
		System.out.println(ai.get() & ~lowerBitCapacity);
	}
}




class SubExcept extends Exception {
	public SubExcept(String msg) {
		super(msg);
	}
}
