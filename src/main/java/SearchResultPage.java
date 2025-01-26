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
    private final By productItems = By.cssSelector("div.o-productList__container .o-productList__itemWrapper");  // Ürün öğelerini doğru şekilde seç

    public SearchResultPage(WebDriver driver) {
        super(driver);
    }

    // Arama sonucu başlığını al
    public String getProductCountText() {
        WebElement resultTitle = driver.findElement(productListTitle);
        return resultTitle.getText(); // "gömlek araması için (3.037 ürün) bulundu."
    }

    // Ürün sayısını almak
    public int getProductCountFromText(String text) {
        String countText = text.split("\\(")[1].split("ürün")[0].trim();
        return Integer.parseInt(countText.replace(".", "")); // Sayıyı döndürür
    }

    // Rastgele bir ürünü seç
    public WebElement selectRandomProduct() {
        List<WebElement> products = driver.findElements(productItems);


        if (products.isEmpty()) {
            throw new IllegalStateException("No products found on the page!");
        }

        int randomIndex = (int) (Math.random() * products.size()) + 1; // +1 çünkü nth-child 1'den başlar
        String randomProductSelector = "div.o-productList__container .o-productList__itemWrapper:nth-child(" + randomIndex + ")";

        // Rastgele ürünü bul ve tıklanabilirliğini bekle
        WebElement randomProduct = driver.findElement(By.cssSelector(randomProductSelector));

        // Ürüne tıklanabilir olana kadar bekle
        wait.until(ExpectedConditions.elementToBeClickable(randomProduct));

        // Ürüne tıkla
        randomProduct.click();

        return randomProduct;
    }

    // Ürün bilgilerini dosyaya yazma metodu
    public void writeProductInfoToFile(String productName, String productPrice) {

        String fileName = Paths.get("src", "main", "resources", "productInfo.txt").toString();

        File file = new File(fileName);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            // Dosyayı yazma işlemi
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                writer.write("Ürün Adı: " + productName);
                writer.newLine();
                writer.write("Ürün Fiyatı: " + productPrice);
                writer.newLine();
                writer.write("-----------------------------");
                writer.newLine();

                writer.flush();
                System.out.println("Ürün bilgisi başarıyla kaydedildi!");
            }
        } catch (IOException e) {
            System.err.println("Dosyaya yazma hatası: " + e.getMessage());
        }
    }
}
