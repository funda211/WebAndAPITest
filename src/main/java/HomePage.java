import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.List;

public class HomePage extends BasePage {

    private final By searchBox = By.cssSelector("div.o-header__search input.o-header__search--input");
    private final By clearButton = By.cssSelector("button.o-header__search--close");
    private final By secondSearchBox = By.id("o-searchSuggestion__input");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // Arama kutusunu almak için getter metodu
    public By getSearchBox() {
        return searchBox;
    }

    // Excel dosyasından kelimeleri oku
    public List<String> getKeywordsFromExcel(String filePath) throws Exception {
        String firstKeyword = ExcelUtils.readCell(filePath, 0, 0); // İlk kelime (ör. Şort)
        String secondKeyword = ExcelUtils.readCell(filePath, 1, 0); // İkinci kelime (ör. Gömlek)
        System.out.println("Okunan kelimeler: " + firstKeyword + ", " + secondKeyword);
        return Arrays.asList(firstKeyword, secondKeyword);
    }

    // Arama kutusuna kelime yazma
    public void search(String keyword, boolean isAlreadyInMainContent) {
        if (!isAlreadyInMainContent) {
            switchToMainContent(); // Ana içeriğe geçiş
        }
        waitForElementToBeClickable(searchBox);
        sendKeys(searchBox, keyword); // Arama kutusuna kelimeyi yaz
    }

    // Clear butonunu tıklayarak arama kutusunu temizle
    public void clearSearchBox() {
        WebElement clearButtonElement = driver.findElement(clearButton);
        clearButtonElement.click(); // Clear butonuna tıkla
        // Arama kutusunun tamamen temizlendiğinden emin ol
        wait.until(ExpectedConditions.textToBePresentInElementValue(driver.findElement(searchBox), ""));
    }

    // İlk kelimeyi yaz, temizle, ikinci kelimeyi yaz ve Enter tuşuna bas
    public void searchWithClearAndNewKeyword(String firstKeyword, String secondKeyword, boolean pressEnter, boolean isAlreadyInMainContent) {
        // İlk kelime ile arama yap
        search(firstKeyword, isAlreadyInMainContent);
        clearSearchBox();

        // İkinci arama kutusunu seç ve ikinci kelimeyi yaz
        waitForElementToBeClickable(secondSearchBox);
        sendKeys(secondSearchBox, secondKeyword);

        // Enter tuşuna bas veya tıklama yap
        if (pressEnter) {
            driver.findElement(secondSearchBox).sendKeys(Keys.ENTER);
        } else {
            waitForElementToBeClickable(searchBox);
            click(searchBox); // Alternatif olarak arama kutusunu tıkla
        }


    }

    // iframe'den ana içeriğe geçiş
    void switchToMainContent() {
        try {
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            System.out.println("Ana içeriğe geçiş yapılamadı: " + e.getMessage());
        }
    }
}
