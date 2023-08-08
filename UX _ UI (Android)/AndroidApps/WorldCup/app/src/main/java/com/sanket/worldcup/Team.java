package com.sanket.worldcup;

public class Team extends ListItem {
    private String players;
    private String history;

    public Team(int itemImageResourceId, String itemTitle, String itemSubtitle, String players, String history) {
        super(itemImageResourceId, itemTitle, itemSubtitle);
        this.players = players;
        this.history = history;
    }

    public String getPlayers() {
        return players;
    }

    public String getHistory() {
        return history;
    }
}
