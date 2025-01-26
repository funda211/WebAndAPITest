import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage extends BasePage {

    private final By cartLink = By.cssSelector("a.o-header__userInfo--item.bwi-cart-o");
    private final By quantityDropdown = By.id("quantitySelect0-key-0");
    private final By cardProductPrice = By.cssSelector("span.priceBox__salePrice");
    private final By removeProductButton = By.xpath("//span[text()='Sil']");
    public final By emptyCartMessage = By.cssSelector(".m-empty__messageTitle"); // Sepet boş olduğunda görünen mesajın CSS selector'ü
    public final By myCart = By.cssSelector("a.o-header__userInfo--item.bwi-cart-o");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void goToCart() {
        // myCart elementinin görünür olmasını bekle
        waitForElementVisible(myCart, 20);
        WebElement cartIcon = driver.findElement(myCart);
        cartIcon.click();
    }

    public void setProductQuantity(int quantity) {
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(quantityDropdown));

        Select select = new Select(dropdownElement);
        select.selectByValue(String.valueOf(quantity));
    }


    // Seçilen miktarı al
    public int getSelectedQuantity() {
        WebElement dropdownElement = driver.findElement(quantityDropdown);
        Select select = new Select(dropdownElement);
        return Integer.parseInt(select.getFirstSelectedOption().getText().split(" ")[0]);
    }

    // Adet miktarını kontrol et
    public boolean verifyProductQuantity(int expectedQuantity) {
        return getSelectedQuantity() == expectedQuantity;
    }

    // Sepet ürün fiyatını al
    public String getCartProductPrice() {
        WebElement cartPriceElement = driver.findElement(cardProductPrice); // Doğru CSS seçiciyi kullanın
        return cartPriceElement.getText().trim();
    }

    public void removeProductFromCart() {
        WebElement removeButton = driver.findElement(removeProductButton);
        removeButton.click();

        // Ürün silindikten sonra, sepetin güncellenmesini bekle
        wait.until(ExpectedConditions.invisibilityOf(removeButton));
    }



    public boolean isCartEmpty() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
