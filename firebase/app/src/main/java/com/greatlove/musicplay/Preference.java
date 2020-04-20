package com.greatlove.musicplay;

import android.content.Context;
import android.content.SharedPreferences;

import com.greatlove.musicplay.activity.Helpservices;

public class Preference {

    private static final String USER_LOGIN = "UserLogin";         // check user is login  or not
    private static final String USER_REGISTER = "UserRegister";

    private static final String USER_ID = "user_id";  // login user id
    private static final String TOKEN = "token";    // login user token
    private static final String CHECK_AFTR_REGI = "checkAfterRegister";    // login user token
    private static final String USER_EMAIL_ID = "UserEmailId";    // login user token

    private static final String IS_FIRST_TIME = "IsUserFirstTime";    // login user token



    private static final String Id = "Id";    // user id
    private static final String ZipCount = "zipcount";



    private static final String ZipCountSize = "zipcountsize";


    private static final String Busines = "busines";    // check acc is busines or not


    // User Profile Account

    private static final String USER_Name = "user_name";
    private static final String User_Address = "user_address";



    private static final String USER_Email = "user_email";
    private static final String USER_Imag = "user_img";
    private static final String USER_Acc_ID = "user_acc_id";
    private static final String USER_Lat = "user_lat";
    private static final String USER_Lang = "user_lang";
    private static final String USER_Zip = "user_zip";



    private static final String User_type = "user_type";    // login user token

    private static SharedPreferences get() {
        return Helpservices.getAppContext().getSharedPreferences("YCDBApplication", Context.MODE_PRIVATE);
    }

   /* public static boolean setLogin(LoginResponse res) throws IOException {
        return get().edit()
                .putString(USER_LOGIN, ObjectSerializer.serialize(res))
                .commit();
    }

    public static LoginResponse getLogin() {
        try {
            return (LoginResponse) ObjectSerializer.deserialize(get().getString(USER_LOGIN, ObjectSerializer.serialize(null)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static String getUserIsFirstInstall() {
        return get().getString(IS_FIRST_TIME, "no");
    }

    public static void setUserIsFirstInstall(String checkisfirst) {
        get().edit().putString(IS_FIRST_TIME, checkisfirst).commit();
    }


    public static String getUserId() {
        return get().getString(USER_ID, "");
    }

    public static void setUserId(String user_id) {
        get().edit().putString(USER_ID, user_id).commit();
    }


    public static String getUser_type() {
        return get().getString(User_type, "");
    }

    public static void setUser_type(String user_id) {
        get().edit().putString(User_type, user_id).commit();
    }

    public static String getId() {
        return get().getString(Id, "");
    }
    public static void setId(String id) {
        get().edit().putString(Id, id).commit();
    }




    public static String getZipCountSize() {
        return get().getString(ZipCountSize, "");
    }
    public static void setZipCountSize(String zipcountsize) {
        get().edit().putString(ZipCountSize, zipcountsize).commit();
    }


    public static String getZipCount() {
        return get().getString(ZipCount, "");
    }
    public static void setZipCount(String zipcount) {
        get().edit().putString(ZipCount, zipcount).commit();
    }

    public static String getBusines() {
        return get().getString(Busines, "");
    }
    public static void setBusines(String busines) {
        get().edit().putString(Busines, busines).commit();
    }


    public static String getToken() {
        return get().getString(TOKEN, null);
    }

    public static void setToken(String token) {
        get().edit().putString(TOKEN, token).commit();
    }

    public static String getIsRegister() {
        return get().getString(CHECK_AFTR_REGI, null);
    }

    public static void setIsRegister(String check) {
        get().edit().putString(CHECK_AFTR_REGI, check).commit();
    }

    public static String getEmailId() {
        return get().getString(USER_EMAIL_ID, null);
    }

    public static void SetEmailId(String check) {
        get().edit().putString(USER_EMAIL_ID, check).commit();
    }



    // User account


    public static String getUSER_Lat() {

        return get().getString(USER_Lat, "");
    }

    public static void setUSER_Lat(String user_lat) {
        get().edit().putString(USER_Lat, user_lat).commit();
    }
    public static String getUSER_Lang() {

        return get().getString(USER_Lang, "");
    }
    public static void setUSER_Lang(String user_lang) {
        get().edit().putString(USER_Lang, user_lang).commit();
    }


    public static String getUSER_Zip() {

        return get().getString(USER_Zip, "");
    }
    public static void setUSER_Zip(String user_zip) {
        get().edit().putString(USER_Zip, user_zip).commit();
    }

    public static String getUSER_Name() {
        return get().getString(USER_Name, "");
    }

    public static void setUSER_Name(String user_name) {
        get().edit().putString(USER_Name, user_name).commit();
    }

    public static String getUser_Address() {
        return get().getString(User_Address, "");
    }

    public static void setUser_Address(String user_address) {
        get().edit().putString(User_Address, user_address).commit();
    }

    public static String getUSER_Email() {
        return get().getString(USER_Email, "");
    }

    public static void setUSER_Email(String user_email) {
        get().edit().putString(USER_Email, user_email).commit();
    }

    public static String getUSER_Imag() {
        return get().getString(USER_Imag, "");
    }

    public static void setUSER_Imag(String user_img) {
        get().edit().putString(USER_Imag, user_img).commit();
    }

    public static String getUSER_Acc_ID() {
        return get().getString(USER_Acc_ID, "");
    }

    public static void setUSER_Acc_ID(String user_acc_id) {
        get().edit().putString(USER_Acc_ID, user_acc_id).commit();
    }
}
