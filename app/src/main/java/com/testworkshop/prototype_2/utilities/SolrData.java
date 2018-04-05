package com.testworkshop.prototype_2.utilities;

/**
 * Created by yash on 10/4/18.
 */

public class SolrData {

    private String hotelName;
    private String address;
    private String area;
    private String city;
    private String hotel_star_rating_str;

    public SolrData(String hotelName, String address, String area, String city, String hotel_star_rating_str) {
        this.hotelName = hotelName;
        this.address = address;
        this.area = area;
        this.city = city;
        this.hotel_star_rating_str = hotel_star_rating_str;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHotel_star_rating_str() {
        return hotel_star_rating_str;
    }

    public void setHotel_star_rating_str(String hotel_star_rating_str) {
        this.hotel_star_rating_str = hotel_star_rating_str;
    }
}
