package sg.edu.rp.c347.p06_taskmanager_t2;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 3;
    private static final String TABLE_TASK = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION= "description";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT ,"
                + COLUMN_DESCRIPTION + " TEXT )";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the databased is created

            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Buy milk ");
            values.put(COLUMN_DESCRIPTION, "Low fat");
            db.insert(TABLE_TASK, null, values);

        Log.i("info", "dummy records inserted");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }

    public long insertTask(String name, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        long result = db.insert(TABLE_TASK, null, values);
        db.close();
        Log.d("SQL Insert ",""+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<String> getAllTask() {
        ArrayList<String> notes = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","  + COLUMN_DESCRIPTION +" FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                notes.add(id + " " + name + "\n" + desc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

//    public int updateNote(Task data){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_NAME, data.getName());
//        values.put
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(data.getName())};
//        int result = db.update(TABLE_TASK, values, condition, args);
//        db.close();
//        return result;
//    }
//
//    public int deleteNote(int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String condition = COLUMN_ID + "= ?";
//        String[] args = {String.valueOf(id)};
//        int result = db.delete(TABLE_TASK, condition, args);
//        db.close();
//        Log.v("v", result + "");
//        return result;
//    }

    public ArrayList<Task> getAllTask(String keyword) {
        ArrayList<Task> notes = new ArrayList<Task>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME};
        String condition = COLUMN_NAME + " Like ?";
        String[] args = { "%" +  keyword + "%"};
        Cursor cursor = db.query(TABLE_TASK, columns, condition,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                Task note = new Task(name, description, id);

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }


}
