package com.example.hurley.codeninja;

import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private List<Note> NotesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, date, content;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            content = (TextView) view.findViewById(R.id.content);
            date = (TextView) view.findViewById(R.id.date);
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