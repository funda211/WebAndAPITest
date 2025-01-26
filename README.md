# Beymen Web ve Trello API Test

## Proje Açıklaması

Bu proje, Beymen e-ticaret sitesindeki alışveriş akışını test etmek amacıyla geliştirilmiş bir Selenium tabanlı test otomasyon sistemidir. Proje, kullanıcıların alışveriş sepetine ürün ekleme, miktarlarını değiştirme, ürün silme ve sepetin boş olup olmadığını kontrol etme işlemlerini doğrulamak için yazılmıştır. Ayrıca, **Trello API** ile yapılan bazı işlemleri test etmek amacıyla **REST API** entegrasyonunu da içermektedir.

## Teknolojiler

- **Selenium WebDriver**: Web tarayıcılarıyla etkileşim için kullanılır.
- **JUnit**: Test senaryolarının yazılması ve çalıştırılması için kullanılır.
- **WebDriverManager**: Tarayıcı sürücülerini otomatik olarak yönetir.
- **Page Object Model (POM)**: Testlerin bakımını kolaylaştırmak için kullanılan bir tasarım deseni.
- **Apache POI**: Excel dosyalarından veri okumak için kullanılır.
- **Maven**: Proje bağımlılıkları için kullanılan build aracıdır.
- **Trello API**: Trello üzerinde yapılan işlemler için kullanılır.

## Projeye Dahil Olan Sınıflar

- **HomePage**: Ana sayfada yapılan işlemleri içerir (arama, ürün seçimi vb.).
- **CartPage**: Sepet sayfasındaki işlemleri içerir (ürün silme, miktar değiştirme vb.).
- **BeymenTest**: Alışveriş akışını test eden ana test sınıfıdır.
- **BasePage**: Diğer sayfalarda kullanılacak ortak metotları içerir.
- **ExcelUtils**: Excel dosyasından veri okuma işlemi için yardımcı sınıf.
- **TrelloApiTest**: Trello API ile yapılan işlemleri test eder.
- **ProductPage**: Ürün detay sayfasındaki işlemleri içerir (ürün bilgileri görüntüleme, sepete ekleme vb.).
- **PageActionsHandler**: Sayfada yaygın aksiyonları yöneten sınıf (çerez kabul etme, pop-up kapama vb.).
- **SearchResultPage**: Arama sonuçları sayfasındaki işlemleri içerir (ürün seçimi, fiyat kontrolü vb.).
- **BaseTest**: WebDriver yönetimi, test başlatma ve sonlandırma işlemleri gibi test altyapılarını içeren ana sınıf.

## Kurulum

### Gereksinimler

- Java 8 veya daha üstü
- Maven
- WebDriver (Chrome, Firefox vs.)

### Adımlar

1. Bu projeyi [GitHub repository'sinden](https://github.com) klonlayın.
2. Terminal veya komut satırında proje dizinine gidin.
3. Tarayıcı sürücüsünün düzgün çalışabilmesi için **WebDriverManager** otomatik olarak uygun sürücüyü yönetecektir.
4. Maven bağımlılıklarını yüklemek için şu komutu çalıştırın:
    ```bash
    mvn clean install
    ```

## Test Adımları (BeymenTest)

1. **www.beymen.com** sitesi açılır.
2. Ana sayfanın açıldığı kontrol edilir.
3. Arama kutucuğuna **"şort"** kelimesi girilir. (Şort kelimesi Excel dosyasından 1. sütun 1. satırdan alınarak yazılmalıdır.)
4. Arama kutucuğuna girilen **"şort"** kelimesi silinir.
5. Arama kutucuğuna **"gömlek"** kelimesi girilir. (Gömlek kelimesi Excel dosyasından 2. sütun 1. satırdan alınarak yazılmalıdır.)
6. Klavye üzerinden **"enter"** tuşuna basılır.
7. Arama sonuçları sayfasında, sergilenen ürünlerden rastgele bir ürün seçilir.
8. Seçilen ürünün ürün bilgisi ve tutar bilgisi **txt** dosyasına yazılır.
9. Seçilen ürün sepete eklenir.
10. Ürün sayfasındaki fiyat ile sepette yer alan ürün fiyatının doğruluğu karşılaştırılır.
11. Adet arttırılarak ürün adedinin 2 olduğu doğrulanır.
12. Ürün sepetten silinerek, sepetin boş olduğu kontrol edilir.

## Trello API Test Adımları (TrelloApiTest)

1. Trello üzerinde bir **board** oluşturun.
2. Oluşturduğunuz board'a iki tane **kart** oluşturun.
3. Oluşturduğunuz bu iki karttan rastgele bir tanesini güncelleyin.
4. Oluşturduğunuz kartları silin.
5. Oluşturduğunuz **board**'u silin.

### Trello API Bilgileri

- **API Key ve Token** bilgilerini almak için [Trello API sayfası](https://trello.com/app-key)'nı ziyaret edebilirsiniz.
- API'ler ve request'ler ile ilgili detaylı bilgiye [Trello API Dokümantasyonu](https://developer.atlassian.com/cloud/trello/rest/) üzerinden ulaşabilirsiniz.

