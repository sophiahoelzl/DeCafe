package com.example.decafe;

import java.io.File;

public class Player {
    private double positionX; // X-Koordinate vom Kellner
    private double positionY; // Y-Koordinate vom kellner
    private String imageWithoutProduct; //Bild vom Kellner
    private String imageWithCoffee;
    private String imageWithCake;
    private String product; //Was für ein Produkt der Kellner gerade trägt - Kaffee, Kuchen oder gar nix
    private String input; //Inputs die der Spieler macht - nach oben/unten/links/rechts gehen

    public Player(String imageWithoutProduct, String imageWithCake, String imageWithCoffee) {
        File f = new File("");
        this.imageWithoutProduct = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + imageWithoutProduct;
        this.imageWithCake = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + imageWithCake;
        this.imageWithCoffee = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + imageWithCoffee;
        this.product = "none";
    }

    //Getter
    public double getPositionY() {
        return positionY;
    }

    public double getPositionX() {
        return positionX;
    }

    public String getProduct() {
        return product;
    }

    public String getInput() {
        return input;
    }

    public String getImageWithoutProduct() {
        return imageWithoutProduct;
    }

    public String getImageWithCake() {
        return imageWithCake;
    }

    public String getImageWithCoffee() {
        return imageWithCoffee;
    }

    //Setter
    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setImageWithoutProduct(String imageWithoutProduct){
        this.imageWithoutProduct = imageWithoutProduct;
    }

    //Funktion damit sich der Spieler bewegt
    public void move (String input){

    }

    //Funktion um Bild von Kellner anzuzeigen - vllt auch in HelloController
    public void displayPerson (String image){

    }
}

