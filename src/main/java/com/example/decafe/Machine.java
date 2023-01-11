package com.example.decafe;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Machine {
    private int duration; // Wie lange die Maschine wartet bis ein Produkt erzeugt wird - am Anfang 0
    private int capacity; // Wie viele Produkte die Machine gleichzeitig erzeugen kann
    private Boolean produced;
    private final String PathMachineWithoutProduct;
    private final String PathMachineWithProduct;
    private final String type;

    public Machine(int duration, String filenameImageWithProduct, String filenameImageWithoutProduct, String type){
        this.duration = duration;
        this.capacity = 1;
        this.produced = false;
        File f = new File("");
        this.PathMachineWithProduct = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filenameImageWithProduct;
        this.PathMachineWithoutProduct = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filenameImageWithoutProduct;
        this.type = type;
    }
    //Getter
    public int getCapacity() {
        return capacity;
    }

    public int getDuration() {
        return duration;
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

    //Funktion um ein Produkt anzeigen zu lassen
    public void displayProduct (double x1, double y1, double x2, double y2, ImageView waiter, ImageView machine, Player cofiBrew) throws FileNotFoundException {
        Point2D c = new Point2D(x1, y1);
        Point2D w = new Point2D(x2, y2);
        if (c.distance(w) < 80 && cofiBrew.getProduct().equals("none")) {
            String filePath;
            String filepathTwo;

            if (cofiBrew.getProduct().equals("none")) {
                if (this.getProduced()) {
                    filePath = this.PathMachineWithoutProduct;
                    this.productTaken();
                    cofiBrew.setProduct(this.type);
                    if (this.type.equals("coffee")) {
                        filepathTwo = cofiBrew.getImageWithCoffee();
                    } else {
                        filepathTwo = cofiBrew.getImageWithCake();
                    }
                } else {
                    filePath = this.PathMachineWithProduct;
                    this.produceProduct();
                    filepathTwo = cofiBrew.getImageWithoutProduct();
                }
            } else {
                filePath = this.PathMachineWithoutProduct;
                if (cofiBrew.getProduct().equals("coffee")) {
                    filepathTwo = cofiBrew.getImageWithCoffee();
                } else {
                    filepathTwo = cofiBrew.getImageWithCake();
                }
            }
            InputStream stream = new FileInputStream(filePath);
            InputStream stream2 = new FileInputStream(filepathTwo);
            Image product = new Image(stream);
            Image cofi = new Image(stream2);

            machine.setImage(product);
            waiter.setImage(cofi);
        }
    }

}
