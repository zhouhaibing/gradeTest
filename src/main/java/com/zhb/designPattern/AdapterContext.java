package com.zhb.designPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author ZHOUHAIBING
 * 适配器模式
 */
public class AdapterContext {
	public static List<ChargeAdapter> adapters = new ArrayList<>();
	
	public static void main(String[] args){
		adapters.add(new AndroidChargeAdapter());
		adapters.add(new AppleChargeAdapter());
		adapters.add(new WinPhoneChargeAdapter());
		
		AndroidChargeA androidChargea = new AndroidChargeA();
		for(ChargeAdapter ca : adapters){
			if(ca.support(androidChargea)){
				ca.handle(androidChargea);
			}
		}
	}

}

interface AndroidCharge{
	String directCharge(String msg);
}

interface AppleCharge{
	void quickCharge();
}

interface WinPhoneCharge{
	int slowCharge();
}

class AndroidChargeA implements AndroidCharge{
	public String directCharge(String msg){
		return "AndroidChargeA";
	}
}

class AppleChargeB implements AppleCharge{
	public void quickCharge(){
		System.out.println("AppleChargeB");
	}
}

class WinPhoneChargeC implements WinPhoneCharge{
	public int slowCharge(){
		System.out.println("WinPhoneChargeC");
		return 0;
	}
}

/*adapter*/

interface ChargeAdapter{
	boolean support(Object charge);
	String handle(Object charge);
}

class AndroidChargeAdapter implements ChargeAdapter{

	@Override
	public boolean support(Object charge) {
		return (charge instanceof AndroidCharge);
	}

	@Override
	public String handle(Object charge) {
		return ((AndroidCharge)charge).directCharge("hello");
	}
}

class AppleChargeAdapter implements ChargeAdapter{
	@Override
	public boolean support(Object charge) {
		return (charge instanceof AppleCharge);
	}

	@Override
	public String handle(Object charge) {
		((AppleCharge)charge).quickCharge();
		return "success";
	}
}

class WinPhoneChargeAdapter implements ChargeAdapter{

	@Override
	public boolean support(Object charge) {
		return (charge instanceof WinPhoneCharge);
	}

	@Override
	public String handle(Object charge) {
		int returnCode = ((WinPhoneCharge)charge).slowCharge();
		return "success" + returnCode;
	}
}

