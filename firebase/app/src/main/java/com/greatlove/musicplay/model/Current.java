package com.greatlove.musicplay.model;


/**
 * Created by sagar on 9/23/2019.
 */
public class Current {

    public String catName,sTitle;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public Current() {
    }


    public Current(String catName) {
        this.catName = catName;
    }
}
