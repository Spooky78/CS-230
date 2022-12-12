package com.example.cs230;

/**
 * Build a list of ninjas player can choose to play the game.
 *
 * @author Vic
 */
public enum Ninja {
    BLUE("/Players/ninjaBlue.png", "/Players/BlueNinjaPlayerDown.png",
            "/Players/BlueNinjaPlayerUp.png",
            "/Players/BlueNinjaPlayerRight.png", "/Players/BlueNinjaPlayerLeft.png"),
    GREY("/Players/ninjaGrey.png", "/Players/GreyNinjaPlayerDown.png",
            "/Players/GreyNinjaPlayerUp.png",
            "/Players/GreyNinjaPlayerRight.png", "/Players/GreyNinjaPlayerLeft.png"),
    BLACK("/Players/ninjaBlack.png", "/Players/BlackNinjaPlayerDown.png",
            "/Players/BlackNinjaPlayerUp.png",
            "/Players/BlackNinjaPlayerRight.png", "/Players/BlackNinjaPlayerLeft.png"),
    RED("/Players/ninjaRed.png", "/Players/RedNinjaPlayerDown.png",
            "/Players/RedNinjaPlayerUp.png",
            "/Players/RedNinjaPlayerRight.png", "/Players/RedNinjaPlayerLeft.png");

    private final String urlNinjaFaceSet; //what the ninja looks like on the player select screen.
    private final String urlNinjaDown;
    private final String urlNinjaUp;
    private final String urlNinjaRight;
    private final String urlNinjaLeft;


    /**
     * decide what ninja looks like right now depends on player movement.
     *
     * @param ninjaFaceSet ninja picture
     * @param ninjaDown    ninja move down picture
     * @param ninjaUp      ninja move up picture
     * @param ninjaRight   ninja move right picture
     * @param ninjaLeft    ninja move left picture
     */
    Ninja(String ninjaFaceSet, String ninjaDown, String ninjaUp,
          String ninjaRight, String ninjaLeft) {
        this.urlNinjaFaceSet = ninjaFaceSet;
        this.urlNinjaDown = ninjaDown;
        this.urlNinjaUp = ninjaUp;
        this.urlNinjaRight = ninjaRight;
        this.urlNinjaLeft = ninjaLeft;
    }

    /**
     * get ninja picture.
     *
     * @return ninja picture
     */
    public String getNinjaFaceset() {
        return urlNinjaFaceSet;
    }

    /**
     * get ninja down picture.
     *
     * @return ninja down picture
     */
    public String getUrlNinjaDown() {
        return urlNinjaDown;
    }

    /**
     * get ninja up picture.
     *
     * @return ninja up picture
     */
    public String getUrlNinjaUp() {
        return urlNinjaUp;
    }

    /**
     * get ninja right picture.
     *
     * @return ninja right picture
     */
    public String getUrlNinjaRight() {
        return urlNinjaRight;
    }

    /**
     * get ninja left picture.
     *
     * @return ninja left picture
     */
    public String getUrlNinjaLeft() {
        return urlNinjaLeft;
    }
}
