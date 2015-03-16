package edu.ycp.cs482.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import edu.ycp.cs482.JSON.JSON;
import edu.ycp.cs482.Model.Question;

/**
 * Created by Carl on 3/15/2015.
 */
public class GetQuestion extends {

    public Question getQuestion(int id) throws ClientProtocolException, URISyntaxException, IOException {
        return makeGetRequest(id);
    }

    private Question makeGetRequest(int id) throws URISyntaxException, ClientProtocolException, IOException{
        // Create HTTP client
        HttpClient client = new DefaultHttpClient();
        // Construct URI
        URI uri = URIUtils.createURI("http", "10.0.2.2", 8081, "/question/" + id, null, null);

        // Construct request
        HttpGet request = new HttpGet(uri);

        // Execute request
        HttpResponse response = client.execute(request);
        // Parse response
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            // Add JSON object to request
            HttpEntity entity = response.getEntity();
            // Parse JSON
            return JSON.getObjectMapper().readValue(entity.getContent(), Question.class);
        }

        // Return null if invalid response
        return null;
    }
}
