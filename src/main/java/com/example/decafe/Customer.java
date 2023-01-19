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
import java.util.List;
import java.util.Random;


public class Customer {
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private int coin = 0;

    private ImageView customer;
    private Label orderr;

    public boolean alreadyOrdered;

    Customer(ImageView image, Label label){
        this.customer = image;
        this.orderr = label;
        this.alreadyOrdered = false;
    }

    public int getCoin(){
        return coin;
    }


    public ImageView getImage(){
        return this.customer;
    }

    public Label getLabel(){
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

    public String getOrder(){
        return order;
    }

    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public boolean displayPerson (Label orderlabel, ImageView customerPic, Player CofiBrew, Label coinlabel, Customer customer, List<Customer> customerList, ImageView waiter) throws FileNotFoundException, InterruptedException {

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(60), ev -> {
            leave(customerPic, orderlabel, customerList);
        }));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();

        if (!customer.alreadyOrdered) {
            order = getRandomOrder();
            setOrder(order);
            orderlabel.setText(order);
            this.alreadyOrdered = true;
            System.out.println(alreadyOrdered);

        }
        else {
            if (CofiBrew.getProduct().equals(customer.getOrder())){
                orderlabel.setText(":)");
                coin += 5;
                coinlabel.setText(String.valueOf(coin));
                this.alreadyOrdered = false;

                String filePath = CofiBrew.getImageWithoutProduct();
                InputStream stream = new FileInputStream(filePath);
                Image cofi = new Image(stream);
                waiter.setImage(cofi);
                CofiBrew.setProduct("none");

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                    leave(customerPic, orderlabel, customerList);
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

                return true;


            }else {
                orderlabel.setText(":(");

                String filePath = CofiBrew.getImageWithoutProduct();
                InputStream stream = new FileInputStream(filePath);
                Image cofi = new Image(stream);
                waiter.setImage(cofi);
                CofiBrew.setProduct("none");

                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
                    leave(customerPic, orderlabel, customerList);
                }));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

                return false;

            }
        }

        return false;
    }

    public void leave (ImageView image, Label label, List<Customer> customerList) {
        label.setText("");
        image.setVisible(false);

        customerList.removeIf(customer -> customer.getImage().equals(image));
    }
}

