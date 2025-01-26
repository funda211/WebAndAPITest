import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductPage extends BasePage {

    private final By addToCartButton = By.id("addBasket");
    private final By sizeOption = By.cssSelector("span.m-variation__item"); // Beden seçimi için CSS selector
    private final By productName = By.cssSelector("span.o-productDetail__description");
    private final By productPrice = By.id("priceNew");

    public ProductPage(WebDriver driver) {
        super(driver);
    }

    // Bedeni seçmek
    public void selectSize() {
        // Tüm beden seçeneklerini al
        List<WebElement> sizeOptions = driver.findElements(By.cssSelector(".m-variation__item"));

        for (WebElement option : sizeOptions) {
            if (option.isDisplayed() && !option.getAttribute("class").contains("-disabled")) {
                option.click();
                System.out.println("Seçilen beden: " + option.getText());
                return;
            }
        }

        throw new IllegalStateException("Hiçbir uygun beden bulunamadı!");
    }

    // Sepete ürün ekle
    public void addProductToCart() {
        // Sepete ekle butonunu bekleyip tıklama işlemi
        WebElement addToCartButtonElement = driver.findElement(addToCartButton);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonElement)); // Buton tıklanabilir olana kadar bekle
        addToCartButtonElement.click(); // Sepete ekle butonuna tıklama
    }

    // Ürün fiyatını almak
    public String getProductPrice() {
        WebElement priceElement = driver.findElement(productPrice);
        return priceElement.getText();
    }

    // Ürün sayfasındaki ürün adını almak
    public String getProductNameFromDetailPage() {
        WebElement productNameElement = driver.findElement(productName);
        return productNameElement.getText();
    }

    // Ürün sayfasındaki ürün fiyatını almak
    public String getProductPriceFromDetailPage() {
        WebElement productPriceElement = driver.findElement(productPrice);
        return productPriceElement.getText();
    }
}
