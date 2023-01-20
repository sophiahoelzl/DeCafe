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

    Upgrade(int CoinsNeeded, boolean used, String filenameNotUsed, String filenameUsed, String type){
        this.CoinsNeeded = CoinsNeeded;
        this.used = used;
        File f = new File("");
        this.pathNotUsed = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filenameNotUsed;
        this.pathUSed = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filenameUsed;
        this.type = type;
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

    public int doUpgrades(ImageView upgrade, int coin) throws FileNotFoundException {
        InputStream stream = new FileInputStream(this.getPathUSed());
        Image usedUpgrade = new Image(stream);
        upgrade.setImage(usedUpgrade);
        upgrade.setDisable(true);
        this.setUsed(true);
        coin -= this.getCoinsNeeded();
        return coin;

    }
}
