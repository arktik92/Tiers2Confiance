package com.tiesr2confiance.tiers2confiance;



public class ModelViewProfilItem {

    private String name;
    private String city;
    private String email;

    public ModelViewProfilItem(String name,String city,String email) {

        this.name = name;
        this.city = city;
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
