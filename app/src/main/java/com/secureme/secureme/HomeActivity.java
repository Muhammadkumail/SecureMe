package com.secureme.secureme;

import android.app.ProgressDialog;
import android.content.Context;

import android.content.Intent;
import android.net.ConnectivityManager;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
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

    String mNotificationNumber = "";
    String mNotificationType = "";

    private boolean isBulbOn = true;
    private boolean isalarmOn = true;
    private boolean isdoorOpen = true;
    ImageView ivalarm;
    ImageView ivdoor;
    ImageView ivswith;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ivalarm = (ImageView) findViewById(R.id.home_imageView_alarm);
        ivdoor = (ImageView) findViewById(R.id.home_imageView_door);
        ivswith = (ImageView) findViewById(R.id.home_imageView_switch);

        new asyncTask_getStatus().execute();


    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public void onClick_doorSendNotification(View view) {


        if (isdoorOpen) {
            ivdoor.setImageResource((R.drawable.ic_lock));


            if (isNetworkConnected()) {
                mNotificationNumber = "1";
                mNotificationType = "door";
                new asyncTask_sendNotification().execute();
            } else {
                errorMessage("Please Check Internet connection");
            }

            isdoorOpen = false;
        } else {
            ivdoor.setImageResource((R.drawable.ic_unlock));

            if (isNetworkConnected()) {
                mNotificationNumber = "2";
                mNotificationType = "door";
                new asyncTask_sendNotification().execute();
            } else {
                errorMessage("Please Check Internet connection");
            }
            isdoorOpen = true;
        }

    }


    public void onClick_alaramSendNotification(View view) {


        if (isalarmOn) {
            ivalarm.setImageResource((R.drawable.ic_alarm_on));


            if (isNetworkConnected()) {
                mNotificationNumber = "3";
                mNotificationType = "alarm";
                new asyncTask_sendNotification().execute();
            } else {
                errorMessage("Please Check Internet connection");
            }

            isalarmOn = false;
        } else {
            ivalarm.setImageResource((R.drawable.ic_alarm_off));

            if (isNetworkConnected()) {
                mNotificationNumber = "4";
                mNotificationType = "alarm";
                new asyncTask_sendNotification().execute();
            } else {
                errorMessage("Please Check Internet connection");
            }
            isalarmOn = true;
        }
    }

    public void onClick_switchSendNotification(View view) {


        if (isBulbOn) {
            ivswith.setImageResource((R.drawable.ic_bulb_on));


            if (isNetworkConnected()) {
                mNotificationNumber = "5";
                mNotificationType = "switch";
                new asyncTask_sendNotification().execute();
            } else {
                errorMessage("Please Check Internet connection");
            }

            isBulbOn = false;
        } else {
            ivswith.setImageResource((R.drawable.ic_bulb_off));

            if (isNetworkConnected()) {
                mNotificationNumber = "6";
                mNotificationType = "switch";
                new asyncTask_sendNotification().execute();
            } else {
                errorMessage("Please Check Internet connection");
            }
            isBulbOn = true;
        }


    }

    public void onClick_gpsSendNotification(View view) {
        mNotificationNumber = "7";
        mNotificationType = "gps";
        if (isNetworkConnected()) {
            new asyncTask_sendNotification().execute();
            new asyncTask_getgps().execute();
        } else {
            errorMessage("Please Check Internet connection");
        }

    }

    public void errorMessage(String Errormessage) {
        Toast.makeText(HomeActivity.this, Errormessage, Toast.LENGTH_LONG).show();

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


                    msg = sendNotificationToWebServer(mNotificationNumber, mNotificationType);

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

            //errorMessage(s);

        }
    }

    public String sendNotificationToWebServer(String mNotificationNumber, String mNotificationType) {
        String url = "http://friendsfashion.net/android/secureme/securemeNotification.php";
        String strResponse = "No response";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("notificationNumber", mNotificationNumber));
            nameValuePairs.add(new BasicNameValuePair("notificationType", mNotificationType));
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

    private class asyncTask_getStatus extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomeActivity.this, "Loading...", "wait", true);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {


                msg = getStatusFromToWebServer();


            } catch (Exception ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            String[] splitResult = result.split(",");

            String swith = splitResult[1];
            String door = splitResult[3];
            String alarm = splitResult[5];

            int intswith = Integer.parseInt(swith);
            int intdoor = Integer.parseInt(door);
            int intalarm = Integer.parseInt(alarm);
            try {
                if (intdoor == 1) {
                    ivdoor.setImageResource((R.drawable.ic_lock));
                    isdoorOpen = false;

                }
                if (intdoor == 2) {
                    ivdoor.setImageResource((R.drawable.ic_unlock));
                    isdoorOpen = true;

                }

                if (intalarm == 3) {

                    ivalarm.setImageResource((R.drawable.ic_alarm_on));
                    isalarmOn = false;

                }
                if (intalarm == 4) {

                    ivalarm.setImageResource((R.drawable.ic_alarm_off));
                    isalarmOn = true;

                }
                if (intswith == 9) {
                    ivswith.setImageResource((R.drawable.ic_bulb_on));
                    isBulbOn = false;

                }
                if (intswith == 10) {
                    ivswith.setImageResource((R.drawable.ic_bulb_off));
                    isBulbOn = true;

                }
            } catch (Exception e) {
                errorMessage(e.toString());
                e.printStackTrace();
            }





           /* errorMessage(swith+door+alarm);
            errorMessage(result);*/

        }
    }

    public String getStatusFromToWebServer() {
        String url = "http://friendsfashion.net/android/secureme/getStatus.php";
        String strResponse = "No response";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("getStatus", "getStatus"));
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

    private class asyncTask_getgps extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(HomeActivity.this, "Loading...", "wait", true);

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {


                msg = getGpsFromToWebServer();


            } catch (Exception ex) {
                msg = "Error :" + ex.getMessage();
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            progressDialog.dismiss();
            String[] splitResult = result.split(",");

            String lan = splitResult[0];
            String lng = splitResult[1];


            float floatLan = Float.parseFloat(lan);
            float floatlng = Float.parseFloat(lng);
            Intent intent=new Intent(HomeActivity.this,GoogleMapActivity.class);
            intent.putExtra("lat",floatLan);
            intent.putExtra("lng",floatlng);
            startActivity(intent);

           errorMessage(lan+lng);


        }
    }

    public String getGpsFromToWebServer() {
        String url = "http://friendsfashion.net/android/secureme/getGps.php";
        String strResponse = "No response";
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(url);
        try {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("getGps", "getGps"));
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
