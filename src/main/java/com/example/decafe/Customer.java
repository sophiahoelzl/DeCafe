package com.example.decafe;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;


public class Customer extends HelloController{
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private int coin = 0;
    private int index;

    private ImageView customer;
    private Label orderr;
    private int table;
    private Timer x;

    public boolean alreadyOrdered;

    Customer(ImageView image, Label label, int table) {
        this.customer = image;
        this.orderr = label;
        this.alreadyOrdered = false;
        this.table = table;
        this.x = new Timer();
    }

    public void waitingTime(ImageView customerpic, Label order, List<Customer> customerList, List <Integer> num) {
        x.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        leave(customerpic, order, customerList, num);
                        x.cancel();
                    }
                },
                60000
        );
    }

    public boolean isAlreadyOrdered() {
        return this.alreadyOrdered;
    }

    public int getTable() {
        return table;
    }


    public ImageView getImage() {
        return this.customer;
    }

    public Label getLabel() {
        return this.orderr;
    }

    public String getRandomOrder() {

        Random random = new Random();
        int number = random.nextInt(2);

        switch (number) {
            case 0 -> order = "cake";
            case 1 -> order = "coffee";
        }

        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void displayOrder(Label orderlabel) {
        orderlabel.setVisible(true);
        order = getRandomOrder();
        setOrder(order);
        orderlabel.setText(order);
        this.alreadyOrdered = true;
    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public boolean checkOrder(Label orderlabel, ImageView customerPic, Player CofiBrew, Label coinlabel, Customer customer, List<Customer> customerList, ImageView waiter,  List <Integer> num) throws FileNotFoundException, InterruptedException {
        Timer t = new Timer();
        if (CofiBrew.getProduct().equals(customer.getOrder())) {
            orderlabel.setText(":)");
            coin += 5;
            coinlabel.setText(String.valueOf(coin));
            this.alreadyOrdered = false;

            String filePath = CofiBrew.getImageWithoutProduct();
            InputStream stream = new FileInputStream(filePath);
            Image cofi = new Image(stream);
            waiter.setImage(cofi);
            CofiBrew.setProduct("none");

            t.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            leave(customerPic, orderlabel, customerList, num);
                            t.cancel();
                        }
                    },
                    1000
            );
            this.x.cancel();
            return true;
        } else {
            orderlabel.setText(":(");

            String filePath = CofiBrew.getImageWithoutProduct();
            InputStream stream = new FileInputStream(filePath);
            Image cofi = new Image(stream);
            waiter.setImage(cofi);
            CofiBrew.setProduct("none");
            t.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            leave(customerPic, orderlabel, customerList, num);
                            t.cancel();
                        }
                    },
                    1000
            );
            this.x.cancel();
            return false;
        }
    }


    public void leave (ImageView image, Label label, List<Customer> customerList, List<Integer> num) {
        label.setVisible(false);
        image.setVisible(false);
        customerList.removeIf(customer -> customer.getImage().equals(image));
        num.add(this.table);
    }
}

