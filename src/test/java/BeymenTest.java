import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BeymenTest extends BaseTest {

    @Test
    public void testShoppingFlow() throws Exception {
        // Page Object Model sınıflarını başlat
        HomePage homePage = new HomePage(driver);
        SearchResultPage searchResultPage = new SearchResultPage(driver);
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = new CartPage(driver);
        PageActionsHandler pageActionsHandler = new PageActionsHandler(driver);

        // Beymen sitesine git
        driver.get("https://www.beymen.com");

        // Çerezleri kabul et
        pageActionsHandler.acceptCookies();

        // Pop-up'ları kapat
        pageActionsHandler.closePopUp();
        pageActionsHandler.clickCloseButtonInNotification();

        // Ana sayfanın açıldığını kontrol et
        Assertions.assertTrue(driver.getTitle().contains("Beymen"));

        // Excel'den okunan kelimeleri bir listeye al
        List<String> keywords = homePage.getKeywordsFromExcel("src/main/resources/keywords.xlsx");

        // İlk kelimeyi yaz, temizle ve ikinci kelimeyi yaz
        homePage.searchWithClearAndNewKeyword(keywords.get(0), keywords.get(1), true, true);

        // **Arama sonrası ana içerikten çıkış yap**
        homePage.switchToMainContent();

        // Ürün sayısını al ve doğrula
        String productCountText = searchResultPage.getProductCountText();
        int expectedProductCount = searchResultPage.getProductCountFromText(productCountText);
        System.out.println("Başlıkta belirtilen ürün sayısı: " + expectedProductCount);

        // Rastgele bir ürün seç
        WebElement selectedProductLink;
        selectedProductLink = searchResultPage.selectRandomProduct();

        // Ürün sayfasındaki ürün adını al
        String productNameOnDetailPage = productPage.getProductNameFromDetailPage();

        System.out.println("Ürün Adı: " + productNameOnDetailPage);

// Ürün sayfasındaki ürün fiyatını al
        String productPriceOnDetailPage = productPage.getProductPriceFromDetailPage();
        System.out.println("Ürün Fiyatı: " + productPriceOnDetailPage);

   // Ürün bilgilerini dosyaya yaz
        searchResultPage.writeProductInfoToFile(productNameOnDetailPage,productPriceOnDetailPage);

        productPage.selectSize();
        // Ürüne tıklama işlemi
        productPage.addProductToCart();
        cartPage.goToCart();

    // Gereksiz ondalık kısımları temizle
        String cleanedProductPrice = productPriceOnDetailPage.replace(",00", "").trim();
        String cleanedCartPrice = cartPage.getCartProductPrice().replace(",00", "").trim();

    // Doğrudan String karşılaştır
        Assertions.assertEquals(cleanedProductPrice, cleanedCartPrice, "Ürün fiyatı ile sepet fiyatı uyuşmuyor!");

// Ürün adedini 2 yap
        cartPage.setProductQuantity(2);

// Ürün adedinin doğru olduğunu assert ile doğrula
        int actualQuantity = cartPage.getSelectedQuantity();
        int expectedQuantity = 2;
        Assertions.assertEquals(actualQuantity, expectedQuantity, "Ürün adedi doğru değil!");


        // Sepetteki ürünü sil
        cartPage.removeProductFromCart();
        Thread.sleep(10);
        // Sepetin boş olup olmadığını kontrol et
        boolean isCartEmpty = cartPage.isCartEmpty();

        // Sepet boş olmalı
        Assertions.assertTrue(isCartEmpty, "Sepet boş değil!");
        // Sepet işlemleri (yorum satırına alındı)

    }}