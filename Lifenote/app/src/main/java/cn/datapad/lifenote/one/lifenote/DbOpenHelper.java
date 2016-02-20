package cn.datapad.lifenote.one.lifenote;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by think on 2016/2/20.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    public DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE note(noteid INTEGER PRIMARY KEY AUTOINCREMENT,title VARCHAR(100))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
