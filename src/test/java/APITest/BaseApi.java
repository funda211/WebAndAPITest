package APITest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BaseApi {
    protected static final String BASE_URL = "https://api.trello.com/1";
    protected static final String KEY = "44f2fa6a0cc36c77d873626f11e03af5";
    protected static final String TOKEN = "ATTA0fa52ca1b2763e649c874e9e3b8ea3af91ce7ce45b5f75f13d7c830bf29e4af20D9EC34C";

    protected RequestSpecification requestSpec;

    public BaseApi() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .addQueryParam("key", KEY)
                .addQueryParam("token", TOKEN)
                .build();
    }
}
