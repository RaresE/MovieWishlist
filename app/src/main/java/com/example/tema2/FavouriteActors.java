package com.example.tema2;

import java.io.Serializable;

public class FavouriteActors implements Serializable {
    private String id;
    private String name;
    private int age;

    public FavouriteActors() {

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Actor " + name + " are varsta de " + age + " ani!";
    }
}
