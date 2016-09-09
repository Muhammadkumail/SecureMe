package com.secureme.secureme;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.secureme.secureme.Adaptor.CarListAdaptor;
import com.secureme.secureme.DataSource.CarListDatasource;
import com.secureme.secureme.Entity.Cars;

import java.util.ArrayList;

public class CarListsActivity extends AppCompatActivity {

    ListView mlistview;
    CarListDatasource mcarListDatasource;
    CarListAdaptor mcarListAdaptor;
    Context context;
    ArrayList<Cars> marraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        context = this;
        new carListAsync_Task().execute();

    }




    private class carListAsync_Task extends AsyncTask<Void, Void, Void> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(CarListsActivity.this, "Loading", "Wait for load Data", true);
            marraylist = new ArrayList<Cars>();
            mcarListDatasource = new CarListDatasource();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            marraylist = mcarListDatasource.getList();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            progressDialog.dismiss();

            populate();

            super.onPostExecute(aVoid);
        }


    }
    public void populate() {
        mlistview = (ListView) findViewById(R.id.activity_car_list_listview);

        mcarListAdaptor = new CarListAdaptor((Activity) context, marraylist);
        mlistview.setAdapter(mcarListAdaptor);

        mlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent mintent = new Intent(context, HomeActivity.class);
                startActivity(mintent);

            }
        });
    }
}
