package com.example.decafe;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Game {
    private Machine coffeeMachine;
    private Machine cakeMachine;
    private Upgrade coffeeUpgrade;
    private Upgrade cakeUpgrade;
    private Upgrade playerUpgrade;
    private int coinsEarned;
    private String noCoins;
    private String threeCoins;
    private String fourCoins;
    private String money;

    Game(ImageView upgradeCoffee, ImageView upgradeCake, ImageView upgradePlayer){
        this.coffeeMachine = new Machine(5, "CoffeemachineWithCoffee.png", "coffeeMachine.png", "coffee");
        this.cakeMachine = new Machine(5, "kitchenAidUsed.png", "kitchenAid.png", "cake");
        this.coffeeUpgrade = new Upgrade(20, false, "coffeeUpgrade.png", "coffeeUsed.png", "coffee", upgradeCoffee);
        this.cakeUpgrade = new Upgrade(20, false, "cakeUpgrade.png", "cakeUsed.png", "cake", upgradeCake);
        this.playerUpgrade = new Upgrade(40, false, "upgradeRollschuh.png", "rollschuhUsed.png", "player", upgradePlayer);
        this.coinsEarned = 0;
        this.money = "5coins.png";
        this.fourCoins = "4coins.png";
        this.threeCoins = "3coins.png";
        this.noCoins = "coin.png";

    }

    public Machine getCakeMachine() {
        return cakeMachine;
    }

    public Machine getCoffeeMachine() {
        return coffeeMachine;
    }

    public Upgrade getCakeUpgrade() {
        return cakeUpgrade;
    }

    public Upgrade getCoffeeUpgrade() {
        return coffeeUpgrade;
    }

    public Upgrade getPlayerUpgrade() {
        return playerUpgrade;
    }

    public String getNoCoins() {
        return noCoins;
    }

    public String getThreeCoins() {
        return threeCoins;
    }

    public String getFourCoins() {
        return fourCoins;
    }

    public String getMoney() {
        return money;
    }

    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    public void checkUpgradePossible(Upgrade upgrade, ImageView upgradeImage) throws FileNotFoundException {
        if (!upgrade.isUsed() && this.coinsEarned >= upgrade.getCoinsNeeded()){
            upgradeImage.setDisable(false);
            upgradeImage.setImage(createImage(upgrade.getPathNotUsed()));
        } else {
            upgradeImage.setDisable(true);
            upgradeImage.setImage(createImage(upgrade.getPathUSed()));
        }
    }

    public int getCoinsEarned() {
        return coinsEarned;
    }

    public void setCoinsEarned(int coinsEarned) {
        this.coinsEarned = coinsEarned;
    }

    public void setMoney(Customer customer){
        if (customer.isGreen()){
            this.setCoinsEarned(this.getCoinsEarned()+5);
        }else if (customer.isYellow()){
            this.setCoinsEarned(this.getCoinsEarned()+4);
        }else if (customer.isRed()){
            this.setCoinsEarned(this.getCoinsEarned()+3);
        }
    }

    public void doUpgrade(String type, Player CofiBrew) throws FileNotFoundException {
        if (type.equals("coffee")){
            coinsEarned = coffeeUpgrade.doUpgrades(coffeeUpgrade.getUpgradeImages(), coinsEarned);
            coffeeMachine.setDuration(2);
        } else if (type.equals("cake")){
            coinsEarned = cakeUpgrade.doUpgrades(cakeUpgrade.getUpgradeImages(), coinsEarned);
            cakeMachine.setDuration(2);
        } else if (type.equals("player")){
            coinsEarned = playerUpgrade.doUpgrades(playerUpgrade.getUpgradeImages(), coinsEarned);
            CofiBrew.setMovement(6);
        }
    }
}
