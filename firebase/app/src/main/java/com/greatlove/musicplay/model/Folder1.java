package com.greatlove.musicplay.model;

public class Folder1 {
    private AllSongs allSongs;

    public Folder1() {
    }

    public AllSongs getAllSongs() {
        return allSongs;
    }

    public void setAllSongs(AllSongs allSongs) {
        this.allSongs = allSongs;
    }

    @Override
    public String toString() {
        return "ClassPojo [allSongs = " + allSongs + "]";
    }
}