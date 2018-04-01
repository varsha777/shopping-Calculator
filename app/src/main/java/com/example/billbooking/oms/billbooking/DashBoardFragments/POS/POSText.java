package com.example.billbooking.oms.billbooking.DashBoardFragments.POS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.Pdf.PdfPosText;
import com.example.billbooking.oms.billbooking.Customize.SendMsg;
import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DBModel.Invoice;
import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyer;
import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosAdapters.PosRecyclerAdapter;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosAdapters.PosRecyclerAdapterShow;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.R;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;


@SuppressLint("ValidFragment")
public class POSText extends Fragment implements View.OnClickListener {

    Button to, show, qrScanner, saveCommodity, paidCommodity, printCommodity;

    private RecyclerView mRecyclerView;
    private PosRecyclerAdapter mRecyclerAdapter;
    Button btnAddItem;
    ArrayList<PosModel> myList = new ArrayList<>();
    LinearLayout linearLayout;

    private RecyclerView mRecyclerView_show;
    private PosRecyclerAdapterShow mRecyclerAdapter_show;
    EditText displayId;

    IntentIntegrator qrScan;
    int check = 0;
    String position;
    DatabaseHelper dbh = new DatabaseHelper(getActivity());
    SQLiteDatabase db = null;

    double lati, longi;
    LocationManager locationManager;
    String dispId;

    @SuppressLint("ValidFragment")
    public POSText(ArrayList<PosModel> myList, String position, String displayID) {
        this.myList = myList;
        this.position = position;
        this.dispId = displayID;
        if (myList.size() != 0) {
            check = 1;

            Log.e("Check", String.valueOf(check));
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pos_text, container, false);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Point Of Sales");
        SharedPrefManager.getmInstance(getActivity()).getAllKeyValues();
        initializeViews(view);
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        LatLng latLng = SingletonSession.Instance().getLocation(locationManager, getActivity());

        this.lati = latLng.latitude;
        this.longi = latLng.longitude;

        displayId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (displayId.getText().toString().length() == 8) {

                    Toast.makeText(getActivity(), "Checking", Toast.LENGTH_SHORT).show();
                    DatabaseHelper dbh = new DatabaseHelper(getActivity());
                    SQLiteDatabase db = dbh.getReadableDatabase();
                    ArrayList<UserBuyer> userBuyerArrayList = dbh.getAllBuyers();
                    int a = 0;
                    for (int i = 0; i < userBuyerArrayList.size(); i++) {
                        if (displayId.getText().toString().equals(userBuyerArrayList.get(i).getDisplayId())) {
                            btnAddItem.setEnabled(true);
                            paidCommodity.setEnabled(true);
                            printCommodity.setEnabled(true);
                            saveCommodity.setEnabled(true);
                            show.setEnabled(true);
                            to.setEnabled(false);
                            a = 1;
                            break;
                        }
                    }
                    if (a == 1) {
                        Toast.makeText(getActivity(), "Verifird", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Check ID", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    btnAddItem.setEnabled(false);
                    paidCommodity.setEnabled(false);
                    printCommodity.setEnabled(false);
                    saveCommodity.setEnabled(false);
                    show.setEnabled(false);
                    to.setEnabled(true);
                }

            }
        });

