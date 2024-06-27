package com.medicure;


import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Hello world!
 *
 */
@SuppressWarnings("unused")
public class App 
{
    @SuppressWarnings("deprecation")
    public static void main( String[] args )
    {
      // Load driver
      WebDriverManager.chromedriver().setup();

      // Setup configuration
      ChromeOptions chromeOptions = new ChromeOptions();
      chromeOptions.addArguments("--headless=new"); // Use the new headless mode  chromeOptions.addArguments("--disable-gpu"); // Disable GPU for headless mode
      chromeOptions.addArguments("--no-sandbox"); // Bypass OS security model, necessary for Jenkins
      chromeOptions.addArguments("--disable-dev-shm-usage"); // Overcome limited resource problems
      chromeOptions.addArguments("--remote-debugging-port=9222"); // Add remote debugging port
      chromeOptions.addArguments("--disable-extensions"); // Disable extensions
      WebDriver driver = new ChromeDriver(chromeOptions);
      driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
      
      try {
          System.out.println("Script Executing");
          driver.get("http://13.233.216.104:8081/contact.html");

          // Load application
          WebElement nameField = driver.findElement(By.cssSelector("body > div.contact_section.layout_padding > div > div > div > div.col-md-6.padding_left_0 > div > input:nth-child(1)"));
          nameField.sendKeys("praveen");

          WebElement phoneField = driver.findElement(By.cssSelector("body > div.contact_section.layout_padding > div > div > div > div.col-md-6.padding_left_0 > div > input:nth-child(2)"));
          phoneField.sendKeys("9876776659");

          WebElement emailField = driver.findElement(By.cssSelector("body > div.contact_section.layout_padding > div > div > div > div.col-md-6.padding_left_0 > div > input:nth-child(3)"));
          emailField.sendKeys("ss123@gamil.com");

          WebElement messageField = driver.findElement(By.id("comment"));
          messageField.sendKeys("Excellent");

          // Submit button to form
          WebElement submitButton = driver.findElement(By.className("send_bt"));
          submitButton.click();

          // Get response
          String response = driver.findElement(By.id("message")).getText();
          System.out.println(response);

          try{
            if (response.contains("Email Sent")) {
                System.out.println(response + " sent successfully");
            } 
            } catch (Exception e) {
                System.out.println("sent failed");
            
            }
          

          // Take screenshot
          TakesScreenshot scrShot = ((TakesScreenshot) driver);
          File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
          File destFile = new File("target/surefire-reports/test-report.jpg");
          FileUtils.copyFile(scrFile, destFile);

          // Validate response
          // Add your validation logic here, e.g., assert that response contains expected text

      } catch (Exception e) {
          e.printStackTrace();
      } finally {
          driver.quit();
      }   
    }
}
