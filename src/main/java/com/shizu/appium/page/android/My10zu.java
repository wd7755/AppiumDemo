package com.shizu.appium.page.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class My10zu {
	/**点击登录/注册链接或者昵称显示*/
	@FindBy(id = "user_name")
	public WebElement user_name;		
	
	/**初始化操作：点击注册/登录链接*/
	public void clickLoginLink(){		
		//点击注册/登录链接	
		this.user_name.click();
	}	 
}
