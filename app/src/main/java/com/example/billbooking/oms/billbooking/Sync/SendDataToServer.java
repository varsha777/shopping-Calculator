package com.example.billbooking.oms.billbooking.Sync;

/**
 * Created by OMS Laptop 3 on 23-01-2018.
 */

public class SendDataToServer {


    private String invoice;
    private String orderCommodity;
    private String purchaseInvoice;
    private String userBuyer;
    private String userBuyDetails;
    private String userSeller;
    private String userSellDetails;
    private int uId;
    private String uName;

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public String getOrderCommodity() {
        return orderCommodity;
    }

    public void setOrderCommodity(String orderCommodity) {
        this.orderCommodity = orderCommodity;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getPurchaseInvoice() {
        return purchaseInvoice;
    }

    public void setPurchaseInvoice(String purchaseInvoice) {
        this.purchaseInvoice = purchaseInvoice;
    }

    public String getUserBuyer() {
        return userBuyer;
    }

    public void setUserBuyer(String userBuyer) {
        this.userBuyer = userBuyer;
    }

    public String getUserBuyDetails() {
        return userBuyDetails;
    }

    public void setUserBuyDetails(String userBuyDetails) {
        this.userBuyDetails = userBuyDetails;
    }

    public String getUserSeller() {
        return userSeller;
    }

    public void setUserSeller(String userSeller) {
        this.userSeller = userSeller;
    }

    public String getUserSellDetails() {
        return userSellDetails;
    }

    public void setUserSellDetails(String userSellDetails) {
        this.userSellDetails = userSellDetails;
    }
}
