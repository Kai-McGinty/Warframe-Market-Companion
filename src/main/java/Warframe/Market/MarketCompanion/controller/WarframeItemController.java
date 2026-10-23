package Warframe.Market.MarketCompanion.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import Warframe.Market.MarketCompanion.model.ItemModel;
import Warframe.Market.MarketCompanion.service.WarframeItemService;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@RestController
public class WarframeItemController {

    private final WarframeItemService service;
    private final ObjectMapper objectMapper;

    public WarframeItemController(WarframeItemService service, ObjectMapper objectMapper) {
        this.service = service;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/items")
    public String getItems() {
        return service.getItems();
    }

    @GetMapping("/items/{itemName}")
    public ItemModel getSingleItem(@PathVariable String itemName) {
        String itemJson = service.getSingleItem(itemName);
        return ParseJSON(objectMapper.readTree(itemJson).path("data"));
    }

    @GetMapping("/items/{itemName}/set")
    public List<ItemModel> getItemSet(@PathVariable String itemName) {
        JsonNode items = objectMapper.readTree(service.getItemSet(itemName))
                .path("data")
                .path("items");

        if (!items.isArray()) {
            throw new InvalidWarframeItemResponseException();
        }

        List<ItemModel> parsedItems = new ArrayList<>(items.size());
        for (JsonNode item : items) {
            parsedItems.add(ParseJSON(item));
        }

    return parsedItems;
    }

    //function used to parse the JSON response from the Warframe Market API and extract the relevant information for a single item.
    //It returns an ItemModel object containing the item's ID, English name, and slug.
    private ItemModel ParseJSON(JsonNode data) {
        JsonNode englishName = data.path("i18n").path("en").path("name");
        JsonNode id = data.path("id");
        JsonNode slug = data.path("slug");

        if (id.isMissingNode() || id.isNull()
        || slug.isMissingNode() || slug.isNull()
        || englishName.isMissingNode() || englishName.isNull()) {
            throw new InvalidWarframeItemResponseException();
    }

    return new ItemModel(id.asString(), englishName.asString(), slug.asString());
    }
    
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    private static class InvalidWarframeItemResponseException extends RuntimeException {
    }
}
