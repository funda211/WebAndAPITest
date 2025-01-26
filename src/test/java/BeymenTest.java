import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BeymenTest extends BaseTest {

    @Test
    public void testShoppingFlow() throws Exception {
        // Sayfa nesnelerini başlat
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

        // Excel'den kelimeleri al
        List<String> keywords = homePage.getKeywordsFromExcel("src/main/resources/keywords.xlsx");

        // İlk kelimeyi yaz, temizle ve ikinci kelimeyi yaz
        homePage.searchWithClearAndNewKeyword(keywords.get(0), keywords.get(1), true, true);

        // Arama sonrası ana içerikten çıkış yap
        homePage.switchToMainContent();

        // Ürün sayısını al ve doğrula
        String productCountText = searchResultPage.getProductCountText();
        int expectedProductCount = searchResultPage.getProductCountFromText(productCountText);
        System.out.println("Başlıkta belirtilen ürün sayısı: " + expectedProductCount);

        // Rastgele bir ürün seç ve ürüne git
        WebElement selectedProductLink = searchResultPage.selectRandomProduct();

        // Ürün adını ve fiyatını al
        String productNameOnDetailPage = productPage.getProductNameFromDetailPage();
        String productPriceOnDetailPage = productPage.getProductPriceFromDetailPage();

        System.out.println("Ürün Adı: " + productNameOnDetailPage);
        System.out.println("Ürün Fiyatı: " + productPriceOnDetailPage);

        // Ürün bilgilerini dosyaya yaz
        searchResultPage.writeProductInfoToFile(productNameOnDetailPage, productPriceOnDetailPage);

        // Beden seç ve ürünü sepete ekle
        productPage.selectSize();
        productPage.addProductToCart();

        // Sepete git
        cartPage.goToCart();

        // Sepet fiyatı ile ürün fiyatını karşılaştır
        String cleanedProductPrice = productPriceOnDetailPage.replace(",00", "").trim();
        String cleanedCartPrice = cartPage.getCartProductPrice().replace(",00", "").trim();
        Assertions.assertEquals(cleanedProductPrice, cleanedCartPrice, "Ürün fiyatı ile sepet fiyatı uyuşmuyor!");

        // Ürün adedini 2 yap ve doğrula
        cartPage.setProductQuantity(2);
        Assertions.assertEquals(2, cartPage.getSelectedQuantity(), "Ürün adedi doğru değil!");

        // Sepetteki ürünü sil
        cartPage.removeProductFromCart();

        // Sepetin boş olup olmadığını kontrol et
        wait.until(ExpectedConditions.visibilityOfElementLocated(cartPage.emptyCartMessage));
        Assertions.assertTrue(cartPage.isCartEmpty(), "Sepet boş değil!");
    }
}
