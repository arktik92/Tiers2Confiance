package com.tiesr2confiance.tiers2confiance.Models;

public class ModelRoles {
    public int      ro_id;
    public String   ro_country;
    public String   ro_label;

    /****** CONSTRUCTORS ********************/
    public ModelRoles() {
    }

    public ModelRoles(int ro_id, String ro_country, String ro_label) {
        this.ro_id = ro_id;
        this.ro_country = ro_country;
        this.ro_label = ro_label;
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
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
