package com.zhb.test.basic;

import java.util.Scanner;

public class Test {
	/*public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int n = input.nextInt();
		for(int i=0;i<n;i++){
			int m = input.nextInt();
			int q = input.nextInt();
			int[] nums = new int[m];
			for(int j=0;j<m;j++){
				nums[j] = input.nextInt();
			}
			for(int k=0;k<q;k++){
				int searchI = input.nextInt();
				System.out.println(getIndexInArray(nums,searchI));
			}
		}
	}*/
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int rowNum  = input.nextInt();
		int columnNum  = input.nextInt();
		String[] views = new String[rowNum];
		int num = 0;
		input.nextLine();
		for(int i=0;i<rowNum;i++){
			String s = input.nextLine();
			views[i] = s;
		}
		
		for(int i=0;i<rowNum;i++){
			for(int j=0;j<columnNum;j++){
				char c = views[i].charAt(j);
				if(c == '*'){
					num++;
				}
			}
			
		}
		System.out.println(num);
		
	}
	
	public static int getIndexInArray(int[] array,int key){
		for(int i=0;i<array.length;i++){
			if(array[i] == key){
				return i;
			}
		}
		return -1;
	}

}
