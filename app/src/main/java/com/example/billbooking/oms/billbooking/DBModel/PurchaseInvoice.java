package com.example.billbooking.oms.billbooking.DBModel;

/**
 * Created by OMS Laptop 3 on 18-01-2018.
 */

public class PurchaseInvoice {

    private int webPurchaseInvoiceId;
    private String invoiceDisplayId;
    private Double amount;
    private double totalAmount;
    private String date;
    private int paidStatus;
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

    public int getWebPurchaseInvoiceId() {
        return webPurchaseInvoiceId;
    }

    public void setWebPurchaseInvoiceId(int webPurchaseInvoiceId) {
        this.webPurchaseInvoiceId = webPurchaseInvoiceId;
    }

    public String getInvoiceDisplayId() {
        return invoiceDisplayId;
    }

    public void setInvoiceDisplayId(String invoiceDisplayId) {
        this.invoiceDisplayId = invoiceDisplayId;
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

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPaidStatus() {
        return paidStatus;
    }

    public void setPaidStatus(int paidStatus) {
        this.paidStatus = paidStatus;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }
}
