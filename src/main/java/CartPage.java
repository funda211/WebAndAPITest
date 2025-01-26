import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class CartPage extends BasePage {

    private final By cartLink = By.cssSelector("a.o-header__userInfo--item.bwi-cart-o");
    private final By quantityDropdown = By.id("quantitySelect0-key-0");
    private final By cardProductPrice = By.cssSelector("span.priceBox__salePrice");
    private final By removeProductButton = By.xpath("//span[text()='Sil']");
    private final By emptyCartMessage = By.cssSelector(".m-empty__messageTitle"); // Sepet boş olduğunda görünen mesajın CSS selector'ü

    public CartPage(WebDriver driver) {
        super(driver);
    }

    // Sepete gitmek için bağlantıya tıklama
    public void goToCart() {
        WebElement cartLinkElement = driver.findElement(cartLink);
        cartLinkElement.click();

        // Sepet sayfasının yüklendiğini doğrulamak için bekleme
        By cartPageIndicator = By.cssSelector("strong.m-basket__productInfoCategory"); // Belirleyici öğe
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPageIndicator));
    }

    // Adet miktarını arttır
    public void setProductQuantity(int quantity) {
        WebElement dropdownElement = driver.findElement(quantityDropdown);

        // Select sınıfını kullanarak değer seç
        Select select = new Select(dropdownElement);
        select.selectByValue(String.valueOf(quantity)); // Miktarı seç (ör. "2")
    }

    // Seçilen miktarı al
    public int getSelectedQuantity() {
        WebElement dropdownElement = driver.findElement(quantityDropdown);
        Select select = new Select(dropdownElement);
        return Integer.parseInt(select.getFirstSelectedOption().getText().split(" ")[0]); // İlk seçili seçeneği al ve int'e çevir
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
        wait.until(ExpectedConditions.elementToBeClickable(removeProductButton)).click(); // Element tıklanabilir olduğunda tıklayın
        wait.until(ExpectedConditions.invisibilityOfElementLocated(removeProductButton)); // Silme işlemi tamamlanana kadar bekleyin
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
