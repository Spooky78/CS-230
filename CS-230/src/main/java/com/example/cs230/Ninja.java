package com.example.cs230;

public enum Ninja {
    BLUE("ninjaBlue.png"),
    GREY("ninjaGrey.png"),
    BLACK("ninjaBlack.png"),
    RED("ninjaRed.png");

    private final String urlNinjaFaceSet;

    Ninja(String urlNinjaFaceSet){
        this.urlNinjaFaceSet = urlNinjaFaceSet;
    }

    public String getNinjaFaceset() {
        return urlNinjaFaceSet;
    }
}
