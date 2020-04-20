package com.greatlove.musicplay.model;

public class MyPojo {
    private Folder1 Folder1;

    public MyPojo() {
    }

    public Folder1 getFolder1() {
        return Folder1;
    }

    public void setFolder1(Folder1 Folder1) {
        this.Folder1 = Folder1;
    }

    @Override
    public String toString() {
        return "ClassPojo [Folder1 = " + Folder1 + "]";
    }
}