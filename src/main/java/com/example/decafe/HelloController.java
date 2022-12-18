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
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloController {
    @FXML
    ImageView startButton = new ImageView();

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
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            filePath = f.getAbsolutePath().replace("\\", "\\\\") + "\\src\\main\\resources\\com\\example\\decafe\\hotcoffee.png";;
        } else {
            filePath = f.getAbsolutePath().replace("/", "//") + "//src//main//resources//com//example//decafe//hotcoffee.png";;
        }
        InputStream stream = new FileInputStream(filePath);
        Image hotCoffee = new Image(stream);
        startButton.setImage(hotCoffee);
    }

    public void changeCoffeeImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            filePath = f.getAbsolutePath().replace("\\", "\\\\") + "\\src\\main\\resources\\com\\example\\decafe\\coffee.png";;
        } else {
            filePath = f.getAbsolutePath().replace("/", "//") + "//src//main//resources//com//example//decafe//coffee.png";;
        }
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        startButton.setImage(coffee);
    }


}