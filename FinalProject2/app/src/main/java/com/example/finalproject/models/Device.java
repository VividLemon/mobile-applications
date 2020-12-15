package com.example.finalproject.models;

public class Device {
    private long id;
    private String name;
    private String desc;

    public Device(long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Device(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
