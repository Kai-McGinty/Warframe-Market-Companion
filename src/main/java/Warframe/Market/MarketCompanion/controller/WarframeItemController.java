package Warframe.Market.MarketCompanion.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import Warframe.Market.MarketCompanion.service.WarframeItemService;

@RestController
public class WarframeItemController {

    private final WarframeItemService service;

    public WarframeItemController(WarframeItemService service) {
        this.service = service;
    }

    @GetMapping("/items")
    public String getItems() {
        return service.getItems();
    }

    @GetMapping("/items/{itemName}")
    public String getSingleItem(@PathVariable String itemName) {
        return service.getSingleItem(itemName);
    }
}
