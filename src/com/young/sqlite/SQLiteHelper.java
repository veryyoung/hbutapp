package com.young.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public final String tag = "in MySQLiteHelper";

    private static final String DATABASE_NAME = "HBUT.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "schedule"; //表名称
    //表的字段
    public static final String CUR_NAME = "cur_name"; //课程名称
    public static final String TEACHER = "teacher"; //上课老师
    public static final String PLACE = "place"; //上课地点
    public static final String WEEK = "week";// 和周数
    public static final String DAY = "day"; //星期几
    public static final String DAY_TIME = "day_time"; //本日第几次课
    public static final String ID = "stu_id";//学号
    public static final String IS_LOCAL = "is_local";//是否为本地课表

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建课表数据库
        String sql = " create table if not exists " + TABLE_NAME + " (_id integer primary key autoincrement," +
                CUR_NAME + "  ,VARCHAR" +
                TEACHER + " VARCHAR, " +
                PLACE + " VARCHAR ," +
                DAY + " INTEGER," +
                DAY_TIME + " INTEGER," +
                ID + "  VARCHAR," +
                WEEK + " VARCHAR ," +
                IS_LOCAL + " TINYINT default 0 ) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        String dropSchedule = " drop table if exists schedule ";
        db.execSQL(dropSchedule);
        onCreate(db);
    }


}
