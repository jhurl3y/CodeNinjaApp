package mynotes.example.hurley.codeninja;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import mynotes.example.hurley.codeninja.contentprovider.NotesContentProvider;
import mynotes.example.hurley.codeninja.database.NotesTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    public EditText titleText, contentText;
    private String title = "", content = "";
    private long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarEdit);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_done_white_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(id == -1){
                createNote();
            } else{
                editNote();

                Intent data = new Intent();
                data.putExtra("NOTE_TITLE",title);
                data.putExtra("NOTE_CONTENT",content);

                setResult(RESULT_OK, data);
            }

            finish();
            }
        });

        titleText = (EditText) findViewById(R.id.edit_title);
        contentText = (EditText) findViewById(R.id.edit_content);

        id = getIntent().getLongExtra("NOTE_ID", -1);
        title = getIntent().getStringExtra("NOTE_TITLE");
        content = getIntent().getStringExtra("NOTE_CONTENT");

        titleText.setText(title);
        contentText.setText(content);
    }

    public void createNote(){
        ContentValues values = new ContentValues();

        title = String.valueOf(titleText.getText());
        content = String.valueOf(contentText.getText());

        if (title.matches("") && content.matches("")) {
            return;
        }

        values.put(NotesTable.COLUMN_TITLE, title);
        values.put(NotesTable.COLUMN_CONTENT, content);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put(NotesTable.COLUMN_CREATED_AT, date);
        values.put(NotesTable.COLUMN_UPDATED_AT, date);

        getContentResolver().insert(NotesContentProvider.CONTENT_URI, values);
    }

    public void editNote(){
        ContentValues values = new ContentValues();

        title = String.valueOf(titleText.getText());
        content = String.valueOf(contentText.getText());

        values.put(NotesTable.COLUMN_TITLE, title);
        values.put(NotesTable.COLUMN_CONTENT, content);

        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        values.put(NotesTable.COLUMN_UPDATED_AT, date);

        getContentResolver().update(NotesContentProvider.CONTENT_URI, values,NotesTable.COLUMN_ID+"=?",new String[] {String.valueOf(id)});
    }
}
