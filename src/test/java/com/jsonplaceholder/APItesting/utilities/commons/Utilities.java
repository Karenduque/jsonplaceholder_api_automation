package com.jsonplaceholder.APItesting.utilities.commons;

import com.jsonplaceholder.APItesting.utilities.service.ServicePath;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.bson.types.ObjectId;

public class Utilities {

    static final String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    static final String FORMAT_DATE_YYYYMMDD = "yyyyMMdd";
    static final String FORMAT_DATE_YYYYMMDD_SHORT = "yyyy-MM-dd";
    public static final String TIME_ZONE_MADRID= "Europe/Madrid";
    static final String GUID_MATCHER = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

    public static String getTodayInMilliseconds(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE_MADRID));
        calendar.set(Calendar.HOUR,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTimeInMillis()/1000+"";
    }

    public static String dateToStringWithFormat(Date date){
        DateFormat format = new SimpleDateFormat(FORMAT_DATE_YYYY_MM_DD);
        format.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_MADRID));
        return format.format(date)+"Z";
    }

    public static String calendarToStringWithFormat(Calendar calendar){
        SimpleDateFormat format1 = new SimpleDateFormat(FORMAT_DATE_YYYY_MM_DD);
        format1.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_MADRID));
        String formatted = format1.format(calendar.getTime());
        return formatted+"Z";
    }

    public static Float calendarToFloatWithFormat(Calendar calendar){
        calendar.set(Calendar.HOUR, 1);
        Integer monthInteger = calendar.get(Calendar.MONTH)+1;
        Integer dayInteger = calendar.get(Calendar.DAY_OF_MONTH);
        String month = calendar.get(Calendar.YEAR)+""+(monthInteger<=10?"0"+monthInteger:monthInteger)+""+dayInteger;
        return Float.parseFloat(month);
    }

    public static String calendarToStringWithFormatShort(Calendar calendar){
        SimpleDateFormat format1 = new SimpleDateFormat(FORMAT_DATE_YYYYMMDD_SHORT);
        format1.setTimeZone(TimeZone.getTimeZone(TIME_ZONE_MADRID));
        String formatted = format1.format(calendar.getTime());
        return formatted;
    }

    public static String getIdRandom(){
        String result = java.util.UUID.randomUUID().toString();
        return result;
    }

    public static RequestSpecification getResquest(String baseUrl, String apiVersion){
        RequestSpecification request = null;
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
        return request;
    }

    public static String getRandomBSonString()
    {
        // the resulting string
        return ObjectId.get().toString();
    }

}
