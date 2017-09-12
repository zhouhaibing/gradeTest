package com.zhb.gradle.test;


public class YouxiduoNotifyRequest {

	private String version; // “1.0.0”（目前为定值，随SDK版本改变）
	private String appId; // 应用Id
	private String paymentId; // 支付单号
	private String orderId; // 在创建支付单时填写的订单编号
	private String productName; // 在创建支付单时填写的充值产品名称
	private String productId; // 在创建支付单时填写的充值产品编号
	private String productExt; // 自定义参数，输入参数是product_ext，返回参数是productExt
	private String userId; // 游戏多用户ID编号
	private float price; // 在创建支付单时填写的充值金额
	private String deliveryCode; // 发货通知编号(使用游戏厂商提供的RSA公钥加密)
	private String checkCode; // 发货通知确认码(使用游戏厂商提供的RSA公钥加密)
	private String sign; // 签名

	public String getVersion() {
		return version;
	}

	public String getAppId() {
		return appId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductId() {
		return productId;
	}

	public String getProductExt() {
		return productExt;
	}

	public String getUserId() {
		return userId;
	}

	public float getPrice() {
		return price;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public String getSign() {
		return sign;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setProductExt(String productExt) {
		this.productExt = productExt;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	
	
}
