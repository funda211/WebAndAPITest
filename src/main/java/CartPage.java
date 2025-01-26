import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {


    private final By quantityDropdown = By.id("quantitySelect0-key-0");
    private final By cartProductPrice = By.cssSelector("span.priceBox__salePrice");
    private final By removeProductButton = By.xpath("//span[text()='Sil']");
    public final By emptyCartMessage = By.cssSelector(".m-empty__messageTitle");
    public final By myCart = By.cssSelector("a.o-header__userInfo--item.bwi-cart-o");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Sepete git
    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(myCart));
        cartIcon.click();
    }

    // Ürün adedini seç
    public void setProductQuantity(int quantity) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Dropdown öğesinin tıklanabilir olduğundan emin olun
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(quantityDropdown));

        try {
            // Select sınıfını kullanarak değer seç
            Select select = new Select(dropdownElement);
            select.selectByValue(String.valueOf(quantity));
        } catch (Exception e) {
            System.out.println("Normal yöntemle seçim başarısız, JavaScript ile deneniyor.");

            // JavaScript kullanarak seçim yap
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].value='" + quantity + "'; arguments[0].dispatchEvent(new Event('change'));", dropdownElement);
        }
    }

    // Seçilen miktarı al
    public int getSelectedQuantity() {
        WebElement dropdownElement = driver.findElement(quantityDropdown);
        Select select = new Select(dropdownElement);
        String selectedText = select.getFirstSelectedOption().getText();

        // "1 adet" formatını ayıklamak için split kullan
        return Integer.parseInt(selectedText.split(" ")[0]);
    }

    // Sepet ürün fiyatını al
    public String getCartProductPrice() {
        WebElement cartPriceElement = driver.findElement(cartProductPrice);
        return cartPriceElement.getText().trim();
    }

    // Sepetteki ürünü sil
    public void removeProductFromCart() {
        WebElement removeButton = driver.findElement(removeProductButton);
        removeButton.click();

        // Ürün silindikten sonra, sepetin güncellenmesini bekle
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.invisibilityOf(removeButton));
    }

    // Sepetin boş olup olmadığını kontrol et
    public boolean isCartEmpty() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
