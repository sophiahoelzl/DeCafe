package com.example.decafe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloController {
    @FXML
    ImageView startButton = new ImageView();
    public ImageView coffeeMachine;
    public ImageView kitchenAid;

    public Machine coffeeeMachine = new Machine(0);
    public Machine cakeMachine = new Machine(0);

    @FXML
    private ImageView waiter;

    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();
    }

    public void changeCoffeeImage() throws FileNotFoundException {

        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "hotcoffee.png";
        InputStream stream = new FileInputStream(filePath);
        Image hotCoffee = new Image(stream);
        startButton.setImage(hotCoffee);
    }

    public void changeCoffeeImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "coffee.png";
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        startButton.setImage(coffee);
    }

    public void showCoffee() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (coffeeeMachine.getProduced()){
            filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "coffeeMachine.png";
            coffeeeMachine.productTaken();
        } else {
            filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "CoffeemachineWithCoffee.png";
            coffeeeMachine.produceProduct();

        }
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        coffeeMachine.setImage(coffee);
    }

    public void showCake() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (cakeMachine.getProduced()){
            filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "kitchenAid.png";
            cakeMachine.productTaken();
        } else {
            filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "kitchenAidUsed.png";
            cakeMachine.produceProduct();
        }
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        kitchenAid.setImage(coffee);
    }


}