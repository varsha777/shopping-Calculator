package com.example.billbooking.oms.billbooking.DatabaseFiles;

import android.content.Context;

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

import java.util.ArrayList;

/**
 * Created by OMS Laptop 3 on 12-01-2018.
 */

public class DbInsertOperationsLogin {

    DatabaseHelper db;
    private ArrayList<Commodity> commodityArray;
    private ArrayList<Profile> profileArray;
    private ArrayList<Profile1> profile1Array;
    private ArrayList<UserBuyer> userBuyerArray;
    private ArrayList<UserSeller> userSellerArray;
    private ArrayList<Invoice> invoiceArray;
    private ArrayList<OrderCommodity> orderCommoditieArray;
    private ArrayList<PurchaseInvoice> puchaseInvoiceArray;
    private ArrayList<UserBuyDetails> userBuyDetailsArray;
    private ArrayList<UserSellDetails> userSellDetailsArray;
    private ArrayList<ReportGenerate> reportGenerates;

    private Context context;


    public DbInsertOperationsLogin(Context context, ArrayList<Commodity> commodityArray, ArrayList<Profile> profileArray,
                                   ArrayList<Profile1> profile1Array, ArrayList<UserBuyer> userBuyerArray,
                                   ArrayList<UserSeller> userSellerArray, ArrayList<Invoice> invoiceArray,
                                   ArrayList<OrderCommodity> orderCommoditieArray, ArrayList<PurchaseInvoice> puchaseInvoiceArray,
                                   ArrayList<UserBuyDetails> userBuyDetailsArray, ArrayList<UserSellDetails> userSellDetailsArray,
                                   ArrayList<ReportGenerate> reportGenerates) {

        this.commodityArray = commodityArray;
        this.profileArray = profileArray;
        this.profile1Array = profile1Array;
        this.userBuyerArray = userBuyerArray;
        this.userSellerArray = userSellerArray;
        this.invoiceArray = invoiceArray;
        this.orderCommoditieArray = orderCommoditieArray;
        this.puchaseInvoiceArray = puchaseInvoiceArray;
        this.userBuyDetailsArray = userBuyDetailsArray;
        this.userSellDetailsArray = userSellDetailsArray;
        this.reportGenerates = reportGenerates;
        this.context = context;
        insertCommodity();
        insertProfile();
        insertProfile1();
        insertUserBuyer();
        insertUserSeller();
        insertInvoice();
        insertOrderCommoditie();
        insertPurchaseInvoice();
        uinsertSerBuyDetails();
        insertUserSellDetails();
        insertReportGenerats();
    }

    private void insertCommodity() {

        for (int i = 0; i < commodityArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertCommodity(commodityArray.get(i).getCategory(), commodityArray.get(i).getWebCommodityId(), commodityArray.get(i).getName()
                    , commodityArray.get(i).getPriceStatus(), commodityArray.get(i).getAmount(), commodityArray.get(i).getUom(),
                    commodityArray.get(i).getStock(), commodityArray.get(i).getHsncode(), null, commodityArray.get(i).getGst(),
                    commodityArray.get(i).getCgst(), commodityArray.get(i).getIgst(), commodityArray.get(i).getSgst(), commodityArray.get(i).getDiscount(), null, 0,
                    commodityArray.get(i).getMerchantId(), commodityArray.get(i).getChapter(), commodityArray.get(i).getSchedule());
        }

        // db.showCommodityCount();
    }

    private void insertProfile() {

        for (int i = 0; i < profileArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertProfile(profileArray.get(i).getUid(), profileArray.get(i).getUser_type(), profileArray.get(i).getUser_nature(),
                    profileArray.get(i).getFullname(), profileArray.get(i).getMobile_number(), profileArray.get(i).getEmailid(),
                    profileArray.get(i).getCountry(), profileArray.get(i).getState(), profileArray.get(i).getPincode(),
                    profileArray.get(i).getAddress(), profileArray.get(i).getImage(), profileArray.get(i).getLogo(),
                    profileArray.get(i).getDisplay_id(), profileArray.get(i).getLinked_by(), 0, null);
        }
        // db.showProfileCount();

    }

    private void insertProfile1() {

        for (int i = 0; i < profile1Array.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertProfile1(profile1Array.get(i).getPAN(), profile1Array.get(i).getCIN(), profile1Array.get(i).getIEN(),
                    profile1Array.get(i).getOtherName(), profile1Array.get(i).getOtherValue(), profile1Array.get(i).getGstRegNum(),
                    profile1Array.get(i).getLatitude(), profile1Array.get(i).getLongitude(), profile1Array.get(i).getIMEI(), 0, null,
                    profile1Array.get(i).getAdharNum());
        }
        // db.showProfile1Count();

    }

    private void insertUserBuyer() {

        for (int i = 0; i < userBuyerArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertUserBuyer(userBuyerArray.get(i).getUserBuyerId(), userBuyerArray.get(i).getAddedFrom(), userBuyerArray.get(i).getName(),
                    userBuyerArray.get(i).getAddress(), userBuyerArray.get(i).getEmailId(), userBuyerArray.get(i).getMobileNum(),
                    userBuyerArray.get(i).getState(), userBuyerArray.get(i).getCountry(), 0, null, userBuyerArray.get(i).getDisplayId());
        }
        // db.showUsersBuyerCount();

    }

    private void insertUserSeller() {

        for (int i = 0; i < userSellerArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertUserSeller(userSellerArray.get(i).getUserSellerId(), userSellerArray.get(i).getAddedFrom(),
                    userSellerArray.get(i).getName(), userSellerArray.get(i).getAddress(), userSellerArray.get(i).getEmailId(),
                    userSellerArray.get(i).getMobileNum(), userSellerArray.get(i).getState(), userSellerArray.get(i).getCountry(), 0, userSellerArray.get(i).getUpdatedOn(), userSellerArray.get(i).getDisplayId());
        }
        // db.showUsersSellerCount();

    }

