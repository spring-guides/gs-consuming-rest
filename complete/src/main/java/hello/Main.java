package hello;

import org.codehaus.jackson.JsonNode;
import org.springframework.web.client.RestTemplate;

public class Main {

    public static void main(String args[]) {

        String searchUrl = "http://search.twitter.com/search.json?q={query}";
        String query = "@gopivotal";

        RestTemplate restTemplate = new RestTemplate();

        JsonNode rootNode = restTemplate.getForObject(
            searchUrl, JsonNode.class, query);

        for (JsonNode result : rootNode.get("results")) {
            System.out.println(
                String.format("Tweet: @%s said \"%s\"",
                    result.get("from_user").getTextValue(), result.get("text").getTextValue()));
        }
    }
}