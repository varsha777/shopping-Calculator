package com.example.billbooking.oms.billbooking.Sync;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectListing;
import com.example.billbooking.oms.billbooking.DBModel.Invoice;
import com.example.billbooking.oms.billbooking.DBModel.OrderCommodity;
import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.PurchaseInvoice;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyer;
import com.example.billbooking.oms.billbooking.DBModel.UserSellDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserSeller;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ApiService;
import com.example.billbooking.oms.billbooking.RetrofitFiles.RetrofitConstant;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ServerResponse;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by OMS Laptop 3 on 23-01-2018.
 */

public class SendAllValues {

    private Context context;
    private DatabaseHelper dbh;

    /*AmazonS3Client s3;
    BasicAWSCredentials credentials;
    TransferUtility transferUtility;
    TransferObserver observer;*/

    String key = "AKIAJ3TXMLR4FPL7V3WQ";
    String secret = "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";
    String bucketName = "elasticbeanstalk-ap-south-1-436895957471";

    public SendAllValues(Context context) {
        this.context = context;
        sendPos();
    }

    private void sendPos() {

        ApiService requestInterface = RetrofitConstant.getApiServices();

        dbh = new DatabaseHelper(context);
        SQLiteDatabase db = dbh.getReadableDatabase();
        Gson gson = new GsonBuilder().create();

        dbh.updateUserBuyerDisplayId("0");
        dbh.updateUserSellerDisplayId("0");

        /*dbh.showOrderCommodityCount();
        dbh.showUsersSellerCount();
        dbh.showUserSellDetailsCount();
        dbh.showUsersBuyerCount();
        dbh.showBuyerDetailsCount();
        dbh.showInvoiceCount();
        dbh.showPurchaseInvoiceCount();*/

        /*SELLERS DETAILS*/
        ArrayList<UserSellDetails> userSellDetailsArrayList = dbh.getAllSellersDetails();
        ArrayList<UserSellDetails> userSellDetails1 = new ArrayList<>();
        for (int i = 0; i < userSellDetailsArrayList.size(); i++) {
            if (userSellDetailsArrayList.get(i).getSync() == 1) {
                userSellDetails1.add(userSellDetailsArrayList.get(i));
            }
        }
        JsonArray userSellJsonDetailsArray = gson.toJsonTree(userSellDetails1).getAsJsonArray();
        String userSellDetails = userSellJsonDetailsArray.toString();


        /*ORDER COMMODITIES*/
        ArrayList<OrderCommodity> orderCommodityArrayList = dbh.getAllOrderCommodities();
        ArrayList<OrderCommodity> orderCommodity = new ArrayList<>();
        for (int i = 0; i < orderCommodityArrayList.size(); i++) {
            if (orderCommodityArrayList.get(i).getSync() == 1) {
                // Log.e("Commodity Name", orderCommodityArrayList.get(i).getCommodityName());
                orderCommodity.add(orderCommodityArrayList.get(i));
            }
        }
        JsonArray userOrderCommodityJsonArray = gson.toJsonTree(orderCommodity).getAsJsonArray();
        String userOrderCommodity = userOrderCommodityJsonArray.toString();

        /*SELLERS*/
        ArrayList<UserSeller> userSellerArrayList = dbh.getAllSellers();
        ArrayList<UserSeller> userSeller = new ArrayList<>();
        for (int i = 0; i < userSellerArrayList.size(); i++) {
            if (userSellerArrayList.get(i).getSync() == 1) {
                userSeller.add(userSellerArrayList.get(i));
            }
        }
        JsonArray userSellJsonArray = gson.toJsonTree(userSeller).getAsJsonArray();
        String userSell = userSellJsonArray.toString();


        /*INVOICE*/
        ArrayList<Invoice> invoiceArrayList = dbh.getAllInvoice();
        ArrayList<Invoice> invoice = new ArrayList<>();
        for (int i = 0; i < invoiceArrayList.size(); i++) {
            if (invoiceArrayList.get(i).getSync() == 1) {
                invoice.add(invoiceArrayList.get(i));
            }
        }
        JsonArray userInvoiceJsonArray = gson.toJsonTree(invoice).getAsJsonArray();
        String userInvoice = userInvoiceJsonArray.toString();


        /*PURCHASE INVOICE*/
        ArrayList<PurchaseInvoice> purchaseInvoiceArrayList = dbh.getAllPurchaseInvoice();
        ArrayList<PurchaseInvoice> purchaseInvoice = new ArrayList<>();
        for (int i = 0; i < purchaseInvoiceArrayList.size(); i++) {
            if (purchaseInvoiceArrayList.get(i).getSync() == 1) {
                purchaseInvoice.add(purchaseInvoiceArrayList.get(i));
            }
        }
        JsonArray userPurchaseInvoiceJsonArray = gson.toJsonTree(purchaseInvoice).getAsJsonArray();
        String userPurchaseInvoice = userPurchaseInvoiceJsonArray.toString();


        /*BUYERS DETAILS*/
        ArrayList<UserBuyDetails> userBuyDetailsArrayList = dbh.getAllBuyerDetails();
        ArrayList<UserBuyDetails> userBuyDetails = new ArrayList<>();
        for (int i = 0; i < userBuyDetailsArrayList.size(); i++) {
            if (userBuyDetailsArrayList.get(i).getSync() == 1) {
                userBuyDetails.add(userBuyDetailsArrayList.get(i));
            }
        }
        JsonArray userBuyDetailsJsonArray = gson.toJsonTree(userBuyDetails).getAsJsonArray();
        String userBuyerDetails = userBuyDetailsJsonArray.toString();


        /*BUYERS*/
        ArrayList<UserBuyer> userBuyerArrayList = dbh.getAllBuyers();
        ArrayList<UserBuyer> userBuyer1 = new ArrayList<>();
        for (int i = 0; i < userBuyerArrayList.size(); i++) {
            if (userBuyerArrayList.get(i).getSync() == 1) {
                userBuyer1.add(userBuyerArrayList.get(i));
            }
        }
        JsonArray userBuyerjsonArray = gson.toJsonTree(userBuyer1).getAsJsonArray();
        String userBuyer = userBuyerjsonArray.toString();


        /*User Uid*/
        ArrayList<Profile> userProfile = dbh.getAllProfile();
        int uId = userProfile.get(0).getUid();
        String uName = userProfile.get(0).getFullname();

        SendDataToServer sendDataToServer = new SendDataToServer();

        sendDataToServer.setuId(uId);
        sendDataToServer.setuName(uName);
        sendDataToServer.setOrderCommodity(userOrderCommodity);
        sendDataToServer.setUserSellDetails(userSellDetails);
        sendDataToServer.setUserSeller(userSell);
        sendDataToServer.setInvoice(userInvoice);
        sendDataToServer.setPurchaseInvoice(userPurchaseInvoice);
        sendDataToServer.setUserBuyDetails(userBuyerDetails);
        sendDataToServer.setUserBuyer(userBuyer);

        Log.e("User Id", String.valueOf(uId));
        Log.e("userOrderCommodityArray", userOrderCommodity);
        Log.e("userSellDetailsArray", userSellDetails);
        Log.e("userSellerArray", userSell);
        Log.e("userInvoiceArray", userInvoice);
        Log.e("userPurchasInvoiceArray", userPurchaseInvoice);
        Log.e("userBuyerDetailsArray", userBuyerDetails);
        Log.e("userBuyerArray", userBuyer);
        // Log.e("Final values", sendDataToServer.toString());

        //uploadFile();

        Call<ServerResponse> response = requestInterface.operationSync(sendDataToServer);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                Toast.makeText(context, "enter into Retrofit Class", Toast.LENGTH_SHORT).show();
                if (resp.getStatus().equals("SUCCESS")) {
                    dbh.updateInvoice(0);
                    dbh.updateOrderCommodity(0);
                    dbh.updatePuchaseInvoice(0);
                    dbh.updateUserBuyDetails(0);
                    dbh.updateUserBuyer(0);
                    dbh.updateUserSeller(0);
                    dbh.updateUserSellDetails(0);
                } else {
                    Toast.makeText(context, "Sync Fail", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Toast.makeText(context, "enter into fail Retrofit Class", Toast.LENGTH_SHORT).show();
                Log.e(RetrofitConstant.TAG, "failed" + t.getMessage());
            }
        });
    }

    int value = 0;

   /* boolean uploadImage(String path, String uid) {


        credentials = new BasicAWSCredentials(key, secret);
        s3 = new AmazonS3Client(credentials);
        transferUtility = new TransferUtility(s3, context);


        //File file = new File("file:///storage/emulated/0/BillBooking/Sales/Commodity-5658.jpg");
        File file = new File(Environment.getExternalStorageDirectory(),
                "/123.png");

        if (!file.exists()) {
            Log.e("Name ", file.getName());
            Toast.makeText(context, "File Not Found!", Toast.LENGTH_SHORT).show();
            return false;
        }
        s3.putObject(bucketName, "images/", file);
        observer = transferUtility.upload(
                "images",
                file.getName(),
                file
        );

        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {

                if (state.COMPLETED.equals(observer.getState())) {

                    Toast.makeText(context, "File Upload Complete", Toast.LENGTH_SHORT).show();
                    value = 1;
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {

                Toast.makeText(context, "" + ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (value == 1) {
            value = 0;
            return true;
        }

        return false;
    }*/

    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;

    private void uploadFile() {

        //AWSMobileClient.getInstance().initialize(context).execute();
        BasicAWSCredentials credentials = new BasicAWSCredentials(key, secret);
        AmazonS3Client s3Client = new AmazonS3Client(credentials);

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(context)
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();

        File file = new File(Environment.getExternalStorageDirectory(),
                "/123.png");

        if (!file.exists()) {
            Log.e("Name ", file.getName());
            Toast.makeText(context, "File Not Found!", Toast.LENGTH_SHORT).show();
            return;
        }

// "jsaS3" will be the folder that contains the file
        TransferObserver uploadObserver =
                transferUtility.upload("images/" + file.getName(), file);

        uploadObserver.setTransferListener(new TransferListener() {

            @Override
            public void onStateChanged(int id, TransferState state) {
                if (TransferState.COMPLETED == state) {
                    // Handle a completed download.
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                int percentDone = (int) percentDonef;
            }

            @Override
            public void onError(int id, Exception ex) {
                // Handle errors
            }

        });

// If your upload does not trigger the onStateChanged method inside your
// TransferListener, you can directly check the transfer state as shown here.
        if (TransferState.COMPLETED == uploadObserver.getState()) {
            // Handle a completed upload.
            Log.e("Uploaded", "");
        }
    }


    public void upload() {

        CognitoCachingCredentialsProvider credentialsProvider;
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "ap-south-1:8TcCAcR8SUputPZQwZUlzVRJ8eUbqN0OO8sVOpTr", // Identity Pool ID
                Regions.AP_SOUTH_1 // Region
        );
        File file = new File(Environment.getExternalStorageDirectory(),
                "/123.png");

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        TransferUtility transferUtility = new TransferUtility(s3, context);
        final TransferObserver observer = transferUtility.upload(
                bucketName,  //this is the bucket name on S3
                "images", //this is the path and name
                file, //path to the file locally
                CannedAccessControlList.PublicRead //to make the file public
        );
        observer.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {
                if (state.equals(TransferState.COMPLETED)) {
                    //Success
                } else if (state.equals(TransferState.FAILED)) {
                    //Failed
                }

            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {

            }
        });
    }


}
