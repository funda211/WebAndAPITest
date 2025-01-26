package APITest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BoardApi extends BaseApi {

    // Board oluşturma metodu
    public Response createBoard(String name) {
        String jsonBody = "{\"name\": \"" + name + "\"}"; // JSON formatında veri oluşturuluyor

        return given()
                .spec(requestSpec) // Ortak request spec'i kullanıyoruz
                .contentType("application/json") // İçerik tipi JSON olarak ayarlanıyor
                .body(jsonBody) // JSON verisi request body'sine ekleniyor
                .post("/boards"); // POST isteği gönderiliyor
    }

    // Board silme metodu
    public Response deleteBoard(String boardId) {
        return given()
                .spec(requestSpec) // Ortak request spec'i kullanıyoruz
                .delete("/boards/" + boardId); // Board ID'sine göre board siliniyor
    }
}
