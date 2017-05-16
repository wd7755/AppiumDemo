package com.shizu.appium.core.page.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.shizu.appium.utils.CommonUtils;

public class SendVerifyCode {
	/**发送验证码按钮*/
	@FindBy(id = "timerBtn")
	public WebElement timerBtn;		
	/**密码输入框*/
	@FindBy(id = "password")
	public WebElement password;		     
	/**确认密码输入框*/
	@FindBy(id = "confirm_password")
	public WebElement confirm_password;	   
    /**立即注册按钮*/
	@FindBy(id = "registerSubmitBtn")
	public WebElement registerSubmitBtn;	
	/**验证码输入框*/
	@FindBy(id = "codeET")
	public WebElement codeET;	
	
	public void clickRegisterButton(){
	   	 this.registerSubmitBtn.click();	
	}	
	 /**验证保持短信验证码为空*/
	public void InputPasswordAndRePassword(){		 
		 //点击发送验证码按钮
		 this.timerBtn.click();
		 //输入一致的密码和确认密码
		 this.password.sendKeys(CommonUtils.PASSWORD);
		 this.confirm_password.sendKeys(CommonUtils.PASSWORD);		
	}
	/**验证输入错误的短信验证码*/
	public void InputWrongVerifyCode(){		
		 this.codeET.sendKeys("1234");		
	}
	 /**验证输入不一致的密码和确认密码*/
	public void InputInconsistentPassword(String mobilePhone){	
		 //取得正确的验证码
		 String code = CommonUtils.getVerificationCode(mobilePhone);	
		 this.codeET.clear();
		 //输入正确的验证码
		 this.codeET.sendKeys(code);		
		 //输入不一致的密码		
		 this.confirm_password.sendKeys(CommonUtils.PASSWORD + "7");	 
	 }	
	  /**验证注册成功*/
	public void InputConsistentPassword(){		
		 //输入一致的密码
		 this.password.clear();
		 this.password.sendKeys(CommonUtils.PASSWORD);
		 this.confirm_password.clear();
		 this.confirm_password.sendKeys(CommonUtils.PASSWORD);		 
		 //点击立即注册按钮
		 clickRegisterButton();
	}	 
}
