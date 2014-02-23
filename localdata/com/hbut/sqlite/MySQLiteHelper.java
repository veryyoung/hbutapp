package com.hbut.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	public final String tag = "in MySQLiteHelper";
	
	private static final String DATABASE_NAME = "HBUT.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_NAME = "schedule"; //表名称
	//表的字段
	public static final String COURSE_NAME = "course_name"; //课程名称
	public static final String COURSE_TEACHER = "course_teacher"; //上课老师
	public static final String TIME_AND_PLACE = "time_and_place"; //上课地点和周数
	public static final String DAY_OF_WEEK = "day_of_week"; //星期几
	public static final String TIMES_OF_COURSE = "times_of_course"; //第几次课
	public static final String IS_CHANGED = "is_changed"; //是否被修改, 0,没有被修改， 1，被修改.

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 创建数据表,课表：schedule 
	 * _id 主键，自动增加
	 * course_name 课程名称
	 * time_and_place 上课地点和周数
	 * day_of_week  星期几1,2,3,4,5,6,7
	 * times_of_course 第几次课1,2,3,4,5
	 * is_changed 是否被修改; 0.没有被修改，1，被修改。默认0。
	 */

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sqlMySchedule = " create table if not exists "+TABLE_NAME+" (_id integer primary key autoincrement," +
				COURSE_NAME+" text," +
				COURSE_TEACHER+" text, " +
				TIME_AND_PLACE+" text ," +
				DAY_OF_WEEK	+" integer," +
				TIMES_OF_COURSE+" integer," +
				IS_CHANGED+" integer default '0') ";
		db.execSQL(sqlMySchedule);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String dropSchedule = " drop table if exists schedule ";
		db.execSQL(dropSchedule);
	}
	
	

}
