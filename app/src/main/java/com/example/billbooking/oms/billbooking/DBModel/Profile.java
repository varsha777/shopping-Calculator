package com.example.billbooking.oms.billbooking.DBModel;

/**
 * Created by OMS Laptop 3 on 11-01-2018.
 */

public class Profile {

    private int uid;
    private int user_type;
    private int user_nature;
    private String fullname;
    private String mobile_number;
    private String emailid;
    private int country;
    private int state;
    private String pincode;
    private String address;
    private String display_id;
    private int linked_by;


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    private String logo;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getUser_nature() {
        return user_nature;
    }

    public void setUser_nature(int user_nature) {
        this.user_nature = user_nature;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisplay_id() {
        return display_id;
    }

    public void setDisplay_id(String display_id) {
        this.display_id = display_id;
    }

    public int getLinked_by() {
        return linked_by;
    }

    public void setLinked_by(int linked_by) {
        this.linked_by = linked_by;
    }
}
