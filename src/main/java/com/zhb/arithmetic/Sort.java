package com.zhb.arithmetic;

import org.jf.smali.smaliParser.integer_literal_return;


public class Sort {
	
	public static void main(String[] args) {
		int[] nums = new int[]{13, 15, 11, 9, 3, 23, 34, 5, 22};
		//bubble(nums);
		//selectSort(nums);
		quickSort(nums,0, nums.length - 1);
		print(nums);
	}
	
	//O(n2)
	public static void bubble(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			for (int j = 0; j < nums.length - i - 1; j++){
				if (nums[j] > nums[j+1]) {
					int temp = nums[j];
					nums[j] = nums[j+1];
					nums[j+1] = temp;
				}
			}
		}
	}
	
	// O(n2)
	public static void selectSort(int[] nums) {
		for (int i = 0; i < nums.length - 1; i++) {
			int minIndex = i;
			for (int j = i + 1; j < nums.length; j++){
				if (nums[j] < nums[minIndex]) {
					minIndex = j;
				}
			}
			if (minIndex != i) {
				int temp = nums[i];
				nums[i] = nums[minIndex];
				nums[minIndex] = temp;
			}
		}
	}
	
	//nlog2 n
	public static void quickSort(int[] nums, int start, int end) {
		int i = start;
		int j = end;
		int key = nums[i];
		while(i < j) {
			while(i < j && key <= nums[j]) {
				j--;
			}
			if (i < j) {
				nums[i] = nums[j];
			}
			while(i < j && key >= nums[i]) {
				i++;
			}
			if (i < j) {
				nums[j] = nums[i];
			}
		}
		nums[i] = key;
		if (i - start > 1) {
			quickSort(nums, 0, i - 1);
		}
		if (end - j > 1) {
			quickSort(nums, j+1, end);
		}
	}
	
	
	public static void mergeSort(int[] nums, int start, int end) {
		int mid = (start + end) / 2;
		if (start < end) {
			mergeSort(nums, start, mid);
			mergeSort(nums, mid + 1, end);
			merge(nums, start, mid, end);
		}
	}
	
	public static void merge(int[] nums, int start, int mid, int end) {
		int[] temp = new int[end - start + 1];
		int i = start;
		int j = mid + 1;
		int k = 0;
		
		while(i <= mid && j <= end) {
			if (nums[i] < nums[j]){
				temp[k++] = nums[i++];
			} else {
				temp[k++] = nums[j++];
			}
		}
		while(i <= mid){
			temp[k++] = nums[i++];
		}
		while(j <= end){
			temp[k++] = nums[j++];
		}
		// 把新数组中的数覆盖nums数组  
        for (int k2 = 0; k2 < temp.length; k2++) {  
            nums[k2 + start] = temp[k2];  
        }  
	}
	
	
	public static void insertSort(int[] array){
		for(int i=1; i < array.length;i++){
			int temp = array[i];
			int j;
			for(j = i;j > 0; j--){
				if(temp < array[j-1]){
					array[j] = array[j-1];
				}else{
					break;
				}
			}
			array[j] = temp;
		}
	}
	
	public static void print(int[] nums) {
		for(int i : nums){
			System.out.print(i + " ");
		}
	}

}
