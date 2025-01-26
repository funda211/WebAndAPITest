import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));  // 20 saniyelik bir bekleme süresi
    }

    // Elementi tıklamak için bekleme ve tıklama işlemi
    protected void click(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();  // Öğeye tıklama
    }

    // Arama kutusuna metin yazmak için bekleme ve yazma işlemi
    protected void sendKeys(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

        if (element.isDisplayed() && element.isEnabled()) {
            element.clear();  // Önce temizliyoruz
            element.sendKeys(text);  // Sonra yazıyoruz
        } else {
            System.out.println("Element is not visible or enabled.");  // Eğer öğe görünür veya etkin değilse, hata mesajı
        }
    }

    // Öğenin görünür metnini almak
    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    // Öğenin görünür olup olmadığını kontrol etmek
    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            return false;  // Eğer öğe bulunamazsa, false döner
        }
    }

    // JavaScript ile arama kutusuna yazı girer
    protected void sendKeysWithJavaScript(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].focus();", element);
        element.clear();
        element.sendKeys(text);
    }
    // Elementin görünür olmasını bekler
    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }


    // Bir elementin tıklanabilir olmasını bekler
    protected void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Bir elementin sayfada görünür olmasını bekler
    protected void waitForElementToBePresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Check if an element is clickable
    public boolean isElementClickable(By element) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
