package appium.tutorial.android.util; // this will need to change

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.net.URL;

public class YouiDriver<T extends WebElement> extends AppiumDriver
{
    private static final int TIMEOUT_SEC = 10; //seconds
    private static final int SLEEPTIME = 500; //ms


	public YouiDriver(URL remoteAddress, Capabilities desiredCapabilities)
	{
        super(remoteAddress, desiredCapabilities);
    }

    @Deprecated
    @Override public MobileElement scrollTo(String text) {
        return (MobileElement) findElement(By.id(text));
    }

    @Deprecated
    @Override public MobileElement scrollToExact(String text) {
        return (MobileElement) findElement(By.id(text));
    }

    /**
     * Return Return true if it finds Element with class className *
     */

    public boolean waitForClassName (String className) throws Exception
    {
        return waitForClassName (className, TIMEOUT_SEC);
    }

    public boolean waitForClassName (String className, int timeoutSec) throws Exception
    {
        boolean foundItem = false;
        long startTime = System.currentTimeMillis(); //fetch starting time

        System.out.print("\n Searching for : " + className);

        while (!foundItem && (System.currentTimeMillis()-startTime) < timeoutSec*1000)
        {
            try{
                foundItem = true;
                this.findElementByClassName(className);
            } catch(NoSuchElementException e) {
                foundItem = false;
                Thread.sleep(SLEEPTIME);
                System.out.print("\n Elapsed miliseconds: " + (System.currentTimeMillis()-startTime));
            }
        }
        System.out.print("\n Elapsed miliseconds: " + (System.currentTimeMillis()-startTime));
        if (foundItem)
            return true;
        else
            return false;
    }

    /**
     * Return true if it finds Element with name itemName *
     */

    public boolean waitForName (String itemName) throws Exception
    {
        return waitForName (itemName, TIMEOUT_SEC);
    }

    public boolean waitForName (String itemName, int timeoutSec) throws Exception
    {
        boolean foundItem = false;
        long startTime = System.currentTimeMillis(); //fetch starting time

        System.out.print("\n Searching for : " + itemName);

        while (!foundItem && (System.currentTimeMillis()-startTime) < timeoutSec*1000)
        {
            try{
                foundItem = true;
                this.findElementByName(itemName);
            } catch(NoSuchElementException e) {
                foundItem = false;
                Thread.sleep(SLEEPTIME);
                System.out.print("\n Elapsed time: " + (System.currentTimeMillis()-startTime));
            }
        }
        System.out.print("\n Elapsed miliseconds: " + (System.currentTimeMillis()-startTime));
        if (foundItem)
            return true;
        else
            return false;
    }
}
