package com.zhb.db.test;

import java.text.MessageFormat;

public class HelloWorld {
	public static String[] dates = new  String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
	public static void main(String[] args){
		int i =0;
		while(true){
			System.out.println(MessageFormat.format("今天是【{0}】", dates[i]));
			System.out.println("太阳公公起床了");
			System.out.println("小鸟在为宝宝歌唱，哈哈哈");
			System.out.println("花儿静静绽放，时不时向你微笑，哈哈");
			System.out.println("给宝宝一个么么哒！");
			System.out.println("此处欢乐多多多，省略N个字，哈哈");
			i++;
			if(i == 7){
				i = 0;
			}
		}
	}

}
