package com.example.decafe;

// Class to handle Methods used to change the Image and movement speed of waiter
public class Player {
    private final String imageWithoutProduct; // Image of the waiter without anything in his hands
    private final String imageWithCoffee; // Image of the waiter with coffee in his hands
    private final String imageWithCake; // Image of the waiter with cake in his hands
    private String product; // The type of product the waiter holds in his hands (Coffee or Cake)
    private int movementSpeed; // the movement speed at which the waiter moves

    // Constructor
    public Player(String imageWithoutProduct, String imageWithCake, String imageWithCoffee, int movement) {
        this.imageWithoutProduct = imageWithoutProduct;
        this.imageWithCake =  imageWithCake;
        this.imageWithCoffee = imageWithCoffee;
        this.product = "none";
        this.movementSpeed = movement;
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
        return movementSpeed;
    }

    //Setter
    public void setProduct(String product) {
        this.product = product;
    }

    public void setMovement(int movement) {
        this.movementSpeed = movement;
    }
}

