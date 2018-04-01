package com.example.billbooking.oms.billbooking.Customize;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by OMS Laptop 3 on 05-02-2018.
 */

public class SendMsg extends AsyncTask<String, String, String> {
    @Override
    protected String doInBackground(String... strings) {

        String msg = strings[0];
        String number = strings[1];
        Log.e("Number+varsha" + number, "message" + msg);

        //Your authentication key
        String authkey = "190782A4KENbSPZ5a78370f";
//Multiple mobiles numbers separated by comma
        String mobiles = number;
//Sender ID,While using route4 sender id should be 6 characters long.
        String senderId = "BLBKOL";
//Your message to send, Add URL encoding here.
        String message = msg;
//define route
        String route = "0";

        URLConnection myURLConnection = null;
        URL myURL = null;
        BufferedReader reader = null;

//encoding message
        String encoded_message = URLEncoder.encode(message);

//Send SMS API
        String mainUrl = "http://api.msg91.com/api/sendhttp.php?";

//Prepare parameter string
        StringBuilder sbPostData = new StringBuilder(mainUrl);
        sbPostData.append("authkey=" + authkey);
        sbPostData.append("&mobiles=" + mobiles);
        sbPostData.append("&message=" + encoded_message);
        sbPostData.append("&route=" + route);
        sbPostData.append("&sender=" + senderId);

//final string
        mainUrl = sbPostData.toString();
        try {
            //prepare connection
            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", "" + response);

            //finally close connection
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }


}