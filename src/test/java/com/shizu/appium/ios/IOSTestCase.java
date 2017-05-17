package com.shizu.appium.ios;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import com.shizu.appium.utils.CommonUtils;
import io.appium.java_client.ios.IOSDriver;

/**
 * PO模式iOS Testcase的基类
 *
 */
public class IOSTestCase {
	protected static WebDriver driver;

	/**
	 * 获取可用的driver
	 * 
	 * @return WebDriver
	 */
	@SuppressWarnings("rawtypes")
	public static WebDriver getDriver() {
		if (driver == null) {
			// set up appium
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "iPhone 5s");
			capabilities.setCapability("PLATFORM_NAME", "IOS");
			capabilities.setCapability("platformVersion", "9.1");
			capabilities
					.setCapability("app",
							"/Users/admin/10zu_ios/trunk/head/ShiZu/build/Debug-iphonesimulator/szapp.app");
			capabilities.setCapability("waitForAppScript", "true");
			try {
				driver = new IOSDriver(new URL(
						"http://192.168.8.237:4723/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return driver;
	}

	/**
	 * 每个测试方法开始执行前执行该方法：获取driver并利用其打开首页
	 * 
	 * @throws Exception
	 *             可能抛出异常
	 */
	@Before
	public void setUp() throws Exception {
		driver = getDriver();
		driver.manage().timeouts()
				.implicitlyWait(CommonUtils.SLEEP, TimeUnit.SECONDS);
	}

	/**
	 * 每个测试方法结束执行后执行该方法：退出driver并置空
	 * 
	 * @throws Exception
	 *             可能抛出异常
	 */
	@After
	public void tearDown() throws Exception {
		driver.quit();
		driver = null;
	}
}
