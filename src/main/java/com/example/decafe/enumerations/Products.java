package com.example.decafe.enumerations;

public enum Products {
    COFFEEE, CAKE;

    public String getCategory () {
        return switch (this) {
            case COFFEEE -> "coffee";
            case CAKE -> "cake";
        };
    }
}
