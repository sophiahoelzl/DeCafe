package com.example.decafe;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;


public class HelloController implements Initializable {


    @FXML
    ImageView startButton = new ImageView();
    public ImageView coffeeMachine;
    public ImageView kitchenAid;
    public Label controlLabel;

    public Machine coffeeeMachine = new Machine(5, "CoffeemachineWithCoffee.png", "coffeeMachine.png", "coffee");
    public Machine cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");
    //public Customer customer = new Customer();
    public Player CofiBrew = new Player("cofiBrew.png", "cofiBrewWithCake.png", "cofiBrewWithCoffee.png");

    private BooleanProperty wPressed = new SimpleBooleanProperty();
    private BooleanProperty aPressed = new SimpleBooleanProperty();
    private BooleanProperty sPressed = new SimpleBooleanProperty();
    private BooleanProperty dPressed = new SimpleBooleanProperty();

    private BooleanBinding keyPressed = wPressed.or(aPressed).or(sPressed).or(dPressed);

    @FXML
    private ImageView waiter;

    @FXML
    public Label orderlabel1 = new Label();
    public Label orderlabel2 = new Label();
    public Label orderlabel3 = new Label();
    public Label orderlabel4 = new Label();
    public Label orderlabel5 = new Label();
    public Label orderlabel6 = new Label();
    public Label orderlabel7 = new Label();
    public Label orderlabel8 = new Label();
    public ImageView first;
    public ImageView second;
    public ImageView third;
    public ImageView fourth;
    public ImageView fifth;
    public ImageView sixth;
    public ImageView seventh;
    public ImageView eighth;
    public ProgressBar progressCoffee;
    public ProgressBar progressCake;
    public Label table1;
    public Label table2;
    public Label table3;
    public Label table4;
    public Label plantsAbove;
    public Label countRight;
    public Label countBelow;
    public Label customerTop1;
    public Label customerTop2;
    public Label customerTop3;
    public Label customerTop4;
    public Label customerBot1;
    public Label customerBot2;
    public Label customerBot3;
    public Label customerBot4;
    public Label plant;
    public Label edgeBot;
    public Label edgeTop;
    public Label edgeLeft;
    public Label edgeRight;
    public Label coinLabel;

    private ImageView[] pics = new ImageView[8];
    private ImageView customerImage = new ImageView();
    public List<Customer> customerList = new ArrayList<>();
    public Random random = new Random();
    public int coin = 0;


