package com.example.billbooking.oms.billbooking.SingletonClass;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;


/**
 * Created by VarshaDhoni on 08-11-2017.
 */

public class SingletonSession extends Application {


    private static SingletonSession instance;
    private static ProgressDialog progressDoalog;

    private SingletonSession() {
    }

    public static SingletonSession Instance() {
        if (instance == null) {
            instance = new SingletonSession();
        }
        return instance;
    }

    public void sharePdf(Activity activity, Uri uri) {
        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        try{
            activity.startActivity(share);
        }catch (Exception e){
            Log.e("share exp",e.getMessage());
        }

    }


    public void showProgress(Activity activity, String message) {

        progressDoalog = new ProgressDialog(activity);
        progressDoalog.setTitle(message);
        progressDoalog.setCancelable(false);
        progressDoalog.show();

    }

    public void hideProgress() {
        if (progressDoalog.isShowing())
            progressDoalog.dismiss();
    }


    public LatLng getLocation(LocationManager locationManager, Activity activity) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            double latti = location.getLatitude();
            double longi = location.getLongitude();
            return new LatLng(latti, longi);
        }

        return new LatLng(0.0, 0.0);
    }

    public String getPath(Uri contentUri) {
        String filePath = "";
        String wholeID = DocumentsContract.getDocumentId(contentUri);

        // Split at colon, use second item in the array
        String id = wholeID.split(":")[1];

        String[] column = {MediaStore.Images.Media.DATA};

        // where id is equal to
        String sel = MediaStore.Images.Media._ID + "=?";

        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, new String[]{id}, null);

        int columnIndex = cursor.getColumnIndex(column[0]);

        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex);
        }
        cursor.close();
        return filePath;
    }

}

