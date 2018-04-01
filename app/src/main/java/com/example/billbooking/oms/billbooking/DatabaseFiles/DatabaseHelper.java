package com.example.billbooking.oms.billbooking.DatabaseFiles;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DBModel.Invoice;
import com.example.billbooking.oms.billbooking.DBModel.OrderCommodity;
import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.PurchaseInvoice;
import com.example.billbooking.oms.billbooking.DBModel.ReportGenerate;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyer;
import com.example.billbooking.oms.billbooking.DBModel.UserSellDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserSeller;

import java.util.ArrayList;

import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.commodityTable;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.commodityTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.invoice;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.invoiceTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.master_commodity;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.master_commodityTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.mata_data;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.mata_dataTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.order_commodity;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.order_commodityTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.profile;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.profile1;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.profile1TblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.profileTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.purchase_invoice;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.purchase_invoiceTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.report_generate;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.report_generateTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_buy_details;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_buy_detailsTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_buyer;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_buyerTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_sell_details;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_sell_detailsTblName;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_seller;
import static com.example.billbooking.oms.billbooking.DatabaseFiles.TablesDb.user_sellerTblName;

/**
 * Created by OMS Laptop 3 on 10-01-2018.
 */


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG = "DatabaseHelper";
    private DatabaseHelper mOpenHelper;
    // Database Version
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db = null;
    // Database Name
    private static final String DATABASE_NAME = "billBooking";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    private Context context;


    @Override
    public void onCreate(SQLiteDatabase db) {

        Toast.makeText(context, "conformed", Toast.LENGTH_SHORT).show();
        db.execSQL(commodityTable);
        db.execSQL(invoice);
        db.execSQL(master_commodity);
        db.execSQL(mata_data);
        db.execSQL(order_commodity);
        db.execSQL(profile);
        db.execSQL(profile1);
        db.execSQL(purchase_invoice);
        db.execSQL(report_generate);
        db.execSQL(user_buy_details);
        db.execSQL(user_buyer);
        db.execSQL(user_sell_details);
        db.execSQL(user_seller);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        /*db.execSQL("DROP TABLE IF EXISTS " + commodityTblName);
        db.execSQL("DROP TABLE IF EXISTS " + invoiceTblName);
        db.execSQL("DROP TABLE IF EXISTS " + master_commodityTblName);
        db.execSQL("DROP TABLE IF EXISTS " + mata_dataTblName);
        db.execSQL("DROP TABLE IF EXISTS " + order_commodityTblName);
        db.execSQL("DROP TABLE IF EXISTS " + profileTblName);
        db.execSQL("DROP TABLE IF EXISTS " + profile1TblName);
        db.execSQL("DROP TABLE IF EXISTS " + purchase_invoiceTblName);
        db.execSQL("DROP TABLE IF EXISTS " + report_generateTblName);
        db.execSQL("DROP TABLE IF EXISTS " + user_buy_detailsTblName);
        db.execSQL("DROP TABLE IF EXISTS " + user_buyerTblName);
        db.execSQL("DROP TABLE IF EXISTS " + user_sell_detailsTblName);
        db.execSQL("DROP TABLE IF EXISTS " + user_sellerTblName);

        onCreate(db);
*/
    }


    public void deleteAllTables() {
        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        try {
            context.deleteDatabase(DATABASE_NAME);
           /* db.execSQL("DROP TABLE IF EXISTS " + commodityTblName);
            db.execSQL("DROP TABLE IF EXISTS " + invoiceTblName);
            db.execSQL("DROP TABLE IF EXISTS " + master_commodityTblName);
            db.execSQL("DROP TABLE IF EXISTS " + mata_dataTblName);
            db.execSQL("DROP TABLE IF EXISTS " + order_commodityTblName);
            db.execSQL("DROP TABLE IF EXISTS " + profileTblName);
            db.execSQL("DROP TABLE IF EXISTS " + profile1TblName);
            db.execSQL("DROP TABLE IF EXISTS " + purchase_invoiceTblName);
            db.execSQL("DROP TABLE IF EXISTS " + report_generateTblName);
            db.execSQL("DROP TABLE IF EXISTS " + user_buy_detailsTblName);
            db.execSQL("DROP TABLE IF EXISTS " + user_buyerTblName);
            db.execSQL("DROP TABLE IF EXISTS " + user_sell_detailsTblName);
            db.execSQL("DROP TABLE IF EXISTS " + user_sellerTblName);*/
            Toast.makeText(context, "Delete All Tables", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Exception in delete db", e.getMessage());
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void createTables() {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        Toast.makeText(context, "conformed", Toast.LENGTH_SHORT).show();
        db.execSQL(commodityTable);
        db.execSQL(invoice);
        db.execSQL(master_commodity);
        db.execSQL(mata_data);
        db.execSQL(order_commodity);
        db.execSQL(profile);
        db.execSQL(profile1);
        db.execSQL(purchase_invoice);
        db.execSQL(report_generate);
        db.execSQL(user_buy_details);
        db.execSQL(user_buyer);
        db.execSQL(user_sell_details);
        db.execSQL(user_seller);
    }

    public void insertCommodity(int category, int web_commodity_id, String practice_name,
                                int price_status, double amount, String uom, int stock, String hsncode, String image,
                                double gst, double cgst, double igst, double sgst, double discount, String last_updated_on, int sync,
                                String merchantId, String chapter, String schedule) {
        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_commodity = "insert into " + commodityTblName + " (category,web_commodity_id,practice_name," +
                "price_status,amount,uom,stock,hsncode,image,gst,cgst,igst,sgst,discount,last_updated_on,sync,merchantid,chapter,schedule)" +
                " values('" + category + "','" + web_commodity_id + "','" + practice_name + "','" + price_status +
                "','" + amount + "','" + uom + "','" + stock + "','" + hsncode + "','" + image + "','" + gst + "','" + cgst + "','"
                + igst + "','" + sgst + "','" + discount + "','" + last_updated_on + "','" + sync + "','" + merchantId + "','"
                + chapter + "','" + schedule + "');";

        try {
            Log.i("sql1=", insert_commodity);
            db.execSQL(insert_commodity);
            showCommodityCount();
        } catch (Exception e) {
            Log.d("sqlite Exception", e.getMessage());
        }
        db.close();

    }


    public boolean insertOrderCommodity(int web_order_commodity_id, int web_purchase_commodity_id, int commodity_status, int invoice_id, String name,
                                        int qty, double amount, double discount, String uom, double gst, double cgst, double igst, double sgst,
                                        String hsncode, int category, int price_status, String chapter, String schedule,
                                        String image, int sync, String last_updated_on, int order_commodity_id, int invoice_type) {
        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_order_commodity = "insert into " + order_commodityTblName + " (web_order_commodity_id,web_purchase_commodity_id, commodity_status, invoice_id," +
                "name,qty,amount,discount,uom,gst,cgst,igst,sgst,hsncode,category,price_status,chapter,schedule,image,sync,last_updated_on,order_commodity_id,invoice_type)" +
                " values('" + web_order_commodity_id + "','" + web_purchase_commodity_id + "','" + commodity_status + "','" + invoice_id + "','" + name +
                "','" + qty + "','" + amount + "','" + discount + "','" + uom + "','" + gst + "','" + cgst + "','" + igst + "','" + sgst + "','" + hsncode + "','" + category + "','" + price_status + "','"
                + chapter + "','" + schedule + "','" + image + "','" + sync + "','" + last_updated_on + "','" + order_commodity_id + "','" + invoice_type + "');";

        try {
            Log.i("sql1=", insert_order_commodity);
            db.execSQL(insert_order_commodity);
            showOrderCommodityCount();
            db.close();
            return true;
        } catch (Exception e) {
            Log.d("sqlite Exception", e.getMessage());
            return false;
        }

    }


    public boolean insertInvoice(int web_invoice_id, String invoice_display_id, double amount, double total_amount, int paid_status,
                                 int sync, String last_updated_on) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_invoice = "insert into " + invoiceTblName + " (web_invoice,invoice_display_id,amount,total_amount," +
                "paid_status,sync, last_updated_on) values('" + web_invoice_id + "','" + invoice_display_id + "','" + amount + "','" + total_amount +
                "','" + paid_status + "','" + sync + "','" + last_updated_on + "');";

        try {
            Log.i("sql1=", insert_invoice);
            db.execSQL(insert_invoice);
            showInvoiceCount();
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("sqlite Exception", e.getMessage());
            return false;
        }

    }


    public boolean insertUserSellDetails(int web_sell_id, int user_sell_to, int invoice_id, int template, String latitude,
                                         String longitude, String time, int sync, String last_updated_on, String address, int userSellToType) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_user_sell_details = "insert into " + user_sell_detailsTblName + " ( web_sell_id, user_sell_to,invoice_id" +
                " ,template,latitude ,longitude ,time,sync, last_updated_on,address,user_sell_to_type) values('" + web_sell_id + "','" + user_sell_to + "','" + invoice_id +
                "','" + template + "','" + latitude + "','" + longitude + "','" + time + "','" + sync + "','" + last_updated_on + "','" + address + "','" + userSellToType + "');";

        try {
            Log.i("sql1=", insert_user_sell_details);
            db.execSQL(insert_user_sell_details);
            showUserSellDetailsCount();
            db.close();
            return true;
        } catch (Exception e) {
            Log.d("sqlite Exception", e.getMessage());
            return false;
        }

    }


    public boolean insertPurchaseInvoice(int web_purchase_invoice_id, String invoice_display_id, double total_amount,
                                         int paid_status, int sync, String last_updated_on, Double amount) {


        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_purchase_invoice = "insert into " + purchase_invoiceTblName + " (web_purchase_invoice_id,invoice_display_id," +
                "total_amount,paid_status,sync,last_updated_on,amount) " +
                "values('" + web_purchase_invoice_id + "','" + invoice_display_id + "','" + total_amount + "','" + paid_status +
                "','" + sync + "','" + last_updated_on + "','" + amount + "');";

        try {
            Log.i("sql1=", insert_purchase_invoice);
            db.execSQL(insert_purchase_invoice);
            showPurchaseInvoiceCount();
            db.close();
            return true;
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
            return false;
        }


    }


    public boolean insertUserBuyDetails(int web_buy_id, int user_buy_from, int self_purchase, int invoice_id, int template, String latitude,
                                        String longitude, String time, int sync, String last_updated_on, String addres, int userBuyFromType) {


        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_userr_buy_details = "insert into " + user_buy_detailsTblName + " (web_buy_id,user_buy_from,self_purchase,invoice_id,template " +
                ",latitude,longitude,time,sync,last_updated_on,address,user_buy_from_type) " +
                "values('" + web_buy_id + "','" + user_buy_from + "','" + self_purchase + "','" + invoice_id + "','" + template + "','" + latitude + "','" +
                longitude + "','" + time + "','" + sync + "','" + last_updated_on + "','" + addres + "','" + userBuyFromType + "');";

        try {
            Log.i("sql1=", insert_userr_buy_details);
            db.execSQL(insert_userr_buy_details);
            showBuyerDetailsCount();
            db.close();
            return true;
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
            return false;
        }

    }


    public void insertMataData(int web_metadata_id, int invoice_type, int sync, String last_updated_on) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_mata_data = "insert into " + mata_dataTblName + " (web_metadata_id,invoice_type,sync,last_update_on) " +
                "values('" + web_metadata_id + "','" + invoice_type + "','" + sync + "','" + last_updated_on + "');";

        try {
            Log.i("sql1=", insert_mata_data);
            db.execSQL(insert_mata_data);
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
        }
        db.close();

    }


    public void insertUserSeller(int user_seller_id, int added_from, String name, String address, String emailid,
                                 String mobile_number, int state, int country, int sync, String last_updated_on,
                                 String display_id) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_user_seller = "insert into " + user_sellerTblName + " (user_seller_id, added_from,name, address, emailid" +
                ", mobile_number,state, country, sync,last_updated_on, display_id) " +
                "values('" + user_seller_id + "','" + added_from + "','" + name + "','" + address + "','" + emailid + "','" + mobile_number + "','" +
                state + "','" + country + "','" + sync + "','" + last_updated_on + "','" + display_id + "');";

        try {
            Log.i("sql1=", insert_user_seller);
            db.execSQL(insert_user_seller);
            showUsersSellerCount();
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
        }
        db.close();


    }


    public void insertUserBuyer(int user_buyer_id, int added_from, String name, String address, String emailid,
                                String mobile_number, int state, int country, int sync, String last_updated_on,
                                String display_id) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_user_buyer = "insert into " + user_buyerTblName + " (user_buyer_id, added_from,name, address, emailid" +
                ", mobile_number,state, country, sync,last_update_on, display_id) " +
                "values('" + user_buyer_id + "','" + added_from + "','" + name + "','" + address + "','" + emailid + "','" + mobile_number + "','" +
                state + "','" + country + "','" + sync + "','" + last_updated_on + "','" + display_id + "');";

        try {
            Log.i("sql1=", insert_user_buyer);
            db.execSQL(insert_user_buyer);
            showUsersBuyerCount();
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
        }
        db.close();


    }


    public void insertProfile1(String PAN, String CIN, String IEN, String other_name, String other_value,
                               String gst_reg_num, String latitude, String longitude, String IMEI, int sync,
                               String last_updated_on, String adhar_num) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_profile1 = "insert into " + profile1TblName + " (PAN,CIN,IEN,other_name,other_value,gst_reg_num," +
                "latitude,longitude,IMEI,sync,last_updated_on,adhar_num) " +
                "values('" + PAN + "','" + CIN + "','" + IEN + "','" + other_name + "','" + other_value + "','" + gst_reg_num + "','" + latitude +
                "','" + longitude + "','" + IMEI + "','" + sync + "','" + last_updated_on + "','" + adhar_num + "');";

        try {
            Log.i("sql1=", insert_profile1);
            db.execSQL(insert_profile1);
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
        }
        db.close();


    }


    public void insertProfile(int uid, int user_type, int user_nature, String fullname, String mobile_number, String email_id,
                              int country, int state, String pincode, String address, String image, String logo, String display_id,
                              int linked_by, int sync, String last_updated_on) {


        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_profile = "insert into " + profileTblName + " (uid,user_type,user_nature,fullname,mobile_number,email_id," +
                "country,state,pincode,address,image,logo,display_id,linked_by,sync,last_updated_on) " +
                "values('" + uid + "','" + user_type + "','" + user_nature + "','" + fullname + "','" + mobile_number + "','" + email_id + "','" + country + "','" +
                state + "','" + pincode + "','" + address + "','" + image + "','" + logo + "','" + display_id + "','" + linked_by + "','" + sync + "','" + last_updated_on + "');";

        try {
            Log.i("sql1=", insert_profile);
            db.execSQL(insert_profile);
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
        }
        db.close();


    }


    public void insertReportGenerate(double sale_total, String sale_top_1_name, double sale_top_1_value,
                                     String sale_top_2_name, double sale_top_2_value, String sale_top_3_name,
                                     double sale_top_3_value, String sale_top_4_name, double sale_top_4_value,
                                     String purchase_top_1_name, double purchase_top_1_value, String purchase_top_2_name,
                                     double purchase_top_2_value, String purchase_top_3_name, double purchase_top_3_value,
                                     String purchase_top_4_name, double purchase_top_4_value, double purchase_total) {

        mOpenHelper = new DatabaseHelper(context);
        db = mOpenHelper.getWritableDatabase();
        String insert_report_generate = "insert into " + report_generateTblName + " (sale_total, sale_top_1_name, " +
                "sale_top_1_value, sale_top_2_name, sale_top_2_value, sale_top_3_name, sale_top_3_value, " +
                "sale_top_4_name, sale_top_4_value, purchase_top_1_name, purchase_top_1_value, " +
                "purchase_top_2_name, purchase_top_2_value, purchase_top_3_name, purchase_top_3_value," +
                " purchase_top_4_name, purchase_top_4_value,purchase_total) " +
                "values('" + sale_total + "','" + sale_top_1_name + "','" + sale_top_1_value + "','" + sale_top_2_name + "','" + sale_top_2_value +
                "','" + sale_top_3_name + "','" + sale_top_3_value + "','" + sale_top_4_name + "','" + sale_top_4_value + "','" + purchase_top_1_name +
                "','" + purchase_top_1_value + "','" + purchase_top_2_name + "','" + purchase_top_2_value + "','" + purchase_top_3_name +
                "','" + purchase_top_3_value + "','" + purchase_top_4_name + "','" + purchase_top_4_value + "','" + purchase_total + "');";

        try {
            Log.i("sql1=", insert_report_generate);
            db.execSQL(insert_report_generate);
        } catch (Exception e) {
            Log.d("sqlite Exception varsha", e.getMessage());
        }
        db.close();


    }


    private void showCommodityCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id", "category", "web_commodity_id", "practice_name", "price_status", "amount", "uom", "stock", "hsncode",
                "image", "gst", "cgst", "igst", "sgst", "discount", "last_updated_on", "sync"};
        Cursor cur = db.query(commodityTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Row Commodity counts", String.valueOf(num));

        db.close();
    }

    void showProfileCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"profile_id"};
        Cursor cur = db.query(profileTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Row Profile counts", String.valueOf(num));

        db.close();
    }

    void showProfile1Count() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"profile1_id"};
        Cursor cur = db.query(profile1TblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Row Profile1 counts", String.valueOf(num));

        db.close();
    }

    public void showUsersBuyerCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id"};
        Cursor cur = db.query(user_buyerTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("User Buyer Count", String.valueOf(num));

        db.close();
    }

    public void showUsersSellerCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id"};
        Cursor cur = db.query(user_sellerTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("User Seller Count", String.valueOf(num));

        db.close();
    }

    public void showInvoiceCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id", "web_invoice", "invoice_display_id", "amount", "total_amount", "paid_status", "sync", "last_updated_on"};
        Cursor cur = db.query(invoiceTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Invoice Counts", String.valueOf(num));

        db.close();
    }

    public void showOrderCommodityCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id", "web_order_commodity_id", "web_purchase_commodity_id", "commodity_status", "invoice_id", "name", "qty", "amount",
                "discount", "uom", "gst", "cgst", "igst", "sgst", "hsncode", "category", "price_status", "chapter", "schedule", "image", "sync", "last_updated_on"};
        Cursor cur = db.query(order_commodityTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Order Commodity Count", String.valueOf(num));

        db.close();
    }

    public void showPurchaseInvoiceCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id"};
        Cursor cur = db.query(purchase_invoiceTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Purchase Invoice Count", String.valueOf(num));

        db.close();
    }

    public void showBuyerDetailsCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id"};
        Cursor cur = db.query(user_buy_detailsTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        Log.e("Buyer Details count", String.valueOf(num));

        db.close();
    }

    public void showUserSellDetailsCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id", "web_sell_id", "user_sell_to", "invoice_id", "template", "latitude", "longitude", "time", "sync", "last_updated_on"};
        Cursor cur = db.query(user_sell_detailsTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        cur.moveToFirst();
        Log.e("Sellers Details Count", String.valueOf(num));
        db.close();

    }

    public void showGenerateReportsCount() {

        db = mOpenHelper.getReadableDatabase();
        String col[] = {"id"};
        Cursor cur = db.query(report_generateTblName, col, null, null, null, null, null);
        Integer num = cur.getCount();
        cur.moveToFirst();
        Log.e("Row Generate counts:", String.valueOf(num));
        db.close();

    }


    public ArrayList<Commodity> getAllCommodities() {
        ArrayList<Commodity> array_list = new ArrayList<Commodity>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from commodity", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            Commodity commodity = new Commodity();

            commodity.setCategory(Integer.parseInt(res.getString(1)));
            commodity.setWebCommodityId(Integer.parseInt(res.getString(2)));
            commodity.setName(res.getString(3));
            commodity.setPriceStatus(Integer.parseInt(res.getString(4)));
            commodity.setAmount(Double.parseDouble(res.getString(5)));
            commodity.setUom(res.getString(6));
            commodity.setStock(Integer.parseInt(res.getString(7)));
            commodity.setHsncode(res.getString(8));
            commodity.setImage(res.getString(9));
            commodity.setGst(Double.parseDouble(res.getString(10)));
            commodity.setCgst(Double.parseDouble(res.getString(11)));
            commodity.setIgst(Double.parseDouble(res.getString(12)));
            commodity.setSgst(Double.parseDouble(res.getString(13)));
            commodity.setDiscount(Double.parseDouble(res.getString(14)));
            commodity.setMerchantId((res.getString(17)));
            commodity.setChapter(res.getString(18));
            commodity.setSchedule(res.getString(19));

            array_list.add(commodity);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<UserBuyer> getAllBuyers() {
        ArrayList<UserBuyer> array_list = new ArrayList<UserBuyer>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user_buyer", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            UserBuyer userBuyer = new UserBuyer();

            userBuyer.setId(Integer.parseInt(res.getString(0)));
            userBuyer.setUserBuyerId(Integer.parseInt(res.getString(1)));
            userBuyer.setAddedFrom(Integer.parseInt(res.getString(2)));
            userBuyer.setName(res.getString(3));
            userBuyer.setAddress(res.getString(4));
            userBuyer.setEmailId(res.getString(5));
            userBuyer.setMobileNum(res.getString(6));
            userBuyer.setState(Integer.parseInt(res.getString(7)));
            userBuyer.setCountry(Integer.parseInt(res.getString(8)));
            userBuyer.setSync(Integer.parseInt(res.getString(9)));
            userBuyer.setDisplayId(res.getString(11));


            array_list.add(userBuyer);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<UserSeller> getAllSellers() {
        ArrayList<UserSeller> array_list = new ArrayList<UserSeller>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user_seller", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            UserSeller userSeller = new UserSeller();

            userSeller.setId(Integer.parseInt(res.getString(0)));
            userSeller.setUserSellerId(Integer.parseInt(res.getString(1)));
            userSeller.setAddedFrom(Integer.parseInt(res.getString(2)));
            userSeller.setName(res.getString(3));
            userSeller.setAddress(res.getString(4));
            userSeller.setEmailId(res.getString(5));
            userSeller.setMobileNum(res.getString(6));
            userSeller.setState(Integer.parseInt(res.getString(7)));
            userSeller.setCountry(Integer.parseInt(res.getString(8)));
            userSeller.setSync(Integer.parseInt(res.getString(9)));
            userSeller.setDisplayId(res.getString(11));

            array_list.add(userSeller);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<ReportGenerate> getAllReports() {
        ArrayList<ReportGenerate> array_list = new ArrayList<ReportGenerate>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from report_generate", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            ReportGenerate reportGenerate = new ReportGenerate();

            reportGenerate.setSaleTop1Name(res.getString(2));
            reportGenerate.setSaleTop1Value(Double.parseDouble(res.getString(3)));
            reportGenerate.setSaleTop2Name(res.getString(4));
            reportGenerate.setSaleTop2Value(Double.parseDouble(res.getString(5)));
            reportGenerate.setSaleTop3Name(res.getString(6));
            reportGenerate.setSaleTop3Value(Double.parseDouble(res.getString(7)));
            reportGenerate.setSaleTop4Name(res.getString(8));
            reportGenerate.setSaleTop4Value(Double.parseDouble(res.getString(9)));
            reportGenerate.setPurchaseTop1Name(res.getString(10));
            reportGenerate.setPurchaseTop1Value(Double.parseDouble(res.getString(11)));
            reportGenerate.setPurchaseTop2Name(res.getString(12));
            reportGenerate.setPurchaseTop2Value(Double.parseDouble(res.getString(13)));
            reportGenerate.setPurchaseTop3Name(res.getString(14));
            reportGenerate.setPurchaseTop3Value(Double.parseDouble(res.getString(15)));
            reportGenerate.setPurchaseTop4Name(res.getString(16));
            reportGenerate.setPurchaseTop4Value(Double.parseDouble(res.getString(17)));

            array_list.add(reportGenerate);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Profile> getAllProfile() {
        ArrayList<Profile> array_list = new ArrayList<Profile>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from profile", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Profile profile = new Profile();
            profile.setUid(Integer.parseInt(res.getString(1)));
            profile.setUser_type(Integer.parseInt(res.getString(2)));
            profile.setUser_nature(Integer.parseInt(res.getString(3)));
            profile.setFullname(res.getString(4));
            profile.setMobile_number(res.getString(5));
            profile.setEmailid(res.getString(6));
            profile.setCountry(Integer.parseInt(res.getString(7)));
            profile.setState(Integer.parseInt(res.getString(8)));
            profile.setPincode(res.getString(9));
            profile.setAddress(res.getString(10));
            profile.setImage(res.getString(11));
            profile.setLogo(res.getString(12));
            profile.setDisplay_id(res.getString(13));
            profile.setLinked_by(Integer.parseInt(res.getString(14)));


            array_list.add(profile);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<UserSellDetails> getAllSellersDetails() {
        ArrayList<UserSellDetails> array_list = new ArrayList<UserSellDetails>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user_sell_details", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            UserSellDetails userSellerDetails = new UserSellDetails();

            userSellerDetails.setId(Integer.parseInt(res.getString(0)));
            userSellerDetails.setWebSellId(Integer.parseInt(res.getString(1)));
            userSellerDetails.setUserSellTo(Integer.parseInt(res.getString(2)));
            userSellerDetails.setInvoiceID(Integer.parseInt(res.getString(3)));
            userSellerDetails.setTemplate(Integer.parseInt(res.getString(4)));
            userSellerDetails.setLatitude(res.getString(5));
            userSellerDetails.setLongitude(res.getString(6));
            userSellerDetails.setTime(res.getString(7));
            userSellerDetails.setSync(Integer.parseInt(res.getString(8)));
            userSellerDetails.setAddress(res.getString(10));
            userSellerDetails.setUserSellToType(Integer.parseInt(res.getString(11)));

            array_list.add(userSellerDetails);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<UserBuyDetails> getAllBuyerDetails() {
        ArrayList<UserBuyDetails> array_list = new ArrayList<UserBuyDetails>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from user_buy_details", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            UserBuyDetails userBuyDetails = new UserBuyDetails();

            userBuyDetails.setId(Integer.parseInt(res.getString(0)));
            userBuyDetails.setWebBuyId(Integer.parseInt(res.getString(1)));
            userBuyDetails.setUserBuyFrom(Integer.parseInt(res.getString(2)));
            userBuyDetails.setSelfPurchase(Integer.parseInt(res.getString(3)));
            userBuyDetails.setInvoiceID(Integer.parseInt(res.getString(4)));
            userBuyDetails.setTemplate(Integer.parseInt(res.getString(5)));
            userBuyDetails.setLatitude(res.getString(6));
            userBuyDetails.setLongitude(res.getString(7));
            userBuyDetails.setTime(res.getString(8));
            userBuyDetails.setSync(Integer.parseInt(res.getString(9)));
            userBuyDetails.setAddress(res.getString(11));
            userBuyDetails.setUserBuyFromType(Integer.parseInt(res.getString(12)));


            array_list.add(userBuyDetails);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<OrderCommodity> getAllOrderCommodities() {
        ArrayList<OrderCommodity> array_list = new ArrayList<OrderCommodity>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from order_commodity", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            OrderCommodity orderCommodity = new OrderCommodity();

            orderCommodity.setId(Integer.parseInt(res.getString(0)));
            orderCommodity.setWebOrdId(Integer.parseInt(res.getString(1)));
            orderCommodity.setWebPurId(Integer.parseInt(res.getString(2)));
            orderCommodity.setCommStatus(Integer.parseInt(res.getString(3)));
            orderCommodity.setInvoiceId(Integer.parseInt(res.getString(4)));
            orderCommodity.setCommodityName(res.getString(5));
            orderCommodity.setQty(Integer.parseInt(res.getString(6)));
            orderCommodity.setRatePerAmt(Integer.parseInt(res.getString(7)));
            orderCommodity.setDiscount(Integer.parseInt(res.getString(8)));
            orderCommodity.setUOM(res.getString(9));
            orderCommodity.setGst(Double.parseDouble(res.getString(10)));
            orderCommodity.setCgst(Double.parseDouble(res.getString(11)));
            orderCommodity.setIgst(Double.parseDouble(res.getString(12)));
            orderCommodity.setSgst(Double.parseDouble(res.getString(13)));
            orderCommodity.setHsnCode(res.getString(14));
            orderCommodity.setCategory(Integer.parseInt(res.getString(15)));
            orderCommodity.setStatusofprice(Integer.parseInt(res.getString(16)));
            orderCommodity.setChapter(res.getString(17));
            orderCommodity.setSchedule(res.getString(18));
            orderCommodity.setImage(res.getString(19));
            orderCommodity.setSync(Integer.parseInt(res.getString(20)));
            orderCommodity.setOrderCommodityId(Integer.parseInt(res.getString(22)));
            orderCommodity.setInvoice_type(Integer.parseInt(res.getString(23)));

            array_list.add(orderCommodity);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Invoice> getAllInvoice() {
        ArrayList<Invoice> array_list = new ArrayList<Invoice>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from invoice", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            Invoice invoice = new Invoice();

            invoice.setId(Integer.parseInt(res.getString(0)));
            invoice.setWebInvoiceId(Integer.parseInt(res.getString(1)));
            invoice.setInvoiceDisplayId(res.getString(2));
            invoice.setAmount(Double.parseDouble(res.getString(3)));
            invoice.setTotalAmount(Double.parseDouble(res.getString(4)));
            invoice.setPaidStatus(Integer.parseInt(res.getString(5)));
            invoice.setSync(Integer.parseInt(res.getString(6)));

            array_list.add(invoice);
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<PurchaseInvoice> getAllPurchaseInvoice() {
        ArrayList<PurchaseInvoice> array_list = new ArrayList<PurchaseInvoice>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from purchase_invoice", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            PurchaseInvoice invoice = new PurchaseInvoice();

            invoice.setId(Integer.parseInt(res.getString(0)));
            invoice.setWebPurchaseInvoiceId(Integer.parseInt(res.getString(1)));
            invoice.setInvoiceDisplayId(res.getString(2));
            invoice.setAmount(Integer.parseInt(res.getString(3)));
            invoice.setTotalAmount(Integer.parseInt(res.getString(4)));
            invoice.setPaidStatus(Integer.parseInt(res.getString(5)));
            invoice.setSync(Integer.parseInt(res.getString(6)));
            array_list.add(invoice);
            res.moveToNext();
        }
        return array_list;
    }

    public boolean updateCommodityList(String name, int stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("practice_name", name);
        contentValues.put("stock", stock);
        db.update("commodity", contentValues, "practice_name = ?", new String[]{name});
        return true;
    }

    public boolean updateInvoice(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("invoice", contentValues, null, null);
        return true;
    }

    public boolean updateOrderCommodity(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("order_commodity", contentValues, null, null);
        return true;
    }

    public boolean updatePuchaseInvoice(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("purchase_invoice", contentValues, null, null);
        return true;
    }

    public boolean updateUserBuyer(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("user_buyer", contentValues, null, null);
        return true;
    }

    public boolean updateUserBuyerDisplayId(String display_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("display_id", display_id);
        ArrayList<UserBuyer> userBuyerArrayList = getAllBuyers();
        for (int i = 0; i < userBuyerArrayList.size(); i++) {
            if (userBuyerArrayList.get(i).getSync() == 1) {
                if (userBuyerArrayList.get(i).getDisplayId().substring(0, 3).equals("Tmp")) {
                    String a = String.valueOf(userBuyerArrayList.get(i).getId());
                    db.update("user_buyer", contentValues, "id = ?", new String[]{a});
                }
            }
        }
        return true;
    }


    public boolean updateUserBuyDetails(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("user_buy_details", contentValues, null, null);
        return true;
    }

    public boolean updateUserSeller(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("user_seller", contentValues, null, null);
        return true;
    }

    public boolean updateUserSellerDisplayId(String display_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("display_id", display_id);

        ArrayList<UserSeller> userSellerArrayList = getAllSellers();
        for (int i = 0; i < userSellerArrayList.size(); i++) {
            if (userSellerArrayList.get(i).getSync() == 1) {
                if (userSellerArrayList.get(i).getDisplayId().substring(0, 3).equals("Tmp")) {
                    String a = String.valueOf(userSellerArrayList.get(i).getId());
                    db.update("user_seller", contentValues, "id = ?", new String[]{a});
                }
            }
        }
        return true;
    }

    public boolean updateUserSellDetails(int sync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sync", sync);
        db.update("user_sell_details", contentValues, null, null);
        return true;
    }


}
