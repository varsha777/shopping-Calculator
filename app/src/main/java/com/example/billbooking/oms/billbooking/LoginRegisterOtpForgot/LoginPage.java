package com.example.billbooking.oms.billbooking.LoginRegisterOtpForgot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.billbooking.oms.billbooking.Customize.SharedPrefManager;
import com.example.billbooking.oms.billbooking.DBModel.Commodity;
import com.example.billbooking.oms.billbooking.DBModel.Invoice;
import com.example.billbooking.oms.billbooking.DBModel.OrderCommodity;
import com.example.billbooking.oms.billbooking.DBModel.Profile;
import com.example.billbooking.oms.billbooking.DBModel.Profile1;
import com.example.billbooking.oms.billbooking.DBModel.PurchaseInvoice;
import com.example.billbooking.oms.billbooking.DBModel.ReportGenerate;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserBuyer;
import com.example.billbooking.oms.billbooking.DBModel.UserSellDetails;
import com.example.billbooking.oms.billbooking.DBModel.UserSeller;
import com.example.billbooking.oms.billbooking.DashBoard;
import com.example.billbooking.oms.billbooking.DatabaseFiles.DbInsertOperationsLogin;
import com.example.billbooking.oms.billbooking.MainActivity;
import com.example.billbooking.oms.billbooking.R;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ApiService;
import com.example.billbooking.oms.billbooking.RetrofitFiles.RetrofitConstant;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ServerRequest;
import com.example.billbooking.oms.billbooking.RetrofitFiles.ServerResponse;
import com.example.billbooking.oms.billbooking.SingletonClass.SingletonSession;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginPage extends android.app.Fragment implements View.OnClickListener {

    EditText mobileNumber, password;
    Button login, signup;
    TextView forgotPassword;
    private ArrayList<Profile1> profile1Array;
    private ArrayList<Profile> profileArray;
    private ArrayList<Commodity> commodityArray;
    private ArrayList<UserBuyer> userBuyerArray;
    private ArrayList<UserSeller> userSellerArray;
    private ArrayList<Invoice> invoiceArray;
    private ArrayList<OrderCommodity> orderCommoditieArray;
    private ArrayList<PurchaseInvoice> purchaseInvoices;
    private ArrayList<UserBuyDetails> userBuyDetailArray;
    private ArrayList<UserSellDetails> userSellDetailArray;
    private ArrayList<ReportGenerate> generateArray;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);

        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {

        mobileNumber = (EditText) view.findViewById(R.id.user_phone_number);
        password = (EditText) view.findViewById(R.id.user_password);
        login = (Button) view.findViewById(R.id.user_login_btn);
        signup = (Button) view.findViewById(R.id.user_register_btn);
        forgotPassword = (TextView) view.findViewById(R.id.user_forgot_password);

        login.setOnClickListener(this);
        signup.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.user_register_btn:
                ((MainActivity) getActivity()).gotoOtpVerification();
                break;

            case R.id.user_login_btn:
                if (mobileNumber.getText().toString().isEmpty()) {
                    mobileNumber.setError("Required Field");
                } else if (mobileNumber.getText().toString().length() < 10) {
                    mobileNumber.setError("Enter Valid Mobile Number");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("Required Field");
                } else {
                    Log.e("Login", "enter");
                    SingletonSession.Instance().showProgress(getActivity(), "Processing...");
                    loginProcess(mobileNumber.getText().toString(), password.getText().toString());
                }
        }

    }


    void loginProcess(final String phone, final String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RetrofitConstant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService requestInterface = retrofit.create(ApiService.class);
        ServerRequest request = new ServerRequest();

        request.setPassword(password);
        request.setUserName(phone);

        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Log.e("Varsh Reports " + response, "asdf");

                if (resp.getStatus().equals("SUCCESS")) {
                    SingletonSession.Instance().hideProgress();
                    SharedPrefManager.getmInstance(getActivity()).storeMobileNumber(phone);
                    SharedPrefManager.getmInstance(getActivity()).storePassword(password);
                    SharedPrefManager.getmInstance(getActivity()).storeLaunchScreen("1");

                    Toast.makeText(getActivity(), resp.getStatus(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                    profile1Array = new ArrayList<>(Arrays.asList(resp.getProfile1()));
                    profileArray = new ArrayList<>(Arrays.asList(resp.getProfile()));
                    commodityArray = new ArrayList<>(Arrays.asList(resp.getCommodity()));
                    userBuyerArray = new ArrayList<>(Arrays.asList(resp.getUser_buyer()));
                    userSellerArray = new ArrayList<>(Arrays.asList(resp.getUser_seller()));
                    invoiceArray = new ArrayList<>(Arrays.asList(resp.getInvoice()));
                    orderCommoditieArray = new ArrayList<>(Arrays.asList(resp.getOrder_commodity()));
                    purchaseInvoices = new ArrayList<>(Arrays.asList(resp.getPurchase_invoice()));
                    userBuyDetailArray = new ArrayList<>(Arrays.asList(resp.getUser_buy_details()));
                    userSellDetailArray = new ArrayList<>(Arrays.asList(resp.getUser_sell_details()));
                    generateArray = new ArrayList<>(Arrays.asList(resp.getReport_generate()));

                    new DbInsertOperationsLogin(getActivity(), commodityArray, profileArray, profile1Array, userBuyerArray,
                            userSellerArray, invoiceArray, orderCommoditieArray, purchaseInvoices, userBuyDetailArray,
                            userSellDetailArray, generateArray);

                    getActivity().startActivity(new Intent(getActivity(), DashBoard.class));
                    getActivity().finish();

                } else {
                    SingletonSession.Instance().hideProgress();
                    Toast.makeText(getActivity(), "Failed To Login", Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                SingletonSession.Instance().hideProgress();
                Toast.makeText(getActivity(), "Fail" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("varsha", t.getMessage());
            }
        });
    }


}
