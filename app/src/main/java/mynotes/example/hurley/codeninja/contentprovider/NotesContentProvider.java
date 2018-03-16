package mynotes.example.hurley.codeninja.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import mynotes.example.hurley.codeninja.database.NotesDatabaseHelper;
import mynotes.example.hurley.codeninja.database.NotesTable;


public class NotesContentProvider extends ContentProvider {

    // database
    private NotesDatabaseHelper database;

    // used for the UriMatcher
    private static final int NOTES = 1;
    private static final int NOTE_ID = 2;

    private static final String AUTHORITY = "com.example.hurley.notes.contentprovider";

    private static final String NOTES_TABLE = "notes";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + NOTES_TABLE);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/notes";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/notes";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, NOTES_TABLE, NOTES);
        sURIMatcher.addURI(AUTHORITY, NOTES_TABLE + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new NotesDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(NotesTable.TABLE_NOTES);

        int uriType = sURIMatcher.match(uri);

        switch (uriType) {
            case NOTES:
                break;
            case NOTE_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(NotesTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();

        long id = 0;
        switch (uriType) {
            case NOTES:
                id = sqlDB.insert(NotesTable.TABLE_NOTES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(NOTES_TABLE + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case NOTES:
                rowsDeleted = sqlDB.delete(NotesTable.TABLE_NOTES, selection,
                        selectionArgs);
                break;
            case NOTE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(
                            NotesTable.TABLE_NOTES,
                            NotesTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(
                            NotesTable.TABLE_NOTES,
                            NotesTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;

        switch (uriType) {
            case NOTES:
                rowsUpdated = sqlDB.update(NotesTable.TABLE_NOTES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case NOTE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(NotesTable.TABLE_NOTES,
                            values,
                            NotesTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(NotesTable.TABLE_NOTES,
                            values,
                            NotesTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { NotesTable.COLUMN_TITLE,
                NotesTable.COLUMN_CONTENT, NotesTable.COLUMN_CREATED_AT,
                NotesTable.COLUMN_UPDATED_AT, NotesTable.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(
                    Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(
                    Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException(
                        "Unknown columns in projection");
            }
        }
    }

}