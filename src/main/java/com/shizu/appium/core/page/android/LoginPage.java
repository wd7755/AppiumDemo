package com.shizu.appium.core.page.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {
	/**用户名输入框*/
	@FindBy(id = "username")
	public WebElement username;	
	/**密码输入框*/
	@FindBy(id = "password")
	public WebElement password;		
	/**登录按钮*/
	@FindBy(id = "loginBtn")
	public WebElement loginBtn;	
	/**免费注册链接*/
	@FindBy(id = "tv_register")
	public WebElement tv_register;	
	/**忘记密码链接*/
	@FindBy(id = "tv_forgot_password")
	public WebElement tv_forgot_password;	
	
	public void clickRegisterButton(){
		 //在登录页面点击免费注册链接		
		 this.tv_register.click();	
	}
}
