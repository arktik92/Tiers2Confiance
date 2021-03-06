package com.tiesr2confiance.tiers2confiance.Models;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelUsers {
    private String us_auth_uid;
    private String us_nickname;
    private String us_pwd;
    private String us_email;
    private String us_first_name;
    private String us_last_name;
    private Date us_registered_date;
    private Date us_last_connexion_date;
    private long us_role;
    private long us_balance;
    private String us_nephews;
    private String us_nephews_request_from;
    private String us_nephews_request_to;
    private String us_godfather;
    private String us_godfather_request_from;
    private String us_godfather_request_to;
    private String us_matchs;
    private String us_matchs_pending;
    private String us_matchs_request_from;
    private String us_matchs_request_to;
    private Date us_birth_date;
    private String us_country_lang;
    private long us_postal_code;
    private String us_city;
    private String us_city_lowercase;
    private String us_presentation;
    private long us_gender;
    private long us_sexual_orientation;
    private long us_marital_status;
    private long us_has_kids;
    private String us_profession;
    private long us_height;
    private long us_shape;
    private long us_ethnic_group;
    private long us_hair_color;
    private long us_hair_length;
    private long us_eye_color;
    private long us_smoker;
    private String us_personality;
    private String us_sports;
    private String us_hobbies;
    private String us_avatar;
    private String us_photos;
    private String us_token;

    /****** CONSTRUCTORS ********************/
    public ModelUsers() {
    }

    public ModelUsers(String us_auth_uid, String us_nickname, String us_pwd, String us_email, String us_first_name, String us_last_name, Date us_registered_date, Date us_last_connexion_date, long us_role, long us_balance, String us_nephews, String us_nephews_request_from, String us_nephews_request_to,  String us_godfather, String us_godfather_request_from, String us_godfather_request_to, String us_matchs, String us_matchs_pending, String us_matchs_request_to, String us_matchs_request_from,  Date us_birth_date, String us_country_lang, long us_postal_code, String us_city, String us_city_lowercase, String us_presentation, long us_gender, long us_sexual_orientation, long us_marital_status, long us_has_kids, String us_profession, long us_height, long us_shape, long us_ethnic_group, long us_hair_color, long us_hair_length, long us_eye_color, long us_smoker, String us_personality, String us_sports, String us_hobbies, String us_avatar, String us_photos, String us_token) {
        this.us_auth_uid = us_auth_uid;
        this.us_nickname = us_nickname;
        this.us_pwd = us_pwd;
        this.us_email = us_email;
        this.us_first_name = us_first_name;
        this.us_last_name = us_last_name;
        this.us_registered_date = us_registered_date;
        this.us_last_connexion_date = us_last_connexion_date;
        this.us_role = us_role;
        this.us_balance = us_balance;
        this.us_nephews = us_nephews;
        this.us_nephews_request_from = us_nephews_request_from;
        this.us_nephews_request_to = us_nephews_request_to;
        this.us_godfather = us_godfather;
        this.us_godfather_request_from = us_godfather_request_from;
        this.us_godfather_request_to = us_godfather_request_to;
        this.us_matchs = us_matchs;
        this.us_matchs_pending = us_matchs_pending;
        this.us_matchs_request_from = us_matchs_request_from;
        this.us_matchs_request_to = us_matchs_request_to;
        this.us_birth_date = us_birth_date;
        this.us_country_lang = us_country_lang;
        this.us_postal_code = us_postal_code;
        this.us_city = us_city;
        this.us_city_lowercase = us_city_lowercase;
        this.us_presentation = us_presentation;
        this.us_gender = us_gender;
        this.us_sexual_orientation = us_sexual_orientation;
        this.us_marital_status = us_marital_status;
        this.us_has_kids = us_has_kids;
        this.us_profession = us_profession;
        this.us_height = us_height;
        this.us_shape = us_shape;
        this.us_ethnic_group = us_ethnic_group;
        this.us_hair_color = us_hair_color;
        this.us_hair_length = us_hair_length;
        this.us_eye_color = us_eye_color;
        this.us_smoker = us_smoker;
        this.us_personality = us_personality;
        this.us_sports = us_sports;
        this.us_hobbies = us_hobbies;
        this.us_avatar = us_avatar;
        this.us_photos = us_photos;
        this.us_token = us_token;
    }

    /******************** SETTERS ************************************/
    public void setUs_auth_uid(String us_auth_uid) {
        this.us_auth_uid = us_auth_uid;
    }

    public void setUs_nickname(String us_nickname) {
        this.us_nickname = us_nickname;
    }

    public void setUs_pwd(String us_pwd) {
        this.us_pwd = us_pwd;
    }

    public void setUs_email(String us_email) {
        this.us_email = us_email;
    }

    public void setUs_first_name(String us_first_name) {
        this.us_first_name = us_first_name;
    }

    public void setUs_last_name(String us_last_name) {
        this.us_last_name = us_last_name;
    }

    public void setUs_registered_date(Date us_registered_date) {
        this.us_registered_date = us_registered_date;
    }

    public void setUs_last_connexion_date(Date us_last_connexion_date) {
        this.us_last_connexion_date = us_last_connexion_date;
    }

    public void setUs_role(long us_role) {
        this.us_role = us_role;
    }

    public void setUs_balance(long us_balance) {
        this.us_balance = us_balance;
    }

    public void setUs_nephews(String us_nephews) {
        this.us_nephews = us_nephews;
    }

    public void setUs_nephews_request_from(String us_nephews_request_from) { this.us_nephews_request_from = us_nephews_request_from; }

    public void setUs_nephews_request_to(String us_nephews_request_to) { this.us_nephews_request_to = us_nephews_request_to; }

    public void setUs_godfather(String us_godfather) {
        this.us_godfather = us_godfather;
    }

    public void setUs_godfather_request_from(String us_godfather_request_from) { this.us_godfather_request_from = us_godfather_request_from; }

    public void setUs_godfather_request_to(String us_godfather_request_to) { this.us_godfather_request_to = us_godfather_request_to; }

    public void setUs_matchs(String us_matchs) { this.us_matchs = us_matchs; }

    public void setUs_matchs_pending(String us_matchs_pending) { this.us_matchs_pending = us_matchs_pending; }

    public void setUs_matchs_request_from(String us_matchs_request_from) { this.us_matchs_request_from = us_matchs_request_from; }

    public void setUs_matchs_request_to(String us_matchs_request_to) { this.us_matchs_request_to = us_matchs_request_to; }

    public void setUs_birth_date(Date us_birth_date) {
        this.us_birth_date = us_birth_date;
    }

    public void setUs_country_lang(String us_country_lang) {
        this.us_country_lang = us_country_lang;
    }

    public void setUs_postal_code(long us_postal_code) {
        this.us_postal_code = us_postal_code;
    }

    public void setUs_city(String us_city) {
        this.us_city = us_city;
    }

    public void setUs_city_lowercase(String us_city_lowercase) { this.us_city_lowercase = us_city_lowercase; }

    public void setUs_presentation(String us_presentation) {
        this.us_presentation = us_presentation;
    }

    public void setUs_gender(long us_gender) {
        this.us_gender = us_gender;
    }

    public void setUs_sexual_orientation(long us_sexual_orientation) {
        this.us_sexual_orientation = us_sexual_orientation;
    }

    public void setUs_marital_status(long us_marital_status) {
        this.us_marital_status = us_marital_status;
    }

    public void setUs_has_kids(long us_has_kids) {
        this.us_has_kids = us_has_kids;
    }

    public void setUs_profession(String us_profession) {
        this.us_profession = us_profession;
    }

    public void setUs_height(long us_height) {
        this.us_height = us_height;
    }

    public void setUs_shape(long us_shape) {
        this.us_shape = us_shape;
    }

    public void setUs_ethnic_group(long us_ethnic_group) {
        this.us_ethnic_group = us_ethnic_group;
    }

    public void setUs_hair_color(long us_hair_color) {
        this.us_hair_color = us_hair_color;
    }

    public void setUs_hair_length(long us_hair_length) {
        this.us_hair_length = us_hair_length;
    }

    public void setUs_eye_color(long us_eye_color) {
        this.us_eye_color = us_eye_color;
    }

    public void setUs_smoker(long us_smoker) {
        this.us_smoker = us_smoker;
    }

    public void setUs_personality(String us_personality) {
        this.us_personality = us_personality;
    }

    public void setUs_sports(String us_sports) {
        this.us_sports = us_sports;
    }

    public void setUs_hobbies(String us_hobbies) {
        this.us_hobbies = us_hobbies;
    }

    public void setUs_avatar(String us_avatar) {
        this.us_avatar = us_avatar;
    }

    public void setUs_photos(String us_photos) {
        this.us_photos = us_photos;
    }

    public void setUs_token(String us_token) {this.us_token = us_token; }

    /******************** GETTERS ************************************/
    public String getUs_auth_uid() {
        return us_auth_uid;
    }

    public String getUs_nickname() {
        return us_nickname;
    }

    public String getUs_pwd() {
        return us_pwd;
    }

    public String getUs_email() {
        return us_email;
    }

    public String getUs_first_name() {
        return us_first_name;
    }

    public String getUs_last_name() {
        return us_last_name;
    }

    public Date getUs_registered_date() {
        return us_registered_date;
    }

    public Date getUs_last_connexion_date() {
        return us_last_connexion_date;
    }

    public long getUs_role() {
        return us_role;
    }

    public long getUs_balance() {
        return us_balance;
    }

    public String getUs_nephews() {
        return us_nephews;
    }

    public String getUs_nephews_request_from() { return us_nephews_request_from; }

    public String getUs_nephews_request_to() { return us_nephews_request_to; }

    public String getUs_godfather() {
        return us_godfather;
    }

    public String getUs_godfather_request_from() { return us_godfather_request_from; }

    public String getUs_godfather_request_to() { return us_godfather_request_to; }

    public String getUs_matchs() { return us_matchs; }

    public String getUs_matchs_pending() { return us_matchs_pending; }

    public String getUs_matchs_request_from() { return us_matchs_request_from; }

    public String getUs_matchs_request_to() { return us_matchs_request_to; }

    public Date getUs_birth_date() {
        return us_birth_date;
    }

    public String getUs_country_lang() {
        return us_country_lang;
    }

    public long getUs_postal_code() {
        return us_postal_code;
    }

    public String getUs_city() {
        return us_city;
    }

    public String getUs_city_lowercase() { return us_city_lowercase; }

    public String getUs_presentation() {
        return us_presentation;
    }

    public long getUs_gender() {
        return us_gender;
    }

    public long getUs_sexual_orientation() {
        return us_sexual_orientation;
    }

    public long getUs_marital_status() {
        return us_marital_status;
    }

    public long getUs_has_kids() {
        return us_has_kids;
    }

    public String getUs_profession() {
        return us_profession;
    }

    public long getUs_height() {
        return us_height;
    }

    public long getUs_shape() {
        return us_shape;
    }

    public long getUs_ethnic_group() {
        return us_ethnic_group;
    }

    public long getUs_hair_color() {
        return us_hair_color;
    }

    public long getUs_hair_length() {
        return us_hair_length;
    }

    public long getUs_eye_color() {
        return us_eye_color;
    }

    public long getUs_smoker() {
        return us_smoker;
    }

    public String getUs_personality() {
        return us_personality;
    }

    public String getUs_sports() {
        return us_sports;
    }

    public String getUs_hobbies() {
        return us_hobbies;
    }

    public String getUs_avatar() {
        return us_avatar;
    }

    public String getUs_photos() {
        return us_photos;
    }

    public String getUs_token() { return us_token;}

    /**********************************************************************************************/
    /**********************************************************************************************/
    /*******************************   Getters Sp??cifiques ****************************************/
    /**********************************************************************************************/
    /**********************************************************************************************/
    public int getUs_age() {

        Date birthDate  = us_birth_date;
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int birthDateInt = Integer.parseInt(formatter.format(birthDate));
        int todayInt = Integer.parseInt(formatter.format(today));
        int age = (todayInt - birthDateInt) / 10000;

//        Log.i(TAGAPP, "CalculateUserAge: " + birthDate);

        return age;

    }




}