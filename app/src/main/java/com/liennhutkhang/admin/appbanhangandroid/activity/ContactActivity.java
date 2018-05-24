package com.liennhutkhang.admin.appbanhangandroid.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liennhutkhang.admin.appbanhangandroid.R;

public class ContactActivity extends AppCompatActivity {

    Toolbar toolbarContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolbarContact = (Toolbar) findViewById(R.id.toolBarInformation);
        ActionToolBar();
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarContact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarContact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
