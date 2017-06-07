//package net.gumcode.malangvoice.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by A. Fauzi Harismawan on 12/30/2015.
// */
//public class DatabaseHelper extends SQLiteOpenHelper {
//
//    private static final String DATABASE_NAME = "rss";
//    private static final int DATABASE_VERSION = 1;
//
//    private final String TABLE_ALL = "FEEDS";
//
//    private final String COLUMN_ID = "ID";
//    private final String COLUMN_TITLE = "TITLE";
//    private final String COLUMN_DESC = "DESC";
//    private final String COLUMN_TIME = "TIME";
//
//    private SQLiteDatabase writableDatabase;
//    private SQLiteDatabase readableDatabase;
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        writableDatabase = getWritableDatabase();
//        readableDatabase = getReadableDatabase();
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ALL + " ("
//                + COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
//                + COLUMN_TITLE + " VARCHAR NOT NULL, "
//                + COLUMN_DESC + " VARCHAR NOT NULL, "
//                + COLUMN_TIME + " VARCHAR NOT NULL)");
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL);
//    }
//
//    public void insertFeed(List<RSSFeed> objList) {
//        writableDatabase.execSQL("DELETE FROM " + TABLE_ALL);
//        ContentValues values = new ContentValues();
//        for (RSSFeed instance : objList) {
//            values.put(COLUMN_TITLE, instance.getTitle());
//            values.put(COLUMN_DESC, instance.getDesc());
//            values.put(COLUMN_TIME, instance.getTime());
//
//            writableDatabase.insert(TABLE_ALL, null, values);
//        }
//    }
//
//    public List<RSSFeed> getFeed() {
//        List<RSSFeed> list = new ArrayList<>();
//        Cursor mCursor = readableDatabase.rawQuery("SELECT * FROM " + TABLE_ALL, null);
//        mCursor.moveToFirst();
//
//        if (!mCursor.isAfterLast()) {
//            do {
//                list.add(new RSSFeed(
//                        mCursor.getString(mCursor.getColumnIndex(COLUMN_TITLE)),
//                        mCursor.getString(mCursor.getColumnIndex(COLUMN_DESC)),
//                        mCursor.getString(mCursor.getColumnIndex(COLUMN_TIME))
//                ));
//            } while (mCursor.moveToNext());
//        }
//
//        return list;
//    }
//}
