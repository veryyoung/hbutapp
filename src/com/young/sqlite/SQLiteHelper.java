package com.young.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public final String tag = "in MySQLiteHelper";

    private static final String DATABASE_NAME = "HBUT.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "schedule"; // 表名称
    // 表的字段
    public static final String CUR_NAME = "cur_name"; // 课程名称
    public static final String TEACHER = "teacher"; // 上课老师
    public static final String PLACE = "place"; // 上课地点
    public static final String WEEK = "week";// 和周数
    public static final String DAY = "day"; // 星期几
    public static final String DAY_TIME = "day_time"; // 本日第几次课
    public static final String ID = "stu_id";// 学号
    public static final String IS_LOCAL = "is_local";// 是否为本地课表
    //成绩字段
    public static final String TABLE_SCORE = "score";//score 表名
    public static final String TASK_NO = "task_no";//课程编号
    public static final String COURSE_NAME = "course_name"; //课程名称
    public static final String COURSE_TYPE = "course_type";  //课程类型
    public static final String COURSE_CREDIT = "course_credit"; //课程绩点
    public static final String GRADE = "grade"; //成绩
    public static final String STU_ID = "stu_id"; //学号
    public static final String GRADE_POINT = "grade_point";//学分
    public static final String IS_SHOW_SCORE = "is_show_score"; //是否公布
    //学生成绩字段
    public static final String CLASS_NAME = "class_name"; //班级
    public static final String STU_NAME = "stu_name"; //学生姓名
    public static final String STU_NUM = "stu_id"; // 学号
    public static final String ID_CARD = "id_card"; //身份证号
    public static final String SEX = "sex"; //性别
    public static final String ETHNIC = "ethnic"; //民族
    public static final String COLLEGE = "college"; //学院
    public static final String MAJOR = "major"; // 专业
    public static final String YEAR = "year"; // 学制
    public static final String POLITICAL_STATUS = "political_status";//政治面貌
    public static final String BIRTH_DAY = "birth_day"; // 出生日期
    public static final String ENTER_SCHOOL = "enter_school"; // 入校日期
    public static final String LEAVE_SCHOOL = "leave_school"; // 离校日期
    
    
    
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建课表数据表
        String sql = " create table if not exists " + TABLE_NAME
                + " (_id integer primary key autoincrement," + CUR_NAME
                + " VARCHAR, " + TEACHER + " VARCHAR, " + PLACE + " VARCHAR ,"
                + DAY + " INTEGER," + DAY_TIME + " INTEGER," + ID
                + "  VARCHAR," + WEEK + " VARCHAR ) ";
        db.execSQL(sql);
        // 创建本地课表数据表
        sql = " create table if not exists  local_schedule (_id integer primary key autoincrement," + CUR_NAME
                + " VARCHAR, " + TEACHER + " VARCHAR, " + PLACE + " VARCHAR ,"
                + DAY + " INTEGER," + DAY_TIME + " INTEGER," + ID
                + "  VARCHAR," + WEEK + " VARCHAR ," + IS_LOCAL
                + " TINYINT default 0 ) ";
        db.execSQL(sql);
        //创建成绩数据表
        sql = "create table if not exists  score (_id integer primary key autoincrement," +
                "task_no VARCHAR,course_name VARCHAR,course_type VARCHAR,stu_id VARCHAR," +
                "course_credit double,grade double,grade_point double,is_show_score  VARCHAR) ";
        db.execSQL(sql);
        //创建个人信息表
        sql = "create table if not exists student (_id integer primary key autoincrement,"+
        		"class_name varchar, stu_name varchar, stu_id varchar, id_card varchar, "+
        		"sex varchar, ethnic varchar, college varchar, major varchar, year varchar, "+
        		"political_status varchar, birth_day varchar, enter_school varchar, leave_school varchar) ";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropSchedule = " drop table if exists schedule ";
        db.execSQL(dropSchedule);
        //删除 score 表
        String dropScore = " drop table if exists score ";
        db.execSQL(dropScore);
        //删除student表
        String dropStudent = " drop table if exists student ";
        db.execSQL(dropStudent);
        onCreate(db);
    }

}
