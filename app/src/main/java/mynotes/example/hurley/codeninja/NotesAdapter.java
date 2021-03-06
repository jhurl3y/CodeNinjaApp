package mynotes.example.hurley.codeninja;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mynotes.example.hurley.codeninja.contentprovider.NotesContentProvider;
import mynotes.example.hurley.codeninja.database.NotesTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.MyViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private boolean mDataValid;
    private int mRowIdColumn;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView titleText, dateText, contentText;
        private String title = "", content = "", date = "";
        private long id = -1;
        private final Context context;

        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            view.setOnClickListener(this);

            titleText = (TextView) view.findViewById(R.id.title);
            contentText = (TextView) view.findViewById(R.id.content);
            dateText = (TextView) view.findViewById(R.id.date);

            titleText.setEllipsize(TextUtils.TruncateAt.END);
            titleText.setMaxLines(2);

            contentText.setEllipsize(TextUtils.TruncateAt.END);
            contentText.setMaxLines(2);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ViewActivity.class);
            intent.putExtra("NOTE_ID", id);
            intent.putExtra("NOTE_TITLE", title);
            intent.putExtra("NOTE_CONTENT", content);

            context.startActivity(intent);
        }

        public void updateView(String title, String content, String date) throws ParseException {
            this.titleText.setText(title);
            this.title = title;

            this.contentText.setText(content);
            this.content = content;

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = format.parse(date);

            this.dateText.setText(new SimpleDateFormat("HH:mm d MMM").format(d));
            this.date = date;
        }

        public void passId(long id){
            this.id = id;
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

        Long id = mCursor.getLong(mCursor.getColumnIndex(NotesTable.COLUMN_ID));
        String title = mCursor.getString(mCursor.getColumnIndex(NotesTable.COLUMN_TITLE));
        String content = mCursor.getString(mCursor.getColumnIndex(NotesTable.COLUMN_CONTENT));
        String date = mCursor.getString(mCursor.getColumnIndex(NotesTable.COLUMN_UPDATED_AT));

        try {
            holder.updateView(title, content, date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.passId(id);
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
        mCursor.moveToPosition(position);
        Long id = mCursor.getLong(mCursor.getColumnIndex(NotesTable.COLUMN_ID));

        mContext.getContentResolver().delete(NotesContentProvider.CONTENT_URI,NotesTable.COLUMN_ID+"=?",new String[] {String.valueOf(id)});
    }
}