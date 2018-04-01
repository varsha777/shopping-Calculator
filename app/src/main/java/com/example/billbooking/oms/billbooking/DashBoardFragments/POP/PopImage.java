package com.example.billbooking.oms.billbooking.DashBoardFragments.POP;

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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.Pdf.PdfPopImage;
import com.example.billbooking.oms.billbooking.Customize.SendMsg;
import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.PurchaseInvoice;
import com.example.billbooking.oms.billbooking.DBModel.UserSeller;
import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopAdapters.PopImageRecyclerAdapter;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopAdapters.PopImageRecyclerAdapterShow;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModelImage;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.R;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;

@SuppressLint("ValidFragment")
public class PopImage extends Fragment implements View.OnClickListener {

    Button to, show, qrScanner, addItem, paid, print, save;
    EditText displayId;
    IntentIntegrator qrScan;
    ImageView commodityImage;
    EditText commodityQuantity, commodityPrice;
    AutoCompleteTextView commodityName;
    ArrayList<PopModelImage> myList = new ArrayList<>();
    RecyclerView mRecyclerView;
    PopImageRecyclerAdapter mRecyclerAdapter;

    RecyclerView mRecyclerView_image_show;
    PopImageRecyclerAdapterShow mRecyclerAdapter_show;
    RecyclerView.LayoutManager mLayoutManager_show;
    LinearLayout linearLayout, addRow;


    DatabaseHelper dbh = new DatabaseHelper(getActivity());
    SQLiteDatabase db = null;

    private LocationManager locationManager;
    double lati, longi;

    String[] commodityNames;
    Double[] commodityPrices;
    int value = -1;
    int status = 1;


    int check = 0;
    String position;
    String dispId;


