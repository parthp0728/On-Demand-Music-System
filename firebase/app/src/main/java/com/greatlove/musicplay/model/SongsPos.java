package com.greatlove.musicplay.model;

public class SongsPos implements  Comparable<SongsPos>{

    private int likes;
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SongsPos(int likes, int position) {
        this.likes = likes;
        this.position = position;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public int compareTo(SongsPos songsPos) {
        return this.likes - songsPos.likes;

    }
}
