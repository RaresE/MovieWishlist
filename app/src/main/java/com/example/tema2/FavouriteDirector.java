package com.example.tema2;

import java.io.Serializable;

public class FavouriteDirector implements Serializable {
    private String id;
    private String denumire;
    private int age;

    public FavouriteDirector() {
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

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "Director " + denumire + " are varsta de " + age + " ani!";
    }
}
