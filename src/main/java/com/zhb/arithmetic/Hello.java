package com.zhb.arithmetic;

public class Hello {
	
	
	public static void bubbleSort(int[] array){
		for(int i=0; i < array.length - 1; i++){
			for(int j=0; j < array.length - i -1; j++){
				if(array[j] > array[j+1]){
					int temp = array[j];
					array[j] = array[j+1];
					array[j+1] = temp;
				}
			}
		}
	}
	
	public static void selectSort(int[] array){
		for(int i=0; i < array.length - 1; i++){
			int minIndex = i;
			int minValue = array[i];
			for(int j=i+1; j < array.length;j++){
				if(minValue > array[j]){
					minIndex = j;
					minValue = array[j];
				}
			}
			if(minIndex != i){
				int temp = array[i];
				array[i] = minValue;
				array[minIndex] = temp;
			}
		}
	}
	
	public static void insertSort(int[] array, int a){
		
	}
	
	
	public static void main(String[] args){
		int[] dd = {5,7,3,8,1,9,20,4};
		//bubbleSort(dd);
		selectSort(dd);
		for(int i : dd){
			System.out.print(i + " ");
		}
	}
	

}
