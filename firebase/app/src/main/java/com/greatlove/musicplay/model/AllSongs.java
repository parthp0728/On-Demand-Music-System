package com.greatlove.musicplay.model;

public class AllSongs {
    private String discription;

    private String fav_by;

    private String fav;

    private String song_name;

    private String url;
    private String time;
    private String categoty;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AllSongs() {
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getFav_by() {
        return fav_by;
    }

    public void setFav_by(String fav_by) {
        this.fav_by = fav_by;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCategoty() {
        return categoty;
    }

    public void setCategoty(String categoty) {
        this.categoty = categoty;
    }

    @Override
    public String toString() {
        return "ClassPojo [discription = " + discription + ", fav_by = " + fav_by + ", fav = " + fav + ", song_name = " + song_name + ", url = " + url + "]";
    }
}