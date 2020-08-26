package com.example.schoolapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "School DB";
    private static final String TABLE_NAME = "Dept_and_Session_table";
    private static final int DATABASE_VERSION = 1;
    private static final String ID = "_id";
    private static final String DEPT_NAME = "dept_name" ;
    private static final String SESSION = "session";

    private static final String CREATE_TABLE_1 = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+DEPT_NAME+" VARCHAR(256)," +
            " "+SESSION+" VARCHAR(256));";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_1);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    }

    long saveData(String[] dept_name, String[] session)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        long id = -1;

        for (int i = 0; i<dept_name.length; i++)
        {
            contentValues.put(DEPT_NAME, dept_name[i]);
            contentValues.put(SESSION, session[i]);

            id = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        }
        return id;
    }
}
