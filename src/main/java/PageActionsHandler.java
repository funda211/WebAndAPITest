import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PageActionsHandler extends BasePage {

    // Constructor
    public PageActionsHandler(WebDriver driver) {
        super(driver);
    }

    // Çerez kabul etme
    public void acceptCookies() {
        try {
            By acceptButtonLocator = By.id("onetrust-accept-btn-handler");
            click(acceptButtonLocator);
            System.out.println("Çerezler kabul edildi.");
        } catch (Exception e) {
            System.out.println("Çerez kabul butonu bulunamadı veya zaten kabul edilmiş.");
        }
    }

    // Pop-up'ı kapatma (X ikonu veya Kadın butonu)
    public void closePopUp() {
        try {
            By closeButtonLocator = By.xpath("//button[contains(@class, 'close') or @aria-label='Close']");
            click(closeButtonLocator);
            System.out.println("Pop-up kapatıldı (X ikonu).");
        } catch (Exception e) {
            System.out.println("X ikonu bulunamadı. Kadın butonuna tıklanıyor.");
            By womanButtonLocator = By.xpath("//button[contains(text(), 'Kadın')]");
            click(womanButtonLocator);
            System.out.println("Pop-up kapatıldı (Kadın butonu).");
        }
    }

    // Notification üzerindeki X ikonuna tıklama
    public void clickCloseButtonInNotification() {
        try {
            By closeButtonLocator = By.xpath("//button[contains(@aria-label, 'Close') or contains(@class, 'close')]");
            click(closeButtonLocator);
            System.out.println("Bildirim pop-up'ı kapatıldı (X ikonu).");
        } catch (Exception e) {
            System.out.println("X ikonu bulunamadı.");
        }
    }
}
