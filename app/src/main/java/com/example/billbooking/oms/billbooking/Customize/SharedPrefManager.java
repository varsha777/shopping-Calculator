package com.example.billbooking.oms.billbooking.Customize;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModelImage;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModelImage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by VarshaDhoni on 10/9/2017.
 */

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "fcmsharedprefdemo";
    private static final String SHARED_PREF_NAME_np = "sharedprefNumberPassword";
    private static final String MOBILE_NUMBER = "number";
    private static final String PASSWORD = "password";
    private static final String LAUNCH_SCREEN = "screen";
    private static final String SHARED_PREF_DIS_ID = "sharedprefdisplayid";


    private static Context mCtx;
    private static SharedPrefManager mInstance;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getmInstance(Context context) {
        if (mInstance == null)
            mInstance = new SharedPrefManager(context);
        return mInstance;
    }


    public void storeMobileNumber(String number) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_np, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MOBILE_NUMBER, number);
        editor.apply();
    }

    public String getNumber() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_np, Context.MODE_PRIVATE);
        return sharedPreferences.getString(MOBILE_NUMBER, null);
    }

    public boolean storePassword(String password) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_np, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, password);
        editor.apply();
        return true;
    }

    public String getPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_np, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }


    public boolean storeLaunchScreen(String screen) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_np, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LAUNCH_SCREEN, screen);
        editor.apply();
        return true;
    }

    public String getLaunchScreen() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME_np, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LAUNCH_SCREEN, "0");
    }


    public boolean storeDisplayId(String displayid, String disIdKey) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_DIS_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(disIdKey, displayid);
        editor.apply();
        return true;
    }

    public String getDisplayId(String disIdKey) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_DIS_ID, Context.MODE_PRIVATE);
        return sharedPreferences.getString(disIdKey, "0");
    }

    /* Delete Shared Value After Paid Done */
    public boolean deleteKeyValueList(String deleteValue) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(deleteValue);
        editor.apply();
        return true;
    }

    public boolean deleteDisplayValueDisp(String deleteValue) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_DIS_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(deleteValue);
        editor.apply();
        return true;
    }


    /*Text Point Of Sales Methods */

    public void getAllKeyValues() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.e("map values varsha", entry.getKey() + ": " + entry.getValue().toString());

        }
    }

    public void storeTextPosArray(ArrayList<PosModel> number, String displayId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int keyIndx = 0;
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values varsha", entry.getKey() + ": " + entry.getValue().toString());
            keyIndx++;
        }

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString("PosTxt" + keyIndx, set.toString());
        storeDisplayId(displayId, "PosTxtDis" + keyIndx);
        editor.apply();
    }

    public ArrayList<PosModel> getTextPosArray(String pos) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        ArrayList<PosModel> value = new ArrayList<PosModel>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        //Log.e("Shared Values", String.valueOf(allEntries.get("some_name" + pos)));
        allEntries.get(pos);

        String savedValue = sharedPreferences.getString(pos, null);
        //Log.e("shares", savedValue);

        String json = savedValue;
        PosModel[] respone = new Gson().fromJson(json, PosModel[].class);

        if (respone != null) {

            for (PosModel s : respone) {

                value.add(s);
            }


        }

        //   Log.e("Sharedhghgj" + value.size(), "");
        return value;
    }

    public int getTextPosArray() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences("fcmsharedprefdemo", Context.MODE_PRIVATE);

        Map<String, ?> allEntries = sharedPreferences.getAll();
        int length = allEntries.size();
        return length;
    }

    public void updateTextPosArray(ArrayList<PosModel> number, String position) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString(position, set.toString());
        editor.apply();
    }

    /*Image Point Of Sales Methods */

    public void storeImagePosArray(ArrayList<PosModelImage> number, String displayId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int keyIndx = 0;
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            keyIndx++;
        }

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString("PosImg" + keyIndx, set.toString());
        storeDisplayId(displayId, "PosImgDis" + keyIndx);
        editor.apply();
    }

    public ArrayList<PosModelImage> getImagePosArray(String pos) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        ArrayList<PosModelImage> value = new ArrayList<PosModelImage>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        //Log.e("Shared Values", String.valueOf(allEntries.get("some_name" + pos)));
        allEntries.get(pos);

        String savedValue = sharedPreferences.getString(pos, null);
        //Log.e("shares", savedValue);

        String json = savedValue;
        PosModelImage[] respone = new Gson().fromJson(json, PosModelImage[].class);

        if (respone != null) {

            for (PosModelImage s : respone) {

                value.add(s);
            }


        }

        //   Log.e("Sharedhghgj" + value.size(), "");
        return value;
    }


    public void updateImagePosArray(ArrayList<PosModelImage> number, String position) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString(position, set.toString());
        editor.apply();
    }

    /* Text Point of Purchases*/

    public void storeTextPopArray(ArrayList<PopModel> number, String displayId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int keyIndx = 0;
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            keyIndx++;
        }

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString("PopTxt" + keyIndx, set.toString());
        storeDisplayId(displayId, "PopTxtDis" + keyIndx);
        editor.apply();
    }

    public ArrayList<PopModel> getTextPopArray(String pos) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        ArrayList<PopModel> value = new ArrayList<PopModel>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        //Log.e("Shared Values", String.valueOf(allEntries.get("some_name" + pos)));
        allEntries.get(pos);

        String savedValue = sharedPreferences.getString(pos, null);
        //Log.e("shares", savedValue);

        String json = savedValue;
        PopModel[] respone = new Gson().fromJson(json, PopModel[].class);

        if (respone != null) {

            for (PopModel s : respone) {

                value.add(s);
            }


        }

        //   Log.e("Sharedhghgj" + value.size(), "");
        return value;
    }

    public void updateTextPopArray(ArrayList<PopModel> number, String position) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString(position, set.toString());
        editor.apply();
    }


    /* Image Point of Purchases */

    public void storeImagePopArray(ArrayList<PopModelImage> number, String displayId) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        int keyIndx = 0;
        Map<String, ?> allEntries = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            //Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            keyIndx++;
        }

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString("PopImg" + keyIndx, set.toString());
        storeDisplayId(displayId, "PopImgDis" + keyIndx);
        editor.apply();
    }

    public ArrayList<PopModelImage> getImagePopArray(String pos) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        ArrayList<PopModelImage> value = new ArrayList<PopModelImage>();
        Map<String, ?> allEntries = sharedPreferences.getAll();
        //Log.e("Shared Values", String.valueOf(allEntries.get("some_name" + pos)));
        allEntries.get(pos);

        String savedValue = sharedPreferences.getString(pos, null);
        //Log.e("shares", savedValue);

        String json = savedValue;
        PopModelImage[] respone = new Gson().fromJson(json, PopModelImage[].class);

        if (respone != null) {

            for (PopModelImage s : respone) {

                value.add(s);
            }


        }

        //   Log.e("Sharedhghgj" + value.size(), "");
        return value;
    }

    public void updateImagePopArray(ArrayList<PopModelImage> number, String position) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayList<String> set = new ArrayList<String>();
        for (int i = 0; i < number.size(); i++) {
            Gson gson = new Gson();
            String json = gson.toJson(number.get(i));
            set.add(json);
        }
        editor.putString(position, set.toString());
        editor.apply();
    }


}
