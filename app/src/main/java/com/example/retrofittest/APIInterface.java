package com.example.retrofittest;


import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface APIInterface {

    @Headers({
            "Content-Type: 	text/xml",
            "Accept-Charset:	utf-8",
            "soapaction:	http://tempuri.org/IAdjdRMSService/GetTransactionDetail"
    })
    @POST("/AdjdRMSService.svc")
    Call<String> makeRequest(@Body RequestBody requestBody);
}