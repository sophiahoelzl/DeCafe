package com.example.decafe;

public class Player {
    private int positionX; // X-Koordinate vom Kellner
    private int positionY; // Y-Koordinate vom kellner
    private String image; //Bild vom Kellner
    private String product; //Was für ein Produkt der Kellner gerade trägt - Kaffee, Kuchen oder gar nix
    private String input; //Inputs die der Spieler macht - nach oben/unten/links/rechts gehen

    //Getter
    public int getPositionY() {
        return positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public String getImage() {
        return image;
    }

    public String getProduct() {
        return product;
    }

    public String getInput() {
        return input;
    }

    //Setter
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setInput(String input) {
        this.input = input;
    }

    //Funktion damit sich der Spieler bewegt
    public void move (String input){

    }

    //Funktion um Bild von Kellner anzuzeigen - vllt auch in HelloController
    public void displayPerson (String image){

    }
}

