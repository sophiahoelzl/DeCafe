package com.example.decafe;

import java.io.File;

public class Player {
    private String imageWithoutProduct; //Bild vom Kellner
    private String imageWithCoffee;
    private String imageWithCake;
    private String product; //Was für ein Produkt der Kellner gerade trägt - Kaffee, Kuchen oder gar nix
    private int movement;

    public Player(String imageWithoutProduct, String imageWithCake, String imageWithCoffee, int movement) {
        this.imageWithoutProduct = imageWithoutProduct;
        this.imageWithCake =  imageWithCake;
        this.imageWithCoffee = imageWithCoffee;
        this.product = "none";
        this.movement = movement;
    }

    //Getter

    public String getProduct() {
        return product;
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

    public int getMovement() {
        return movement;
    }

    //Setter

    public void setProduct(String product) {
        this.product = product;
    }

    public void setImageWithoutProduct(String imageWithoutProduct){
        this.imageWithoutProduct = imageWithoutProduct;
    }

    public void setMovement(int movement) {
        this.movement = movement;
    }
}

