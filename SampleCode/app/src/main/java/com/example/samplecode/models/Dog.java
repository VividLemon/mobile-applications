package com.example.samplecode.models;

import java.util.ArrayList;

public class Dog {

    public static ArrayList<Dog> allDogs = new ArrayList(){{
        add(new Dog(1,"Buster",true));
        add(new Dog(2, "Lassie", false));
    }};

    private long id;
    private String name;
    private boolean vaccinated;

    public Dog(long id, String name, boolean vaccinated){
        this.id = id;
        this.name = name;
        this.vaccinated = vaccinated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(boolean vaccinated) {
        this.vaccinated = vaccinated;
    }
    @Override
    public String toString(){
        String grammar = this.vaccinated ? "Yes" : "NO";
        return String.format("ID: %d NAME: %s VACCINATED: %s", this.id,this.name,grammar);
    }

}
