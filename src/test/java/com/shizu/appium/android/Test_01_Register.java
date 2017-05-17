package com.shizu.appium.android;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;

import com.shizu.appium.page.android.HomePage;
import com.shizu.appium.page.android.LoginPage;
import com.shizu.appium.page.android.My10zu;
import com.shizu.appium.page.android.Register;
import com.shizu.appium.page.android.RegisterTip;
import com.shizu.appium.page.android.SendVerifyCode;
import com.shizu.appium.utils.CommonUtils;

public class Test_01_Register extends AndroidTestCase {
	@Test
	public void test_01_Register() throws Exception {
		HomePage homePage = PageFactory.initElements(driver, HomePage.class);
		homePage.clickMyButton();

		My10zu my10zu = PageFactory.initElements(driver, My10zu.class);
		my10zu.clickLoginLink();

		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		// 在登录页面点击免费注册链接
		loginPage.tv_register.click();
		Register register = PageFactory.initElements(driver, Register.class);
		register.InputWrongFormatMobilePhone();
		Assert.assertNotNull("输入的手机号格式错误，请重新输入！", register.acceptAgreement);	

		register.InputExistsMobilePhone();

		SendVerifyCode sendVerifyCode = PageFactory.initElements(driver,
				SendVerifyCode.class);
		VerifyRegister(sendVerifyCode);

		String mobilePhone = register.InputUnRegisterMobilePhone();
		VerifyRegister(sendVerifyCode);

		sendVerifyCode.InputPasswordAndRePassword();
		VerifyRegister(sendVerifyCode);

		sendVerifyCode.InputWrongVerifyCode();
		VerifyRegister(sendVerifyCode);

		sendVerifyCode.InputInconsistentPassword(mobilePhone);
		VerifyRegister(sendVerifyCode);
		// 将注册成功的用户手机号码作为公用值
		CommonUtils.USER = mobilePhone;

		sendVerifyCode.InputConsistentPassword();
		RegisterTip registerTip = PageFactory.initElements(driver, RegisterTip.class);
		Assert.assertTrue(registerTip.timerTV.getText().endsWith("秒后将开启您的购物之旅"));
	}
	
	private void VerifyRegister(SendVerifyCode sendVerifyCode){
		sendVerifyCode.clickRegisterButton();
		Assert.assertNotNull("对不起，没有在当前页面找到立即注册按钮！", sendVerifyCode.registerSubmitBtn);
	}	
}
