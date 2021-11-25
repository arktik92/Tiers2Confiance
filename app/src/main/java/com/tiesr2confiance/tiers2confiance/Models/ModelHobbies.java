package com.tiesr2confiance.tiers2confiance.Models;

import java.util.Date;

public class ModelHobbies {
    public long      ho_id;
    public String   ho_country;
    public String   ho_label;


    /****** CONSTRUCTORS ********************/
    public ModelHobbies() {
    }

    public ModelHobbies(long ho_id, String ho_country, String ho_label) {
        this.ho_id = ho_id;
        this.ho_country = ho_country;
        this.ho_label = ho_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public long getHo_id() {
        return ho_id;
    }

    public String getHo_country() {
        return ho_country;
    }

    public String getHo_label() {
        return ho_label;
    }
    /*******************************************************************************************/
    /*******************************************************************************************/
    /*******************************************************************************************/
    /*******************************************************************************************/
    /*******************************************************************************************/

}
