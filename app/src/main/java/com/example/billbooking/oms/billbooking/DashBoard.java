package com.example.billbooking.oms.billbooking;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.DashBoardFragments.BillsInProgress.BillsInProgress;
import com.example.billbooking.oms.billbooking.DashBoardFragments.NoRecordsFound;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.POPText;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopImage;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POP.PopModel.PopModelImage;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.POSText;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosImage;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModel;
import com.example.billbooking.oms.billbooking.DashBoardFragments.POS.PosModel.PosModelImage;
import com.example.billbooking.oms.billbooking.DashBoardFragments.PdfViews;
import com.example.billbooking.oms.billbooking.DashBoardFragments.QrCode;
import com.example.billbooking.oms.billbooking.DashBoardFragments.ReportScreen.ReportScreen;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;
import com.example.billbooking.oms.billbooking.Sync.SendAllValues;

import java.util.ArrayList;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int dashBoardFragment = R.id.dashboard_layout;
    Fragment posText, posImage, popText, popImage, reportScreen, billsInProgress, pfdViews, qrGenerator, noRecordsFound;
    FragmentTransaction reportScreenFragmentTransaction, posTextFragmentTransaction, posImageFragmentTransaction,
            popTextFragmentTransaction, popImageFragmentTransaction, billsInProgressFragmentTransaction,
            pfdViewsFragmentTransaction, qrGeneratorFragmentTransaction, noRecordsFoundFragmentTransaction;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Reports");


        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        gotoReport();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            case 1:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }

        }
    }


    private Boolean exit = false;

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit) {
            finish(); // finish activity
        } else {
            gotoReport();
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.goto_dashboard:
                gotoReport();
                break;

            case R.id.logout:
                /*int total = db.getAllOrderCommodities().size() + db.getAllBuyerDetails().size() + db.getAllBuyers().size() + db.getAllSellers().size() + db.getAllSellersDetails().size() + db.getAllInvoice().size() + db.getAllPurchaseInvoice().size();
                if (total != 0) {
                    showDialogAlert("Logout Alert", "All Local Storage Data Values Are Lost Without Sync", this,
                            getResources().getDrawable(R.drawable.error));
                } else {
                    SharedPrefManager.getmInstance(this).storeMobileNumber(null);
                    SharedPrefManager.getmInstance(this).storePassword(null);
                    SharedPrefManager.getmInstance(this).storeLaunchScreen("0");
                    db = new DatabaseHelper(this);
                    db.deleteAllTables();
                    startActivity(new Intent(DashBoard.this, MainActivity.class));
                }*/
                showDialogAlert("Logout Alert", "All Local Storage Data Values Are Lost Without Sync", this,
                        getResources().getDrawable(R.drawable.error));
                break;
            case R.id.generate_qr:
                gotoQrGenerate();
                break;
            case R.id.sync:

                new SendAllValues(this);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.pos) {

            final CharSequence[] options2 = {"Text", "Image", "Scanner", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Point of Sales");
            builder.setItems(options2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options2[item].equals("Cancel")) {
                        dialog.dismiss();
                    } else if (options2[item].equals("Text")) {

                        gotoPOSText(new ArrayList<PosModel>(), null, null);

                    } else if (options2[item].equals("Image")) {

                        gotoPOSImage(new ArrayList<PosModelImage>(), null, null);
                    } else if (options2[item].equals("Scanner")) {
                        Toast.makeText(DashBoard.this, "Wait For Next Update", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();


        } else if (id == R.id.pop) {

            Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();


            final CharSequence[] options2 = {"Text", "Image", "Scanner", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Point of Purchase");
            builder.setItems(options2, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (options2[item].equals("Cancel")) {
                        dialog.dismiss();
                    } else if (options2[item].equals("Text")) {

                        gotoPOPText(new ArrayList<PopModel>(), null, null);

                    } else if (options2[item].equals("Image")) {

                        gotoPOPImage(new ArrayList<PopModelImage>(), null, null);
                    } else if (options2[item].equals("Scanner")) {
                        Toast.makeText(DashBoard.this, "Going to Scanner", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.show();


        } else if (id == R.id.bils_in_progress) {

            gotoBillsInProg();

        } else if (id == R.id.reports) {
            gotoReport();
        } else if (id == R.id.pdfs) {
            verifyStoragePermissions(this);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void gotoQrGenerate() {
        qrGenerator = new QrCode();
        qrGeneratorFragmentTransaction = getSupportFragmentManager().beginTransaction();
        qrGeneratorFragmentTransaction.replace(dashBoardFragment, qrGenerator).addToBackStack(null).commit();
    }

    public void gotoNoRecordFound() {
        noRecordsFound = new NoRecordsFound();
        noRecordsFoundFragmentTransaction = getSupportFragmentManager().beginTransaction();
        noRecordsFoundFragmentTransaction.replace(dashBoardFragment, noRecordsFound).addToBackStack(null).commit();
    }

    public void gotoPdfs() {
        pfdViews = new PdfViews();
        pfdViewsFragmentTransaction = getSupportFragmentManager().beginTransaction();
        pfdViewsFragmentTransaction.replace(dashBoardFragment, pfdViews).addToBackStack(null).commit();
    }

    public void gotoBillsInProg() {
        billsInProgress = new BillsInProgress();
        billsInProgressFragmentTransaction = getSupportFragmentManager().beginTransaction();
        billsInProgressFragmentTransaction.replace(dashBoardFragment, billsInProgress).addToBackStack(null).commit();
    }

    public void gotoPOSText(ArrayList<PosModel> myList, String position, String displayId) {
        posText = new POSText(myList, position, displayId);
        posTextFragmentTransaction = getSupportFragmentManager().beginTransaction();
        posTextFragmentTransaction.replace(dashBoardFragment, posText).addToBackStack(null).commit();
    }

    public void gotoPOSImage(ArrayList<PosModelImage> myList, String position, String displayId) {
        posImage = new PosImage(myList, position, displayId);
        posImageFragmentTransaction = getSupportFragmentManager().beginTransaction();
        posImageFragmentTransaction.replace(dashBoardFragment, posImage).addToBackStack(null).commit();
    }

    public void gotoPOPText(ArrayList<PopModel> myList, String position, String displayId) {
        popText = new POPText(myList, position, displayId);
        popTextFragmentTransaction = getSupportFragmentManager().beginTransaction();
        popTextFragmentTransaction.replace(dashBoardFragment, popText).addToBackStack(null).commit();
    }

    public void gotoPOPImage(ArrayList<PopModelImage> myList, String position, String displayId) {
        popImage = new PopImage(myList, position, displayId);
        popImageFragmentTransaction = getSupportFragmentManager().beginTransaction();
        popImageFragmentTransaction.replace(dashBoardFragment, popImage).addToBackStack(null).commit();
    }

    public void gotoReport() {
        reportScreen = new ReportScreen();
        reportScreenFragmentTransaction = getSupportFragmentManager().beginTransaction();
        reportScreenFragmentTransaction.replace(dashBoardFragment, reportScreen).commit();
    }

    public void showDialogAlert(String title, String msg, final Activity activity, Drawable icon) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setIcon(icon);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                SharedPrefManager.getmInstance(activity).storeMobileNumber(null);
                SharedPrefManager.getmInstance(activity).storePassword(null);
                SharedPrefManager.getmInstance(activity).storeLaunchScreen("0");
                db = new DatabaseHelper(activity);
                db.deleteAllTables();
                startActivity(new Intent(DashBoard.this, MainActivity.class));
                dialog.dismiss();

            }
        });

        alertDialog.setButton2("Sync", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                new SendAllValues(activity);
                dialog.dismiss();
            }
        });
        alertDialog.setButton3("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }


    private static String[] PERMISSIONS_STORAGE = {
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int REQUEST_EXTERNAL_STORAGE = 1;

    public void verifyStoragePermissions(Activity activity) {
        //Toast.makeText(activity, "enter", Toast.LENGTH_SHORT).show();
        // Check if we have write permission
        if (ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(activity, "in", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } else {
            //  Toast.makeText(activity, "Checking Compleated", Toast.LENGTH_SHORT).show();
            gotoPdfs();
        }
    }


}
