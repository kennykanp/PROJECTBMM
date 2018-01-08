package com.example.kennykanp.bmmbusiness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginDriveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_drive);
    }

    public void goCreateAccount(View view){
        Intent intent = new Intent(this, CreateAccountDriverActivity.class);
        startActivity(intent);
    }
}