    private void insertInvoice() {

        for (int i = 0; i < invoiceArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertInvoice(invoiceArray.get(i).getWebInvoiceId(), invoiceArray.get(i).getInvoiceDisplayId(), invoiceArray.get(i).getAmount(),
                    invoiceArray.get(i).getTotalAmount(), invoiceArray.get(i).getPaidStatus(), 0, invoiceArray.get(i).getLastUpdatedOn());
        }
        //   db.showInvoiceCount();

    }

    private void insertOrderCommoditie() {

        for (int i = 0; i < orderCommoditieArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertOrderCommodity(orderCommoditieArray.get(i).getWebOrdId(), orderCommoditieArray.get(i).getWebPurId(), orderCommoditieArray.get(i).getCommStatus(),
                    orderCommoditieArray.get(i).getInvoiceId(), orderCommoditieArray.get(i).getCommodityName(),
                    orderCommoditieArray.get(i).getQty(), orderCommoditieArray.get(i).getRatePerAmt(), orderCommoditieArray.get(i).getDiscount(),
                    orderCommoditieArray.get(i).getUOM(), orderCommoditieArray.get(i).getGst(), orderCommoditieArray.get(i).getCgst(), orderCommoditieArray.get(i).getIgst(),
                    orderCommoditieArray.get(i).getSgst(), orderCommoditieArray.get(i).getHsnCode(), orderCommoditieArray.get(i).getCategory(),
                    orderCommoditieArray.get(i).getStatusofprice(), orderCommoditieArray.get(i).getChapter(), orderCommoditieArray.get(i).getSchedule(),
                    orderCommoditieArray.get(i).getImage(), 0, null, orderCommoditieArray.get(i).getOrderCommodityId(),orderCommoditieArray.get(i).getInvoice_type());
        }
        // db.showOrderCommodityCount();

    }

    private void insertPurchaseInvoice() {

        for (int i = 0; i < puchaseInvoiceArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertPurchaseInvoice(puchaseInvoiceArray.get(i).getWebPurchaseInvoiceId(), puchaseInvoiceArray.get(i).getInvoiceDisplayId(),
                    puchaseInvoiceArray.get(i).getTotalAmount(), puchaseInvoiceArray.get(i).getPaidStatus(), 0, puchaseInvoiceArray.get(i).getLastUpdatedOn(), puchaseInvoiceArray.get(i).getAmount());
        }
        // db.showPurchaseInvoiceCount();

    }

    private void uinsertSerBuyDetails() {

        for (int i = 0; i < userBuyDetailsArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertUserBuyDetails(userBuyDetailsArray.get(i).getWebBuyId(), userBuyDetailsArray.get(i).getUserBuyFrom(),
                    userBuyDetailsArray.get(i).getSelfPurchase(), userBuyDetailsArray.get(i).getInvoiceID(), userBuyDetailsArray.get(i).getTemplate(),
                    userBuyDetailsArray.get(i).getLatitude(), userBuyDetailsArray.get(i).getLongitude(), userBuyDetailsArray.get(i).getTime(), 0, userBuyDetailsArray.get(i).getLastUpdatedOn()
                    , userBuyDetailsArray.get(i).getAddress(), userBuyDetailsArray.get(i).getUserBuyFromType());
        }
        // db.showBuyerDetailsCount();
    }

    private void insertUserSellDetails() {

        for (int i = 0; i < userSellDetailsArray.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertUserSellDetails(userSellDetailsArray.get(i).getWebSellId(), userSellDetailsArray.get(i).getUserSellTo(),
                    userSellDetailsArray.get(i).getInvoiceID(), userSellDetailsArray.get(i).getTemplate(), userSellDetailsArray.get(i).getLatitude(),
                    userSellDetailsArray.get(i).getLongitude(), userSellDetailsArray.get(i).getTime(), 0,
                    userSellDetailsArray.get(i).getLastUpdatedOn(), userSellDetailsArray.get(i).getAddress(),
                    userSellDetailsArray.get(i).getUserSellToType());
        }
        //db.showUserSellDetailsCount();
    }

    private void insertReportGenerats() {

        for (int i = 0; i < reportGenerates.size(); i++) {
            db = new DatabaseHelper(context);

            db.insertReportGenerate(reportGenerates.get(i).getSaleTotal(), reportGenerates.get(i).getSaleTop1Name(), reportGenerates.get(i).getSaleTop1Value(),
                    reportGenerates.get(i).getSaleTop2Name(), reportGenerates.get(i).getSaleTop2Value(), reportGenerates.get(i).getSaleTop3Name(),
                    reportGenerates.get(i).getSaleTop3Value(), reportGenerates.get(i).getSaleTop4Name(), reportGenerates.get(i).getSaleTop4Value(),
                    reportGenerates.get(i).getPurchaseTop1Name(), reportGenerates.get(i).getPurchaseTop1Value(), reportGenerates.get(i).getPurchaseTop2Name(),
                    reportGenerates.get(i).getPurchaseTop2Value(), reportGenerates.get(i).getPurchaseTop3Name(), reportGenerates.get(i).getPurchaseTop3Value(),
                    reportGenerates.get(i).getPurchaseTop4Name(), reportGenerates.get(i).getPurchaseTop4Value(), reportGenerates.get(i).getPurchaseTotal());
        }
        //db.showGenerateReportsCount();
    }

}
