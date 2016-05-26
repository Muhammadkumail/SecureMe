package com.secureme.secureme;

import android.app.ProgressDialog;
import android.content.Context;

import android.net.ConnectivityManager;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    Boolean flag = false;
    String mNotificationNumber = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }



    public void onClick_cameraSendNotification(View view) {
        mNotificationNumber = "camera";
        if (isNetworkConnected()) {
            new asyncTask_sendNotification().execute();
        } else {
            errorMessage("Please Check Internet connection");
        }

    }
    public void onClick_gpsSendNotification(View view) {
        mNotificationNumber = "gps";
        if (isNetworkConnected()) {
            new asyncTask_sendNotification().execute();
        } else {
            errorMessage("Please Check Internet connection");
        }

    }
    public void onClick_alaramSendNotification(View view) {
        mNotificationNumber = "alaram";
        if (isNetworkConnected()) {
            new asyncTask_sendNotification().execute();
        } else {
            errorMessage("Please Check Internet connection");
        }

    }
    public void onClick_switchSendNotification(View view) {
        mNotificationNumber = "switch";
        if (isNetworkConnected()) {
            new asyncTask_sendNotification().execute();
        } else {
            errorMessage("Please Check Internet connection");
        }

    }

    public void errorMessage(String Errormessage) {
        Toast.makeText(HomeActivity.this, Errormessage, Toast.LENGTH_SHORT).show();

    }

    private class asyncTask_sendNotification extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomeActivity.this, "Sending...", "wait", true);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                if (mNotificationNumber.length() > -0) {


                    msg = sendNotificationToWebServer(mNotificationNumber);

                }
            } catch (Exception ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            mNotificationNumber = "";

            errorMessage("Your Message Has been sent");

        }
    }

    public String sendNotificationToWebServer(String mNotificationNumber) {
        String url = "http://friendsfashion.net/android/secureme/securemeNotification.php";
        String strResponse = "No response";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            nameValuePairs.add(new BasicNameValuePair("notificationNumber", mNotificationNumber));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            strResponse = EntityUtils.toString(response.getEntity());
        } catch (ClientProtocolException e) {
            strResponse = e.getMessage();
        } catch (IOException e) {
            Log.e("IOException:", e.getMessage());
            strResponse = e.getMessage();
        }
        return strResponse;
    }


}
