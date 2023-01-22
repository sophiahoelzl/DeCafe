package com.example.decafe;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;


public class Customer {
    private String order; //The order of the customer
    private ImageView customer; //picture of the customer
    private Label orderLabel; //label that displays order
    private int chair; //number of chair the customer is sitting
    private Timer x; //timer for the 60 seconds waiting time
    private static Timer t; //timer for leaving, spawning
    private ImageView smiley; //picture of smiley for the mood of the customer
    private ImageView coinImage; //picture of the money the customer is leaving behind

    private boolean alreadyOrdered; //boolean to see if the customer has already ordered
    private boolean green; //boolean for smiley
    private boolean yellow; //boolean for smiley
    private boolean red; //boolean for smiley

    private boolean left = true; //boolean to see if customer has left

    public static List<Customer> customerList = new ArrayList<>(); //list with all customers that are in the café
    public static List<Customer> allCustomers = new ArrayList<>(); //list with all customers
    public static List<Integer> num = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7)); //integer list with all chair numbers
    public static ImageView[] pics; //array with all customer pictures
    private static int number = 0;
    public static ImageView[] smileyImages; //image for smiley
    public static Label[] orderLabels; //label for order
    public static ImageView[] coinImages; //image for coins

    Customer(){}


    Customer(ImageView image, Label label, int chair, ImageView smiley, ImageView coinImage) {
        this.customer = image;
        this.orderLabel = label;
        this.alreadyOrdered = false;
        this.chair = chair;
        this.smiley = smiley;
        this.coinImage = coinImage;
        this.x = new Timer();
    }

    public static Timer getT() {
        return t;
    }

    public Timer getX() {
        return x;
    }

    public static void addNum(int table) { //add chair number to the list when customer has left
        Customer.num.add(table);
    }

    public boolean isGreen() { //to see if the color of the smiley
        return green;
    }

    public boolean isRed() { //to see if the color of the smiley
        return red;
    }

    public boolean isYellow() { //to see if the color of the smiley
        return yellow;
    }

    public boolean isAlreadyOrdered() { //return if the customer has already ordered or not
        return this.alreadyOrdered;
    }

    public int getTable() { //get the number of the chair the customer is sitting
        return chair;
    }

    public ImageView getImage() { //returns the image of the customer
        return this.customer;
    }

    public Label getLabel() { //returns the label of the customer
        return this.orderLabel;
    }

    public String getRandomOrder() { //returns random order

        Random random = new Random();
        int number = random.nextInt(2);

        switch (number) {
            case 0 -> order = "cake";
            case 1 -> order = "coffee";
        }

        return order;
    }

    public ImageView getCoinImage() { //returns the image of the coin
        return coinImage;
    }

    public void setOrder(String order) { //sets the order of the customer
        this.order = order;
    }

    public static void setT(Timer te) { //sets the timer
        t = te;
    }

    public String getOrder() { //returns the order of the customer
        return order;
    }

    //General methode to create an Imagepath
    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    //Returns the appropriate image for the customer
    public static ImageView getImage(ImageView customer, ImageView[] searchArray ){
        ImageView wantedImage = new ImageView();

        if (pics[0].equals(customer)) {
            wantedImage = searchArray[0];
        } else if (pics[1].equals(customer)) {
            wantedImage = searchArray[1];
        } else if (pics[2].equals(customer)) {
            wantedImage = searchArray[2];
        } else if (pics[3].equals(customer)) {
            wantedImage = searchArray[3];
        } else if (pics[4].equals(customer)) {
            wantedImage = searchArray[4];
        } else if (pics[5].equals(customer)) {
            wantedImage = searchArray[5];
        } else if (pics[6].equals(customer)) {
            wantedImage = searchArray[6];
        } else if (pics[7].equals(customer)) {
            wantedImage = searchArray[7];
        }

        return wantedImage;
    }

    //Returns the appropriate label for the customer
    public static Label getLabel(ImageView customer) {

        Label customerOrder = new Label();

        if (pics[0].equals(customer)) {
            customerOrder = orderLabels[0];
        } else if (pics[1].equals(customer)) {
            customerOrder = orderLabels[1];
        } else if (pics[2].equals(customer)) {
            customerOrder = orderLabels[2];
        } else if (pics[3].equals(customer)) {
            customerOrder = orderLabels[3];
        } else if (pics[4].equals(customer)) {
            customerOrder = orderLabels[4];
        } else if (pics[5].equals(customer)) {
            customerOrder = orderLabels[5];
        } else if (pics[6].equals(customer)) {
            customerOrder = orderLabels[6];
        } else if (pics[7].equals(customer)) {
            customerOrder = orderLabels[7];
        }

        return customerOrder;

    }

    //Returns random customer picture
    public static ImageView getRandomPic(){
        Random random = new Random();
        int index = num.get(random.nextInt(num.size()));
        number = index;

        if (!num.contains(index)) { //when the customer is already visible make new random number
            getRandomPic();
        }

        num.remove(Integer.valueOf(index)); //remove the number from the number list of chairs so there are no duplicates

        return pics[index];
    }

    //Methode to spawn customers
    public static void spawnCustomers(){
        if (customerList.size() < 3 && num.size() != 0) { //spawn a new customer this when under 3 customers are in the cafe
            ImageView customerImage = getRandomPic(); //get random picture from Array
            customerImage.setVisible(true); //make this picture visible

            Label order = getLabel(customerImage); //get the label for the customer
            ImageView smiley = getImage(customerImage, smileyImages); //gets the smiley picture for the customer
            ImageView coin = getImage(customerImage, coinImages); //gets the coin picture for the customer


            Customer customer = new Customer(customerImage, order, number, smiley, coin); //make new customer object
            customerList.add(customer); //to check if not more than 3 customers are in the store
            allCustomers.add(customer); //to stop all timers that are still alive even after customer has left
            customer.waitingTime(); //place customer in the waitingTime of  60 seconds
        }
    }

    //Timer to spawn the customers
    public void startTimerSpawn(int duration, Timer t){
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Customer.spawnCustomers();
                        t.purge();
                    }
                },
                duration * 1000L
        );
    }

    //Methode when customer leaves the coffee shop
    public void leaveCoffeeShop(boolean left, Player CofiBrew){
        CofiBrew.setProduct("none");
        startTimerLeave(this, left);
    }

    //Methode for the timer when customer leaves
    public void startTimerLeave (Customer customer, boolean left){
        this.orderLabel.setVisible(false);
        this.smiley.setVisible(false);
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        customer.left = left;
                        try {
                            leave(customer.getImage());
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        t.purge();
                    }
                },
                1000
        );
        this.x.cancel(); //cancel the 60 seconds when customer left
    }

    //Methode for the general 60 seconds timer
    public void waitingTime()  {
        Customer customer = this;
        TimerTask timerTask = new TimerTask() {
            int seconds = 60;
            @Override
            public void run() {
                seconds --;
                if (seconds == 59){ //set green smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileygreen.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = true;
                    yellow = false;
                    red = false;
                }else if (seconds == 30){ //set yellow smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyyellow.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = true;
                    red = false;
                }else if (seconds == 15){ //set red smiley when the customer has just spawned
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyred.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = false;
                    red = true;
                }
                else if (seconds == 0){ //when the timer has finished - customer leaves
                    startTimerLeave(customer, true);
                }
            }
        };

        this.x.schedule(timerTask, 0, 1000);//it should call this methode every second

    }

    //Methode to display order
    public void displayOrder(Label orderlabel) {
        orderlabel.setVisible(true);
        order = getRandomOrder();
        setOrder(order);
        orderlabel.setText(order);
        this.alreadyOrdered = true;
    }


    //Methode to check if the order is right or wrong
    public boolean checkOrder(Player CofiBrew, Customer customer, ImageView waiter) throws FileNotFoundException{
        if (CofiBrew.getProduct().equals(customer.getOrder())) { //if CofiBrew has the right order
            waiter.setImage(createImage(CofiBrew.getImageWithoutProduct())); //set CofiBrew without order
            leaveCoffeeShop(false, CofiBrew);
            return true;
        } else {
            waiter.setImage(createImage(CofiBrew.getImageWithoutProduct())); //also set CofiBrew without order if it was false
            leaveCoffeeShop(true, CofiBrew); //leave the coffeeshop
            return false;
        }
    }

    //when the customer leaves after 60 seconds without being served
    public static void noMoneySpent(Customer customer) throws FileNotFoundException {
        customer.coinImage.setVisible(false);
        customer.coinImage.setDisable(true);
        num.add(customer.getTable());
        customer.startTimerSpawn(5, t);
    }

    //Methode for when the customer leaves
    public void leave (ImageView image) throws FileNotFoundException {
        image.setVisible(false);
        customerList.removeIf(customer -> customer.getImage().equals(image)); //remove customer from customerList
        coinImage.setVisible(true);
        coinImage.setDisable(false);
        if (left){ //when customer leaves after 60 seconds
            coinImage.setImage(this.createImage("coin.png"));
            coinImage.setOnMouseClicked(event1 -> {
                try {
                    noMoneySpent(this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

