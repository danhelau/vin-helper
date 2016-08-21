package dangelov;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class InfoCarVinTester {
    private WebDriver webDriver = new FirefoxDriver();
    private String VIN;
    private String carNumber;
    private String url = "https://info-car.pl/infocar/vin.html";

    public InfoCarVinTester(String VIN, String carNumber) {
        this.webDriver.manage().window().setSize(new Dimension(1024, 768));
        this.webDriver.manage().timeouts().pageLoadTimeout(10L, TimeUnit.SECONDS);
        this.VIN = VIN;
        this.carNumber = carNumber;
    }

    private void checkForDate(String date) {
        this.webDriver.navigate().to(url);
        this.webDriver.findElement(By.id("registrationNumber")).sendKeys(VIN);
        this.webDriver.findElement(By.id("vinNumber")).sendKeys(carNumber);
        this.webDriver.findElement(By.id("firstRegistrationDate")).sendKeys(date);
        this.webDriver.findElement(By.id("reg1")).click();
        this.webDriver.findElement(By.id("checkButton")).submit();
        WebElement notFound = (new WebDriverWait(this.webDriver, 10L)).until(ExpectedConditions.visibilityOfElementLocated(By.id("notFound")));
        if (notFound.getText().contains("Sprawdź VIN: Pojazd nie został znaleziony")) {
            System.out.println("fail to found");
        } else {
            System.out.println("found for " + date);
        }

    }
}
