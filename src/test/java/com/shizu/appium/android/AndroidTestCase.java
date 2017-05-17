package com.shizu.appium.android;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.shizu.appium.page.android.HomePage;
import com.shizu.appium.utils.CommonUtils;

import io.appium.java_client.android.AndroidDriver;

/**
 * PO模式Android Testcase的基类
 */
public class AndroidTestCase {
	protected static WebDriver driver;
	private static final int WAIT_SECONDS = 60 * 1000 * 5;

	/**
	 * 获取可用的driver
	 * 
	 * @return WebDriver
	 */
	@SuppressWarnings("rawtypes")
	public static WebDriver getDriver() {
		if (driver == null) {
			// set up appium
			ClassLoader classLoader = AndroidTestCase.class.getClassLoader();
			URL resource = classLoader.getResource("/app/shizu.apk");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("deviceName", "Android Emulator");
			capabilities.setCapability("platformVersion", "4.3.1");
			capabilities.setCapability("app", resource.getPath());
			capabilities.setCapability("unicodeKeyboard", "true");
			capabilities.setCapability("resetKeyboard", "true");
			try {
				driver = new AndroidDriver(new URL(
						"http://10.1.1.144:4723/wd/hub"), capabilities);
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
		needUpgrade();
		HomePage homePage = new HomePage();
		Assert.assertNotNull("对不起，没有找到首页按钮！", homePage.btn_home);
		Assert.assertEquals(homePage.btn_home.getText(), "首页");
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

	/**
	 * 类所有测试方法开始执行前执行该方法：用于启动模拟器并设置等待时间5分钟
	 * 
	 * @throws Exception
	 *             可能抛出异常
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Runtime.getRuntime().exec(new String[] { "emulator", "@Android-18" });
		// 等待5分钟时间用于启动uiautomator
		Thread.sleep(WAIT_SECONDS);
	}

	/**
	 * 类所有测试方法结束执行后执行该方法：用于杀死进程模拟器和adb
	 * 
	 * @throws Exception
	 *             可能抛出异常
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		Runtime.getRuntime().exec(new String[] { "tskill", "emulator-arm" });
		Runtime.getRuntime().exec(new String[] { "tskill", "adb" });
	}

	/**
	 * 检测是否需要升级，如果提示可以升级但不进行升级
	 */
	private void needUpgrade() {
		boolean needUpgrade = false; // 检测到10足商城客户端的新版本
		if (needUpgrade) {
			WebElement el = driver.findElement(By.name("暂不升级"));
			el.click();
		}
	}
}
