package com.example.billbooking.oms.billbooking.DashBoardFragments.ReportScreen;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserSellDetails;
import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DatabaseHelper;
import com.example.billbooking.oms.billbooking.MainActivity;
import com.example.billbooking.oms.billbooking.R;
import com.example.billbooking.oms.billbooking.SplashScreen;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;


public class ReportScreen2 extends Fragment implements OnMapReadyCallback {


    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    Handler handler = new Handler();
    Marker marker;
    Spinner usersType;
    DatabaseHelper dbh = new DatabaseHelper(getActivity());
    SQLiteDatabase db = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_report_screen2, container, false);


        initializeViews(view);
        final String users_type[] = {
                "Type", "Sellers", "Buyers"};
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        getLocation();
        final int delay = 480000;
        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                getLocation();
                handler.postDelayed(this, delay);
            }
        }, delay);


        ArrayAdapter<String> net_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, users_type);
        usersType.setAdapter(net_adapter);

        usersType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (users_type[position].equals("Type")) {
                    getLocation();
                } else if (users_type[position].equals("Sellers")) {
                    mMap.clear();
                    getAllSellersMarkers();
                } else if (users_type[position].equals("Buyers")) {
                    mMap.clear();
                    getAllBuyersMarkers();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    void getLocation() {
        if (
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                this.latitude = latti;
                this.longitude = longi;

            } else {

                Toast.makeText(getActivity(), "Not Found", Toast.LENGTH_SHORT).show();
            }
        }

    }

    double latitude, longitude;

    private void initializeViews(View view) {

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map_frag);
        mapFragment.getMapAsync(this);
        usersType = (Spinner) view.findViewById(R.id.user_type);

        LocationManager
                service = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            showDialogAlert("Alert", "TurnOn GPS", getActivity(), getResources().getDrawable(R.drawable.error));
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

    private GoogleMap mMap;


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().isZoomControlsEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        if (marker != null) {
            marker.remove();
        }

        LatLng yourLocation = new LatLng(latitude, longitude);
        marker = mMap.addMarker(new MarkerOptions().position(yourLocation)
                .title("Your Location"));
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(yourLocation));

        CameraUpdate location2 = CameraUpdateFactory.newLatLngZoom(
                yourLocation, 15);
        mMap.animateCamera(location2);

    }


    void getAllSellersMarkers() {
        dbh = new DatabaseHelper(getActivity());

        ArrayList<UserSellDetails> sellDetailsArrayList = dbh.getAllSellersDetails();
        for (int i = 0; i < sellDetailsArrayList.size(); i++) {
            //Log.e("Latitude", sellDetailsArrayList.get(i).getLatitude());
            if (sellDetailsArrayList.get(i).getLatitude().isEmpty()) {

            } else {
                Double latitude = Double.valueOf(sellDetailsArrayList.get(i).getLatitude());
                Double longitude = Double.valueOf(sellDetailsArrayList.get(i).getLongitude());
                setMarker(new LatLng(latitude, longitude), "Seller " + i);
            }
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 70);
        mMap.animateCamera(cu);
    }

    void getAllBuyersMarkers() {

        dbh = new DatabaseHelper(getActivity());
        ArrayList<UserBuyDetails> userBuyDetailsArrayList = dbh.getAllBuyerDetails();
        for (int i = 0; i < userBuyDetailsArrayList.size(); i++) {
            //Log.e("Latitude", sellDetailsArrayList.get(i).getLatitude());
            if (userBuyDetailsArrayList.get(i).getLatitude().isEmpty()) {

            } else {
                Double latitude = Double.valueOf(userBuyDetailsArrayList.get(i).getLatitude());
                Double longitude = Double.valueOf(userBuyDetailsArrayList.get(i).getLongitude());
                setMarker(new LatLng(latitude, longitude), "Buyer " + i);
            }
        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 60);
        mMap.animateCamera(cu);

    }

    ArrayList<Marker> markers = new ArrayList<Marker>();

    public void setMarker(LatLng position, String name) {

        MarkerOptions options = new MarkerOptions()
                .title(name)
                .position(position);
        Marker goto_markers = mMap.addMarker(options);
        markers.add(goto_markers);
    }

}
