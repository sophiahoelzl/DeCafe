package com.example.decafe;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
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

import java.io.*;
import java.net.URL;
import java.util.*;


public class HelloController implements Initializable {


    @FXML
    ImageView startButton = new ImageView();
    public ImageView coffeeMachine;
    public ImageView kitchenAid;
    public ImageView trashcan;

    public Machine coffeeeMachine = new Machine(4, "coffeeMachineWithCoffee.png", "coffeeMachine.png", "coffee");
    public Machine cakeMachine = new Machine(4, "kitchenAidUsed.png", "kitchenAid.png", "cake");
    public Player CofiBrew = new Player("cofiBrew.png", "cofiBrewWithCake.png", "cofiBrewWithCoffee.png");
    public Upgrade coffeeUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png", "coffee");
    public Upgrade cakeUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", "cake");
    public Upgrade playerUpgrade = new Upgrade(40, false, "upgradeSkates.png", "upgradeSkatesUsed.png", "player");

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
    public ImageView upgradeCoffee;
    public ImageView upgradeCake;
    public ImageView upgradePlayer;

    // for end screen
    public ImageView gameStartButton;
    public ImageView cofiBrewImage;
    public ImageView playAgainImage;
    public ImageView backToStartImage;
    public Label labelCredits;
    public ImageView endScreenBackground;
    public ImageView quitEndScreenImage;

    public ImageView smileyfirst;
    public ImageView smileysecond;
    public ImageView smileythird;
    public ImageView smileyfourth;
    public ImageView smileyfifth;
    public ImageView smileysixth;
    public ImageView smileyseventh;
    public ImageView smileyeighth;

    public ImageView coinfirst;
    public ImageView coinsecond;
    public ImageView cointhird;
    public ImageView coinfourth;
    public ImageView coinfifth;
    public ImageView coinsixth;
    public ImageView coinseventh;
    public ImageView coineigth;


    public ImageView[] pics;
    public ImageView customerImage = new ImageView();
    public List<Customer> customerList = new ArrayList<>();
    public Random random = new Random();
    public int coin = 0;
    public String smileycolor;

    private int movementVariable = 4;
    public Label[] collisions;

    public int number;
    public boolean start = false;

    private List<Integer> num = new ArrayList<Integer>() {{
        add(0);
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(6);
        add(7);
    }};

