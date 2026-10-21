package Warframe.Market.MarketCompanion.model;

public class ItemModel {

    private String id;
    private String itemName;
    private String urlName;

    public ItemModel(String id, String itemName, String urlName) {
        this.id = id;
        this.itemName = itemName;
        this.urlName = urlName;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getUrlName() {
        return urlName;
    }
}
