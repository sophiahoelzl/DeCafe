package com.example.decafe;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.util.*;


public class Customer {
    private String order; //Was der Gast bestellt - Kaffee oder Kuchen

    private ImageView customer;
    private Label orderr;
    private int table;
    private Timer x;
    private Timer t;
    private Timer y;
    private Timer s;
    private Timer c;
    private ImageView smiley;
    private ImageView coinImage;

    private boolean alreadyOrdered;
    private boolean green;
    private boolean yellow;
    private boolean red;

    private boolean left = true;

    public static List<Customer> customerList = new ArrayList<>();
    public static List<Customer> allCustomers = new ArrayList<>();
    public static List<Integer> num = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7));
    public static ImageView[] pics;
    private static int number = 0;
    public static ImageView[] simleyImages;
    public static Label[] orderLabels;
    public static ImageView[] coinImages;

    Customer(){
        this.t = new Timer();
        this.s = new Timer();
        this.y = new Timer();
        this.c = new Timer();
        this.x = new Timer();
    }


    Customer(ImageView image, Label label, int table, ImageView smiley, ImageView coinImage) {
        this.customer = image;
        this.orderr = label;
        this.alreadyOrdered = false;
        this.table = table;
        this.x = new Timer();
        this.smiley = smiley;
        this.coinImage = coinImage;
        this.t = new Timer();
        this.s = new Timer();
        this.y = new Timer();
        this.c = new Timer();
    }

    public static void setCustomerList(List<Customer> customerList) {
        Customer.customerList = customerList;
    }

    public static void setPics(ImageView[] pics) {
        Customer.pics = pics;
    }

    public static List<Customer> getCustomerList() {
        return customerList;
    }

    public static ImageView[] getPics() {
        return pics;
    }

    public Timer getT() {
        return t;
    }

    public Timer getX() {
        return x;
    }

    public Timer getY() {
        return y;
    }

    public Timer getS() {
        return s;
    }

    public Timer getC() {
        return c;
    }

    public static void addNum(int table) {
        Customer.num.add(table);
    }

    public boolean isGreen() {
        return green;
    }

    public boolean isRed() {
        return red;
    }

    public boolean isYellow() {
        return yellow;
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

    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    public static ImageView getImage(ImageView cust, ImageView[] searchArray ){
        ImageView wantedImage = new ImageView();

        if (pics[0].equals(cust)) {
            wantedImage = searchArray[0];
        } else if (pics[1].equals(cust)) {
            wantedImage = searchArray[1];
        } else if (pics[2].equals(cust)) {
            wantedImage = searchArray[2];
        } else if (pics[3].equals(cust)) {
            wantedImage = searchArray[3];
        } else if (pics[4].equals(cust)) {
            wantedImage = searchArray[4];
        } else if (pics[5].equals(cust)) {
            wantedImage = searchArray[5];
        } else if (pics[6].equals(cust)) {
            wantedImage = searchArray[6];
        } else if (pics[7].equals(cust)) {
            wantedImage = searchArray[7];
        }

        return wantedImage;
    }

    public static Label getLabel(ImageView cust) {

        Label customerOrder = new Label();

        if (pics[0].equals(cust)) {
            customerOrder = orderLabels[0];
        } else if (pics[1].equals(cust)) {
            customerOrder = orderLabels[1];
        } else if (pics[2].equals(cust)) {
            customerOrder = orderLabels[2];
        } else if (pics[3].equals(cust)) {
            customerOrder = orderLabels[3];
        } else if (pics[4].equals(cust)) {
            customerOrder = orderLabels[4];
        } else if (pics[5].equals(cust)) {
            customerOrder = orderLabels[5];
        } else if (pics[6].equals(cust)) {
            customerOrder = orderLabels[6];
        } else if (pics[7].equals(cust)) {
            customerOrder = orderLabels[7];
        }

        return customerOrder;

    }

    public static ImageView getRandomPic(){
        Random random = new Random();
        int index = num.get(random.nextInt(num.size()));
        number = index;

        if (!num.contains(index)) {
            getRandomPic();
        }

        num.remove(Integer.valueOf(index));

        return pics[index];
    }

    public static void spawnCustomers(){
        if (customerList.size() < 3 && num.size() != 0) {
            ImageView customerImage = getRandomPic(); //get random picture from Array
            customerImage.setVisible(true);//make this picture visible

            Label order = getLabel(customerImage);
            ImageView smiley = getImage(customerImage, simleyImages);
            ImageView coin = getImage(customerImage, coinImages);


            Customer customer = new Customer(customerImage, order, number, smiley, coin);
            customerList.add(customer);
            allCustomers.add(customer);
            customer.waitingTime(customerImage, order);
        }
    }

    public void startTimerSpawn(int duration, Timer t){
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        Customer.spawnCustomers();
                        t.cancel();
                    }
                },
                duration * 1000L
        );
    }

    public void leaveCoffeeShop(Timer t, boolean left, Player CofiBrew, ImageView waiter){
        CofiBrew.setProduct("none");
        startTimerLeave(t, this, left);
    }

    public void startTimerLeave (Timer t, Customer customer, boolean left){
        this.orderr.setVisible(false);
        this.smiley.setVisible(false);
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        customer.left = left;
                        leave(customer.getImage(), customer.getLabel());
                        t.cancel();
                    }
                },
                1000
        );
        this.x.cancel();
    }

    public void waitingTime(ImageView customerpic, Label order)  {
        Customer cust = this;
        TimerTask timerTask = new TimerTask() {
            int seconds = 60;
            @Override
            public void run() {
                seconds --;
                if (seconds == 59){
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileygreen.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = true;
                    yellow = false;
                    red = false;
                }else if (seconds == 30){
                    smiley.setVisible(true);
                    try {
                        smiley.setImage(createImage("smileyyellow.png"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    green = false;
                    yellow = true;
                    red = false;
                }else if (seconds == 15){
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
                else if (seconds == 0){
                    startTimerLeave(cust.t, cust, true);
                }
            }
        };

        x.schedule(timerTask, 0, 1000);

    }

    public void displayOrder(Label orderlabel) {
        orderlabel.setVisible(true);
        order = getRandomOrder();
        setOrder(order);
        orderlabel.setText(order);
        this.alreadyOrdered = true;
    }


    //Funktion um Bild von Gast anzuzeigen - vllt auch in HelloController
    public boolean checkOrder(Player CofiBrew, Customer customer, ImageView waiter) throws FileNotFoundException{
        if (CofiBrew.getProduct().equals(customer.getOrder())) {
            waiter.setImage(createImage(CofiBrew.getImageWithoutProduct()));
            leaveCoffeeShop(this.t, false, CofiBrew, waiter);
            return true;
        } else {
            waiter.setImage(createImage(CofiBrew.getImageWithoutProduct()));
            leaveCoffeeShop(this.t, true, CofiBrew, waiter);
            return false;
        }
    }

    public static void noMoneySpent(Customer customer) throws FileNotFoundException {
        customer.coinImage.setVisible(false);
        customer.coinImage.setDisable(true);
        customer.coinImage.setImage(customer.createImage("coin.png"));
        num.add(customer.getTable());
        customer.startTimerSpawn(5, customer.s);
    }

    public void leave (ImageView image, Label label) {
        image.setVisible(false);
        customerList.removeIf(customer -> customer.getImage().equals(image));
        coinImage.setVisible(true);
        coinImage.setDisable(false);
        if (left){
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

