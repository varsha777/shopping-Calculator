package com.example.billbooking.oms.billbooking.DBModel;

/**
 * Created by OMS Laptop 3 on 18-01-2018.
 */

public class UserBuyDetails {

    private int webBuyId;
    private int userBuyFrom;
    private int userBuyFromType;
    private int selfPurchase;
    private int invoiceID;
    private int template;
    private String latitude;
    private String longitude;
    private String time;
    private String lastUpdatedOn;
    private int sync;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public int getWebBuyId() {
        return webBuyId;
    }

    public void setWebBuyId(int webBuyId) {
        this.webBuyId = webBuyId;
    }

    public int getUserBuyFrom() {
        return userBuyFrom;
    }

    public void setUserBuyFrom(int userBuyFrom) {
        this.userBuyFrom = userBuyFrom;
    }

    public int getUserBuyFromType() {
        return userBuyFromType;
    }

    public void setUserBuyFromType(int userBuyFromType) {
        this.userBuyFromType = userBuyFromType;
    }

    public int getSelfPurchase() {
        return selfPurchase;
    }

    public void setSelfPurchase(int selfPurchase) {
        this.selfPurchase = selfPurchase;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public int getTemplate() {
        return template;
    }

    public void setTemplate(int template) {
        this.template = template;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
