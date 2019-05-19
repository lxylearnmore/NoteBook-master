package com.example.yangtianrui.notebook.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Selection;


public class NoteDAO {
    private DBHelper mHelper;
    private Context context;

    public NoteDAO(Context context) {
        mHelper = new DBHelper(context);
    }

    public long insertNote(ContentValues contentValues) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long id = db.insert(DBHelper.DB_NAME, null, contentValues);
        db.close();
        return id;
    }

    public int updateNote(ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count;
        count = db.update(DBHelper.DB_NAME, values, whereClause, whereArgs);
        db.close();
        return count;
    }

    public int deleteNote(String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        int count = db.delete(DBHelper.DB_NAME, whereClause, whereArgs);
        return count;
    }

    public Cursor queryNote(String selection, String[] selectionArgs,String order) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor c = db.query(false, DBHelper.DB_NAME, null, selection, selectionArgs
                , null, null, order, null);
        return c;
    }

}




