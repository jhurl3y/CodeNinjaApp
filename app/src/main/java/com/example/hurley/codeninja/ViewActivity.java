package com.example.hurley.codeninja;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarView);
        setSupportActionBar(toolbar);
    }

}
