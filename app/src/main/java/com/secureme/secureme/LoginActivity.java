package com.secureme.secureme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void onClick_login(View view) {

        Intent sintent=new Intent();
        sintent.setClass(LoginActivity.this,HomeActivity.class);
        startActivity(sintent);

    }

}
