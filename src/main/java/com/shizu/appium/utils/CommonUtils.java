package com.shizu.appium.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * 工具类，提供公用方法 *
 */
public class CommonUtils {
	/**
	 * 常量：休眠时间，180秒
	 */
    public static final int SLEEP = 180;
	/**
	 * 默认登录用户：17774909952
	 */
    public static String USER = "17774909952";
	/**
	 * 默认登录密码：123456
	 */
    public static final String PASSWORD = "123456";

    private static String sql;
    private static final String[] telFirst = "134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153,180,181,182,183,186,187,188,189,170,171"
			.split(",");

	/** 取得已注册的任一会员的sql */
    private static final String getRegistrationUserSql = "select mobile_phone from core.t_member m where mobile_phone is not null "
			+ "and mobile_phone like '13%' and not exists(select 1 from core.t_agent where m.id=id) and not exists"
			+ "(select 1 from log.t_punishment where target_type='E' and begin_time<=now() and end_time>=now() "
			+ "and cancel_time is null and disabled_authority!='NT' and target_id=m.id) order by RAND() limit 1";
	
    /**
     * 获取已注册的任一会员的手机号码
     * @return 手机号码
     */
    public static String getRegistrationUser() {
        return OperationDB.queryData(getRegistrationUserSql);
    }

    private static int getNum(int start, int end) {
    	return (int) (Math.random() * (end - start + 1) + start);
    }

    private static String getRandomMobilePhone() {
    	int index = getNum(0, telFirst.length - 1);
    	String first = telFirst[index];
    	String second = String.valueOf(getNum(1, 888) + 10000).substring(1);
    	String thrid = String.valueOf(getNum(1, 9100) + 10000).substring(1);
    	return first + second + thrid;
    }

	/**
	 * 获取尚未注册的任一会员的手机号码
	 * @return 手机号码
	 */
    public static String getNoRegisrationUser() {
    	String newUser = "";
    	while (true) {
            newUser = getRandomMobilePhone();
            sql = "select count(*) from core.t_member where mobile_phone='"
            		+ newUser + "'";
            if (OperationDB.queryData(sql).equals("0")) {
            	return newUser;
            }
    	}
    }

	/**
	 * @param user 登录名，也就是其手机号码
	 * @return 该会员的昵称
	 */
    public static String getUserNickName(String user) {
    	sql = "select nickname from t_member where mobile_phone=?";
    	return OperationDB.queryData(sql, new String[] { user });
    }
	
	/**
	 * 获取验证码
	 * @param content 验证码关键内容
	 * @return 验证码
	 */
    public static String getVerificationCode(String content) {
    	sql = "SELECT code FROM t_verification WHERE content LIKE '%" + content
    			+ "%' ORDER BY id DESC LIMIT 1";
    	return OperationDB.queryData(sql);
    }
    
   
    /**
     *  获取屏幕快照 
     * @param driver WebDriver
     * @param savePath 截图文件名
     * @return 截图保存路径
     */
    public static String takeScreenshot(WebDriver driver, String savePath) {

        File classpathRoot = new File(System.getProperty("user.dir"));

        String path = classpathRoot + "/screenshots/" + savePath;
        try {
            File screenShotFile = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            if (!savePath.endsWith("/.png")) {
                path = path + ".png";
            }

            FileUtils.copyFile(screenShotFile, new File(path));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return path;
    }
	
 
    /**
     * 获取页面元素是否显示 
     * @param driver WebDriver
     * @param by 元素查找方式
     * @return 找到的元素，没有找到返回空
     */
    public static WebElement elementIsPresented(WebDriver driver, final By by) {
        Wait<WebDriver> wait = new WebDriverWait(driver, SLEEP);
        try {
            WebElement element = wait.until(visibilityOfElementLocated(by));
            return element;
        } catch (NoSuchElementException e) {
            return null;
        }
    }    
   
    /**
     *  获取执行的方法的对应的类的名称和方法名 
     * @return 执行的方法的对应的类的名称和方法名 
     */
    public static String getTraceInfo() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        sb.append(stacks[1].getClassName()).append(".")
                .append(stacks[1].getMethodName());
        return sb.toString();
    }
    
    private static ExpectedCondition<WebElement> visibilityOfElementLocated(
                    final By by) {
        return new ExpectedCondition<WebElement>() {
            public WebElement apply(WebDriver driver) {
                WebElement element = driver.findElement(by);
                return element.isDisplayed() ? element : null;
            }
        };
    }
    
    /**
     * 验证元素是否存在
     * @param el 需要验证的元素
     * @return true or false
     */
    public static boolean elementPresentedstate(WebElement el) {		
    	try {
    	    el.isDisplayed();
    	    return true;
    	} catch (Exception e) {
    	    return false;
    	}
    }
    
    /**
     * 休眠时间 
     * @param time 时间，单位为毫秒
     * @throws Exception 可能抛出异常
     */
    public static void sleep(int time) throws Exception {
    	Thread.sleep(time);
    }    
  
    /**
     *  登录验证
     * @param driver WebDriver
     */
    public static void verifyLogin(WebDriver driver) {
        // 打开登录页面
        driver.findElement(By.id("btn_my")).click();
        driver.findElement(By.id("user_name")).click();
        // 登录
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys(USER);
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys(PASSWORD);
        WebElement loginButton = driver.findElement(By.id("loginBtn"));
        loginButton.click();

        // 验证登录成功
        WebElement user_name = driver.findElement(By.id("user_name"));
        // 取得会员昵称
        String nickName = getUserNickName(USER);
        Assert.assertEquals(nickName, user_name.getText());
    }
}
