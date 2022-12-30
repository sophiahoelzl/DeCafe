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
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Customer {
    private int positionX; // X-Koordinate vom Gast
    private int positionY; // Y-Koordinate vom Gast
    private String image;  // Bild vom Gast
    private int tableNr; //Tischnummer auf dem der Gast sitzt
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private List<ImageView> list = new LinkedList<>();
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

    public List<ImageView> makeListCustomer() {

        list.add(first);
        list.add(second);
        list.add(third);
        list.add(fourth);
        list.add(fifth);
        list.add(sixth);
        list.add(seventh);
        list.add(eighth);

        return list;
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

    public ImageView getRandomPic(List<ImageView> list){
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    //Gast sucht nach einem Tisch wo er sich hinsetzten kann
    public void searchForTable() throws FileNotFoundException {

        list = makeListCustomer();
        customerImage = getRandomPic(list);
        displayPerson(customerImage);

        File f = new File("");
        String filePath;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            filePath = f.getAbsolutePath().replace("\\", "\\\\") + "\\src\\main\\resources\\com\\example\\decafe\\characterOben.png";;
        } else {
            filePath = f.getAbsolutePath().replace("/", "//") + "//src//main//resources//com//example//decafe//characterOben.png";;
        }
        InputStream stream = new FileInputStream(filePath);
        ImageView customerPic = new ImageView(stream);
        displayPerson(customerPic);
                

    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public void displayPerson (ImageView image){

        image.setVisible(true);
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10), image);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(5);

    }

    //Funktion um die Bestellung vom Kunden anzeigen zu lassen - vllt auch im HelloController
    public void displayOrder(String order){

    }

    //Funktion damit der Kunde geht
    public void leave () {

    }
}
