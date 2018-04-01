package com.example.billbooking.oms.billbooking;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot.IntroSlider;
import com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot.LoginPage;
import com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot.OtpVerification;
import com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot.Register;

public class MainActivity extends AppCompatActivity {


    int registerFragment = R.id.register_login_layout;
    Fragment login, otpVerification, register, introslider;
    FragmentTransaction loginFragmentTransaction, otpVerificationFragmentTransaction,
            registerFragmentTransaction, introsliderFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        }

        if (SharedPrefManager.getmInstance(this).getLaunchScreen().equals("0")) {
            gotoIntroSlider();
        } else {
            gotoLogin();
        }

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

        }
    }


    public void gotoIntroSlider() {
        introslider = new IntroSlider();
        introsliderFragmentTransaction = getFragmentManager().beginTransaction();
        introsliderFragmentTransaction.replace(registerFragment, introslider).commit();
    }

    public void gotoLogin() {
        login = new LoginPage();
        loginFragmentTransaction = getFragmentManager().beginTransaction();
        loginFragmentTransaction.replace(registerFragment, login).commit();

    }

    public void gotoOtpVerification() {
        otpVerification = new OtpVerification();
/*
        Bundle args = new Bundle();
        args.putInt("page_position", position + 1);
        fragment.setArguments(args);
        Bundle args = getArguments();*/

        otpVerificationFragmentTransaction = getFragmentManager().beginTransaction();
        otpVerificationFragmentTransaction.replace(registerFragment, otpVerification).commit();

    }

    public void gotoRegistration() {
        register = new Register();
        registerFragmentTransaction = getFragmentManager().beginTransaction();
        registerFragmentTransaction.replace(registerFragment, register).commit();

    }

}
