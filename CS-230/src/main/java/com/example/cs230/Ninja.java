package com.example.cs230;

public enum Ninja {
    BLUE("/Players/ninjaBlue.png", "/Players/BlueNinjaPlayerDown.png", "/Players/BlueNinjaPlayerUp.png",
            "/Players/BlueNinjaPlayerRight.png", "/Players/BlueNinjaPlayerLeft.png"),
    GREY("/Players/ninjaGrey.png", "/Players/GreyNinjaPlayerDown.png", "/Players/GreyNinjaPlayerUp.png",
            "/Players/GreyNinjaPlayerRight.png", "/Players/GreyNinjaPlayerLeft.png"),
    BLACK("/Players/ninjaBlack.png", "/Players/BlackNinjaPlayerDown.png", "/Players/BlackNinjaPlayerUp.png",
            "/Players/BlackNinjaPlayerRight.png", "/Players/BlackNinjaPlayerLeft.png"),
    RED("/Players/ninjaRed.png", "/Players/RedNinjaPlayerDown.png", "/Players/RedNinjaPlayerUp.png",
            "/Players/RedNinjaPlayerRight.png", "/Players/RedNinjaPlayerLeft.png");

    private final String urlNinjaFaceSet;
    private final String urlNinjaDown;
    private final String urlNinjaUp;
    private final String urlNinjaRight;
    private final String urlNinjaLeft;


    Ninja(String urlNinjaFaceSet, String urlNinjaDown, String urlNinjaUp, String urlNinjaRight, String urlNinjaLeft) {
        this.urlNinjaFaceSet = urlNinjaFaceSet;
        this.urlNinjaDown = urlNinjaDown;
        this.urlNinjaUp = urlNinjaUp;
        this.urlNinjaRight = urlNinjaRight;
        this.urlNinjaLeft = urlNinjaLeft;
    }

    public String getNinjaFaceset() {
        return urlNinjaFaceSet;
    }

    public String getUrlNinjaDown() {
        return urlNinjaDown;
    }

    public String getUrlNinjaUp() {return  urlNinjaUp;}
    public String getUrlNinjaRight() {return urlNinjaRight;}
    public String getUrlNinjaLeft() {return urlNinjaLeft;}
}