    // jump to game screen
    public void switchToGameScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.setResizable(false);
        HelloApplication.stage.show();
    }

    // jump to instructions
    public void switchToInstructions() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("instructions.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();
    }

    // jump to end screen
    public void switchToEndScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("endScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();

    }

    // jump to start screen
    public void switchToStartScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.setResizable(false);
        HelloApplication.stage.show();
    }

    // key events if wasd-keys are pressed
    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(true);
            case A -> aPressed.set(true);
            case S -> sPressed.set(true);
            case D -> dPressed.set(true);
        }
    }

    // key events if wasd-keys are released
    @FXML
    public void keyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case W -> wPressed.set(false);
            case A -> aPressed.set(false);
            case S -> sPressed.set(false);
            case D -> dPressed.set(false);
        }
    }

    // for smoother motion
    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long timestamp) {
            //File f = new File("");
            //String filePath;
            //filePath = "";
            double move = movementVariable; // store movementVariable in new variable

            // if two keys are pressed at once and player moves diagonally - correct diagonal speed
            if (wPressed.get() && aPressed.get() || wPressed.get() && dPressed.get() ||
                    sPressed.get() && aPressed.get() || sPressed.get() && dPressed.get())
                move -= movementVariable - Math.sqrt(Math.pow(movementVariable, 2) / 2);

            // control waiter via wasd keys ([0|0] top-left, [100|100] bottom-right)

            double xMove = 0; // move on x-axis
            double yMove = 0; // move on y-axis

            // if waiter should move up
            if (wPressed.get()) {
                yMove = -move; // negative move because otherwise waiter would move down
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrew.png";
            }

            // if waiter should move down
            if (sPressed.get()) {
                yMove = move;
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrewUnten.png";
            }

            // if waiter should move left
            if (aPressed.get()) {
                xMove = -move; // negative move because otherwise waiter would move right
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrewLinks.png";
            }

            // if waiter should move right
            if (dPressed.get()) {
                xMove = move;
                //filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "cofiBrewRechts.png";
            }

            // set x and y coordinates of waiter
            waiter.setLayoutX(waiter.getLayoutX() + xMove);
            waiter.setLayoutY(waiter.getLayoutY() + yMove);

            // if collision is detected, set x and y coordinates back to where no collision occurred
            if (checkForCollision(waiter)) {
                waiter.setLayoutX(waiter.getLayoutX() - xMove);
                waiter.setLayoutY(waiter.getLayoutY() - yMove);
            }


            /* if we want to change the waiter image while walking
            InputStream stream = null;
            try {
                stream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image cofi = new Image(stream);
            waiter.setImage(cofi);
            */
        }
    };


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        keyPressed.addListener((((observableValue, aBoolean, t1) -> { // if any key from the four keys is pressed
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        })));

        // transparent labels on top of the images to look for collisions
        collisions = new Label[] {plant, plantsAbove, customerBot1, customerBot2, customerBot3, customerBot4, customerTop1, customerTop2, customerTop3, customerTop4, table1, table2, table3, table4, edgeBot, edgeLeft, edgeRight, edgeTop, countRight, countBelow};

        pics = new ImageView[] {first, second, third, fourth, fifth, sixth, seventh, eighth};

        if (pics[0] != null && !start){
            Timer t = new Timer();
            t.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                searchForTable();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            t.cancel();
                        }
                    },
                    1000
            );
            Timer x = new Timer();
            x.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                searchForTable();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            t.cancel();
                        }
                    },
                    5000
            );
            Timer y = new Timer();
            y.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                searchForTable();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            t.cancel();
                        }
                    },
                    10000
            );
            start = true;
        }
    }



    // start screen - change start button on mouse entered
    public void changeStartCoffeeImage() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "startCoffeeHot.png";
        InputStream stream = new FileInputStream(filePath);
        Image hotCoffee = new Image(stream);
        startButton.setImage(hotCoffee);
    }

    // start screen - change start button on mouse exited
    public void changeStartCoffeeImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "startCoffee.png";
        InputStream stream = new FileInputStream(filePath);
        Image start = new Image(stream);
        startButton.setImage(start);
    }

    // instructions - change GOT IT! on mouse entered
    public void changeStartImage() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "instructionsGotIt.png";
        InputStream stream = new FileInputStream(filePath);
        Image start = new Image(stream);
        gameStartButton.setImage(start);
    }

    // instructions - change GOT IT! on mouse exited
    public void changeStartImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "instructionsGotItBrighter.png";
        InputStream stream = new FileInputStream(filePath);
        Image startButton = new Image(stream);
        gameStartButton.setImage(startButton);
    }

    // end screen - change PlayAgain Button when mouse entered
    public void changePlayAgain() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "playAgainBrighter.png";
        InputStream stream = new FileInputStream(filePath);
        Image playAgainBrighter = new Image(stream);
        playAgainImage.setImage(playAgainBrighter);
    }

    // end screen - change PlayAgain Button when mouse exited
    public void changePlayAgainBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "playAgain.png";
        InputStream stream = new FileInputStream(filePath);
        Image playAgain = new Image(stream);
        playAgainImage.setImage(playAgain);
    }

    // end screen - change BackToStartMenu Button when mouse entered
    public void changeBackToStartMenu() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "backToStartMenuBrighter.png";
        InputStream stream = new FileInputStream(filePath);
        Image backToStartBrighter = new Image(stream);
        backToStartImage.setImage(backToStartBrighter);
    }

    // end screen - change BackToStartMenu Button when mouse exited
    public void changeBackToStartMenuBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "backToStartMenu.png";
        InputStream stream = new FileInputStream(filePath);
        Image backToStart = new Image(stream);
        backToStartImage.setImage(backToStart);
    }

    // end screen - change Quit Button when mouse entered
    public void changeQuitEndScreen() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "quitEndScreenBrighter.png";
        InputStream stream = new FileInputStream(filePath);
        Image quitEndScreenBrighter = new Image(stream);
        quitEndScreenImage.setImage(quitEndScreenBrighter);
    }

    // end screen - change Quit Button when mouse exited
    public void changeQuitEndScreenBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "quitEndScreen.png";
        InputStream stream = new FileInputStream(filePath);
        Image quitEndScreen = new Image(stream);
        quitEndScreenImage.setImage(quitEndScreen);
    }

    // if waiter is near coffee machine, change appearance when clicked
    public void showCoffee() throws FileNotFoundException {
        if (waiter.getBoundsInParent().intersects(coffeeMachine.getBoundsInParent())) {
            coffeeeMachine.displayProduct(waiter, coffeeMachine, CofiBrew, progressCoffee);
        }
    }

    // if waiter is near cake machine, change appearance when clicked
    public void showCake() throws FileNotFoundException {
        if (waiter.getBoundsInParent().intersects(kitchenAid.getBoundsInParent())) {
            cakeMachine.displayProduct(waiter, kitchenAid, CofiBrew, progressCake);
        }
    }

    // if no product is held by waiter
    public void noProduct() throws FileNotFoundException {
        String filePath = CofiBrew.getImageWithoutProduct();
        InputStream stream = new FileInputStream(filePath);
        Image cofi = new Image(stream);
        waiter.setImage(cofi);
        CofiBrew.setProduct("none");
    }

    public Label getLabel(ImageView cust) {

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


    public ImageView getSmileyLabel(ImageView cust) {

        ImageView smileylabel = new ImageView();

        if (first.equals(cust)) {
            smileylabel = smileyfirst;
        } else if (second.equals(cust)) {
            smileylabel = smileysecond;
        } else if (third.equals(cust)) {
            smileylabel = smileythird;
        } else if (fourth.equals(cust)) {
            smileylabel = smileyfourth;
        } else if (fifth.equals(cust)) {
            smileylabel = smileyfifth;
        } else if (sixth.equals(cust)) {
            smileylabel = smileysixth;
        } else if (seventh.equals(cust)) {
            smileylabel = smileyseventh;
        } else if (eighth.equals(cust)) {
            smileylabel = smileyeighth;
        }

        return smileylabel;

    }

    public ImageView getCoinLabel(ImageView cust) {

        ImageView coin = new ImageView();

        if (first.equals(cust)) {
            coin = coinfirst;
        } else if (second.equals(cust)) {
            coin = coinsecond;
        } else if (third.equals(cust)) {
            coin = cointhird;
        } else if (fourth.equals(cust)) {
            coin = coinfourth;
        } else if (fifth.equals(cust)) {
            coin = coinfifth;
        } else if (sixth.equals(cust)) {
            coin = coinsixth;
        } else if (seventh.equals(cust)) {
            coin = coinseventh;
        } else if (eighth.equals(cust)) {
            coin = coineigth;
        }

        return coin;

    }

    public Customer findCustomer(List<Customer> customerList, ImageView cust) {

        for (Customer customer : customerList) {
            if (customer.getImage().equals(cust)) {
                return customer;
            }
        }
        return null;
    }


    public void displayPerson(MouseEvent event) throws IOException, InterruptedException {

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
        // controlLabel.setText(String.valueOf(c.distance(w)));

        Customer customer = findCustomer(customerList, cust);

        if (c.distance(w) < 128) {
            if (customerList.size() < 3){
                Timer t = new Timer();
                t.schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                try {
                                    searchForTable();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                                t.cancel();
                            }
                        },
                        3000
                );
            }

            if (!customer.isAlreadyOrdered()){
                customer.displayOrder(customer.getLabel());
            } else {
                if (customer.checkOrder(customer.getLabel(), cust, CofiBrew, coinLabel, customer, customerList, waiter, num)) {
                    File f = new File("");
                    String filePath = "";
                    if (customer.getSmiley().equals("green")){
                        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "5coins.png";
                    } else if (customer.getSmiley().equals("yellow")){
                        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "4coins.png";
                    } else if (customer.getSmiley().equals("red")){
                        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "3coins.png";
                    }
                    InputStream stream = new FileInputStream(filePath);
                    Image money = new Image(stream);
                    customer.getCoinImage().setImage(money);
                    customer.getCoinImage().setVisible(true);
                    customer.getCoinImage().setDisable(false);
                    customer.getCoinImage().setOnMouseClicked(event1 -> {
                        try {
                            getMoney(event1, customer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    Timer t = new Timer();
                    t.schedule(
                            new TimerTask() {
                                @Override
                                public void run() {
                                    try {
                                        searchForTable();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    t.cancel();
                                }
                            },
                            5000
                    );
                }
            }
        }
    }

    public void checkUpgradePossible(Upgrade upgrade, ImageView upgradeImage) throws FileNotFoundException {
        if (!upgrade.isUsed() && coin >= upgrade.getCoinsNeeded()){
            upgradeImage.setDisable(false);
            String filePath;
            filePath = upgrade.getPathNotUsed();
            InputStream stream = new FileInputStream(filePath);
            Image upgrades = new Image(stream);
            upgradeImage.setImage(upgrades);
        } else {
            upgradeImage.setDisable(true);
            String filePath;
            filePath = upgrade.getPathUSed();
            InputStream stream = new FileInputStream(filePath);
            Image upgrades = new Image(stream);
            upgradeImage.setImage(upgrades);
        }
    }

    public void doUpgrade (MouseEvent e) throws FileNotFoundException {
        if (((ImageView) e.getSource()).getId().equals("coffee")){
            coin = coffeeUpgrade.doUpgrades(upgradeCoffee, coin);
            coffeeeMachine.setDuration(2);
        } else if (((ImageView) e.getSource()).getId().equals("cake")){
            coin = cakeUpgrade.doUpgrades(upgradeCake, coin);
            cakeMachine.setDuration(2);
        } else if (((ImageView) e.getSource()).getId().equals("player")){
            coin = playerUpgrade.doUpgrades(upgradePlayer, coin);
            movementVariable = 6;
        }
        coinLabel.setText(String.valueOf(coin));
        checkUpgradePossible(coffeeUpgrade, upgradeCoffee);
        checkUpgradePossible(cakeUpgrade, upgradeCake);
        checkUpgradePossible(playerUpgrade, upgradePlayer);
    }

    public ImageView getRandomPic (ImageView[]pics , List <Integer> num){
        Random random = new Random();
        int index = num.get(random.nextInt(num.size()));
        number = index;

        if (!num.contains(index)) {
            getRandomPic(pics, num);
        }

        num.remove(Integer.valueOf(index));

        return pics[index];
    }

    public void searchForTable () throws FileNotFoundException {
        if (customerList.size() < 3 && num.size() != 0) {
            //ImageView customerImage = new ImageView();
            customerImage = getRandomPic(pics, num); //get random picture from Array
            customerImage.setVisible(true);//make this picture visible

            Label order = getLabel(customerImage);
            ImageView smiley = getSmileyLabel(customerImage);
            ImageView coin = getCoinLabel(customerImage);


            Customer customer = new Customer(customerImage, order, number, smiley, coin);
            customerList.add(customer);
            customer.waitingTime(customerImage, order, customerList, num);

        }

    }

    // check if collisions occur
    public boolean checkForCollision (ImageView waiter){
        for (int i = 0; i < collisions.length; i++) { // iterate through labels
            if (waiter.getBoundsInParent().intersects(collisions[i].getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public void getMoney(MouseEvent e, Customer customer) throws IOException {
        smileycolor = customer.getSmiley();
        num.add(customer.getTable());

        if (Objects.equals(smileycolor, "green")){
            coin += 5;
        }else if (Objects.equals(smileycolor, "yellow")){
            coin += 4;
        }else if (Objects.equals(smileycolor, "red")){
            coin += 3;
        }

        ((ImageView)e.getSource()).setVisible(false);
        ((ImageView)e.getSource()).setDisable(true);

        if (coin < 80) {
            checkUpgradePossible(coffeeUpgrade, upgradeCoffee);
            checkUpgradePossible(cakeUpgrade, upgradeCake);
            checkUpgradePossible(playerUpgrade, upgradePlayer);
            coinLabel.setText(String.valueOf(coin));
            Timer t = new Timer();
            t.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            try {
                                searchForTable();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            t.cancel();
                        }
                    },
                    5000
            );
        }else {
            switchToEndScreen();
        }
    }

    // end game (called when exit or quit button is clicked)
    public void endGame(){
        Platform.exit();
    }
}
