package com.example.bookapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "book_review_app.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String bookmarkSql = "CREATE TABLE bookmarks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER, " +
                "saved_date TEXT)";

        String reviewSql = "CREATE TABLE reviews (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER, " +
                "rating REAL, " +
                "content TEXT, " +
                "created_date TEXT)";

        String readingSql = "CREATE TABLE reading_records (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "reading_time TEXT, " +
                "record_date TEXT)";

        db.execSQL(bookmarkSql);
        db.execSQL(reviewSql);
        db.execSQL(readingSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS bookmarks");
        db.execSQL("DROP TABLE IF EXISTS reviews");
        db.execSQL("DROP TABLE IF EXISTS reading_records");
        onCreate(db);
    }

    public int getBookmarkCount() {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(DISTINCT book_id) FROM bookmarks",
                null
        );

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public int getReviewCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM reviews", null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public int getReadingRecordCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM reading_records", null);

        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        return count;
    }

    public void insertBookmark(int bookId) {
        if (isBookmarked(bookId)) {
            return;
        }

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("book_id", bookId);
        values.put("saved_date", "2026.05.25");

        db.insert("bookmarks", null, values);
    }

    public boolean isBookmarked(int bookId) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT id FROM bookmarks WHERE book_id = ? LIMIT 1",
                new String[]{String.valueOf(bookId)}
        );

        boolean exists = cursor.moveToFirst();
        cursor.close();

        return exists;
    }

    public void deleteBookmark(int bookId) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(
                "bookmarks",
                "book_id = ?",
                new String[]{String.valueOf(bookId)}
        );
    }

    public Cursor getBookmarks() {
        SQLiteDatabase db = getReadableDatabase();

        return db.rawQuery(
                "SELECT MIN(id) AS id, book_id, saved_date " +
                        "FROM bookmarks " +
                        "GROUP BY book_id " +
                        "ORDER BY id DESC",
                null
        );
    }

    public void removeDuplicateBookmarks() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL(
                "DELETE FROM bookmarks " +
                        "WHERE id NOT IN (" +
                        "SELECT MIN(id) FROM bookmarks GROUP BY book_id" +
                        ")"
        );
    }

    public void insertReview(int bookId, float rating, String content) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("book_id", bookId);
        values.put("rating", rating);
        values.put("content", content);
        values.put("created_date", "2026.05.25");

        db.insert("reviews", null, values);
    }

    public Cursor getReviews() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM reviews ORDER BY id DESC", null);
    }

    public void insertReadingRecord(String readingTime) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("reading_time", readingTime);
        values.put("record_date", "2026.05.25");

        db.insert("reading_records", null, values);
    }
}