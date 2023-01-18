package com.example.decafe;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.*;


public class HelloController implements Initializable {


    @FXML
    ImageView startButton = new ImageView();
    public ImageView coffeeMachine;
    public ImageView kitchenAid;
    public Label controlLabel;

    public Machine coffeeeMachine = new Machine(0, "CoffeemachineWithCoffee.png", "coffeeMachine.png", "coffee");
    public Machine cakeMachine = new Machine(0, "kitchenAidUsed.png", "kitchenAid.png", "cake");
    public Customer customer = new Customer();
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

    private ImageView pics[] = new ImageView[8];
    private ImageView customerImage = new ImageView();

    private int movementVariable = 5;

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
            double move = movementVariable;

            // if two keys are pressed at once and player moves diagonally
            if (wPressed.get() && aPressed.get() || wPressed.get() && dPressed.get() ||
                    sPressed.get() && aPressed.get() || sPressed.get() && dPressed.get())
                move -= movementVariable - Math.sqrt(Math.pow(movementVariable, 2) / 2);

            // for collision detection later
            double xMove = 0;
            double yMove = 0;

            if (wPressed.get()) yMove = -move; // if waiter should go up
            System.out.println(yMove);
            if (sPressed.get()) yMove = move; // if waiter should go down
            System.out.println(yMove);

            if (aPressed.get()) xMove = -move; // if waiter should move left
            System.out.println(xMove);

            if (dPressed.get()) xMove = move; // if waiter should move right
            System.out.println(xMove);

            if(checkForCollision(waiter) == 1){ // left boundary
                System.out.println("detect");
                waiter.setLayoutX(waiter.getLayoutX() - xMove);
                //waiter.setLayoutY(waiter.getLayoutY() + yMove);
            }
            if(checkForCollision(waiter) == 2){ // right boundary
                waiter.setLayoutX(waiter.getLayoutX() - xMove);
                waiter.setLayoutY(waiter.getLayoutY() + yMove);
            }
            if(checkForCollision(waiter) == 3){ // upper boundary
                waiter.setLayoutX(waiter.getLayoutX() + xMove);
                waiter.setLayoutY(waiter.getLayoutY() - yMove);
            }
            if(checkForCollision(waiter) == 4){ // bottom boundary
                waiter.setLayoutX(waiter.getLayoutX() + xMove);
                waiter.setLayoutY(waiter.getLayoutY() - yMove);
            }

            else{
                waiter.setLayoutX(waiter.getLayoutX() + xMove);
                waiter.setLayoutY(waiter.getLayoutY() + yMove);

            }
            System.out.println(waiter.getLayoutX() +  ", " + waiter.getLayoutY());
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
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                wPressed.set(true);
                break;

            case A:
                aPressed.set(true);
                break;

            case S:
                sPressed.set(true);
                break;

            case D:
                dPressed.set(true);
                break;
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W:
                wPressed.set(false);
                break;

            case A:
                aPressed.set(false);
                break;

            case S:
                sPressed.set(false);
                break;

            case D:
                dPressed.set(false);
                break;
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
        double x1 = coffeeMachine.getLayoutX();
        double y1 = coffeeMachine.getLayoutY();
        double x2 = waiter.getLayoutX();
        double y2 = waiter.getLayoutY();

        if (coffeeeMachine.checkApperance(x1, y1, x2, y2)) {
            coffeeeMachine.displayProduct(waiter, coffeeMachine, CofiBrew, progressCoffee);
        }
    }

    // when cake machine is running
    public void showCake() throws FileNotFoundException {
        double x1 = kitchenAid.getLayoutX();
        double y1 = kitchenAid.getLayoutY();
        double x2 = waiter.getLayoutX();
        double y2 = waiter.getLayoutY();

        if (cakeMachine.checkApperance(x1, y1, x2, y2)) {
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


    public void displayPerson(MouseEvent event) throws InterruptedException {

        ImageView cust = (ImageView)event.getSource();
        Label order = new Label();
        double x1 = 0.0;
        double y1 = 0.0;

        if (first.equals(cust)) {
            order = orderlabel1;
            x1 = first.getLayoutX();
            y1 = first.getLayoutY();
        } else if (second.equals(cust)) {
            order = orderlabel2;
            x1 = second.getLayoutX();
            y1 = second.getLayoutY();
        } else if (third.equals(cust)) {
            order = orderlabel3;
            x1 = third.getLayoutX();
            y1 = third.getLayoutY();
        } else if (fourth.equals(cust)) {
            order = orderlabel4;
            x1 = fourth.getLayoutX();
            y1 = fourth.getLayoutY();
        } else if (fifth.equals(cust)) {
            order = orderlabel5;
            x1 = fifth.getLayoutX();
            y1 = fifth.getLayoutY();
        } else if (sixth.equals(cust)) {
            order = orderlabel6;
            x1 = sixth.getLayoutX();
            y1 = sixth.getLayoutY();
        } else if (seventh.equals(cust)) {
            order = orderlabel7;
            x1 = seventh.getLayoutX();
            y1 = seventh.getLayoutY();
        } else if (eighth.equals(cust)) {
            order = orderlabel8;
            x1 = eighth.getLayoutX();
            y1 = eighth.getLayoutY();
        }

        double x2 = waiter.getLayoutX();
        double y2 = waiter.getLayoutY();

        Point2D c = new Point2D(x1, y1);
        Point2D w = new Point2D(x2, y2);
        controlLabel.setText(String.valueOf(c.distance(w)));
        if (c.distance(w) < 120) {
            customer.displayPerson(order, cust);
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

    public ImageView getRandomPic(ImageView[] pics){

        Random random = new Random();
        int index = random.nextInt(8);

        if (pics[index].getStyle() == "visibility: true;"){
            getRandomPic(pics);
        }

        return pics[index];
    }

    public void searchForTable() {

        pics = makeArrayCustomer();
        customerImage = getRandomPic(pics);
        makePersonVisible(customerImage);

        Timer t = new Timer();
        t.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        customerImage.setVisible(false);
                        t.cancel();
                    }
                },
                10000
        );

    }

    public void makePersonVisible(ImageView customerImage){

        customerImage.setVisible(true);

    }

    public int checkForCollision(ImageView waiter){
        if(waiter.getLayoutX() <= 49){ // hitting left boundary
            return 1;
        }
        if(waiter.getLayoutX() >= 840){ // hitting right boundary
            return 2;
        }
        if(waiter.getLayoutY() <= 59.5){ // hitting upper boundary
            return 3;
        }
        if(waiter.getLayoutY() >= 670) { // hitting bottom boundary
            return 4;
        }
        else{
            return 0;
        }

    }


    }
