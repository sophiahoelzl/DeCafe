package com.example.decafe;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Customer {
    private int positionX; // X-Koordinate vom Gast
    private int positionY; // Y-Koordinate vom Gast
    private String image;  // Bild vom Gast
    private int tableNr; //Tischnummer auf dem der Gast sitzt
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private ImageView pics[] = new ImageView[8];
    private ImageView customerImage = new ImageView();


    @FXML
    public ImageView first;
    @FXML
    public ImageView second;
    @FXML
    public ImageView third;
    @FXML
    public ImageView fourth;
    @FXML
    public ImageView fifth;
    @FXML
    public ImageView sixth;
    @FXML
    public ImageView seventh;
    @FXML
    public ImageView eighth;


    //Getter
    public int getPositionX() {
        return positionX;
    }

    public ImageView[] makeArrayCustomer() {

        pics[0] = new ImageView(String.valueOf(getClass().getResource("characterOben.png")));
        pics[1] = new ImageView(String.valueOf(getClass().getResource("characterOben.png")));
        pics[2] = new ImageView(String.valueOf(getClass().getResource("characterUnten.png")));
        pics[3] = new ImageView(String.valueOf(getClass().getResource("characterUnten.png")));
        pics[4] = new ImageView(String.valueOf(getClass().getResource("characterLinks.png")));
        pics[5] = new ImageView(String.valueOf(getClass().getResource("characterUnten.png")));
        pics[6] = new ImageView(String.valueOf(getClass().getResource("characterUnten.png")));
        pics[7] = new ImageView(String.valueOf(getClass().getResource("characterRechts.png")));

        System.out.println("wtf2");

        return pics;
    }

    public int getPositionY() {
        return positionY;
    }

    public String getImage() {
        return image;
    }

    public int getTableNr() {
        return tableNr;
    }

    public String getOrder() {

        Random random = new Random();
        int number = random.nextInt(2);

        switch (number) {
            case 0 -> order = "Cake";
            case 1 -> order = "Coffee";
        }

        return order;
    }

    //Setter
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTableNr(int tableNr) {
        this.tableNr = tableNr;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public ImageView getRandomPic(ImageView[] pics){

        Random random = new Random();
        int index = random.nextInt(8);

        return pics[index];
    }

    //Gast sucht nach einem Tisch wo er sich hinsetzten kann
    public void searchForTable() {

        pics = makeArrayCustomer();
        customerImage = getRandomPic(pics);
        displayPerson(customerImage);

        System.out.println("wtf3");
    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public void displayPerson (ImageView image){

        //image.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), image);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(5);
        fadeTransition.play();

        System.out.println("wtf4");

    }

    //Funktion um die Bestellung vom Kunden anzeigen zu lassen - vllt auch im HelloController
    public void displayOrder(String order){

    }

    //Funktion damit der Kunde geht
    public void leave (ImageView image) {

        image.setStyle("visibility: false;");
    }
}
