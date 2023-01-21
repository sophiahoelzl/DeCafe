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
    private final String pathMachineWithProduct;
    private final String type;

    public Machine(int duration, String filenameImageWithProduct, String filenameImageWithoutProduct, String type){
        this.duration = duration;
        this.capacity = 1;
        this.produced = false;
        this.pathMachineWithProduct = filenameImageWithProduct;
        this.PathMachineWithoutProduct = filenameImageWithoutProduct;
        this.type = type;
    }
    //Getter
    public int getCapacity() {
        return capacity;
    }

    public int getDuration() { return duration; }

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

    public void setProduced(Boolean produced){
        this.produced = produced;
    }

    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
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
                        Duration.seconds(this.getDuration()),
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
                this.duration* 1000L
        );
    }

    //Funktion um ein Produkt anzeigen zu lassen
    public void displayProduct (ImageView waiter, ImageView machine, Player cofiBrew, ProgressBar progress) throws FileNotFoundException {

        Timer t = new Timer();

        String imageMachine = this.pathMachineWithProduct;
        String imageCofi = cofiBrew.getImageWithoutProduct();
        boolean gotProduced = false;

        if (!this.produced && cofiBrew.getProduct().equals("none")) {
            this.setProduced(true);
            gotProduced = true;
        } else if (!this.produced && cofiBrew.getProduct().equals("coffee")) {
            this.setProduced(true);
            gotProduced = true;
            imageCofi = cofiBrew.getImageWithCoffee();
        } else if (!this.produced && cofiBrew.getProduct().equals("cake")) {
            this.setProduced(true);
            gotProduced = true;
            imageCofi = cofiBrew.getImageWithCake();
        } else {
            if (cofiBrew.getProduct().equals("none")){
                this.setProduced(false);
                imageMachine = this.PathMachineWithoutProduct;
                cofiBrew.setProduct(this.type);
                if (this.type.equals("coffee")){
                    imageCofi = cofiBrew.getImageWithCoffee();
                } else {
                    imageCofi = cofiBrew.getImageWithCake();
                }
            } else {
                    if (cofiBrew.getProduct().equals("coffee")){
                        imageCofi = cofiBrew.getImageWithCoffee();
                    } else {
                        imageCofi = cofiBrew.getImageWithCake();
                    }
            }
        }

        waiter.setImage(createImage(imageCofi));

        if (gotProduced) {
            doAnimation(t, machine, progress, createImage(imageMachine));
        } else {
            progress.setVisible(this.getProduced());
            machine.setImage(createImage(imageMachine));
        }

    }

}
