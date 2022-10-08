package com.github.denislavpetkov.simplerestclientserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.denislavpetkov.simplerestclientserver.DTO.Sum;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class Client {

    public static void main(String[] args) throws IOException {
        HttpClient client = new DefaultHttpClient();
        System.out.println("\nCurrent sum is " + getCurrentSum(client));

        double num1 = ThreadLocalRandom.current().nextDouble(0, 100);
        double num2 = ThreadLocalRandom.current().nextDouble(0, 100);
        System.out.println(String.format("\nNew sum of %f and %f is %f", num1, num2, getNewSum(client,num1,num2)));

        System.out.println(String.format("XML response of current sum: %s",getXmlSum(client)));

        num1 = ThreadLocalRandom.current().nextDouble(0, 100);
        num2 = ThreadLocalRandom.current().nextDouble(0, 100);

        System.out.println(String.format("New sum created: %s",createNewSum(client,num1,num2)));
    }


    private static double getCurrentSum(HttpClient client) throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/sum");
        HttpResponse response = client.execute(request);

        ObjectMapper xmlMapper = new XmlMapper();
        Sum sum = xmlMapper.readValue(response.getEntity().getContent(),Sum.class);
        return sum.getResult();
    }

    private static double getNewSum(HttpClient client, double num1, double num2) throws IOException {
        HttpGet request = new HttpGet(String.format("http://localhost:8080/sum/update?num1=%f&num2=%f", num1, num2));
        HttpResponse response = client.execute(request);

        ObjectMapper xmlMapper = new XmlMapper();
        Sum sum = xmlMapper.readValue(response.getEntity().getContent(),Sum.class);
        return sum.getResult();
    }

    private static String getXmlSum(HttpClient client) throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/sum");
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder responseString = new StringBuilder();
        for (String line; (line = rd.readLine()) != null; ) {
            responseString.append(line);
        }

        return String.valueOf(responseString);
    }

    private static String createNewSum(HttpClient client, double num1, double num2) throws IOException {
        Sum newSum = new Sum(num1, num2);
        HttpPost request = new HttpPost("http://localhost:8080/sum/create");

        XmlMapper xmlMapper = new XmlMapper();
        String xmlSum = xmlMapper.writeValueAsString(newSum);

        StringEntity input = new StringEntity(xmlSum, org.apache.http.entity.ContentType.TEXT_XML);

        request.setEntity(input);
        HttpResponse response = client.execute(request);

        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder responseString = new StringBuilder();
        for (String line; (line = rd.readLine()) != null; ) {
            responseString.append(line);
        }

        return String.valueOf(responseString);
    }

}