    @SuppressLint("ValidFragment")
    public PopImage(ArrayList<PopModelImage> myList, String position, String displayID) {
        this.myList = myList;
        this.position = position;
        dispId = displayID;
        if (myList.size() != 0) {
            check = 1;

            Log.e("Check", String.valueOf(check));
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    102);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pop_image, container, false);
        ((DashBoard) getActivity()).getSupportActionBar().setTitle("Point Of Purchases");
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
                    ArrayList<UserSeller> userSellerArrayList = dbh.getAllSellers();
                    int a = 0;
                    for (int i = 0; i < userSellerArrayList.size(); i++) {
                        if (displayId.getText().toString().equals(userSellerArrayList.get(i).getDisplayId())) {
                            addItem.setEnabled(true);
                            paid.setEnabled(true);
                            print.setEnabled(true);
                            save.setEnabled(true);
                            show.setEnabled(true);
                            to.setEnabled(false);
                            commodityImage.setEnabled(true);
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
                    addItem.setEnabled(false);
                    paid.setEnabled(false);
                    print.setEnabled(false);
                    save.setEnabled(false);
                    show.setEnabled(false);
                    to.setEnabled(true);
                    commodityImage.setEnabled(false);
                }


            }
        });


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_item, commodityNames);

        commodityName.setThreshold(1);
        commodityName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                value = i;

                Log.e("value in", String.valueOf(value));
                if (value == -1) {
                    status = 1;
                } else {
                    status = 0;
                    commodityPrice.setText(getPrice(commodityName.getText().toString()));
                    value = -1;
                }


            }
        });
        Log.e("value", String.valueOf(value));
        commodityName.setAdapter(arrayAdapter);


        return view;
    }

    private String getPrice(String item) {
        for (int i = 0; i < commodityNames.length; i++) {

            if (commodityNames[i].equals(item))
                return String.valueOf(commodityPrices[i]);

        }
        return null;
    }

    private void initializeViews(View view) {

        LocationManager
                service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            showDialogAlert("Alert", "TurnOn GPS", getActivity(), getResources().getDrawable(R.drawable.error));
        }

        DatabaseHelper dbh = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbh.getReadableDatabase();
        ArrayList<Commodity> commodities = dbh.getAllCommodities();
        commodityNames = new String[commodities.size()];
        commodityPrices = new Double[commodities.size()];
        if (commodities.size() != 0) {
            for (int i = 0; i < commodities.size(); i++) {
                commodityNames[i] = commodities.get(i).getName();
                commodityPrices[i] = commodities.get(i).getAmount();
                Log.e("Item" + i, commodities.get(i).getName());
            }
        }


        to = (Button) view.findViewById(R.id.to_pos_image);
        displayId = (EditText) view.findViewById(R.id.display_id_image);
        qrScanner = (Button) view.findViewById(R.id.qr_scan_image);
        addItem = (Button) view.findViewById(R.id.add_item_image);
        show = (Button) view.findViewById(R.id.show_image);
        linearLayout = (LinearLayout) view.findViewById(R.id.linear_image_show_pop);
        addRow = (LinearLayout) view.findViewById(R.id.add_row);
        print = (Button) view.findViewById(R.id.print_pop_image);
        paid = (Button) view.findViewById(R.id.paid_pop_image);
        save = (Button) view.findViewById(R.id.save_image_pop);

        commodityImage = (ImageView) view.findViewById(R.id.commodity_image);
        commodityName = (AutoCompleteTextView) view.findViewById(R.id.commodity_name_image1);
        commodityQuantity = (EditText) view.findViewById(R.id.commodity_quantity_image);
        commodityPrice = (EditText) view.findViewById(R.id.commodity_price_image);

        addItem.setEnabled(false);
        paid.setEnabled(false);
        print.setEnabled(false);
        save.setEnabled(false);
        show.setEnabled(false);
        commodityImage.setEnabled(false);


        commodityImage.setOnClickListener(this);
        qrScan = new IntentIntegrator(getActivity());
        qrScanner.setOnClickListener(this);

        to.setOnClickListener(this);
        qrScanner.setOnClickListener(this);
        addItem.setOnClickListener(this);
        show.setOnClickListener(this);
        print.setOnClickListener(this);
        paid.setOnClickListener(this);
        save.setOnClickListener(this);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_pos_image);
        mRecyclerAdapter = new PopImageRecyclerAdapter(myList, getActivity());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRecyclerAdapter);


        mRecyclerView_image_show = (RecyclerView) view.findViewById(R.id.recycler_view_pos_show_image);
        mRecyclerView_image_show.setVisibility(View.GONE);

        if (check == 1) {
            addItem.setEnabled(true);
            paid.setEnabled(true);
            print.setEnabled(true);
            save.setEnabled(true);
            show.setEnabled(true);
            to.setEnabled(false);
            commodityImage.setEnabled(true);
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

        if (displayId.getText().toString().length() != 0) {

            ArrayList<UserSeller> userSellerArrayList = dbh.getAllSellers();
            for (int i = 0; i < userSellerArrayList.size(); i++) {
                if (userSellerArrayList.get(i).getSync() == 1) {
                    if (displayId.getText().toString().equals(userSellerArrayList.get(i).getDisplayId())) {

                        name.setText(userSellerArrayList.get(i).getName());
                        mobileNumber.setText(userSellerArrayList.get(i).getMobileNum());
                        address.setText(userSellerArrayList.get(i).getAddress());
                        email.setText(userSellerArrayList.get(i).getEmailId());

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

                    dbh.insertUserSeller(0, 2, name.getText().toString(),
                            address.getText().toString(), email.getText().toString(), mobileNumber.getText().toString(), 0, 0, 1, null, tempDisplayId);

                    addItem.setEnabled(true);
                    paid.setEnabled(true);
                    print.setEnabled(true);
                    save.setEnabled(true);
                    show.setEnabled(true);
                    to.setEnabled(false);
                    commodityImage.setEnabled(true);
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

            case R.id.to_pos_image:

                dialogBox();
                break;


            case R.id.qr_scan_image:

                qrScan.setPrompt("Scan a barcode");
                qrScan.forSupportFragment(this).initiateScan();
                break;

            case R.id.commodity_image:

                selectImage();
                break;

            case R.id.save_image_pop:
                Toast.makeText(getActivity(), "Save List In Bills Progress", Toast.LENGTH_SHORT).show();
                if (check == 1) {
                    SharedPrefManager.getmInstance(getActivity()).updateImagePopArray(myList, position);
                    myList.clear();
                    displayId.setText("");
                    mRecyclerAdapter.notifyData(myList);
                    //Log.e("Enter Check1", "");
                } else {

                    SharedPrefManager.getmInstance(getActivity()).storeImagePopArray(myList, displayId.getText().toString());
                    myList.clear();
                    displayId.setText("");
                    mRecyclerAdapter.notifyData(myList);
                    // Log.e("Enter Check0", "");
                }


            case R.id.add_item_image:

                if (filePath == null) {
                    Toast.makeText(getActivity(), "Caputure image", Toast.LENGTH_SHORT).show();
                } else if (commodityName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Missing Commodity Name", Toast.LENGTH_SHORT).show();
                } else if (commodityQuantity.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Missing Commodity Qantity", Toast.LENGTH_SHORT).show();
                } else if (commodityPrice.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Missing Commodity Price", Toast.LENGTH_SHORT).show();
                } else {

                    PopModelImage posModel = new PopModelImage();

                    FileOutputStream fo;
                    try {
                        file.createNewFile();
                        fo = new FileOutputStream(file);
                        fo.write(bytes.toByteArray());
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    posModel.setStatus(status);
                    posModel.setImagePath(filePath.toString());
                    posModel.setCommodity(commodityName.getText().toString());
                    posModel.setQuantity(Integer.parseInt(commodityQuantity.getText().toString()));
                    posModel.setPrice(Double.parseDouble(commodityPrice.getText().toString()));
                    posModel.setTotal((Double.parseDouble(commodityQuantity.getText().toString())) * (Double.parseDouble(commodityPrice.getText().toString())));

                    myList.add(posModel);
                    mRecyclerAdapter.notifyData(myList);
                    filePath = null;
                    commodityName.setText("");
                    commodityPrice.setText("");
                    commodityQuantity.setText("");
                    commodityPrice.setText("");
                    commodityImage.setImageDrawable(getResources().getDrawable(R.drawable.add_a_photo));
                }
                break;

            case R.id.show_image:

                if (show.getText().toString().equals("Show")) {

                    show.setText("Back");
                    addItem.setEnabled(false);
                    addRow.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    mRecyclerView_image_show.setVisibility(View.VISIBLE);

                    mRecyclerAdapter_show = new PopImageRecyclerAdapterShow(myList, getActivity(), mRecyclerView_image_show, linearLayout);
                    final LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
                    layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
                    mRecyclerView_image_show.setLayoutManager(layoutManager2);
                    mRecyclerView_image_show.setAdapter(mRecyclerAdapter_show);


                    mRecyclerAdapter_show.notifyData(myList);

                } else if (show.getText().toString().equals("Back")) {
                    show.setText("Show");
                    addRow.setVisibility(View.VISIBLE);
                    addItem.setEnabled(true);
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mRecyclerView_image_show.setVisibility(View.GONE);
                    mRecyclerAdapter.notifyData(myList);

                }

                break;
            case R.id.print_pop_image:
                verifyStoragePermissions(getActivity(), myList, 101);
                break;

            case R.id.paid_pop_image:

                if (myList.size() == 0) {
                    Toast.makeText(getActivity(), "Add Items Bill Is Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Paid", Toast.LENGTH_SHORT).show();
                    dialogBoxPaidStatus();
                }
                break;

        }
    }

    int userBuyTo;
    String name, number;

    void paidMethod(int paidStatus) {
        SingletonSession.Instance().showProgress(getActivity(), "Updating...");

        dbh = new DatabaseHelper(getActivity());

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
        boolean result = dbh.insertPurchaseInvoice(0, display, total, paidStatus, 1, String.valueOf(currentTime), total);
        if (!result) {
            Log.e("Not Inserted", "Row");
        } else {
            dbh.showInvoiceCount();
            db = dbh.getReadableDatabase();
            //Log.e("Inserted", "One Row");
            String col[] = {"id"};
            //POSText.MyLocationListener mc = new POSText.MyLocationListener();

            this.userBuyTo = getSellId(displayId.getText().toString());
            String address = null;

            ArrayList<PurchaseInvoice> purchaseInvoices = dbh.getAllPurchaseInvoice();
            purchaseInvoices.get(purchaseInvoices.size() - 1).getId();

            boolean res = dbh.insertUserBuyDetails(0, userBuyTo, 1,
                    purchaseInvoices.get(purchaseInvoices.size() - 1).getId(), 1, String.valueOf(lati),
                    String.valueOf(longi), String.valueOf(currentTime), 1, String.valueOf(currentTime),
                    address, checkSellId(displayId.getText().toString()));
            if (res) {
                insertInOrderCommodity(purchaseInvoices.get(purchaseInvoices.size() - 1).getId());
            } else {
                Log.e("Failed", "Insertd user Sell Details");
            }
        }
        if (position != null) {
            SharedPrefManager.getmInstance(getActivity()).deleteKeyValueList(position);
            SharedPrefManager.getmInstance(getActivity()).deleteDisplayValueDisp(dispId);
        }


        ArrayList<UserSeller> userSellerArrayList = dbh.getAllSellers();
        for (int i = 0; i < userSellerArrayList.size(); i++) {
            if (userSellerArrayList.get(i).getSync() == 1) {
                if (userBuyTo == (userSellerArrayList.get(i).getId())) {
                    //this.name = userBuyerArrayList.get(i).getName();
                    this.number = userSellerArrayList.get(i).getMobileNum();
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

    boolean insertInOrderCommodity(int id) {

        // Log.e("Inserted", "Insertd user Sell Details");
        int a = 0, test = 0;
        dbh.showUserSellDetailsCount();
        ArrayList<Commodity> tempItems = dbh.getAllCommodities();
        for (int i = 0; i < myList.size(); i++) {

            double discount = 0.0, gst = 0.0, cgst = 0.0, igst = 0.0, sgst = 0.0;
            String hsncode = null, image = String.valueOf(filePath), uom = null, chapter = null, schedule = null;
            int category = 0, priceStatus = 0, stock = 0, orderCommodityId = 0;

            if (myList.get(i).getStatus() == 0) {
                for (int j = 0; j < tempItems.size(); j++) {
                    if (myList.get(i).equals(tempItems.get(j).getName())) {
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
                        test = 1;
                        break;
                    }
                }
            } else {

            }

            boolean value = dbh.insertOrderCommodity(0, 0, myList.get(i).getStatus(),
                    id, myList.get(i).getCommodity(), myList.get(i).getQuantity(),
                    myList.get(i).getPrice(), discount, uom, gst, cgst, igst, sgst, hsncode, category,
                    priceStatus, chapter, schedule, image, 1, null, orderCommodityId, 2);

            dbh.showOrderCommodityCount();
            if (test == 1) {
                dbh.updateCommodityList(myList.get(i).getCommodity(), stock);
               /* ArrayList<Commodity> items = dbh.getAllCommodities();
                Log.e("After Update Stock", String.valueOf(items.get(i).getStock()));*/
            }

            dbh.showOrderCommodityCount();
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

    private int getSellId(String s) {
        ArrayList<UserSeller> userSellArrayList = dbh.getAllSellers();
        for (int i = 0; i < userSellArrayList.size(); i++) {
            if (userSellArrayList.get(i).getSync() == 1) {
                if (s.equals(userSellArrayList.get(i).getDisplayId())) {
                    if (s.substring(0, 3).equals("Tmp"))
                        return userSellArrayList.get(i).getId();
                    else
                        return userSellArrayList.get(i).getUserSellerId();
                }
            }
        }
        return 0;
    }

    private int checkSellId(String s) {
        ArrayList<UserSeller> userSellArrayList = dbh.getAllSellers();
        for (int i = 0; i < userSellArrayList.size(); i++) {
            if (userSellArrayList.get(i).getSync() == 1) {
                if (s.equals(userSellArrayList.get(i).getDisplayId())) {
                    if (s.substring(0, 3).equals("Tmp"))
                        return 1;
                    else
                        return 0;
                }
            }
        }
        return 1;
    }


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public void verifyStoragePermissions(Activity activity, ArrayList<PopModelImage> posModelArrayList, int value) {
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
                createPdf(display, value);
            }
        }
    }


/*    void showPdfName() {

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

    private void createPdf(final String s, int value) {
        PdfPopImage pdf = new PdfPopImage();

        if (value == 101) {
            boolean result = pdf.createPDF(getActivity(), s, myList, "/temp/");
            File file2 = new File(Environment.getExternalStorageDirectory(),
                    "/temp/" + s + ".pdf");
            final Uri path2 = FileProvider.getUriForFile(getActivity(),
                    "com.example.billbooking.oms.billbooking" + ".provider", file2);
            SingletonSession.Instance().sharePdf(getActivity(), path2);

        } else {
            boolean result = pdf.createPDF(getActivity(), s, myList, "/BillBookOnline/Pdfs/");
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


    private void selectImage() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Toast.makeText(getActivity(), "Take Photo", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent, 102);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    102);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

        if (requestCode == 102) {

            Toast.makeText(getActivity(), "requesting", Toast.LENGTH_SHORT).show();
            try {
                onCaptureImageResult(data);
            } catch (Exception e) {
                Log.e("exception", e.getMessage());
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    Uri filePath;
    ByteArrayOutputStream bytes;
    File file;

    private void onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/BillBookOnline/PurchaseImages");
        myDir.mkdirs();

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Commodity-" + n + ".jpg";
        file = new File(myDir, fname);
        commodityImage.setImageBitmap(bitmap);

        filePath = Uri.fromFile(file);
        Log.e("image", filePath.toString());
        Toast.makeText(getActivity(), filePath.toString(), Toast.LENGTH_SHORT).show();


    }


}
