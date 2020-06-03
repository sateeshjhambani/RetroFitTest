package com.example.retrofittest;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView responseText;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        responseText = (TextView) findViewById(R.id.responseText);
        try {
            apiInterface = APIClient.getClient().create(APIInterface.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String strRequestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\">\n    <soapenv:Header>\n        <tem:Version>1.0</tem:Version>\n        <tem:UserId>adjd_user</tem:UserId>\n        <tem:TimeStamp>2020-04-06T07:38:51Z</tem:TimeStamp>\n        <tem:TerminalUser>RMS</tem:TerminalUser>\n        <tem:TerminalID>61100009</tem:TerminalID>\n        <tem:SubMerchantId></tem:SubMerchantId>\n        <tem:SourceApplication>RMS</tem:SourceApplication>\n        <tem:SignatureFields>RequestId,MerchantId</tem:SignatureFields>\n        <tem:ServiceId>10</tem:ServiceId>\n        <tem:SecureHash>V8atvA/TT9+k4NJZekuhpDAbiSbTs8Um5gatf2NAIPCAF73BksHD6Djy/sENQI93kVqMSQTcInBWeBcc94Ghpdda+LMWVrCD</tem:SecureHash>\n        <tem:RequestType>REQ</tem:RequestType>\n        <tem:RequestId>d19365d1-f307-4f25-8b00-9a2405863acd</tem:RequestId>\n        <tem:RequestCategory>REQUEST</tem:RequestCategory>\n        <tem:PaymentMethod>OCD</tem:PaymentMethod>\n        <tem:PaymentChannel>POS</tem:PaymentChannel>\n        <tem:Password>FkVAvjHMi7Z5sMWqhRnMUg==</tem:Password>\n        <tem:MerchantId>ADJD01</tem:MerchantId>\n        <tem:Language>EN</tem:Language>\n        <tem:IpAssigned>127.0.0.1</tem:IpAssigned>\n        <tem:EnServiceName>ADJDRMSService</tem:EnServiceName>\n        <tem:BankId>EBI</tem:BankId>\n        <tem:ArServiceName>ADJDRMSService</tem:ArServiceName>\n    </soapenv:Header>\n    <soapenv:Body>\n        <tem:GetTransactionDetailRequest>\n            <!--Optional:-->\n            <tem:ReferenceNumber>null</tem:ReferenceNumber>\n            <!--Optional:-->\n            <tem:TokenReferenceNumber>2QVNZ5KU</tem:TokenReferenceNumber>\n        </tem:GetTransactionDetailRequest>\n    </soapenv:Body>\n</soapenv:Envelope>";
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml"), strRequestBody);
        Call<String> call = apiInterface.makeRequest(requestBody);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                Log.i("TAG-Response", response.body());
                XMLToJson(response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                call.cancel();
                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void responseToXML(String response) throws XmlPullParserException, JSONException {
//        try {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//            XmlPullParser xpp = factory.newPullParser();
//
//            xpp.setInput( new StringReader( response ) ); // pass input whatever xml you have
//            int eventType = xpp.getEventType();
//            while (eventType != XmlPullParser.END_DOCUMENT) {
//                if(eventType == XmlPullParser.START_DOCUMENT) {
//                    Log.i("TAG","Start document");
//                } else if(eventType == XmlPullParser.START_TAG) {
//                    Log.i("TAG","Start tag "+xpp.getName());
//                } else if(eventType == XmlPullParser.END_TAG) {
//                    Log.i("TAG","End tag "+xpp.getName());
//                } else if(eventType == XmlPullParser.TEXT) {
//                    Log.i("TAG","Text "+xpp.getText()); // here you get the text from xml
//                }
//                eventType = xpp.next();
//            }
//
//            Log.i("TAG","End document");
//            //XMLToJson(xpp.getText());
//
//        } catch (XmlPullParserException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void XMLToJson(String xml){
        JSONObject jsonObj = null;
        try {
            jsonObj = XML.toJSONObject(xml);
        } catch (JSONException e) {
            Log.e("JSON exception", e.getMessage());
            e.printStackTrace();
        }
        Log.i("TAG-Json", jsonObj.toString());
        responseText.setText(jsonObj.toString());

//        jsonObj = XML.toJSONObject(stringToXml);
//        JSONObject body = jsonObj.getJSONObject("s:Envelope").getJSONObject("s:Body").getJSONObject("---");
//        JSONObject header = jsonObj.getJSONObject("s:Envelope").getJSONObject("s:Header");
    }
}
