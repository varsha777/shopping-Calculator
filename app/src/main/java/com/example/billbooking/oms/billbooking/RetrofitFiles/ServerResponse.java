package com.example.billbooking.oms.billbooking.RetrofitFiles;


import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DBModel.Invoice;
import com.example.billbooking.oms.billbooking.DBModel.OrderCommodity;
import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.Profile1;
import com.example.billbooking.oms.billbooking.DBModel.PurchaseInvoice;
import com.example.billbooking.oms.billbooking.DBModel.ReportGenerate;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyer;
import com.example.billbooking.oms.billbooking.DBModel.UserSellDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserSeller;

/**
 * Created by VarshaDhoni on 11/22/2017.
 */

public class ServerResponse {

    private String status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;

    }


    public void setStatus(String status) {
        this.status = status;
    }

    private Commodity[] commodity;
    private Invoice[] invoice;
    private OrderCommodity[] order_commodity;
    private Profile[] profile;
    private Profile1[] profile1;
    private PurchaseInvoice[] purchase_invoice;
    private UserBuyDetails[] user_buy_details;
    private UserBuyer[] user_buyer;
    private UserSellDetails[] user_sell_details;
    private UserSeller[] user_seller;
    private ReportGenerate[] report_generate;

    public ReportGenerate[] getReport_generate() {
        return report_generate;
    }

    public void setReport_generate(ReportGenerate[] report_generate) {
        this.report_generate = report_generate;
    }

    public Commodity[] getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity[] commodity) {
        this.commodity = commodity;
    }

    public Invoice[] getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice[] invoice) {
        this.invoice = invoice;
    }

    public OrderCommodity[] getOrder_commodity() {
        return order_commodity;
    }

    public void setOrder_commodity(OrderCommodity[] order_commodity) {
        this.order_commodity = order_commodity;
    }

    public Profile[] getProfile() {
        return profile;
    }

    public void setProfile(Profile[] profile) {
        this.profile = profile;
    }

    public Profile1[] getProfile1() {
        return profile1;
    }

    public void setProfile1(Profile1[] profile1) {
        this.profile1 = profile1;
    }

    public PurchaseInvoice[] getPurchase_invoice() {
        return purchase_invoice;
    }

    public void setPurchase_invoice(PurchaseInvoice[] purchase_invoice) {
        this.purchase_invoice = purchase_invoice;
    }

    public UserBuyDetails[] getUser_buy_details() {
        return user_buy_details;
    }

    public void setUser_buy_details(UserBuyDetails[] user_buy_details) {
        this.user_buy_details = user_buy_details;
    }

    public UserBuyer[] getUser_buyer() {
        return user_buyer;
    }

    public void setUser_buyer(UserBuyer[] user_buyer) {
        this.user_buyer = user_buyer;
    }

    public UserSellDetails[] getUser_sell_details() {
        return user_sell_details;
    }

    public void setUser_sell_details(UserSellDetails[] user_sell_details) {
        this.user_sell_details = user_sell_details;
    }

    public UserSeller[] getUser_seller() {
        return user_seller;
    }

    public void setUser_seller(UserSeller[] user_seller) {
        this.user_seller = user_seller;
    }
}