        return view;

    }

    private void initializeViews(View view) {

        LocationManager
                service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            showDialogAlert("Alert", "TurnOn GPS", getActivity(), getResources().getDrawable(R.drawable.error));
        }

        to = (Button) view.findViewById(R.id.to_pos);
        btnAddItem = (Button) view.findViewById(R.id.add_item);
        show = (Button) view.findViewById(R.id.show);
        qrScanner = (Button) view.findViewById(R.id.qr_scan);
        saveCommodity = (Button) view.findViewById(R.id.save_commodity);
        paidCommodity = (Button) view.findViewById(R.id.paid_commodity);
        printCommodity = (Button) view.findViewById(R.id.print_commodity);
        displayId = (EditText) view.findViewById(R.id.display_id);

        btnAddItem.setEnabled(false);
        paidCommodity.setEnabled(false);
        printCommodity.setEnabled(false);
        saveCommodity.setEnabled(false);
        show.setEnabled(false);


        linearLayout = (LinearLayout) view.findViewById(R.id.layout_pos);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_pos);
        mRecyclerAdapter = new PosRecyclerAdapter(myList, getActivity());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mRecyclerView_show = (RecyclerView) view.findViewById(R.id.recycler_view_pos_show);
        mRecyclerView_show.setVisibility(View.GONE);

        qrScan = new IntentIntegrator(getActivity());
        to.setOnClickListener(this);
        btnAddItem.setOnClickListener(this);
        show.setOnClickListener(this);
        qrScanner.setOnClickListener(this);
        saveCommodity.setOnClickListener(this);
        paidCommodity.setOnClickListener(this);
        printCommodity.setOnClickListener(this);


        if (check == 1) {
            btnAddItem.setEnabled(true);
            paidCommodity.setEnabled(true);
            printCommodity.setEnabled(true);
            saveCommodity.setEnabled(true);
            show.setEnabled(true);
            displayId.setText(dispId);
        }


    }


    public void showDialogAlert(String title, String msg, Activity activity, Drawable icon) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(icon);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }


    void dialogBox() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pos_to_dialog);
        dialog.setTitle("Title...");

        Button ok = (Button) dialog.findViewById(R.id.ok_dialog);
        final EditText name = (EditText) dialog.findViewById(R.id.name);
        final EditText mobileNumber = (EditText) dialog.findViewById(R.id.mobile_number);
        final EditText address = (EditText) dialog.findViewById(R.id.address);
        final EditText email = (EditText) dialog.findViewById(R.id.emailid);

        Log.e("DisplayId " + displayId.getText().toString(), "length " + displayId.getText().toString().length());
        if (displayId.getText().toString().length() != 0) {
            Log.e("Enter", "hai");
            DatabaseHelper dbh = new DatabaseHelper(getActivity());
            SQLiteDatabase db = null;
            ArrayList<UserBuyer> userBuyerArrayList = dbh.getAllBuyers();
            for (int i = 0; i < userBuyerArrayList.size(); i++) {

                if (userBuyerArrayList.get(i).getSync() == 1) {
                    if (displayId.getText().toString().equals(userBuyerArrayList.get(i).getDisplayId())) {

                        name.setText(userBuyerArrayList.get(i).getName());
                        mobileNumber.setText(userBuyerArrayList.get(i).getMobileNum());
                        address.setText(userBuyerArrayList.get(i).getAddress());
                        email.setText(userBuyerArrayList.get(i).getEmailId());

                        name.setFocusableInTouchMode(false);
                        mobileNumber.setFocusableInTouchMode(false);
                        email.setFocusableInTouchMode(false);
                        address.setFocusableInTouchMode(false);
                        ok.setEnabled(false);

                        break;
                    }
                }
            }
        }


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (name.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (mobileNumber.getText().toString().length() < 10) {
                    Toast.makeText(getActivity(), "Enter MobileNumber", Toast.LENGTH_SHORT).show();
                } else if (address.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Address", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "Enter Email", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                    Toast.makeText(getActivity(), "Enter Valid Email", Toast.LENGTH_SHORT).show();
                } else {
                    dbh = new DatabaseHelper(getActivity());

                    Random generator = new Random();
                    int n = 10000;
                    n = generator.nextInt(n);
                    String tempDisplayId = "Tmp_" + n;
                    displayId.setText(tempDisplayId);

                    dbh.insertUserBuyer(0, 2, name.getText().toString(),
                            address.getText().toString(), email.getText().toString(), mobileNumber.getText().toString(), 0,
                            0, 1, null, tempDisplayId);
                    btnAddItem.setEnabled(true);
                    paidCommodity.setEnabled(true);
                    printCommodity.setEnabled(true);
                    saveCommodity.setEnabled(true);
                    show.setEnabled(true);
                    dialog.cancel();

                }
            }
        });

        dialog.show();
    }

    void dialogBoxPaidStatus() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.paid_or_unpaid);
        dialog.setTitle("Title...");

        Button ok = (Button) dialog.findViewById(R.id.done_paid_status);
        RadioGroup paidStatus = (RadioGroup) dialog.findViewById(R.id.paid_status);

        paidStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            RadioButton rb;

            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (radioGroup.getId()) {

                    case R.id.paid_status:
                        rb = (RadioButton) dialog.findViewById(checkedId);
                        if (rb.getId() == R.id.paid) {
                            paidMethod(1);
                            dialog.dismiss();
                        } else if (rb.getId() == R.id.unpaid) {
                            paidMethod(0);
                            dialog.dismiss();
                        }
                        break;

                }
            }
        });


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        dialog.show();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.to_pos:

                dialogBox();
                break;

            case R.id.add_item:

                PosModel posModel = new PosModel();

                posModel.setCommodity("Commodity");
                posModel.setQuantity(Integer.parseInt("10"));
                posModel.setPrice(Double.parseDouble("25"));
                posModel.setTotal(Double.parseDouble("250"));
                posModel.setStatus(1);

                myList.add(posModel);
                mRecyclerAdapter.notifyData(myList);

                break;

            case R.id.show:

                if (show.getText().toString().equals("Show")) {

                    show.setText("Back");
                    btnAddItem.setEnabled(false);
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerView_show.setVisibility(View.VISIBLE);

                    mRecyclerAdapter_show = new PosRecyclerAdapterShow(myList, getActivity(), mRecyclerView_show, linearLayout);
                    final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
                    layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecyclerView_show.setLayoutManager(layoutManager2);
                    mRecyclerView_show.setAdapter(mRecyclerAdapter_show);


                    mRecyclerAdapter_show.notifyData(myList);

                } else if (show.getText().toString().equals("Back")) {
                    show.setText("Show");
                    btnAddItem.setEnabled(true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView_show.setVisibility(View.GONE);
                    mRecyclerAdapter.notifyData(myList);

                }

                break;

            case R.id.qr_scan:

                qrScan.setPrompt("Scan a barcode");
                qrScan.forSupportFragment(this).initiateScan();
                break;

            case R.id.save_commodity:
                Toast.makeText(getActivity(), "Save List In Bills Progress", Toast.LENGTH_SHORT).show();
                if (check == 1) {
                    SharedPrefManager.getmInstance(getActivity()).updateTextPosArray(myList, position);
                    myList.clear();
                    displayId.setText("");
                    mRecyclerAdapter.notifyData(myList);
                    // Log.e("Enter Check1", "");
                } else {

                    SharedPrefManager.getmInstance(getActivity()).storeTextPosArray(myList, displayId.getText().toString());
                    myList.clear();
                    displayId.setText("");
                    mRecyclerAdapter.notifyData(myList);
                    //Log.e("Enter Check0", "");
                }
                break;

            case R.id.paid_commodity:

                if (myList.size() == 0) {
                    Toast.makeText(getActivity(), "Add Items Bill Is Empty", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getActivity(), "Paid", Toast.LENGTH_SHORT).show();
                    dialogBoxPaidStatus();
                }

                break;

            case R.id.print_commodity:

                verifyStoragePermissions(getActivity(), myList, 101);

        }

    }

    int userSellTo;
    String name, number;

    void paidMethod(int paidStatus) {


        dbh = new DatabaseHelper(getActivity());
        SingletonSession.Instance().showProgress(getActivity(), "Updating...");

        Random generator = new Random();
        int n = 100;
        n = generator.nextInt(n);
        String display = "Temp_" + n;
        double total = 0.0;
        for (int i = 0; i < myList.size(); i++) {

            total = total + myList.get(i).getTotal();

        }
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String currentTime = s.format(new Date());

        boolean result = dbh.insertInvoice(0, display, total, total, paidStatus, 1, String.valueOf(currentTime));
        if (!result) {
            Log.e("Not Inserted", "Row");
        } else {
            db = dbh.getReadableDatabase();
            this.userSellTo = getBuyerId(displayId.getText().toString());
            String address = null;


            ArrayList<Invoice> invoices = dbh.getAllInvoice();
            int id = invoices.get(invoices.size() - 1).getId();

            boolean res = dbh.insertUserSellDetails(0, userSellTo, invoices.get(invoices.size() - 1).getId(), 1, String.valueOf(lati),
                    String.valueOf(longi), String.valueOf(currentTime), 1, String.valueOf(currentTime), address, checkBuyerId(displayId.getText().toString()));

            if (res) {
                insertInOrderCommodity(invoices.get(invoices.size() - 1).getId());
            } else {
                Log.e("Failed", "Insertd user Sell Details");
            }
        }
        if (position != null) {
            SharedPrefManager.getmInstance(getActivity()).deleteKeyValueList(position);
            SharedPrefManager.getmInstance(getActivity()).deleteDisplayValueDisp(dispId);

        }


        ArrayList<UserBuyer> userBuyerArrayList = dbh.getAllBuyers();
        for (int i = 0; i < userBuyerArrayList.size(); i++) {
            if (userBuyerArrayList.get(i).getSync() == 1) {
                if (userSellTo == (userBuyerArrayList.get(i).getId())) {
                    //this.name = userBuyerArrayList.get(i).getName();
                    this.number = userBuyerArrayList.get(i).getMobileNum();
                    Log.e("Check Number", number);
                    break;
                }
            }
        }


        SingletonSession.Instance().hideProgress();
        verifyStoragePermissions(getActivity(), myList, 102);
        myList.clear();
        displayId.setText("");
        mRecyclerAdapter.notifyData(myList);
        ArrayList<Profile> profileArrayList = dbh.getAllProfile();
        this.name = profileArrayList.get(0).getFullname();

        String message = "Welcome to billbookonline.com\n" + "Your Purchase Details\n" +
                "Seller Name: " + name + "\n" + "Purchase Amount: "
                + total + " INR" + "\nDate Time: " + currentTime + "\nTHANK YOU";

        SendMsg sendMsg = new SendMsg();
        sendMsg.execute(message, number);
        Log.e("Values " + number, message);
    }

    private int getBuyerId(String s) {
        ArrayList<UserBuyer> userBuyerArrayList = dbh.getAllBuyers();
        for (int i = 0; i < userBuyerArrayList.size(); i++) {
            if (userBuyerArrayList.get(i).getSync() == 1) {
                if (s.equals(userBuyerArrayList.get(i).getDisplayId())) {
                    if (s.substring(0, 3).equals("Tmp")) {
                        return userBuyerArrayList.get(i).getId();
                    } else
                        return userBuyerArrayList.get(i).getUserBuyerId();
                }
            }
        }
        return 0;
    }

    private int checkBuyerId(String s) {
        ArrayList<UserBuyer> userBuyerArrayList = dbh.getAllBuyers();
        for (int i = 0; i < userBuyerArrayList.size(); i++) {
            if (userBuyerArrayList.get(i).getSync() == 1) {
                if (s.equals(userBuyerArrayList.get(i).getDisplayId())) {
                    if (s.substring(0, 3).equals("Tmp"))
                        return 1;
                    else
                        return 0;
                }
            }
        }
        return 1;
    }

    boolean insertInOrderCommodity(int id) {

        //Log.e("Inserted", "Insertd user Sell Details");
        int a = 0, test = 0;
        dbh.showUserSellDetailsCount();
        ArrayList<Commodity> tempItems = dbh.getAllCommodities();
        for (int i = 0; i < myList.size(); i++) {


            double discount = 0.0, gst = 0.0, cgst = 0.0, igst = 0.0, sgst = 0.0;
            String hsncode = null, image = null, uom = null, chapter = null, schedule = null;
            int category = 0, priceStatus = 0, stock = 0, orderCommodityId = 0;

            if (myList.get(i).getStatus() == 0) {
                for (int j = 0; j < tempItems.size(); j++) {
                    if (myList.get(i).getCommodity().equals(tempItems.get(j).getName())) {
                        orderCommodityId = tempItems.get(j).getWebCommodityId();
                        discount = tempItems.get(j).getDiscount();
                        gst = tempItems.get(j).getGst();
                        cgst = tempItems.get(j).getCgst();
                        igst = tempItems.get(j).getIgst();
                        sgst = tempItems.get(j).getSgst();
                        hsncode = tempItems.get(j).getHsncode();
                        image = tempItems.get(j).getImage();
                        uom = tempItems.get(j).getUom();
                        category = tempItems.get(j).getCategory();
                        priceStatus = tempItems.get(j).getPriceStatus();
                        chapter = tempItems.get(j).getChapter();
                        schedule = tempItems.get(j).getSchedule();
                        stock = tempItems.get(j).getStock();
                        orderCommodityId = tempItems.get(j).getWebCommodityId();
                        //Log.e("Commodity Stock", String.valueOf(stock));
                        test = 1;
                        break;
                    }
                }
            } else {

            }

            boolean value = dbh.insertOrderCommodity(0, 0, myList.get(i).getStatus(),
                    id, myList.get(i).getCommodity(), myList.get(i).getQuantity(),
                    myList.get(i).getPrice(), discount, uom, gst, cgst, igst, sgst, hsncode, category,
                    priceStatus, chapter, schedule, image, 1, null, orderCommodityId, 1);
            if (test == 1) {
                dbh.updateCommodityList(myList.get(i).getCommodity(), stock);
                /*ArrayList<Commodity> items = dbh.getAllCommodities();
                 Log.e("After Update Stock", String.valueOf(items.get(i).getStock()));*/

            }

            //dbh.showOrderCommodityCount();
            if (value) {
                a = 1;
            } else {
                a = 0;
                break;
            }
        }
        if (a == 1) {
            return true;
        } else return false;

    }


    /*void showPdfName() {

        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.pdf);
        dialog.setTitle("Title...");

        Button ok = (Button) dialog.findViewById(R.id.save_dialog);
        final EditText fileName = (EditText) dialog.findViewById(R.id.file_name);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fileName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "File Name Should Not Empty", Toast.LENGTH_SHORT).show();
                } else {
                    createPdf(fileName.getText().toString());
                    dialog.cancel();
                }
            }
        });

        dialog.show();
    }*/

    private void createPdf(final String s, int value, ArrayList<PosModel> posModelArrayList) {
        PdfPosText pdf = new PdfPosText();

        if (value == 101) {
            boolean result = pdf.createPDF(getActivity(), s, posModelArrayList, "/temp/");
            File file2 = new File(Environment.getExternalStorageDirectory(),
                    "/temp/" + s + ".pdf");
            final Uri path2 = FileProvider.getUriForFile(getActivity(),
                    "com.example.billbooking.oms.billbooking" + ".provider", file2);
            SingletonSession.Instance().sharePdf(getActivity(), path2);

        } else {
            boolean result = pdf.createPDF(getActivity(), s, posModelArrayList, "/BillBookOnline/Pdfs/");
            if (result) {
                Toast.makeText(getActivity(), "Created", Toast.LENGTH_SHORT).show();
                Snackbar snackbar = Snackbar
                        .make(linearLayout, "Click To View Invoice", Snackbar.LENGTH_LONG);

                File file = new File(Environment.getExternalStorageDirectory(),
                        "/BillBookOnline/Pdfs/" + s + ".pdf");

                final Uri path = FileProvider.getUriForFile(getActivity(),
                        "com.example.billbooking.oms.billbooking" + ".provider", file);

                snackbar.setAction("View", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

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
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            } else
                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public void verifyStoragePermissions(Activity activity, ArrayList<PosModel> posModelArrayList, int value) {
        // Check if we have write permission
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }

        } else {
            //Toast.makeText(activity, "Checking Compleated", Toast.LENGTH_SHORT).show();
            if (posModelArrayList.size() == 0) {
                Toast.makeText(activity, "Add Items Bill Is Empty", Toast.LENGTH_SHORT).show();
            } else {
                Random generator = new Random();
                int n = 1000;
                n = generator.nextInt(n);
                String display = "Invoice_Pdf" + n;
                createPdf(display, value, posModelArrayList);
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //Toast.makeText(getActivity(), "enter", Toast.LENGTH_SHORT).show();
        Log.e("result", "hffgh");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                displayId.setText(result.getContents());
            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }


}
