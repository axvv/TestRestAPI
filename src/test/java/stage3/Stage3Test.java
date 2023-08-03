package stage3;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class Stage3Test extends AbstractTest {
    @Test
//    задание 1.1.1
    void postPositiveTest() {
        given().spec(getRequestSpecification())
                .when()
                .multiPart("username", "" + getCorrectUsername())
                .multiPart("password", "" + getCorrectPassword())
                .post()
                .then()
                .spec(responseSpecificationPositive);
    }

    @Test
        //    задание 1.1.2
    void postNegativeTest() {
//        передаём пустые поля формы
        given().spec(getRequestSpecification())
                .when()
                .multiPart("username", "")
                .multiPart("password", "")
                .post()
                .then()
                .spec(responseSpecificationNegative);
    }

    @Test
        //    задание 1.1.3
    void postNegativeUpperCaseTest() {
//        передаём имя пользователя прописными
        given().spec(getRequestSpecification())
                .when()
                .multiPart("username", "" + (getCorrectUsername().toUpperCase()))
                .multiPart("password", "")
                .post()
                .then()
                .spec(responseSpecificationNegative);
    }

    @Test
        //    задание 1.2.1
    void getASCPositiveTest1() {
        String token = getToken();

//        запрос на выдачу чужих постов сортировка ASC
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("owner", "notMe")
                .queryParam("order", getOrder1())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }

    @Test
        //    задание 1.2.2
    void getDESCPositiveTest() {
        String token = getToken();
        //        запрос на выдачу чужих постов сортировка DESC
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("owner", "notMe")
                .queryParam("order", getOrder2())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }

    @Test
        //    задание 1.2.3
    void getALLPositiveTest() {
        String token = getToken();
        //        запрос на выдачу чужих постов сортировка ALL
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("owner", "notMe")
                .queryParam("order", getOrder3())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }

    @Test
        //    задание 1.2.4
    void getASC_pagePositiveTest() {
        String token = getToken();
        //        запрос на выдачу чужих постов сортировка ASC страница 10000
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("owner", "notMe")
                .queryParam("order", getOrder1())
                .queryParam("page", getPage2())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }

    @Test
        //    задание 1.2.5
    void getASC_BigNumberPageNegativeTest() {
        String token = getToken();
        //        запрос на выдачу чужих постов сортировка ASC страница 10000000100000001000000010000000
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("owner", "notMe")
                .queryParam("order", getOrder1())
                .queryParam("page", (getPage2() + getPage2() + getPage2() + getPage2()))
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }

    @Test
        //    задание 1.2.6
    void getASC_pageZeroNegativeTest() {
        String token = getToken();
        //        запрос на выдачу чужих постов сортировка ASC страница 0
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("owner", "notMe")
                .queryParam("order", getOrder1())
                .queryParam("page", 0)
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }
    @Test
    //    задание 1.3.1
    void getASCMePostsPositiveTest() {
        String token = getToken();
        //        запрос на выдачу своих постов сортировка ASC страница 1
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("order", getOrder1())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }
    @Test
        //    задание 1.3.2
    void getDESCMePostsPositiveTest() {
        String token = getToken();
        //        запрос на выдачу своих постов сортировка DESC страница 1
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("order", getOrder2())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }
    @Test
        //    задание 1.3.3
    void getALLMePostsPositiveTest() {
        String token = getToken();
        //        запрос на выдачу своих постов сортировка ALL страница 1
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("order", getOrder3())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }
    @Test
        //    задание 1.3.4
    void getDESC_pageMePostsNegativeTest() {
        String token = getToken();
        //        запрос на выдачу своих постов сортировка DESC страница -1
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("order", getPage2())
                .queryParam("page", -1)
                .when()
                .get()
                .then()
                .statusCode(500);
    }
    @Test
        //    задание 1.3.5
    void getEmptyPageMePostsPositiveTest() {
        String token = getToken();
        //        запрос на выдачу своих постов сортировка ASC пустая страница
        given().spec(requestSpecificationGive)
                .headers("X-Auth-Token", token)
                .queryParam("order", getOrder1())
                .queryParam("page", getPage2())
                .when()
                .get()
                .then()
                .spec(responseSpecificationGive);
    }
    @Test
        //    задание 1.3.6
    void getUnauthorizedPositiveTest() {
        //        запрос неавторизованного пользователя на выдачу своих постов сортировка ASC
        given().spec(requestSpecificationGive)
                .queryParam("order", getOrder1())
                .queryParam("page", getPage1())
                .when()
                .get()
                .then()
                .assertThat()
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    private String getToken() {
        Response response = given().spec(getRequestSpecification())
                .when()
                .multiPart("username", "" + getCorrectUsername())
                .multiPart("password", "" + getCorrectPassword())
                .post()
                .then()
                .extract().response();
        String token = response.jsonPath().get("token").toString();
        return token;
    }

}


