package com.tiesr2confiance.tiers2confiance.Models;

public class ModelPersonality {
    public int      pe_id;
    public String   pe_country;
    public String   pe_label;

    /****** CONSTRUCTORS ********************/
    public ModelPersonality() {
    }

    public ModelPersonality(int pe_id, String pe_country, String pe_label) {
        this.pe_id = pe_id;
        this.pe_country = pe_country;
        this.pe_label = pe_label;
    }

    /******************** SETTERS ************************************/

    /******************** GETTERS ************************************/
    public void setPe_id(int pe_id) {
        this.pe_id = pe_id;
    }

    public void setPe_country(String pe_country) {
        this.pe_country = pe_country;
    }

    public void setPe_label(String pe_label) {
        this.pe_label = pe_label;
    }
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
/*******************************************************************************************/
}
