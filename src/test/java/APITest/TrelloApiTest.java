package APITest;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static io.restassured.RestAssured.given; // RestAssured import'u

public class TrelloApiTest {

    private BoardApi boardApi;
    private CardApi cardApi;

    @BeforeEach
    public void setup() {
        boardApi = new BoardApi();
        cardApi = new CardApi();
    }

    @Test
    public void testTrelloApiFlow() {
        // 1. Board oluştur
        Response createBoardResponse = boardApi.createBoard("Test Board");
        Assertions.assertNotNull(createBoardResponse, "Board creation response is null");
        Assertions.assertEquals(200, createBoardResponse.statusCode(), "Failed to create board");

        String boardId = createBoardResponse.jsonPath().getString("id");

        // 2. Board'da bir liste alın
        Response getListsResponse = given()
                .spec(boardApi.requestSpec)
                .get("/boards/" + boardId + "/lists");
        Assertions.assertNotNull(getListsResponse, "Get lists response is null");
        Assertions.assertEquals(200, getListsResponse.statusCode(), "Failed to get lists");

        String listId = getListsResponse.jsonPath().getString("[0].id");

        // 3. İki kart oluştur
        Response createCard1 = cardApi.createCard(listId, "Card 1");
        Response createCard2 = cardApi.createCard(listId, "Card 2");

        // Kontroller
        Assertions.assertNotNull(createCard1, "Card 1 creation response is null");
        Assertions.assertEquals(200, createCard1.statusCode(), "Failed to create Card 1");
        Assertions.assertNotNull(createCard2, "Card 2 creation response is null");
        Assertions.assertEquals(200, createCard2.statusCode(), "Failed to create Card 2");

        String cardId1 = createCard1.jsonPath().getString("id");
        String cardId2 = createCard2.jsonPath().getString("id");

        // 4. Rastgele bir kartı güncelle
        String randomCardId = new Random().nextBoolean() ? cardId1 : cardId2;
        Response updateCardResponse = cardApi.updateCard(randomCardId, "Updated Card");
        Assertions.assertNotNull(updateCardResponse, "Card update response is null");
        Assertions.assertEquals(200, updateCardResponse.statusCode(), "Failed to update card");

        // 5. Kartları sil
        Assertions.assertEquals(200, cardApi.deleteCard(cardId1).statusCode(), "Failed to delete Card 1");
        Assertions.assertEquals(200, cardApi.deleteCard(cardId2).statusCode(), "Failed to delete Card 2");

        // 6. Board'u sil
        Assertions.assertEquals(200, boardApi.deleteBoard(boardId).statusCode(), "Failed to delete board");
    }
}
