package com.example.decafe;

import com.example.decafe.enumerations.Products;

public class Machine {
    private int duration; // Wie lange die Maschine wartet bis ein Produkt erzeugt wird - am Anfang 0
    private int capacity; // Wie viele Produkte die Machine gleichzeitig erzeugen kann
    private String image; // Wie die Machine aussieht
    private Boolean produced;

    public Machine(int duration){
        this.duration = duration;
        this.capacity = 1;
        this.produced = false;
    }
    //Getter
    public int getCapacity() {
        return capacity;
    }

    public int getDuration() {
        return duration;
    }

    public String getImage() {
        return image;
    }

    public Boolean getProduced() {
        return produced;
    }

    //Setter
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //Funktion um ein Produkt zu erzeugen
    public void produceProduct(){
        produced = true;
    }

    public void productTaken(){
        produced = false;
    }

    //Funktion um eine bestimmte Zeit zu warten
    public void wait (int duration){

    }

    //Funktion um ein Produkt anzeigen zu lassen - vllt auch HelloController
    public void displayProduct (Products product, String image){

    }

}
