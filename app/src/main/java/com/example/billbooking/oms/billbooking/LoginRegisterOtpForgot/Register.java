package com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.SendMsg;
import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.MainActivity;
import com.example.billbooking.oms.billbooking.R;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ApiService;
import com.example.billbooking.oms.billbooking.RetrofitFiles.AppUserSignUpDetails;
import com.example.billbooking.oms.billbooking.RetrofitFiles.RetrofitConstant;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ServerRequest;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ServerResponse;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Register extends android.app.Fragment implements View.OnClickListener {

    EditText name, mobileNumber, email, pincode, password;
    Spinner state;
    Button register;
    TelephonyManager telephonyManager;
    String IMEI_Number_Holder;
    int stateposition;


    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("Mobile number0", SharedPrefManager.getmInstance(getActivity()).getNumber());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initializeViews(view);
        Log.e("Mobile number", SharedPrefManager.getmInstance(getActivity()).getNumber());
        mobileNumber.setText(SharedPrefManager.getmInstance(getActivity()).getNumber());

        String lic_type_names[] = {
                "State", "Andaman and Nicobar Islands ", "Andhra Pradesh"
                , "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh"
                , "Dadra and Nagar Haveli", "Daman and Diu", "Goa", "Gujarat", "Haryana"
                , "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Lakshadweep "
                , "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland"
                , "National Capital Territory of Delhi", "Odisha"
                , "Puducherry", "Punjab", "Rajasthan", "Sikkim"
                , "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand"
                , "West Bengal"};
        ArrayAdapter<String> net_adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, lic_type_names);
        state.setAdapter(net_adapter);

        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateposition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    private void initializeViews(View view) {

        name = (EditText) view.findViewById(R.id.user_name);
        mobileNumber = (EditText) view.findViewById(R.id.mobile_number);
        password = (EditText) view.findViewById(R.id.user_password);
        email = (EditText) view.findViewById(R.id.user_email);
        pincode = (EditText) view.findViewById(R.id.pincode);
        //state = (EditText) view.findViewById(R.id.state);
        //city = (EditText) view.findViewById(R.id.city);
        register = (Button) view.findViewById(R.id.register);
        state = (Spinner) view.findViewById(R.id.state);

        telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        IMEI_Number_Holder = telephonyManager.getDeviceId();
        Toast.makeText(getActivity(), SharedPrefManager.getmInstance(getActivity()).getNumber(), Toast.LENGTH_SHORT).show();
        Log.e("Mobile Number", SharedPrefManager.getmInstance(getActivity()).getNumber());
        mobileNumber.setText(SharedPrefManager.getmInstance(getActivity()).getNumber());
        register.setOnClickListener(this);
        register.setEnabled(true);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.register:
                Toast.makeText(getActivity(), "Register Pressed", Toast.LENGTH_SHORT).show();
                validation();
                break;

        }
    }


    private void validation() {

        if (name.getText().toString().length() == 0) {
            name.setError("Required Field");
        } else if (email.getText().toString().length() == 0) {
            email.setError("Required Field");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError("Not a Email");
        } else if (password.getText().toString().length() == 0) {
            password.setError("Required Field");
        } else if (pincode.getText().toString().length() < 6) {
            pincode.setError("Required Field");
        } else if (stateposition == 0) {
            Toast.makeText(getActivity(), "Select State", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "All Fields Are Filled", Toast.LENGTH_SHORT).show();

            SingletonSession.Instance().showProgress(getActivity(), "Please Wait...");
            registrationProcess(name.getText().toString(), email.getText().toString(),
                    password.getText().toString(), SharedPrefManager.getmInstance(getActivity()).getNumber(),
                    IMEI_Number_Holder, pincode.getText().toString(), stateposition);
        }

    }


    private void registrationProcess(String name, String email, String password, String phone, String imei, String pincode, int state) {

        ApiService requestInterface = RetrofitConstant.getApiServices();

        ServerRequest request = new ServerRequest();


        request.setPincode(pincode);
        request.setFullName(name);
        request.setImei(imei);
        request.setMobileNumber(phone);
        request.setCountry(1);
        request.setState(state);
        request.setEmailId(email);

        Call<ServerResponse> response = requestInterface.operationRegister(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();

                if (resp.getStatus().equals("success")) {

                    SingletonSession.Instance().hideProgress();

                    ((MainActivity) getActivity()).gotoLogin();
                } else {
                    SingletonSession.Instance().hideProgress();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                SingletonSession.Instance().hideProgress();
                Log.d(RetrofitConstant.TAG, "failed");
            }
        });
    }


}
