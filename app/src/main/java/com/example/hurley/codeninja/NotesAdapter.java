package com.example.hurley.codeninja;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<Note> NotesList;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView title, date, content;
        private final Context context;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();

            view.setOnClickListener(this);

            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            date = (TextView) view.findViewById(R.id.date);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ViewActivity.class);
            context.startActivity(intent);
        }
    }


    public NotesAdapter(List<Note> NotesList) {
        this.NotesList = NotesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Note Note = NotesList.get(position);
        holder.title.setText(Note.getTitle());
        holder.content.setText(Note.getContent());
        holder.date.setText(Note.getDate());
    }

    @Override
    public int getItemCount() {
        return NotesList.size();
    }
}