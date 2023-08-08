package com.sanket.worldcup;

public class Great extends ListItem {
    String birthPlace;
    String postion;
    String clubsRepresented;

    public Great(int itemImageResourceId, String itemTitle, String itemSubtitle, String birthPlace, String postion, String clubsRepresented) {
        super(itemImageResourceId, itemTitle, itemSubtitle);
        this.birthPlace = birthPlace;
        this.postion = postion;
        this.clubsRepresented = clubsRepresented;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public String getPostion() {
        return postion;
    }

    public String getClubsRepresented() {
        return clubsRepresented;
    }
}
