package com.example.billbooking.oms.billbooking.DatabaseFiles;

/**
 * Created by OMS Laptop 3 on 11-01-2018.
 */

public class TablesDb {

    static final String commodityTblName = "commodity";
    static final String invoiceTblName = "invoice";
    static final String master_commodityTblName = "master_commodity";
    static final String mata_dataTblName = "mata_data";
    static final String order_commodityTblName = "order_commodity";
    static final String profileTblName = "profile";
    static final String profile1TblName = "profile1";
    static final String purchase_invoiceTblName = "purchase_invoice";
    static final String report_generateTblName = "report_generate";
    static final String user_buy_detailsTblName = "user_buy_details";
    static final String user_buyerTblName = "user_buyer";
    static final String user_sell_detailsTblName = "user_sell_details";
    static final String user_sellerTblName = "user_seller";


    static final String commodityTable = "CREATE TABLE commodity (id INTEGER PRIMARY KEY,category INTEGER,web_commodity_id INTEGER," +
            "practice_name TEXT,price_status INTEGER,amount REAL,uom TEXT,stock INTEGER,hsncode TEXT, image TEXT, gst REAL," +
            " cgst REAL, igst REAL, sgst REAL, discount REAL, sync INTEGER,last_updated_on DATETIME,merchantid Text,chapter TEXT,schedule TEXT );";

    static final String invoice = "CREATE TABLE invoice (id INTEGER, web_invoice INTEGER, invoice_display_id  TEXT, " +
            "amount REAL, total_amount REAL, paid_status INTEGER, sync INTEGER, last_updated_on DATETIME, PRIMARY KEY(id));";

    static final String master_commodity = "CREATE TABLE master_commodity (id INTEGER, web_master_commodity_id INTEGER, " +
            "master_commodity_hsncode TEXT, master_commodity_name TEXT, master_commodity_gst TEXT, master_commodity_imagepath TEXT," +
            " master_commodity_cgst TEXT, master_commodity_sgst TEXT, master_commodity_igst TEXT, master_commodity_category INTEGER," +
            " master_commodity_addedby INTEGER, master_commodity_addedon TEXT, master_commodity_active INTEGER," +
            " master_commodity_chapter TEXT, master_commodity_schedule TEXT, sync INTEGER, last_updated_on DATETIME, PRIMARY KEY(id) );";

    static final String mata_data = "CREATE TABLE mata_data ( id INTEGER, web_metadata_id INTEGER, invoice_type INTEGER, " +
            "sync INTEGER, last_update_on DATETIME, PRIMARY KEY(id) );";

    static final String order_commodity = "CREATE TABLE order_commodity (id INTEGER, web_order_commodity_id INTEGER, " +
            "web_purchase_commodity_id INTEGER, commodity_status INTEGER, invoice_id INTEGER, name TEXT, qty REAL, amount REAL, " +
            "discount REAL, uom TEXT, gst REAL, cgst REAL, igst REAL, sgst REAL, hsncode TEXT, category INTEGER," +
            " price_status INTEGER, chapter TEXT, schedule TEXT, image TEXT, sync INTEGER, last_updated_on DATETIME,order_commodity_id INTEGER,invoice_type TEXT,PRIMARY KEY(id) );";

    static final String profile = "CREATE TABLE profile (profile_id INTEGER PRIMARY KEY,uid INTEGER ,user_type INTEGER,user_nature INTEGER," +
            "fullname TEXT, mobile_number TEXT, email_id TEXT, country INTEGER, state INTEGER, pincode TEXT, address TEXT, image TEXT," +
            " logo TEXT, display_id TEXT, linked_by INTEGER, sync INTEGER, last_updated_on DATETIME );";

    static final String profile1 = "CREATE TABLE profile1 ( profile1_id INTEGER, PAN TEXT, CIN TEXT, IEN TEXT, other_name TEXT," +
            " other_value TEXT, gst_reg_num TEXT, latitude TEXT, longitude TEXT, IMEI TEXT, sync INTEGER, last_updated_on DATETIME, " +
            "adhar_num TEXT, PRIMARY KEY(profile1_id) );";

    static final String purchase_invoice = "CREATE TABLE purchase_invoice ( id INTEGER, web_purchase_invoice_id INTEGER, " +
            "invoice_display_id TEXT, amount REAL, total_amount REAL, paid_status INTEGER, sync INTEGER, last_updated_on DATETIME," +
            " PRIMARY KEY(id) );";

    static final String report_generate = "CREATE TABLE report_generate ( id INTEGER, sale_total REAL, sale_top_1_name TEXT, " +
            "sale_top_1_value REAL, sale_top_2_name TEXT, sale_top_2_value REAL, sale_top_3_name TEXT, sale_top_3_value REAL, " +
            "sale_top_4_name TEXT, sale_top_4_value REAL, purchase_top_1_name TEXT, purchase_top_1_value REAL, " +
            "purchase_top_2_name TEXT, purchase_top_2_value REAL, purchase_top_3_name TEXT, purchase_top_3_value REAL," +
            " purchase_top_4_name TEXT, purchase_top_4_value REAL,purchase_total REAL );";

    static final String user_buy_details = "CREATE TABLE user_buy_details ( id INTEGER, web_buy_id INTEGER, " +
            "user_buy_from INTEGER, self_purchase INTEGER, invoice_id INTEGER, template INTEGER, latitude TEXT, longitude TEXT, " +
            "time TEXT, sync INTEGER, last_updated_on DATETIME,address TEXT,user_buy_from_type INTEGER, PRIMARY KEY(id));";

    static final String user_buyer = "CREATE TABLE user_buyer ( id INTEGER, user_buyer_id INTEGER, added_from INTEGER, name TEXT," +
            " address TEXT, emailid TEXT, mobile_number TEXT, state INTEGER, country INTEGER, sync INTEGER, last_update_on DATETIME, " +
            "display_id TEXT, PRIMARY KEY(id) );";

    static final String user_sell_details = "CREATE TABLE user_sell_details ( id INTEGER, web_sell_id INTEGER, user_sell_to INTEGER, " +
            "invoice_id INTEGER, template INTEGER, latitude TEXT, longitude TEXT, time TEXT, sync INTEGER, last_updated_on DATETIME," +
            " address TEXT,user_sell_to_type INTEGER,PRIMARY KEY(id)  )";

    static final String user_seller = "CREATE TABLE user_seller ( id INTEGER, user_seller_id INTEGER, added_from INTEGER," +
            " name TEXT, address TEXT, emailid TEXT, mobile_number TEXT, state INTEGER, country INTEGER, sync INTEGER," +
            " last_updated_on DATETIME, display_id TEXT, PRIMARY KEY(id) )";


}
