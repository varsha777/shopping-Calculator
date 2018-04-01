package com.example.billbooking.oms.billbooking.DBModel;

/**
 * Created by OMS Laptop 3 on 18-01-2018.
 */

public class OrderCommodity {

    private int webOrdId;
    private int webPurId;
    private int invoiceId;
    private int ratePerAmt;
    private int qty;
    private int statusofprice;
    private int category;
    private String commodityName;
    private double discount;
    private double gst;
    private double cgst;
    private double igst;
    private double sgst;
    private String UOM;
    private String hsnCode;
    private String chapter;
    private String schedule;
    private String image;
    private int commStatus;
    private int sync;
    private int id;
    private int orderCommodityId;
    private int invoice_type;

    public int getInvoice_type() {
        return invoice_type;
    }

    public void setInvoice_type(int invoice_type) {
        this.invoice_type = invoice_type;
    }

    public int getOrderCommodityId() {
        return orderCommodityId;
    }

    public void setOrderCommodityId(int orderCommodityId) {
        this.orderCommodityId = orderCommodityId;
    }

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

    public int getCommStatus() {
        return commStatus;
    }

    public void setCommStatus(int commStatus) {
        this.commStatus = commStatus;
    }

    public int getWebOrdId() {
        return webOrdId;
    }

    public void setWebOrdId(int webOrdId) {
        this.webOrdId = webOrdId;
    }

    public int getWebPurId() {
        return webPurId;
    }

    public void setWebPurId(int webPurId) {
        this.webPurId = webPurId;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getRatePerAmt() {
        return ratePerAmt;
    }

    public void setRatePerAmt(int ratePerAmt) {
        this.ratePerAmt = ratePerAmt;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStatusofprice() {
        return statusofprice;
    }

    public void setStatusofprice(int statusofprice) {
        this.statusofprice = statusofprice;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getGst() {
        return gst;
    }

    public void setGst(double gst) {
        this.gst = gst;
    }

    public double getCgst() {
        return cgst;
    }

    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    public double getIgst() {
        return igst;
    }

    public void setIgst(double igst) {
        this.igst = igst;
    }

    public double getSgst() {
        return sgst;
    }

    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    public String getUOM() {
        return UOM;
    }

    public void setUOM(String UOM) {
        this.UOM = UOM;
    }

    public String getHsnCode() {
        return hsnCode;
    }

    public void setHsnCode(String hsnCode) {
        this.hsnCode = hsnCode;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
