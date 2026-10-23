package Warframe.Market.MarketCompanion.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class WarframeItemService {

    private final RestClient client;

    public WarframeItemService() {
        client = RestClient.builder()
        .baseUrl("https://api.warframe.market/v2/")
        .build();
    }

    public String getItems() {
        return client.get().uri("/items").retrieve().body(String.class);
    }

    public String getSingleItem(String itemName) {
        return client.get().uri("/item/{itemName}", itemName).retrieve().body(String.class);
    }

    public String getItemSet(String itemName) {
        return client.get().uri("/item/{itemName}/set", itemName).retrieve().body(String.class);
    }
}
