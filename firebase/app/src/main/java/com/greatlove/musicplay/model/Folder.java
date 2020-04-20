package com.greatlove.musicplay.model;

import java.io.Serializable;

public class Folder implements Serializable {

    String name;

    public Folder() {
    }

    public Folder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}