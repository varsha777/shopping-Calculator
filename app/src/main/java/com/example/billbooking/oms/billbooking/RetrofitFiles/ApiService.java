package com.example.billbooking.oms.billbooking.RetrofitFiles;

import com.example.billbooking.oms.billbooking.Sync.SendDataToServer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by VarshaDhoni on 11/10/2017.
 */

public interface ApiService {

    //Login, phone verify in otpVerification file
    @POST("applogin.html")
    Call<ServerResponse> operation(@Body ServerRequest request);

    @POST("appSignup.html")
    Call<ServerResponse> operationRegister(@Body ServerRequest request);

    @POST("AppPOSGenerateInvoice.html")
    Call<ServerResponse> operationSync(@Body SendDataToServer sendDataToServer);

}
