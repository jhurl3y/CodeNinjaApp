package com.example.hurley.codeninja;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hurley.codeninja.database.NotesTable;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private boolean mDataValid;
    private int mRowIdColumn;

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

    public NotesAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("id") : -1;
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        if (mDataValid && mCursor != null) {
            return mCursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong(mRowIdColumn);
        }
        return 0;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mCursor.moveToPosition(position);

        String title = mCursor.getString(mCursor.getColumnIndex(NotesTable.COLUMN_TITLE));
        String content = mCursor.getString(mCursor.getColumnIndex(NotesTable.COLUMN_CONTENT));
        String date = mCursor.getString(mCursor.getColumnIndex(NotesTable.COLUMN_UPDATED_AT));

        holder.title.setText(title);
        holder.content.setText(content);
        holder.date.setText(date);
    }

    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        mCursor = newCursor;

        if (mCursor != null) {
            mRowIdColumn = newCursor.getColumnIndexOrThrow("id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    public void remove(int position) {
//        Note Note = NotesList.get(position);
//
//        if (NotesList.contains(Note)) {
//            NotesList.remove(position);
//            notifyItemRemoved(position);
//        }
    }
}