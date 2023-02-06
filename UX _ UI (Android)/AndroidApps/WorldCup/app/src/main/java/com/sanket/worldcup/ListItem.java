package com.sanket.worldcup;

public abstract class ListItem {
    private int itemImageResourceId;
    private String itemTitle;
    private String itemSubtitle;

    public ListItem(int itemImageResourceId, String itemTitle, String itemSubtitle) {
        this.itemImageResourceId = itemImageResourceId;
        this.itemTitle = itemTitle;
        this.itemSubtitle = itemSubtitle;
    }

    public int getItemImageResourceId() {
        return itemImageResourceId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemSubtitle() {
        return itemSubtitle;
    }
}