    private int movementVariable = 4;
    public ArrayList <Label> collisionObjects = new ArrayList<Label>();
    // jump from start screen to game screen
    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();
    }

    // for smoother motion
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            //File f = new File("");
            //String filePath;
            //filePath = "";
            double move = movementVariable;

            // if two keys are pressed at once and player moves diagonally
            if (wPressed.get() && aPressed.get() || wPressed.get() && dPressed.get() ||
                    sPressed.get() && aPressed.get() || sPressed.get() && dPressed.get())
                move -= movementVariable - Math.sqrt(Math.pow(movementVariable, 2) / 2);

            // for collision detection later
            double xMove = 0;
            double yMove = 0;

            if (wPressed.get()) {
                yMove = -move;
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrew.png";
            }// if waiter should go up

            if (sPressed.get()) {
                yMove = move;
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrewUnten.png";
            }// if waiter should go down

            if (aPressed.get()) {
                xMove = -move;
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrewLinks.png";
            }// if waiter should move left

            if (dPressed.get()) {
                xMove = move; // if waiter should move right
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrewRechts.png";
            }

            waiter.setLayoutX(waiter.getLayoutX() + xMove);
            waiter.setLayoutY(waiter.getLayoutY() + yMove);
            if (checkForCollision(waiter)) {
                waiter.setLayoutX(waiter.getLayoutX() - xMove);
                waiter.setLayoutY(waiter.getLayoutY() - yMove);
            }

            /*InputStream stream = null;
            try {
                stream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image cofi = new Image(stream);
            waiter.setImage(cofi);*/
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyPressed.addListener((((observableValue, aBoolean, t1) -> { // if any key from the four keys is pressed
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        })));
        collisionObjects.add(plantsAbove);
        collisionObjects.add(plant);
        collisionObjects.add(table1);
        collisionObjects.add(table2);
        collisionObjects.add(table3);
        collisionObjects.add(table4);
        collisionObjects.add(customerBot1);
        collisionObjects.add(customerBot2);
        collisionObjects.add(customerBot3);
        collisionObjects.add(customerBot4);
        collisionObjects.add(customerTop1);
        collisionObjects.add(customerTop2);
        collisionObjects.add(customerTop3);
        collisionObjects.add(customerTop4);
        collisionObjects.add(countBelow);
        collisionObjects.add(countRight);
        collisionObjects.add(edgeBot);
        collisionObjects.add(edgeLeft);
        collisionObjects.add(edgeRight);
        collisionObjects.add(edgeTop);

        pics = makeArrayCustomer();

        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(3), ev -> {
            searchForTable(pics);
        }));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(true);
            case A -> aPressed.set(true);
            case S -> sPressed.set(true);
            case D -> dPressed.set(true);
        }
    }


    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(false);
            case A -> aPressed.set(false);
            case S -> sPressed.set(false);
            case D -> dPressed.set(false);
        }
    }



    // start screen - change coffee button on mouse entered
    public void changeCoffeeImage() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "hotcoffee.png";
        InputStream stream = new FileInputStream(filePath);
        Image hotCoffee = new Image(stream);
        startButton.setImage(hotCoffee);
    }

    // start screen - change coffee button on mouse exited
    public void changeCoffeeImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "coffee.png";
        InputStream stream = new FileInputStream(filePath);
        Image coffee = new Image(stream);
        startButton.setImage(coffee);
    }

    // when coffee is produced, change appearance
    public void showCoffee() throws FileNotFoundException {
        if (waiter.getBoundsInParent().intersects(coffeeMachine.getBoundsInParent())) {
            coffeeeMachine.displayProduct(waiter, coffeeMachine, CofiBrew, progressCoffee);
        }
    }

    // when cake machine is running
    public void showCake() throws FileNotFoundException {
        if (waiter.getBoundsInParent().intersects(kitchenAid.getBoundsInParent())) {
            cakeMachine.displayProduct(waiter, kitchenAid, CofiBrew, progressCake);
        }
    }

    public void noProduct() throws FileNotFoundException {
        String filePath = CofiBrew.getImageWithoutProduct();
        InputStream stream = new FileInputStream(filePath);
        Image cofi = new Image(stream);
        waiter.setImage(cofi);
        CofiBrew.setProduct("none");
    }

    public Label getLabel(ImageView cust){

        Label customorder = new Label();

        if (first.equals(cust)) {
            customorder = orderlabel1;
        } else if (second.equals(cust)) {
            customorder = orderlabel2;
        } else if (third.equals(cust)) {
            customorder = orderlabel3;
        } else if (fourth.equals(cust)) {
            customorder = orderlabel4;
        } else if (fifth.equals(cust)) {
            customorder = orderlabel5;
        } else if (sixth.equals(cust)) {
            customorder = orderlabel6;
        } else if (seventh.equals(cust)) {
            customorder = orderlabel7;
        } else if (eighth.equals(cust)) {
            customorder = orderlabel8;
        }

        return customorder;

    }

    public Customer findCustomer(List<Customer> customerList, ImageView cust){

        for (Customer customer : customerList) {
            if (customer.getImage().equals(cust)) {
                return customer;
            }
        }
        return null;
    }


    public void displayPerson(MouseEvent event) throws FileNotFoundException, InterruptedException {

        ImageView cust = (ImageView) event.getSource();
        double x1 = 0.0;
        double y1 = 0.0;

        if (first.equals(cust)) {
            x1 = first.getLayoutX();
            y1 = first.getLayoutY();
        } else if (second.equals(cust)) {
            x1 = second.getLayoutX();
            y1 = second.getLayoutY();
        } else if (third.equals(cust)) {
            x1 = third.getLayoutX();
            y1 = third.getLayoutY();
        } else if (fourth.equals(cust)) {
            x1 = fourth.getLayoutX();
            y1 = fourth.getLayoutY();
        } else if (fifth.equals(cust)) {
            x1 = fifth.getLayoutX();
            y1 = fifth.getLayoutY();
        } else if (sixth.equals(cust)) {
            x1 = sixth.getLayoutX();
            y1 = sixth.getLayoutY();
        } else if (seventh.equals(cust)) {
            x1 = seventh.getLayoutX();
            y1 = seventh.getLayoutY();
        } else if (eighth.equals(cust)) {
            x1 = eighth.getLayoutX();
            y1 = eighth.getLayoutY();
        }

        double x2 = waiter.getLayoutX();
        double y2 = waiter.getLayoutY();

        Point2D c = new Point2D(x1, y1);
        Point2D w = new Point2D(x2, y2);
        controlLabel.setText(String.valueOf(c.distance(w)));

        Customer customer = findCustomer(customerList, cust);

        if (c.distance(w) < 120) {
            if (customer.displayPerson(customer.getLabel(), cust, CofiBrew, coinLabel, customer, customerList, waiter)){
                coin += 5;
                coinLabel.setText(String.valueOf(coin));
            }
        if (c.distance(w) < 125) {
            customer.displayPerson(order, cust, CofiBrew, coinLabel);
        }

    }

    public ImageView[] makeArrayCustomer() {

        pics[0] = first;
        pics[1] = second;
        pics[2] = third;
        pics[3] = fourth;
        pics[4] = fifth;
        pics[5] = sixth;
        pics[6] = seventh;
        pics[7] = eighth;

        return pics;
    }

    private List<Integer> num = new ArrayList<Integer>(){{
        add(0);
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
    }};

    public ImageView getRandomPic(ImageView[] pics, List<Integer> num) {

        int index = num.get(random.nextInt(num.size()));

        if (!num.contains(index)){
            getRandomPic(pics, num);
        }

        num.remove(Integer.valueOf(index));

        return pics[index];
    }

    public void searchForTable(ImageView[] pics) {

        if (customerList.size() < 3){
            //pics = makeArrayCustomer();
            customerImage = getRandomPic(pics, num); //get random picture from Array
            customerImage.setVisible(true);//make this picture visible

            Label order = getLabel(customerImage);

            Customer customer = new Customer(customerImage, order);
            customerList.add(customer);

        }
        else {
            System.out.println("no more customers");
        }

    }

    public boolean checkForCollision(ImageView waiter) {
        for (int i=0; i < collisionObjects.size(); i++){
            if (waiter.getBoundsInParent().intersects(collisionObjects.get(i).getBoundsInParent())){
                return true;
            }
        }

        return false;
    }
}
