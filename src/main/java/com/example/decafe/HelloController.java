package com.example.decafe;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

    public Player CofiBrew = new Player("cofiBrew.png", "cofiBrewWithCake.png", "cofiBrewWithCoffee.png", 4);
    public Game Play;

    private int movementVariable = CofiBrew.getMovement();
    private Label[] collisions;

    private boolean start = false;

    public void loadScene(String sceneName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(sceneName));
        Scene scene = new Scene(fxmlLoader.load());
        HelloApplication.stage.setTitle("DeCafé");
        HelloApplication.stage.setScene(scene);
        HelloApplication.stage.setResizable(false);
        HelloApplication.stage.show();
    }

    // jump from start screen to game screen
    public void startGame() throws IOException {
        loadScene("gameScreen.fxml");
        if (Customer.pics[0] != null && !start){
            Customer cust = new Customer();
            cust.startTimerSpawn(1, cust.getT());
            cust.startTimerSpawn(5, cust.getC());
            cust.startTimerSpawn(10, cust.getY());
            Customer.allCustomers.add(cust);
            start = true;
        }
    }

    // jump to end screen
    public void switchToEndScreen() throws IOException {
        loadScene("endScreen.fxml");
    }

    // jump to start screen
    public void switchToStartScreen() throws IOException { // if button BACK TO START MENU is pressed
        loadScene("startScreen.fxml");
    }

    public void switchToGameScreen() throws IOException { // if button PLAY AGAIN is pressed
        loadScene("gameScreen.fxml");
    }

    // jump to instructions
    public void switchToInstructions() throws IOException {
        loadScene("Instructions.fxml");
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
            movementVariable = CofiBrew.getMovement();
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
            }

            // if waiter should move down
            if (sPressed.get()) {
                yMove = move;
            }

            // if waiter should move left
            if (aPressed.get()) {
                xMove = -move; // negative move because otherwise waiter would move right
            }

            // if waiter should move right
            if (dPressed.get()) {
                xMove = move;
            }

            // set x and y coordinates of waiter
            waiter.setLayoutX(waiter.getLayoutX() + xMove);
            waiter.setLayoutY(waiter.getLayoutY() + yMove);

            // if collision is detected, set x and y coordinates back to where no collision occurred
            if (checkForCollision(waiter)) {
                waiter.setLayoutX(waiter.getLayoutX() - xMove);
                waiter.setLayoutY(waiter.getLayoutY() - yMove);
            }
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
        collisions = new Label[]{plant, plantsAbove, customerBot1, customerBot2, customerBot3, customerBot4, customerTop1, customerTop2, customerTop3, customerTop4, table1, table2, table3, table4, edgeBot, edgeLeft, edgeRight, edgeTop, countRight, countBelow};

        Customer.pics =  new ImageView[]{first, second, third, fourth, fifth, sixth, seventh, eighth};
        Customer.simleyImages = new ImageView[]{smileyfirst, smileysecond, smileythird, smileyfourth, smileyfifth, smileysixth, smileyseventh, smileyeighth};
        Customer.orderLabels = new Label[]{orderlabel1, orderlabel2, orderlabel3, orderlabel4, orderlabel5, orderlabel6, orderlabel7, orderlabel8};
        Customer.coinImages = new ImageView[]{coinfirst, coinsecond, cointhird, coinfourth, coinfifth, coinsixth, coinseventh, coineigth};

        Play = new Game(upgradeCoffee, upgradeCake, upgradePlayer);


    }



    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }
    // start screen - change start button on mouse entered
    public void changeStartCoffeeImage() throws FileNotFoundException {
        startButton.setImage(createImage("startCoffeeHot.png"));
    }

    // start screen - change coffee button on mouse exited
    public void changeStartCoffeeImageBack() throws FileNotFoundException {
        startButton.setImage(createImage("startCoffee.png"));
    }

    // instructions - change GOT IT! on mouse entered
    public void changeStartImage() throws FileNotFoundException {
        gameStartButton.setImage(createImage("instructionsGotIt.png"));
    }

    // instructions - change GOT IT! on mouse exited
    public void changeStartImageBack() throws FileNotFoundException {
        gameStartButton.setImage(createImage("instructionsGotItBrighter.png"));
    }

    // end screen - change PlayAgain Button when mouse entered
    public void changePlayAgain() throws FileNotFoundException {
        playAgainImage.setImage(createImage("playAgainBrighter.png"));
    }

    // end screen - change PlayAgain Button when mouse exited
    public void changePlayAgainBack() throws FileNotFoundException {
        playAgainImage.setImage(createImage("playAgain.png"));
    }

    // end screen - change BackToStartMenu Button when mouse entered
    public void changeBackToStartMenu() throws FileNotFoundException {
        backToStartImage.setImage(createImage("backToStartMenuBrighter.png"));
    }

    // end screen - change BackToStartMenu Button when mouse exited
    public void changeBackToStartMenuBack() throws FileNotFoundException {
        backToStartImage.setImage(createImage("backToStartMenu.png"));
    }

    // end screen - change Quit Button when mouse entered
    public void changeQuitEndScreen() throws FileNotFoundException {
        quitEndScreenImage.setImage(createImage("quitEndScreenBrighter.png"));
    }

    // end screen - change Quit Button when mouse exited
    public void changeQuitEndScreenBack() throws FileNotFoundException {
        quitEndScreenImage.setImage(createImage("quitEndScreen.png"));
    }

    // if waiter is near coffee machine, change appearance when clicked
    public void showCoffee() throws FileNotFoundException {
        if (waiter.getBoundsInParent().intersects(coffeeMachine.getBoundsInParent())) {
            Play.getCoffeeMachine().displayProduct(waiter, coffeeMachine, CofiBrew, progressCoffee);
        }
    }

    // if waiter is near cake machine, change appearance when clicked
    public void showCake() throws FileNotFoundException {
        if (waiter.getBoundsInParent().intersects(kitchenAid.getBoundsInParent())) {
            Play.getCakeMachine().displayProduct(waiter, kitchenAid, CofiBrew, progressCake);
        }
    }

    // if no product is held by waiter
    public void noProduct() throws FileNotFoundException {
        waiter.setImage(createImage(CofiBrew.getImageWithoutProduct()));
        CofiBrew.setProduct("none");
    }

    public Customer findCustomer(List<Customer> customerList, ImageView cust) {
        for (Customer customer : customerList) {
            if (customer.getImage().equals(cust)) {
                return customer;
            }
        }
        return null;
    }


    public void displayPerson(MouseEvent event) throws IOException {

        ImageView cust = (ImageView) event.getSource();
        Customer customer = findCustomer(Customer.customerList, cust);

            if (!customer.isAlreadyOrdered()){
                customer.displayOrder(customer.getLabel());
            } else {
                if (cust.getBoundsInParent().intersects(waiter.getBoundsInParent())) {
                    try {
                        customer.startTimerSpawn(5, customer.getC());
                    } catch (NullPointerException e){
                        switchToEndScreen();
                    }
                    if (customer.checkOrder(CofiBrew, customer, waiter)) {
                        String moneyImage = "";
                        if (customer.isGreen()) {
                            moneyImage = Play.getMoney();
                        } else if (customer.isYellow()) {
                            moneyImage = Play.getFourCoins();
                        } else if (customer.isRed()) {
                            moneyImage = Play.getThreeCoins();
                        }
                        customer.getCoinImage().setImage(createImage(moneyImage));
                        customer.getCoinImage().setOnMouseClicked(event1 -> {
                            try {
                                getMoney(event1, customer);
                            } catch (IOException e) {
                                try {
                                    switchToEndScreen();
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
    }

    public void checkUpgradePossible(Upgrade upgrade, ImageView upgradeImage) throws FileNotFoundException {
        Play.checkUpgradePossible(upgrade, upgradeImage);
    }

    public void doUpgrade (MouseEvent e) throws FileNotFoundException {
        Play.doUpgrade(((ImageView)e.getSource()).getId(), CofiBrew);
        coinLabel.setText(String.valueOf(Play.getCoinsEarned()));
        checkUpgradePossible(Play.getCoffeeUpgrade(), upgradeCoffee);
        checkUpgradePossible(Play.getCakeUpgrade(), upgradeCake);
        checkUpgradePossible(Play.getPlayerUpgrade(), upgradePlayer);
    }

    // check if collisions occur
    public boolean checkForCollision (ImageView waiter){
        for (Label collision : collisions) { // iterate through labels
            if (waiter.getBoundsInParent().intersects(collision.getBoundsInParent())) {
                return true;
            }
        }
        return false;
    }

    public void getMoney(MouseEvent e, Customer customer) throws IOException {
        Customer.addNum(customer.getTable());
        Play.setMoney(customer);
        ((ImageView)e.getSource()).setVisible(false);
        ((ImageView)e.getSource()).setDisable(true);

        if (Play.getCoinsEarned() < 80) {
            checkUpgradePossible(Play.getCoffeeUpgrade(), upgradeCoffee);
            checkUpgradePossible(Play.getCakeUpgrade(), upgradeCake);
            checkUpgradePossible(Play.getPlayerUpgrade(), upgradePlayer);
            coinLabel.setText(String.valueOf(Play.getCoinsEarned()));
            try {
                customer.startTimerSpawn(5, customer.getY());
            } catch (NullPointerException y){
                switchToEndScreen();
            }
        }else {
            stopTimers();
            switchToEndScreen();
        }
    }

    public void searchForTable(){
        Customer.spawnCustomers();
    }

    public void stopTimers(){
        for (Customer customers : Customer.allCustomers){
            customers.getT().cancel();
            customers.getX().cancel();
            customers.getS().cancel();
            customers.getY().cancel();
            customers.getC().cancel();
        }
    }

    // end game (called when exit clicked)
    public void endGameQuick(){
        stopTimers();
        Platform.exit();

    }

    // end game (called when quit button is clicked)
    public void endGame(){
        stopTimers();
        Platform.exit();

    }
}
