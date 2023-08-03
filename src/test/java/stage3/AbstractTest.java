package stage3;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.notNullValue;

public abstract class AbstractTest {
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String correctUsername;
    private static String correctPassword;
    private static String baseUrl;
    private static String order1;
    private static String order2;
    private static String order3;
    private static String page1;
    private static String page2;

    protected static ResponseSpecification responseSpecificationPositive;
    protected static ResponseSpecification responseSpecificationNegative;
    protected static ResponseSpecification responseSpecificationGive;
    protected static RequestSpecification requestSpecification;
    protected static RequestSpecification requestSpecificationGive;



    @BeforeAll
    static void initTest() throws IOException {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);

        correctUsername = prop.getProperty("correctUsername");
        correctPassword = prop.getProperty("correctPassword");
        baseUrl = prop.getProperty("baseUrl");
        order1 = prop.getProperty("order1");
        order2 = prop.getProperty("order2");
        order3 = prop.getProperty("order3");
        page1 = prop.getProperty("page1");
        page2 = prop.getProperty("page2");


        responseSpecificationPositive = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.TEXT)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();
        responseSpecificationNegative = new ResponseSpecBuilder()
                .expectStatusCode(401)
                .expectStatusLine("HTTP/1.1 401 Unauthorized")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .build();
        responseSpecificationGive = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectBody("meta", notNullValue())
                .build();

        requestSpecification = new RequestSpecBuilder()

                .addHeader("Content-Type", "multipart/form-data")
                .setBaseUri(baseUrl + "/gateway/login")
                .log(LogDetail.ALL)
                .build();
        requestSpecificationGive = new RequestSpecBuilder()
                .addQueryParam("sort", "createdAt")

                .setBaseUri(baseUrl + "/api/posts")
                .log(LogDetail.ALL)
                .build();

    }

    public static String getCorrectUsername() {
        return correctUsername;
    }

    public static String getCorrectPassword() {
        return correctPassword;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getOrder1() {
        return order1;
    }
    public static String getOrder2() {
        return order2;
    }
    public static String getOrder3() {
        return order3;
    }
    public static String getPage1() {
        return page1;
    }
    public static String getPage2() {
        return page2;
    }


    public RequestSpecification getRequestSpecification() {
        return requestSpecification;

    }
    public RequestSpecification getRequestSpecificationGive() {
        return requestSpecificationGive;

    }
}
