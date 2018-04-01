package com.example.billbooking.oms.billbooking.DBModel;

/**
 * Created by OMS Laptop 3 on 18-01-2018.
 */

public class UserSellDetails {

    private int webSellId;
    private int userSellTo;
    private int userSellToType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public int getWebSellId() {
        return webSellId;
    }

    public void setWebSellId(int webSellId) {
        this.webSellId = webSellId;
    }

    public int getUserSellTo() {
        return userSellTo;
    }

    public void setUserSellTo(int userSellTo) {
        this.userSellTo = userSellTo;
    }

    public int getUserSellToType() {
        return userSellToType;
    }

    public void setUserSellToType(int userSellToType) {
        this.userSellToType = userSellToType;
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

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }
}
