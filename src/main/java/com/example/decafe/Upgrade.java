package com.example.decafe;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

// Function used to control all the methods used for Upgrades
public class Upgrade {
    private final int CoinsNeeded; // The coins needed to use/do the Upgrade
    private boolean used; // Boolean that indicates if the Upgrade was already used or not
    private final String ImageNotUsed; // Image of "deactivated" Upgrade
    private final String ImageUsed; // Image of "activated" Upgrade
    private final ImageView upgradeImages; // ImageView that is related to the Upgrade

    // Constructor
    Upgrade(int CoinsNeeded, boolean used, String filenameNotUsed, String filenameUsed, ImageView upgradeImages){
        this.CoinsNeeded = CoinsNeeded;
        this.used = used;
        this.ImageNotUsed = filenameNotUsed;
        this.ImageUsed = filenameUsed;
        this.upgradeImages = upgradeImages;
    }

    // Getter
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getCoinsNeeded() {
        return CoinsNeeded;
    }

    public String getImageUsed() {
        return ImageUsed;
    }

    public String getImageNotUsed() {
        return ImageNotUsed;
    }

    public ImageView getUpgradeImages() {
        return upgradeImages;
    }


    // Method used to create an Image Object
    public Image createImage(String filename) throws FileNotFoundException {
        File f = new File(""); // Get filepath of project
        // Get path to certain Image
        String filePath = f.getAbsolutePath() + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "com" + File.separator + "example" + File.separator + "decafe" + File.separator + filename;
        InputStream stream = new FileInputStream(filePath); // Convert path into stream
        return new Image(stream); // Convert stream to Image and return it
    }

    // Method used to use an Upgrade
    public int doUpgrades(ImageView upgrade, int coin) throws FileNotFoundException {
        // Change Image to the "deactivated" Upgrade Image
        upgrade.setImage(createImage(this.ImageUsed));
        // Disable the ImageView
        upgrade.setDisable(true);
        // Set the Used variable to true
        this.setUsed(true);
        // Decrease the coins score according to the upgrade costs
        coin -= this.getCoinsNeeded();
        // return the new coin score
        return coin;
    }
}
