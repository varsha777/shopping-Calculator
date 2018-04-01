package com.example.billbooking.oms.billbooking.DashBoardFragments;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyer;
import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;


public class QrCode extends Fragment {

    ImageView qrCode;
    TextView qrTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_code, container, false);

        ((DashBoard) getActivity()).getSupportActionBar().setTitle("");

        qrCode = (ImageView) view.findViewById(R.id.qrcode_image);
        qrTv = (TextView) view.findViewById(R.id.qrcode_textview);

        DatabaseHelper dbh = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbh.getReadableDatabase();
        ArrayList<Profile> userprofile = dbh.getAllProfile();

        String displayid = userprofile.get(0).getDisplay_id();
        qrTv.setText(displayid);
        barcodeGenerate(displayid);

        return view;
    }


    void barcodeGenerate(String text2Qr) {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qrCode.setImageBitmap(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
