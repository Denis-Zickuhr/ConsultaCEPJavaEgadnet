package controller;

import org.json.JSONObject;
import view.CEPSearchObserver;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CEPSearchController {

    private static CEPSearchController instance;
    private final Cache<String, String> cache;
    private final List<CEPSearchObserver> observer = new ArrayList<>();

    public CEPSearchController() {
        // Cache instantiation
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, String> config = new MutableConfiguration<>();
        config.setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES));
        cache = cacheManager.createCache("CEPS", config);
    }

    public static CEPSearchController getInstance() {
        if (instance == null) {
            instance = new CEPSearchController();
        }
        return instance;
    }

    public void attach(CEPSearchObserver observer) {
        this.observer.add(observer);
    }

    public void search(String request) throws IOException {

        JSONObject response;

        if (!validateCep(request)) {
            response = new JSONObject(
                    "{" +
                            "response" + ":" + "O CEP inserido é inválido!" + "," +
                            "error_code" + ":" + "400" +
                            "}"
            );
        } else {
            if (!cache.containsKey(request)) {
                response = performRequest("https://viacep.com.br/ws/" + request + "/json/");
                response.put("from_cache", "false");
                addToCache(request, response);
            } else {
                response = new JSONObject(cache.get(request));
            }
        }

        for (CEPSearchObserver obs : observer
        ) {
            obs.search(response);
        }
    }

    private boolean validateCep(String cep) {
        return cep.length() == 8;
    }

    private JSONObject performRequest(String request) throws IOException {
        URL url = new URL(request);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return new JSONObject(content.toString());
    }

    private void addToCache(String initiator, JSONObject response) {
        JSONObject responseCopy = new JSONObject(response.toString());
        responseCopy.remove("from_cache");
        responseCopy.put("from_cache", "true");
        cache.putIfAbsent(initiator, String.valueOf(responseCopy));
    }

}
