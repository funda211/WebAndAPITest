import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class SearchResultPage extends BasePage {

    private final By productListTitle = By.cssSelector("div.o-productList__top--title span span");
    private final By productItems = By.cssSelector("div.o-productList__container .o-productList__itemWrapper");

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    public String getProductCountText() {
        WebElement resultTitle = driver.findElement(productListTitle);
        return resultTitle.getText();
    }

    public int getProductCountFromText(String text) {
        String countText = text.split("\\(")[1].split("ürün")[0].trim();
        return Integer.parseInt(countText.replace(".", ""));
    }

    public WebElement selectRandomProduct() {
        List<WebElement> products = driver.findElements(productItems);
        if (products.isEmpty()) {
            throw new IllegalStateException("No products found on the page!");
        }
        int randomIndex = (int) (Math.random() * products.size());
        WebElement randomProduct = products.get(randomIndex);
        wait.until(ExpectedConditions.elementToBeClickable(randomProduct));
        randomProduct.click();
        return randomProduct;
    }

    public void writeProductInfoToFile(String productName, String productPrice) {
        String fileName = Paths.get("src", "main", "resources", "productInfo.txt").toString();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false))) {
            writer.write("Ürün Adı: " + productName);
            writer.newLine();
            writer.write("Ürün Fiyatı: " + productPrice);
            writer.newLine();
            writer.write("-----------------------------");
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }
}
