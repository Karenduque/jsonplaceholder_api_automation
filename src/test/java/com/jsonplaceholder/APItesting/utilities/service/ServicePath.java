package com.jsonplaceholder.APItesting.utilities.service;
import java.net.MalformedURLException;
import java.net.URL;

public class ServicePath {
    public static URL jsonplaceholderLandaDevApi(String baseUrl, String apiVersion) throws MalformedURLException {
        return new URL(String.format("%s", baseUrl));
    }
}

