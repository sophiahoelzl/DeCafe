package com.example.decafe;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.css.PseudoClass;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

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

    public void doAnimation (Timer t, ImageView machine, ProgressBar progress, Image product){
        machine.setDisable(true);
        progress.setVisible(true);
        // code from https://stackoverflow.com/questions/18539642/progressbar-animated-javafx
        Timeline task = new Timeline(
                new KeyFrame(
                        Duration.ZERO,
                        new KeyValue(progress.progressProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(5),
                        new KeyValue(progress.progressProperty(), 1)
                )
        );

        int maxStatus = 12;
        // Create the Property that holds the current status count
        IntegerProperty statusCountProperty = new SimpleIntegerProperty(1);
        // Create the timeline that loops the statusCount till the maxStatus
        Timeline timelineBar = new Timeline(
                new KeyFrame(
                        // Set this value for the speed of the animation
                        Duration.millis(300),
                        new KeyValue(statusCountProperty, maxStatus)
                )
        );
        // The animation should be infinite
        timelineBar.setCycleCount(Timeline.INDEFINITE);
        timelineBar.play();
        // Add a listener to the statusproperty
        statusCountProperty.addListener((ov, statusOld, statusNewNumber) -> {
            int statusNew = statusNewNumber.intValue();
            // Remove old status pseudo from progress-bar
            progress.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusOld.intValue()), false);
            // Add current status pseudo from progress-bar
            progress.pseudoClassStateChanged(PseudoClass.getPseudoClass("status" + statusNew), true);
        });
        task.playFromStart();

        //Idea from https://stackoverflow.com/questions/2258066/run-a-java-function-after-a-specific-number-of-seconds
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        machine.setImage(product);
                        machine.setDisable(false);
                        t.cancel();
                    }
                },
                5000
        );
    }

    //Funktion um ein Produkt anzeigen zu lassen
    public void displayProduct (ImageView waiter, ImageView machine, Player cofiBrew, ProgressBar progress) throws FileNotFoundException {

        Timer t = new Timer();

        String filePath = this.PathMachineWithProduct;
        String filepathTwo = cofiBrew.getImageWithoutProduct();
        boolean gotProduced = false;

        if (!this.produced && cofiBrew.getProduct().equals("none")) {
            this.produceProduct();
            gotProduced = true;
        } else if (!this.produced && cofiBrew.getProduct().equals("coffee")) {
            this.produceProduct();
            gotProduced = true;
            filepathTwo = cofiBrew.getImageWithCoffee();
        } else if (!this.produced && cofiBrew.getProduct().equals("cake")) {
            this.produceProduct();
            gotProduced = true;
            filepathTwo = cofiBrew.getImageWithCake();
        } else {
            if (cofiBrew.getProduct().equals("none")){
                this.productTaken();
                filePath = this.PathMachineWithoutProduct;
                cofiBrew.setProduct(this.type);
                if (this.type.equals("coffee")){
                    filepathTwo = cofiBrew.getImageWithCoffee();
                } else {
                    filepathTwo = cofiBrew.getImageWithCake();
                }
            } else {
                    if (cofiBrew.getProduct().equals("coffee")){
                        filepathTwo = cofiBrew.getImageWithCoffee();
                    } else {
                        filepathTwo = cofiBrew.getImageWithCake();
                    }
            }
        }

        InputStream stream = new FileInputStream(filePath);
        InputStream stream2 = new FileInputStream(filepathTwo);
        Image product = new Image(stream);
        Image cofi = new Image(stream2);
        waiter.setImage(cofi);

        if (gotProduced) {
            doAnimation(t, machine, progress, product);
        } else {
            progress.setVisible(this.getProduced());
            machine.setImage(product);
        }

    }

}
