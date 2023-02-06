package com.sanket.worldcup;

public class Stadium extends ListItem {
    private String matches;
    private String capacity;

    public Stadium(int itemImageResourceId, String itemTitle, String itemSubtitle, String matches, String capacity) {
        super(itemImageResourceId, itemTitle, itemSubtitle);
        this.matches = matches;
        this.capacity = capacity;
    }

    public String getMatches() {
        return matches;
    }

    public String getCapacity() {
        return capacity;
    }
}
