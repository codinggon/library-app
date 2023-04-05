package com.group.libraryapp.domain.user;

public class Fruit {

    private String name;
    private Integer price;

    public Fruit(String name, Integer price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}
