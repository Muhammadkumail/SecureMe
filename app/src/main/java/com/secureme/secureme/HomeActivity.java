package com.secureme.secureme;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class HomeActivity extends AppCompatActivity {

    Boolean flag = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);




    }

  /*  public void onClickLock(View view) {
        ImageView lockImageView = (ImageView) findViewById(R.id.home_key_lock);

        if (flag == false) {
            lockImageView.setImageResource(R.drawable.ic_key_unlock);
            flag = true;
        } else {

            lockImageView.setImageResource(R.drawable.ic_key_lock);
            flag = false;
        }
    }*/

    /*public void onClickSiren(View view) {
        ImageView lockImageView = (ImageView) findViewById(R.id.home_siren);

        if (flag == false) {
            lockImageView.setImageResource(R.drawable.ic_siren);
            flag = true;
        } else {

            lockImageView.setImageResource(R.drawable.ic_mute);
            flag = false;
        }
    }*/

   /* public void onClick_Google_map(View view) {
        Intent sintent = new Intent();
        sintent.setClass(HomeActivity.this, GoogleMapActivity.class);
        startActivity(sintent);

    }*/

   /* public void onClick_PhoneCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + "+923362175378"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }*/

}
