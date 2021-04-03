package com.bo.springboot.optional.code;

/**
 * @author bo
 */
public class Location {
    private String country;
    private String city;

    public Location(String country, String city) {
        this.country = country;
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public static void introduce(String country) {
        System.out.println("I'm from " + country + ".");
    }
}
