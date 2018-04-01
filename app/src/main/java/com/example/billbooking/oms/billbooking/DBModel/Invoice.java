package com.example.billbooking.oms.billbooking.DBModel;

/**
 * Created by OMS Laptop 3 on 18-01-2018.
 */

public class Invoice {

    private int webInvoiceId;
    private double amount;
    private double totalAmount;
    private int paidStatus;
    private String invoiceDisplayId;
    private String date;
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

    public int getWebInvoiceId() {
        return webInvoiceId;
    }

    public void setWebInvoiceId(int webInvoiceId) {
        this.webInvoiceId = webInvoiceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getInvoiceDisplayId() {
        return invoiceDisplayId;
    }

    public void setInvoiceDisplayId(String invoiceDisplayId) {
        this.invoiceDisplayId = invoiceDisplayId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
