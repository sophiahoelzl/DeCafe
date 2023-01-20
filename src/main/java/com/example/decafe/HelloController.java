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

    public Machine coffeeeMachine = new Machine(4, "CoffeemachineWithCoffee.png", "coffeeMachine.png", "coffee");
    public Machine cakeMachine = new Machine(4, "kitchenAidUsed.png", "kitchenAid.png", "cake");
    public Player CofiBrew = new Player("cofiBrew.png", "cofiBrewWithCake.png", "cofiBrewWithCoffee.png");
    public Upgrade coffeeUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png", "coffee");
    public Upgrade cakeUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", "cake");
    public Upgrade playerUpgrade = new Upgrade(40, false, "upgradeRollschuh.png", "rollschuhUsed.png", "player");

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
    public ImageView gameStartButton;
    public ImageView cofiBrewImage;
    public ImageView playAgainImage;
    public ImageView backToStartImage;
    public Label labelCredits;
    public ImageView endScreenBackground;

    public ImageView smileyfirst;
    public ImageView smileysecond;
    public ImageView smileythird;
    public ImageView smileyfourth;
    public ImageView smileyfifth;
    public ImageView smileysixth;
    public ImageView smileyseventh;
    public ImageView smileyeighth;


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



    // jump from start screen to game screen
    public void startGame() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();
    }

    public void switchToEndWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("endScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.show();

    }

    public void switchToStartScreen() throws IOException { // if button BACK TO START MENU is pressed
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.setResizable(false);
        HelloApplication.stage.show();
    }

    public void switchToGameScreen() throws IOException { // if button PLAY AGAIN is pressed
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("gameScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.setResizable(false);
        HelloApplication.stage.show();
    }

    public void switchToInstructions() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Instructions.fxml"));
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
    public void initialize(URL url, ResourceBundle resourceBundle) throws FileNotFoundException{
        keyPressed.addListener((((observableValue, aBoolean, t1) -> { // if any key from the four keys is pressed
            if (!aBoolean) {
                timer.start();
            } else {
                timer.stop();
            }
        })));
        collisions = new Label[]{plant, plantsAbove, customerBot1, customerBot2, customerBot3, customerBot4, customerTop1, customerTop2, customerTop3, customerTop4, table1, table2, table3, table4, edgeBot, edgeLeft, edgeRight, edgeTop, countRight, countBelow};

        pics = new ImageView[]{first, second, third, fourth, fifth, sixth, seventh, eighth};

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
        Image start = new Image(stream);
        startButton.setImage(start);
    }

    // instructions - change start button on mouse entered
    public void changeStartImage() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "Start.png";
        InputStream stream = new FileInputStream(filePath);
        Image start = new Image(stream);
        gameStartButton.setImage(start);
    }

    // instructions - change start button on mouse exited
    public void changeStartImageBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "startHover.png";
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

    // end screen - change BacktoStartMenu Button when mouse entered
    public void changeBackToStartMenu() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "backToStartMenuBrighter.png";
        InputStream stream = new FileInputStream(filePath);
        Image backToStartDark = new Image(stream);
        backToStartImage.setImage(backToStartDark);
    }

    // end screen - change BacktoStartMenu Button when mouse exited
    public void changeBackToStartMenuBack() throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + "toStartMenuButton.png";
        InputStream stream = new FileInputStream(filePath);
        Image backToStart = new Image(stream);
        backToStartImage.setImage(backToStart);
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
                    //coin += 5;
                    smileycolor = customer.getSmiley();
                    if (Objects.equals(smileycolor, "green")){
                        coin += 5;
                    }else if (Objects.equals(smileycolor, "yellow")){
                        coin += 4;
                    }else if (Objects.equals(smileycolor, "red")){
                        coin += 3;
                    }

                    if (coin < 80) {
                        checkUpgradePossibel(coffeeUpgrade, upgradeCoffee);
                        checkUpgradePossibel(cakeUpgrade, upgradeCake);
                        checkUpgradePossibel(playerUpgrade, upgradePlayer);
                        coinLabel.setText(String.valueOf(coin));
                    }else {
                        switchToEndWindow();
                    }
                }
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

    public void checkUpgradePossibel(Upgrade upgrade, ImageView upgradeImage) throws FileNotFoundException {
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
        checkUpgradePossibel(coffeeUpgrade, upgradeCoffee);
        checkUpgradePossibel(cakeUpgrade, upgradeCake);
        checkUpgradePossibel(playerUpgrade, upgradePlayer);
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
        if (customerList.size() < 3) {
            //ImageView customerImage = new ImageView();
            customerImage = getRandomPic(pics, num); //get random picture from Array
            customerImage.setVisible(true);//make this picture visible

            Label order = getLabel(customerImage);
            ImageView smiley = getSmileyLabel(customerImage);


            Customer customer = new Customer(customerImage, order, number, smiley);
            customerList.add(customer);
            customer.waitingTime(customerImage, order, customerList, num);

        }

    }

    public boolean checkForCollision (ImageView waiter){
        for (int i = 0; i < collisions.length; i++) {
            if (waiter.getBoundsInParent().intersects(collisions[i].getBoundsInParent())) {
                return true;
            }
        }

        return false;
    }

    public void endGame(){
        Platform.exit();
    }
}
