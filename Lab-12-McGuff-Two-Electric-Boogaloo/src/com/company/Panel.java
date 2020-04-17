package com.company;

public class Panel {
    private String textToDisplay;
    private boolean beenSelected;

    //default because I might need it??? but also its good practice
    public Panel() {
        setTextToDisplay("-");
        setBeenSelected(false);
    }

    //Used for initial board creation to set all Panels to numbers for ease of player use
    public Panel(String text) {
        setTextToDisplay(text);
        setBeenSelected(false);
    }

    public void setTextToDisplay(String text) {
        this.textToDisplay = text;
    }

    public void setBeenSelected(Boolean used) {
        this.beenSelected = used;
    }

    public String getTextToDisplay() {
        return this.textToDisplay;
    }

    public boolean getBeenSelected() {
        return this.beenSelected;
    }

}
