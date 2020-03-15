package club.zhuol.notepad.DBUtils;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class DBUtils {
    public static final String DATABASE_NAME = "Notepad";   //数据库名
    public static final String DATABASE_TABLE = "Note";   //数据表名
    public static final int DATABASE_VERION = 1;    //数据库版本
    //数据表中的列名
    public static final String NOTEPAD_ID = "id";
    public static final String NOTEPAD_CONTENT = "content";
    public static final String NOTEPAD_TIME = "notetime";
    //获取当前日期
    public static final String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
        //bug
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
