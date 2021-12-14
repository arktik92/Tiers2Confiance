package com.tiesr2confiance.tiers2confiance.Models;

public class ModelRoles {
    private int      ro_id;
    private String   ro_country;
    private String   ro_label;
    private long     ro_is_godfather;

    /****** CONSTRUCTORS ********************/
    public ModelRoles() {
    }

    public ModelRoles(int ro_id, String ro_country, String ro_label, long ro_is_godfather) {
        this.ro_id = ro_id;
        this.ro_country = ro_country;
        this.ro_label = ro_label;
        this.ro_is_godfather = ro_is_godfather;
    }

/******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public int getRo_id() {
        return ro_id;
    }

    public String getRo_country() {
        return ro_country;
    }

    public String getRo_label() {
        return ro_label;
    }

    public long getRo_is_godfather() {
        return ro_is_godfather;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
