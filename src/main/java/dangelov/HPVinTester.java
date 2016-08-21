package dangelov;

import dangelov.Output.ResultCode;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HPVinTester {
    private WebDriver webDriver = new FirefoxDriver();
    private String VIN;
    private String carNumber;
    private String url = "https://historiapojazdu.gov.pl/";

    public HPVinTester(String VIN, String carNumber) {
        this.webDriver.manage().window().setSize(new Dimension(1024, 768));
        this.VIN = VIN;
        this.carNumber = carNumber;
    }

    public static void main(String[] args) {
        HPVinTester infoCarVinTester = new HPVinTester("AY345VV", "zfa19900001397355");
        LocalDate regStartDate = LocalDate.parse("2008-06-03");
        LocalDate regEndDate = LocalDate.parse("2008-06-08");
        infoCarVinTester.find(regStartDate, regEndDate);
    }

    public Output find(LocalDate regStartDate, LocalDate regEndDate) {
        for (LocalDate notFoundMsg = regStartDate; !notFoundMsg.isAfter(regEndDate); notFoundMsg = notFoundMsg.plusDays(1L)) {
            if (this.checkForDate(notFoundMsg.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))) {
                String foundMsg = "Date found: " + notFoundMsg;
                System.out.println(foundMsg);
                return new Output(ResultCode.FOUND, foundMsg);
            }
        }

        String notFoundMsg1 = "Not found for: " + this.VIN + " - " + this.carNumber;
        System.out.println(notFoundMsg1);
        return new Output(ResultCode.NOT_FOUND, notFoundMsg1);
    }

    private boolean checkForDate(String date) {
        this.webDriver.get(this.url);
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:rej")).clear();
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:rej")).sendKeys(new CharSequence[]{this.VIN});
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:vin")).clear();
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:vin")).sendKeys(new CharSequence[]{this.carNumber});
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:data")).clear();
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:data")).sendKeys(new CharSequence[]{date});
        this.webDriver.findElement(By.id("_historiapojazduportlet_WAR_historiapojazduportlet_:btnSprawdz")).click();
        WebElement notFound = (WebElement) (new WebDriverWait(this.webDriver, 10L)).until(ExpectedConditions.visibilityOfElementLocated(By.id("nieZnalezionoHistori")));
        return !notFound.getText().contains("Przepraszamy, ale nie możemy wyświetlić historii wybranego pojazdu");
    }

    public void close() {
        try {
            this.webDriver.close();
        } catch (UnreachableBrowserException var2) {
            System.out.println("Browser most probably was closed");
        }

    }
}
