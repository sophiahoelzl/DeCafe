package com.example.decafe;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Upgrade {
    private int CoinsNeeded;
    private boolean used;
    private String pathNotUsed;
    private String pathUSed;
    private String type;
    private ImageView upgradeImages;

    Upgrade(int CoinsNeeded, boolean used, String filenameNotUsed, String filenameUsed, String type, ImageView upgradeImages){
        this.CoinsNeeded = CoinsNeeded;
        this.used = used;
        this.pathNotUsed = filenameNotUsed;
        this.pathUSed = filenameUsed;
        this.type = type;
        this.upgradeImages = upgradeImages;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getCoinsNeeded() {
        return CoinsNeeded;
    }

    public String getPathUSed() {
        return pathUSed;
    }

    public String getPathNotUsed() {
        return pathNotUsed;
    }

    public ImageView getUpgradeImages() {
        return upgradeImages;
    }


    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File("");
        String filePath;
        filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath);
        return new Image(stream);
    }

    public int doUpgrades(ImageView upgrade, int coin) throws FileNotFoundException {
        upgrade.setImage(createImage(this.pathUSed));
        upgrade.setDisable(true);
        this.setUsed(true);
        coin -= this.getCoinsNeeded();
        return coin;

    }
}
