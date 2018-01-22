package com.example.hurley.codeninja.database;

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

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTES
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_CONTENT + " text not null,"
            + COLUMN_CREATED_AT + " datetime not null"
            + COLUMN_UPDATED_AT + " datetime not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("insert into " + TABLE_NOTES + "(" +
                COLUMN_ID + "," + COLUMN_TITLE + "," + COLUMN_CONTENT + "," +
                COLUMN_CREATED_AT + "," + COLUMN_UPDATED_AT +
                ") values(1, 'Note 1', 'This is my first note', '2002-09-24-06:00', '2002-09-24-06:00')");
        database.execSQL("insert into " + TABLE_NOTES + "(" +
                COLUMN_ID + "," + COLUMN_TITLE + "," + COLUMN_CONTENT + "," +
                COLUMN_CREATED_AT + "," + COLUMN_UPDATED_AT +
                ") values(2, 'Note 2', 'This is my second note', '2002-09-24-06:00', '2002-09-24-06:00')");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(NotesTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(database);
    }
}