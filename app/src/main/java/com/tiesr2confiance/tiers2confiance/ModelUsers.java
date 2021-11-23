package com.tiesr2confiance.tiers2confiance;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ModelUsers extends RecyclerView.ViewHolder {
    public int	    id	;
    public  String	us_nickname	;
    public  String	us_pwd	;
    public  String	us_email	;
    public  String	us_first_name	;
    public  String	us_second_name	;
    public  long	us_registered_date	;
    public  long	us_last_connexion_date	;
    public  int	    us_role	;
    public  int	    us_balance	;
    public  long	us_birth_date	;
    public  int	    us_postal_code	;
    public  String	us_city	;
    public  int	    us_gender	;
    public  int	    us_sexual_orientation	;
    public  int	    us_marital_status	;
    public  int	    us_has_kids	;
    public String	us_profession	;
    public int	    us_height	;
    public int	    us_shape	;
    public int	    us_ethnic_group	;
    public int	    us_hair_color	;
    public int	    us_hair_length	;
    public int	    us_eye_color	;
    public int	    us_smoker	;
    public String	us_personnality	;
    public String	us_sports	;
    public String	us_hobbies	;
    public String	us_photos	;




    /****** CONSTRUCTORS ********************/
    public ModelUsers(@NonNull View itemView) {
        super(itemView);
    }

    public ModelUsers(@NonNull View itemView, int id, String us_nickname, String us_pwd, String us_email, String us_first_name, String us_second_name, long us_registered_date, long us_last_connexion_date, int us_role, int us_balance, long us_birth_date, int us_postal_code, String us_city, int us_gender, int us_sexual_orientation, int us_marital_status, int us_has_kids, String us_profession, int us_height, int us_shape, int us_ethnic_group, int us_hair_color, int us_hair_length, int us_eye_color, int us_smoker, String us_personnality, String us_sports, String us_hobbies, String us_photos) {
        super(itemView);
        this.id = id;
        this.us_nickname = us_nickname;
        this.us_pwd = us_pwd;
        this.us_email = us_email;
        this.us_first_name = us_first_name;
        this.us_second_name = us_second_name;
        this.us_registered_date = us_registered_date;
        this.us_last_connexion_date = us_last_connexion_date;
        this.us_role = us_role;
        this.us_balance = us_balance;
        this.us_birth_date = us_birth_date;
        this.us_postal_code = us_postal_code;
        this.us_city = us_city;
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
        this.us_personnality = us_personnality;
        this.us_sports = us_sports;
        this.us_hobbies = us_hobbies;
        this.us_photos = us_photos;
    }

    /******************** SETTERS ************************************/
    public void setId(int id) {
        this.id = id;
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

    public void setUs_second_name(String us_second_name) {
        this.us_second_name = us_second_name;
    }

    public void setUs_registered_date(long us_registered_date) {
        this.us_registered_date = us_registered_date;
    }

    public void setUs_last_connexion_date(long us_last_connexion_date) {
        this.us_last_connexion_date = us_last_connexion_date;
    }

    public void setUs_role(int us_role) {
        this.us_role = us_role;
    }

    public void setUs_balance(int us_balance) {
        this.us_balance = us_balance;
    }

    public void setUs_birth_date(long us_birth_date) {
        this.us_birth_date = us_birth_date;
    }

    public void setUs_postal_code(int us_postal_code) {
        this.us_postal_code = us_postal_code;
    }

    public void setUs_city(String us_city) {
        this.us_city = us_city;
    }

    public void setUs_gender(int us_gender) {
        this.us_gender = us_gender;
    }

    public void setUs_sexual_orientation(int us_sexual_orientation) {
        this.us_sexual_orientation = us_sexual_orientation;
    }

    public void setUs_marital_status(int us_marital_status) {
        this.us_marital_status = us_marital_status;
    }

    public void setUs_has_kids(int us_has_kids) {
        this.us_has_kids = us_has_kids;
    }

    public void setUs_profession(String us_profession) {
        this.us_profession = us_profession;
    }

    public void setUs_height(int us_height) {
        this.us_height = us_height;
    }

    public void setUs_shape(int us_shape) {
        this.us_shape = us_shape;
    }

    public void setUs_ethnic_group(int us_ethnic_group) {
        this.us_ethnic_group = us_ethnic_group;
    }

    public void setUs_hair_color(int us_hair_color) {
        this.us_hair_color = us_hair_color;
    }

    public void setUs_hair_length(int us_hair_length) {
        this.us_hair_length = us_hair_length;
    }

    public void setUs_eye_color(int us_eye_color) {
        this.us_eye_color = us_eye_color;
    }

    public void setUs_smoker(int us_smoker) {
        this.us_smoker = us_smoker;
    }

    public void setUs_personnality(String us_personnality) {
        this.us_personnality = us_personnality;
    }

    public void setUs_sports(String us_sports) {
        this.us_sports = us_sports;
    }

    public void setUs_hobbies(String us_hobbies) {
        this.us_hobbies = us_hobbies;
    }

    public void setUs_photos(String us_photos) {
        this.us_photos = us_photos;
    }
    /******************** GETTERS ************************************/
    public int getId() {
        return id;
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

    public String getUs_second_name() {
        return us_second_name;
    }

    public long getUs_registered_date() {
        return us_registered_date;
    }

    public long getUs_last_connexion_date() {
        return us_last_connexion_date;
    }

    public int getUs_role() {
        return us_role;
    }

    public int getUs_balance() {
        return us_balance;
    }

    public long getUs_birth_date() {
        return us_birth_date;
    }

    public int getUs_postal_code() {
        return us_postal_code;
    }

    public String getUs_city() {
        return us_city;
    }

    public int getUs_gender() {
        return us_gender;
    }

    public int getUs_sexual_orientation() {
        return us_sexual_orientation;
    }

    public int getUs_marital_status() {
        return us_marital_status;
    }

    public int getUs_has_kids() {
        return us_has_kids;
    }

    public String getUs_profession() {
        return us_profession;
    }

    public int getUs_height() {
        return us_height;
    }

    public int getUs_shape() {
        return us_shape;
    }

    public int getUs_ethnic_group() {
        return us_ethnic_group;
    }

    public int getUs_hair_color() {
        return us_hair_color;
    }

    public int getUs_hair_length() {
        return us_hair_length;
    }

    public int getUs_eye_color() {
        return us_eye_color;
    }

    public int getUs_smoker() {
        return us_smoker;
    }

    public String getUs_personnality() {
        return us_personnality;
    }

    public String getUs_sports() {
        return us_sports;
    }

    public String getUs_hobbies() {
        return us_hobbies;
    }

    public String getUs_photos() {
        return us_photos;
    }
}
