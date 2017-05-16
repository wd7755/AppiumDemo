package com.shizu.appium.core.page.android;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage {
	/** 首页按钮 */
	@FindBy(id = "btn_home")
	public WebElement btn_home;
	/** 我的10足按钮 */
	@FindBy(id = "btn_my")
	public WebElement btn_my;

	/** 初始化操作：在首页点击我的10足按钮 */
	public void clickMyButton() {
		// 在首页点击我的10足按钮
		this.btn_my.click();
	}
}