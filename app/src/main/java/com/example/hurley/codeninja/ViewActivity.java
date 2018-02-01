package com.example.hurley.codeninja;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ViewActivity extends AppCompatActivity {

    public TextView titleText, contentText;
    private String title = "", content = "";
    private long id = -1;

    private int editActivityCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarView);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        titleText = (TextView) findViewById(R.id.view_title);
        contentText = (TextView) findViewById(R.id.view_content);

        id = getIntent().getLongExtra("NOTE_ID", -1);
        title = getIntent().getStringExtra("NOTE_TITLE");
        content = getIntent().getStringExtra("NOTE_CONTENT");

        titleText.setText(title);
        contentText.setText(content);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void editNote(View view) {
        Intent intent = new Intent(this, EditActivity.class);

        intent.putExtra("NOTE_ID", id);
        intent.putExtra("NOTE_TITLE", title);
        intent.putExtra("NOTE_CONTENT", content);

        startActivityForResult(intent, editActivityCode);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == editActivityCode) {
            if (resultCode == RESULT_OK) {
                title = data.getStringExtra("NOTE_TITLE");
                content = data.getStringExtra("NOTE_CONTENT");

                titleText.setText(title);
                contentText.setText(content);
            }
        }
    }

}
