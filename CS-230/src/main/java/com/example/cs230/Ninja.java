package com.example.cs230;

public enum Ninja {
    BLUE("ninjaBlue.png", "player.png"),
    GREY("ninjaGrey.png", "player.png"),
    BLACK("ninjaBlack.png", "player.png"),
    RED("ninjaRed.png", "player.png");

    private final String urlNinjaFaceSet;
    private final String urlNinja;

    Ninja(String urlNinjaFaceSet, String urlNinja){
        this.urlNinjaFaceSet = urlNinjaFaceSet;
        this.urlNinja = urlNinja;
    }

    public String getNinjaFaceset() {
        return urlNinjaFaceSet;
    }

    public String getUrlNinja(){
        return urlNinja;
    }
}
