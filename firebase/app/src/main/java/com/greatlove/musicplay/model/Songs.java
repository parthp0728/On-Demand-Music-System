package com.greatlove.musicplay.model;

public class Songs implements Comparable<Songs> {

    private String name;
    private int likes = 0;
    private String url;
    private String time;


    public String getFolderNamer() {
        return folderNamer;
    }

    public void setFolderNamer(String folderNamer) {
        this.folderNamer = folderNamer;
    }

    private String folderNamer;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int compareTo(Songs songs) {
        return this.likes - songs.likes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    /*  @Override
    public int compareTo(Object o) {
        return  (this.getLikes() < ((Songs) o).getNo() ? -1 : (this.getLikes() == ((Songs) o).getNo() ? 0 : 1));

    }*/
}
