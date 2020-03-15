package club.zhuol.notepad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import club.zhuol.notepad.DBUtils.DBUtils;
import club.zhuol.notepad.bean.NotePadBean;

public class SQLiteHelper extends SQLiteOpenHelper {

    private SQLiteDatabase sqLiteDatabase;

    public SQLiteHelper(Context context) {
        super(context, DBUtils.DATABASE_NAME, null, DBUtils.DATABASE_VERION);
        sqLiteDatabase = this.getWritableDatabase();
    }

    //创建数据表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+DBUtils.DATABASE_TABLE+"("+DBUtils.NOTEPAD_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+ DBUtils.NOTEPAD_CONTENT +" text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    //添加数据
    public boolean insertData(String userContent, String userTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT, userContent);
        contentValues.put(DBUtils.NOTEPAD_TIME, userTime);
        return sqLiteDatabase.insert(DBUtils.DATABASE_TABLE, null, contentValues) > 0;
    }

    /**
     * 根据传入id删除数据库表Note的数据
     * @param id
     * @return
     */
    //删除数据
    public boolean deletetData(String id) {
        String sql = DBUtils.NOTEPAD_ID + "=?";
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete(DBUtils.DATABASE_TABLE, sql, contentValuesArray) > 0;
    }

    /**
     * alter data in table of Note by update() function
     * @param id
     * @param content
     * @param userYear
     * @return
     */
    //修改数据
    public boolean updateData(String id, String content, String userYear) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.NOTEPAD_CONTENT, content);
        contentValues.put(DBUtils.NOTEPAD_TIME, userYear);
        String sql = DBUtils.NOTEPAD_ID + "=?";
        String[] strings = new String[]{id};
        return sqLiteDatabase.update(DBUtils.DATABASE_TABLE, contentValues, sql, strings) > 0;
    }

    /**
     * query all data in table of Note by query() function and return a Cursor object
     * traversal data of in Cursor object by while() function and let data store in
     * List<NotepadBean>
     * @return
     */
    //查询数据
    public List<NotePadBean> query() {
        List<NotePadBean> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DBUtils.DATABASE_TABLE, null, null,
                null, null, null, DBUtils.NOTEPAD_ID + " desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                NotePadBean noteInfo = new NotePadBean();
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex(DBUtils.NOTEPAD_ID)));
                String content = cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_CONTENT));
                String time = cursor.getString(cursor.getColumnIndex(DBUtils.NOTEPAD_TIME));
                noteInfo.setId(id);
                noteInfo.setNotpadContent(content);
                noteInfo.setNotpadTime(time);
                list.add(noteInfo);
            }
            cursor.close();
        }
        return list;
    }
}
