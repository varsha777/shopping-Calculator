package com.example.billbooking.oms.billbooking.RetrofitFiles;

/**
 * Created by VarshaDhoni on 11/22/2017.
 */

public class ServerRequest {

    private String password;
    private String userName;
    private String fullName;
    private String mobileNumber;
    private String emailId;
    private String pincode;
    private int country;
    private int state;
    private String imei;

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private AppUserSignUpDetails appUserSignUpDetails;

    public AppUserSignUpDetails getAppUserSignUpDetails() {
        return appUserSignUpDetails;
    }

    public void setAppUserSignUpDetails(AppUserSignUpDetails appUserSignUpDetails) {
        this.appUserSignUpDetails = appUserSignUpDetails;
    }


}
