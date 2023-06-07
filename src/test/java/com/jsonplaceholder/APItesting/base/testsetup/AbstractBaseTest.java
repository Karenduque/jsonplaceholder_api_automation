package com.jsonplaceholder.APItesting.base.testsetup;


import com.jsonplaceholder.APItesting.utilities.service.ServicePath;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.net.MalformedURLException;

public abstract class AbstractBaseTest {
    protected RequestSpecification request;

   public AbstractBaseTest(String baseUrl, String apiVersion){
        try {
            RestAssured.baseURI = ServicePath.jsonplaceholderLandaDevApi(baseUrl, apiVersion).toString();
            RestAssured.basePath = String.format("/%s", apiVersion);
            request = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON);
                    //.header("Host", baseUrl); se usa para los logs
            request.filter(new AllureRestAssured());
        } catch (MalformedURLException ex){
            ex.printStackTrace();
        }
    }
}