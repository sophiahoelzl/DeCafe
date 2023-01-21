package com.example.decafe;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;


public class Customer {
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen
    private int coin = 0;
    private int index;

    private ImageView customer;
    private Label orderr;
    private int table;
    private Timer x;
    private ImageView smiley;
    private ImageView coinImage;

    public boolean alreadyOrdered;
    public boolean green;
    public boolean yellow;
    public boolean red;
    public String colorsmiley;

    Customer(ImageView image, Label label, int table, ImageView smiley, ImageView coinImage) {
        this.customer = image;
        this.orderr = label;
        this.alreadyOrdered = false;
        this.table = table;
        this.x = new Timer();
        this.smiley = smiley;
        this.coinImage = coinImage;
    }

    public String getSmiley(){
        if (green){
            return "green";
        }else if (yellow){
            return "yellow";
        }else if (red){
            return "red";
        }
        return null;
    }

    public String getSmileyGreen(){
        File f = new File("");
        String smileyGreen = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "smileygreen.png";

        return smileyGreen;
    }

    public String getSmileyYellow(){
        File f = new File("");
        String smileyYellow = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "smileyyellow.png";

        return smileyYellow;
    }

    public String getSmileyRed(){
        File f = new File("");
        String smileyRed = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "smileyred.png";

        return smileyRed;
    }

    public void waitingTime(ImageView customerpic, Label order, List<Customer> customerList, List <Integer> num)  {
        TimerTask timerTask = new TimerTask() {

            int seconds = 60;
            @Override
            public void run() {

                seconds --;

                if (seconds == 59){
                    String filePath = getSmileyGreen();
                    smiley.setVisible(true);
                    InputStream stream = null;
                    try {
                        stream = new FileInputStream(filePath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image sm = new Image(stream);
                    smiley.setImage(sm);
                    green = true;
                    yellow = false;
                    red = false;
                }else if (seconds == 30){
                    String filePath = getSmileyYellow();
                    smiley.setVisible(true);
                    InputStream stream = null;
                    try {
                        stream = new FileInputStream(filePath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image sm = new Image(stream);
                    smiley.setImage(sm);
                    green = false;
                    yellow = true;
                    red = false;
                }else if (seconds == 15){
                    String filePath = getSmileyRed();
                    smiley.setVisible(true);
                    InputStream stream = null;
                    try {
                        stream = new FileInputStream(filePath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Image sm = new Image(stream);
                    smiley.setImage(sm);
                    green = false;
                    yellow = false;
                    red = true;
                }
                else if (seconds == 0){
                    leave(customerpic, order, customerList, num);
                }
            }
        };

        x.schedule(timerTask, 0, 1000);

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

    public ImageView getCoinImage() {
        return coinImage;
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
        String sm;
        if (CofiBrew.getProduct().equals(customer.getOrder())) {
            orderlabel.setText("");

            sm = getSmiley();
            if (sm.equals("green")){
                coin += 5;
                colorsmiley = sm;
            }else if (sm.equals("yellow")){
                coin += 4;
                colorsmiley = sm;
            }else if (sm.equals("red")){
                coin += 3;
                colorsmiley = sm;
            }

            this.alreadyOrdered = false;
            this.x.cancel();
            smiley.setVisible(false);

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
        smiley.setVisible(false);
        num.add(table);
        this.x.cancel();
    }
}

