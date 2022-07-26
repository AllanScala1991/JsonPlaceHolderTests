package br.com.jsontests;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Posts {
    private static String BASE_URL;
    
    @BeforeClass
    public static void setup() {
        BASE_URL = "https://jsonplaceholder.typicode.com";
    }

    @Test
    public void t01_getAllPosts() {
        given()
        .when()
            .get(BASE_URL + "/posts")
        .then()
            .statusCode(200)
            .body("id", is(hasSize(100)))
            .body("[0].id", is(notNullValue()))
            .body("[0].userId", is(notNullValue()))
            .body("[0].title", is(notNullValue()))
            .body("[0].body", is(notNullValue()));
    }

    @Test
    public void t02_getOnePost() {
        given()
        .when()
            .get(BASE_URL + "/posts/1")
        .then()
            .statusCode(200)
            .body("userId", is(equalTo(1)))
            .body("id", is(equalTo(1)))
            .body("title", is(notNullValue()))
            .body("body", is(notNullValue()));
            
    }

    @Test
    public void t03_insertNewPost() {
        Map<String, String> post = new HashMap<String, String>();
        post.put("userId", "999");
        post.put("title", "test title");
        post.put("body", "test body");

        given()
            .body(post)
        .when()
            .post(BASE_URL + "/posts")
        .then()
            .body("id", is(notNullValue()))
            .statusCode(201);
    }

    @Test
    public void t04_updatePost() {
        Map<String, String> post = new HashMap<String, String>();
        post.put("title", "updated title");

        given()
            .body(post)
        .when()
            .put(BASE_URL + "/posts/1")
        .then()
            .statusCode(200)
            .body("id", is(equalTo(1)));
    }

    @Test
    public void t05_deletePost() {
        given()
        .when()
            .delete(BASE_URL + "/posts/1")
        .then()
            .body(is("{}"));
    }
}
