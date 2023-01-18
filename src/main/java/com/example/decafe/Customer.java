package com.example.decafe;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Customer {
    private int positionX; // X-Koordinate vom Gast
    private int positionY; // Y-Koordinate vom Gast
    private int tableNr; //Tischnummer auf dem der Gast sitzt
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private int coin = 0;

    private ImageView customer;
    private Label orderr;
    public HelloController controller = new HelloController();

    private boolean alreadyorder = false;

    Customer(ImageView image, Label label){
        this.customer = image;
        this.orderr = label;
    }


    public int getTableNr() {
        return tableNr;
    }

    public String getOrder() {

        Random random = new Random();
        int number = random.nextInt(2);

        switch (number) {
            case 0 -> order = "cake";
            case 1 -> order = "coffee";
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

    public void setOrder(String order) {
        this.order = order;
    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public void displayPerson (Label orderlabel, ImageView customerPic, Player CofiBrew, Label coinlabel) throws FileNotFoundException {

        if (!alreadyorder) {
            order = getOrder();
            orderlabel.setText(order);
            alreadyorder = true;
        }
        else {
            if (CofiBrew.getProduct().equals(order)){
                orderlabel.setText(":)");
                coin += 1;
                coinlabel.setText(String.valueOf(coin));
                alreadyorder = false;
                CofiBrew.setProduct("none");
                controller.noProduct();

                Timer t = new Timer();
                t.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                leave(customerPic, orderlabel);
                                t.cancel();
                            }
                        },
                        1000
                );

            }else {
                orderlabel.setText(":(");

                Timer t = new Timer();
                t.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                leave(customerPic, orderlabel);
                                t.cancel();
                            }
                        },
                        1000
                );
            }
        }
    }

    //Funktion damit der Kunde geht
    public void leave (ImageView image, Label label) {
        label.setText("");
        image.setVisible(false);
        controller.customerList.remove(customer);
    }
}

