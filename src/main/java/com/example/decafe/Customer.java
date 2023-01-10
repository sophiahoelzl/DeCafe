package com.example.decafe;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.Random;

public class Customer {
    private int positionX; // X-Koordinate vom Gast
    private int positionY; // Y-Koordinate vom Gast
    private String image;  // Bild vom Gast
    private int tableNr; //Tischnummer auf dem der Gast sitzt
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private ImageView pics[] = new ImageView[8];
    private ImageView customerImage = new ImageView();

    private boolean alreadyorder = false;


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

        System.out.println(pics[index].getImage().getUrl());

        return pics[index];
    }

    //Gast sucht nach einem Tisch wo er sich hinsetzten kann
    public void searchForTable() {

        pics = makeArrayCustomer();
        customerImage = getRandomPic(pics);
    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public void displayPerson (Label orderlabel, ImageView customerPic){

        order = getOrder();

        if (!alreadyorder) {
            orderlabel.setText(order);
            alreadyorder = true;
        }
        else {
            orderlabel.setText(":)");
            alreadyorder = false;

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), orderlabel);
            fadeTransition.setFromValue(2);
            fadeTransition.setToValue(0);
            fadeTransition.play();

            leave(customerPic);

        }

    }

    //Funktion um die Bestellung vom Kunden anzeigen zu lassen - vllt auch im HelloController
    public void displayOrder(String order){

    }


    //Funktion damit der Kunde geht
    public void leave (ImageView image) {

        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(2), image);
        fadeTransition2.setFromValue(2);
        fadeTransition2.setToValue(0);
        fadeTransition2.play();
        //image.setStyle("visibility: false;");
    }
}
