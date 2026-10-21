package Warframe.Market.MarketCompanion.model;

public class ItemModel {
    
    private String id;
    private String item_name;
    private String url_name;

    public ItemModel(String id, String item_name, String url_name) {
        this.id = id;
        this.item_name = item_name;
        this.url_name = url_name;
    }

    public String getId() {
        return id;
    }

    public String getItemName() {
        return item_name;
    }

    public String getUrlName() {
        return url_name;
    }
}
