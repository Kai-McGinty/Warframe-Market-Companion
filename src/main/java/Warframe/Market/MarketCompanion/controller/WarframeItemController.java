package Warframe.Market.MarketCompanion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import Warframe.Market.MarketCompanion.model.ItemModel;
import Warframe.Market.MarketCompanion.service.WarframeItemService;

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
        JsonNode data = objectMapper.readTree(itemJson).path("data");
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
