package com.example.hurley.codeninja;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Note> NoteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.notes_list);

        mAdapter = new NotesAdapter(NoteList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(mAdapter);

        prepareNoteData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void createNote(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        this.startActivity(intent);
    }

    private void prepareNoteData() {
        Note note = new Note("Note 1: Fury Road", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque", "Jan 2015");
        NoteList.add(note);

        note = new Note("Note 2: Inside Out", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque", " Feb 2015");
        NoteList.add(note);

        note = new Note("Note 3: Star Wars", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque", " Mar 2015");
        NoteList.add(note);

        note = new Note("Note 4: Shaun the Sheep", "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque", " Apr 2015");
        NoteList.add(note);

        mAdapter.notifyDataSetChanged();
    }
}
