package com.example.billbooking.oms.billbooking.DashBoardFragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.R;
import com.google.android.gms.maps.model.Dash;

import java.io.File;
import java.util.ArrayList;


public class PdfViews extends Fragment {

    ListView listView;
    File dir = new File(android.os.Environment.getExternalStorageDirectory() + "/BillBookOnline/Pdfs/");
    String[] values;
    String[] filePaths;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf_views, container, false);

        listView = (ListView) view.findViewById(R.id.list);

            if (dir.exists()) {
            walkdir(dir);
            addAdapter();
        } else {
            ((DashBoard) getActivity()).gotoNoRecordFound();
        }
        return view;
    }


    public void walkdir(File dir) {
        File listFile[] = dir.listFiles();
        if (listFile == null) {
            Toast.makeText(getActivity(), "No Pdfs", Toast.LENGTH_SHORT).show();
            ((DashBoard) getActivity()).gotoReport();
        }
        values = new String[listFile.length];
        filePaths = new String[listFile.length];

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {

                if (listFile[i].isDirectory()) {// if its a directory need to get the files under that directory
                    walkdir(listFile[i]);
                } else {// add path of  files to your arraylist for later use

                    //Do what ever u want
                    //filepath.add();
                    values[i] = listFile[i].getName();
                    filePaths[i] = listFile[i].getAbsolutePath();
                }
            }
        }
    }

    void addAdapter() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, values);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;


                File file = new File(filePaths[itemPosition]);
                Uri path = FileProvider.getUriForFile(getActivity(),
                        "com.example.billbooking.oms.billbooking" + ".provider", file);

                Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
                pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                pdfOpenintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                pdfOpenintent.setDataAndType(path, "application/pdf");
                try {
                    startActivity(pdfOpenintent);
                } catch (Exception e) {
                    Log.e("Exception Viewer", e.getMessage());
                }


            }

        });


    }




}
