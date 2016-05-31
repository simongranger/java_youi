package appium.tutorial.android.util;

import appium.tutorial.android.page.HomePage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static appium.tutorial.android.util.Helpers.driver;

public class AppiumTest { 

    static {
        // Disable annoying cookie warnings.
        // WARNING: Invalid cookie header
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    }

    /** Page object references. Allows using 'home' instead of 'HomePage' **/
    protected HomePage home;

    /** wait wraps Helpers.wait **/
    public static WebElement wait(By locator) {
        return Helpers.wait(locator);
    }

    @Rule
    public TestRule printTests = new TestWatcher() {
        protected void starting(Description description) {
            System.out.print("  test: " + description.getMethodName());
        }

        protected void finished(Description description) {
            System.out.println();
        }
    };

    //private String sessionId;

    /** Keep the same date prefix to identify job sets. **/
    private static Date date = new Date();

    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private void setupCaps(Boolean isAndroid, String appPath) {
        capabilities.setCapability("appium-version", "1.5.2");
        capabilities.setCapability("app", appPath);
        if (isAndroid){
            capabilities.setCapability("platformName", "android");
            capabilities.setCapability("deviceName", "Android");
            capabilities.setCapability("avd", "nexus5intel");
            capabilities.setCapability("platformVersion", "6.0.1");
        } else {
            capabilities.setCapability("platformVersion", "9.3");
            capabilities.setCapability("platformName", "youi");
            //capabilities.setCapability("youiAppAddress", "localhost");
            //capabilities.setCapability("youiAppPlatform", "iOS");
            //capabilities.setCapability("deviceName", "iPhone Simulator");
            capabilities.setCapability("deviceName", "localhost");
        }
    }

    /** Run before each test **/
    @Before
    public void setUp() throws Exception {
        Boolean isAndroid = false;

        /** Paths get parsed a little differently for iOS because it looks for Info.plist in other code. **/
        String iOsPath = Paths.get(System.getProperty("user.dir"), "../uswish/samples/VideoPlayer/build/ios/Debug-iphonesimulator/VideoPlayer.app").toAbsolutePath().toString();
        //String androidAppPath = "../uswish/samples/VideoPlayer/build/AndroidNative/bin/VideoPlayer-debug.apk";
        //String androidAppPath = Paths.get(System.getProperty("user.dir"), "Application-debug.apk").toAbsolutePath().toString();
        String androidAppPath = Paths.get(System.getProperty("user.dir"), "api.apk").toAbsolutePath().toString();

        String myAppPath = isAndroid ? androidAppPath : iOsPath;
        setupCaps(isAndroid, myAppPath);

        URL serverAddress;
        serverAddress = new URL("http://127.0.0.1:4723/wd/hub");

        driver = new YouiDriver(serverAddress, capabilities);
        //driver = new AndroidDriver(serverAddress, capabilities);
        //driver = new IOSDriver(serverAddress, capabilities);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        Helpers.init(driver, serverAddress);
    }

    /** Run after each test **/
    @After
    public void tearDown() throws Exception {
        if (driver != null) driver.quit();
    }

}