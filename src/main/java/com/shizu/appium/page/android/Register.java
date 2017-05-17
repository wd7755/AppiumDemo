package com.shizu.appium.page.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.shizu.appium.utils.CommonUtils;

public class Register {
	/** 手机号码输入框 */
	@FindBy(id = "mobPhone")
	public WebElement mobPhone;
	/** 下一步按钮 */
	@FindBy(id = "registerSubmitBtn")
	public WebElement registerSubmitBtn;
	/** 接受协议复选框 */
	@FindBy(id = "acceptAgreement")
	public WebElement acceptAgreement;

	/** 输入错误格式的手机号码 */
	public void InputWrongFormatMobilePhone() {
		// 在注册页面输入格式错误的手机号码
		this.mobPhone.sendKeys("1301234567");
		// 点击下一步按钮
		this.registerSubmitBtn.click();
	}
	/** 验证输入已注册的手机号码 */
	public void InputExistsMobilePhone() {
		String phoneNumber = CommonUtils.getRegistrationUser();
		this.mobPhone.clear();
		// 输入已存在的手机号
		this.mobPhone.sendKeys(phoneNumber);
	}
	/** 验证输入尚未注册的手机号码 */
	public String InputUnRegisterMobilePhone() {
		String phoneNumber = CommonUtils.getNoRegisrationUser();
		this.mobPhone.clear();
		// 输入尚未注册的手机号
		this.mobPhone.sendKeys(phoneNumber);
		return phoneNumber;
	}
}
