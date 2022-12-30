package com.example.decafe;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    ImageView startButton = new ImageView();
    public ImageView coffeeMachine;
    public ImageView kitchenAid;

    public Machine coffeeeMachine = new Machine(0);
    public Machine cakeMachine = new Machine(0);

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    @FXML
    private ImageView waiter;

    private int movementVariable = 5;

    // jump from start screen to game screen
    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();
    }

    // for smoother motion
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            double move = movementVariable;

            // if two keys are pressed at once and player moves diagonally
            if (wPressed.get() && aPressed.get() || wPressed.get() && dPressed.get() ||
                    sPressed.get() && aPressed.get() || sPressed.get() && dPressed.get())
                move -= movementVariable - Math.sqrt(Math.pow(movementVariable, 2) / 2);

            // for collision detection later
            double xMove = 0;
            double yMove = 0;

            if (wPressed.get()) yMove = -move;
            if (sPressed.get()) yMove = move;
            if (aPressed.get()) xMove = -move;
            if (dPressed.get()) xMove = move;

            // if (checkForCollision(waiter, xMove, yMove)) return; nächster Schritt dann

            waiter.setLayoutX(waiter.getLayoutX() + xMove);
            waiter.setLayoutY(waiter.getLayoutY() + yMove);
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyPressed.addListener((((observableValue, aBoolean, t1) -> { // if any key from the four keys is pressed
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        })));
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                wPressed.set(true);
                break;

            case A:
                aPressed.set(true);
                break;

            case S:
                sPressed.set(true);
                break;

            case D:
                dPressed.set(true);
                break;
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                wPressed.set(false);
                break;

            case A:
                aPressed.set(false);
                break;

            case S:
                sPressed.set(false);
                break;

            case D:
                dPressed.set(false);
                break;
        }
    }

    // start screen - change coffee button on mouse entered
    public void changeCoffeeImage() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            filePath = f.getAbsolutePath().replace("\\", "\\\\") + "\\src\\main\\resources\\com\\example\\decafe\\hotcoffee.png";
            ;
        } else {
            filePath = f.getAbsolutePath().replace("/", "//") + "//src//main//resources//com//example//decafe//hotcoffee.png";
            ;
        }
        InputStream stream = new FileInputStream(filePath);
        Image hotCoffee = new Image(stream);
        startButton.setImage(hotCoffee);
    }

    // start screen - change coffee button on mouse exited
    public void changeCoffeeImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            filePath = f.getAbsolutePath() + "\\src\\main\\resources\\com\\example\\decafe\\coffee.png";
            ;
        } else {
            filePath = f.getAbsolutePath() + "/src/main/resources/com/example/decafe/coffee.png";
            ;
        }
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        startButton.setImage(coffee);
    }

    // when coffee is produced, change appearance
    public void showCoffee() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (coffeeeMachine.getProduced()) {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                filePath = f.getAbsolutePath() + "\\src\\main\\resources\\com\\example\\decafe\\coffeeMachine.png";
                ;
            } else {
                filePath = f.getAbsolutePath() + "/src/main/resources/com/example/decafe/coffeeMachine.png";
                ;
            }
            coffeeeMachine.productTaken();
        } else {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                filePath = f.getAbsolutePath() + "\\src\\main\\resources\\com\\example\\decafe\\coffee.png";
                ;
            } else {
                filePath = f.getAbsolutePath() + "/src/main/resources/com/example/decafe/coffee.png";
                ;
            }
            coffeeeMachine.produceProduct();

        }
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        coffeeMachine.setImage(coffee);
    }

    // when cake machine is running
    public void showCake() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (cakeMachine.getProduced()) {
            filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "kitchenAid.png";
            ;
            cakeMachine.productTaken();
        } else {
            filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "kitchenAidUsed.png";
            ;
            cakeMachine.produceProduct();
        }
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        kitchenAid.setImage(coffee);
    }
}