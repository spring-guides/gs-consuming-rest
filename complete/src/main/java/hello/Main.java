package hello;

import org.codehaus.jackson.JsonNode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {

    public static void main(String args[]) throws Throwable {

        String searchUrl = "http://search.twitter.com/search.json?q={query}";
        String searchQueryPathVariableValue = "@gopivotal";

        RestTemplate restTemplate = new RestTemplate();

        String bodyOfResponse = restTemplate.getForObject(searchUrl, String.class, searchQueryPathVariableValue);

        JsonNode rootNode = restTemplate.getForObject(searchUrl, JsonNode.class, searchQueryPathVariableValue);

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(searchUrl, JsonNode.class, searchQueryPathVariableValue);

        HttpHeaders httpHeaders = response.getHeaders();

    }

    static void iterateTweets(JsonNode jsonNode) {
        for (JsonNode result : jsonNode.get("results"))
            System.out.println(String.format("Tweet: @%s said \"%s\"", result.get("from_user").getTextValue(), result.get("text").getTextValue()));
        System.out.println();
    }
}