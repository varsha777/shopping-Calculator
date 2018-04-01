package com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.MainActivity;
import com.example.billbooking.oms.billbooking.R;
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.ArrayList;
import java.util.List;

public class OtpVerification extends android.app.Fragment implements View.OnClickListener, VerificationListener {

    EditText otpMobileNumber, otpValue;
    TextView resend;
    Button verify;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkAndRequestPermissions()) {
            Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_otp_verification, container, false);

        initializeView(view);

        otpMobileNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (otpMobileNumber.getText().toString().length() == 10) {
                    resend.setVisibility(View.VISIBLE);
                    //send_sms();
                    verify.setEnabled(true);
                } else {
                    resend.setVisibility(View.GONE);
                    verify.setEnabled(false);
                }
            }
        });
        return view;
    }


    private void initializeView(View view) {

        otpMobileNumber = (EditText) view.findViewById(R.id.otp_mobile_number);
        otpValue = (EditText) view.findViewById(R.id.otp_value);
        resend = (TextView) view.findViewById(R.id.resend_otp);
        verify = (Button) view.findViewById(R.id.verify);


        resend.setVisibility(View.GONE);
        verify.setEnabled(false);
        resend.setOnClickListener(this);
        verify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.verify:
                /*if (otpValue.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Correct Otp", Toast.LENGTH_SHORT).show();
                } else {

                    verifyOtp();
                }*/
               SharedPrefManager.getmInstance(getActivity()).storeMobileNumber(otpMobileNumber.getText().toString());
                Log.e("Mobile", SharedPrefManager.getmInstance(getActivity()).getNumber());
                ((MainActivity) getActivity()).gotoRegistration();
        }
    }

    private void send_sms() {

        Toast.makeText(getActivity(), "sending " + otpMobileNumber.getText().toString(), Toast.LENGTH_SHORT).show();
        mverifacation = SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("91" + otpMobileNumber.getText().toString())
                        .context(getActivity())
                        .autoVerification(true)
                        .build(), (VerificationListener) this);
        mverifacation.initiate();
    }


    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(),
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    1);
            return false;
        }
        return true;
    }


    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                String value = message.substring(0, 5);
                Toast.makeText(context, message + "\n" + value, Toast.LENGTH_LONG).show();
                otpValue.setText(value);
                verifyOtp();
            }
        }
    };


    Verification mverifacation;

    private void verifyOtp() {
        mverifacation.verify(otpValue.getText().toString());

    }

    @Override
    public void onInitiated(String response) {
        //  Toast.makeText(getActivity(), "Initiate" + response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Toast.makeText(getActivity(), "Initiate Failed" + paramException, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerified(String response) {
        //   Toast.makeText(getActivity(), "Ferification " + response, Toast.LENGTH_SHORT).show();
        SharedPrefManager.getmInstance(getActivity()).storeMobileNumber(otpMobileNumber.getText().toString());
        ((MainActivity) getActivity()).gotoRegistration();
    }

    @Override
    public void onVerificationFailed(Exception paramException) {

        Toast.makeText(getActivity(), "Verification Failed" + paramException, Toast.LENGTH_SHORT).show();
    }


}
