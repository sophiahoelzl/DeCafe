package com.example.decafe;

import com.example.decafe.enumerations.Products;

public class Customer {
    private int positionX; // X-Koordinate vom Gast
    private int positionY; // Y-Koordinate vom Gast
    private String image;  // Bild vom Gast
    private int tableNr; //Tischnummer auf dem der Gast sitzt
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen

    //Getter
    public int getPositionX() {
        return positionX;
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

    //Gast sucht nach einem Tisch wo er sich hinsetzten, kann
    public void searchForTable(){

    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public void displayPerson (String image){

    }

    //Funktion um die Bestellung vom Kunden anzeigen zu lassen - vllt auch im HelloController
    public void displayOrder(String order){

    }

    //Funktion damit der Kunde geht
    public void leave () {

    }
}
