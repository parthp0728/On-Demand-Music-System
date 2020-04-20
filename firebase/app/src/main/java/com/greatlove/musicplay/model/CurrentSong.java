package com.greatlove.musicplay.model;


/**
 * Created by sagar on 10/9/2019.
 */
public class CurrentSong {

    public String sTitle;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public CurrentSong() {
    }


    public CurrentSong(String sTitle) {
        this.sTitle = sTitle;
    }
}
