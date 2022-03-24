package pl.pwr.edu.app.api;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.Random;

public class GeoDb {
    HttpRequest request;
    String url = "https://wft-geo-db.p.rapidapi.com/v1/geo/", headerHostName = "X-RapidAPI-Host",
            headerHostValue = "wft-geo-db.p.rapidapi.com", headerKeyName = "X-RapidAPI-Key",
            headerKeyValue = "aa04852247mshf2b89b08206b15fp153fc1jsn5cc22d25a9f8";

    public GeoDb() {

    }

    public int getNoRegions(String countryId) throws IOException, InterruptedException, ParseException {
        Thread.sleep(2000);
        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "countries/" + countryId))
                .header(headerHostName, headerHostValue)
                .header(headerKeyName, headerKeyValue)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject obj = (JSONObject) new JSONParser().parse(response.body());
        return Integer.parseInt(((JSONObject) obj.get("data")).get("numRegions").toString());
    }

    public CountryData randCountry() throws IOException, InterruptedException, ParseException {
        request = HttpRequest.newBuilder()
                .uri(URI.create(url + "countries?offset=" + new Random().nextInt(200) + "&limit=1"))
                .header(headerHostName, headerHostValue)
                .header(headerKeyName, headerKeyValue)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONObject obj = (JSONObject) ((JSONArray) ((JSONObject) new JSONParser().parse(response.body())).get("data")).get(0);
        return new CountryData((String) obj.get("name"), (String) obj.get("wikiDataId"));
    }
}
