package APITest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CardApi extends BaseApi {

    public Response createCard(String listId, String cardName) {
        String jsonBody = "{\"name\": \"" + cardName + "\", \"idList\": \"" + listId + "\"}";

        // POST isteği ile kart oluşturuluyor ve Response dönüyor
        return given()
                .spec(requestSpec)
                .contentType("application/json")
                .body(jsonBody)
                .post("/cards");
    }


    public Response updateCard(String cardId, String newName) {
        return given()
                .spec(requestSpec)
                .queryParam("name", newName)
                .put("/cards/" + cardId);
    }

    public Response deleteCard(String cardId) {
        return given()
                .spec(requestSpec)
                .delete("/cards/" + cardId);
    }
}
