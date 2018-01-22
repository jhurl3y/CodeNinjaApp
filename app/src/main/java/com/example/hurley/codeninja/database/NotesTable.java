package com.example.hurley.codeninja.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class NotesTable {

    // Database table
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_UPDATED_AT = "updated_at";

    // Fields corresponding to database columns
    private long id;
    private String title, content, createdAt, updatedAt;

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_CONTENT + " text not null,"
            + COLUMN_CREATED_AT + " datetime not null,"
            + COLUMN_UPDATED_AT + " datetime not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("insert into " + TABLE_NOTES + "(" +
                COLUMN_ID + "," + COLUMN_TITLE + "," + COLUMN_CONTENT + "," +
                COLUMN_CREATED_AT + "," + COLUMN_UPDATED_AT +
                ") values(1, 'Note 1', 'This is my first note', '2016-02-03 00:00:00Z', '2016-02-03 00:00:00Z')");
        database.execSQL("insert into " + TABLE_NOTES + "(" +
                COLUMN_ID + "," + COLUMN_TITLE + "," + COLUMN_CONTENT + "," +
                COLUMN_CREATED_AT + "," + COLUMN_UPDATED_AT +
                ") values(2, 'Note 2', 'This is my second note', '2016-02-03 00:00:00Z', '2015-01-03 00:00:00Z')");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(NotesTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(database);
    }

    public NotesTable(Long id, String title, String content, String createdAt, String updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NotesTable(final Cursor cursor) {
        // Indices expected to match order in FIELDS!
        this.id = cursor.getLong(0);
        this.title = cursor.getString(1);
        this.content = cursor.getString(2);
        this.createdAt = cursor.getString(3);
        this.updatedAt = cursor.getString(4);
    }

    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that ID is NOT included here
        values.put(COLUMN_TITLE, this.title);
        values.put(COLUMN_CONTENT, this.content);
        values.put(COLUMN_CREATED_AT, this.createdAt);
        values.put(COLUMN_UPDATED_AT, this.updatedAt);

        return values;
    }
}