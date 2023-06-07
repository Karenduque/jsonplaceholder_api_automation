package com.jsonplaceholder.APItesting.tests.guide;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jsonplaceholder.APItesting.model.Resource;
import com.jsonplaceholder.APItesting.utilities.commons.TestGroups;
import com.jsonplaceholder.APItesting.base.testsetup.AbstractBaseTest;
import com.jsonplaceholder.APItesting.utilities.commons.Utilities;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class GuideTest extends AbstractBaseTest {

    private static final String SEPARATOR_PATH = "/";

    @Parameters({"BaseURL", "API_version"})
    public GuideTest(String BaseURL, String apiVersion) {
        super(BaseURL, apiVersion);
    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.GUIDE})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Getting all resources - Happy Path")
    public void getAllResources() {
        String path = "";
        Response response = given().spec(request)
                .get(path);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/guide/Resources-schema.json"));
    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.GUIDE})
    @Severity(SeverityLevel.CRITICAL)
    @Description("add a resource - happy path")
    @Parameters({"title", "body", "userId"})
    public void postAddResource(String title, String body, Integer userId, ITestContext context) {
        Gson gson = new Gson();

        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setBody(body);
        resource.setUserId(userId);

        String bodyRequest = gson.toJson(resource);
        JsonObject bodyPayload = JsonParser.parseString(bodyRequest).getAsJsonObject();
        Response response = given().spec(request)
                .header("Content-type", "application/json; charset=UTF-8")
                .body(bodyPayload.toString())
                .post("");

        response.then().assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body(matchesJsonSchemaInClasspath("schemas/guide/Resources-schema.json"));
    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.GUIDE})
    @Severity(SeverityLevel.CRITICAL)
    @Description("Getting a resource by id- Happy Path")
    @Parameters({"resourceId"})
    public void getJobByOuId(String resourceId) {
        String path = String.format("/%s", resourceId);
        Response response = given().spec(request)
                .get(path);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK);
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/guide/Resources-schema.json"));
    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.GUIDE})
    @Severity(SeverityLevel.NORMAL)
    @Description("Getting a resource by id- Not Found")
    public void getJobByOuIdNotFound() {
        String idRandom = Utilities.getIdRandom();
        String path = String.format("/%s", idRandom);
        Response response = given().spec(request)
                .get(path);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND);
        response.then()
                .body(matchesJsonSchemaInClasspath("schemas/guide/ResourcesBadRequest-schema.json"));

    }

    @Test(groups = {TestGroups.SMOKE, TestGroups.GUIDE})
    @Severity(SeverityLevel.CRITICAL)
    @Description("update a resource - happy path")
    @Parameters({"title", "body", "userId", "resourceId"})
    public void updateResource(String title, String body, Integer userId, Integer resourceId, ITestContext context) {
        Gson gson = new Gson();

        Resource resource = new Resource();
        resource.setTitle(title);
        resource.setBody(body);
        resource.setUserId(userId);
        resource.setId(resourceId);

        String bodyRequest = gson.toJson(resource);
        JsonObject bodyPayload = JsonParser.parseString(bodyRequest).getAsJsonObject();
        String path = String.format("/%s", resourceId);
        Response response = given().spec(request)
                .header("Content-type", "application/json; charset=UTF-8")
                .body(bodyPayload.toString())
                .put(path);

        response.then().assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body(matchesJsonSchemaInClasspath("schemas/guide/Resources-schema.json"));
    }

}
