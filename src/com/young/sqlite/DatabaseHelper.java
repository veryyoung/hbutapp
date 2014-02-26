package com.young.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.young.entry.Schedule;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    public static final String tag = "in OperateDataBase";

    private SQLiteDatabase db = null;
    private SQLiteHelper sqLiteHelper;

    public DatabaseHelper(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        try {
            db = sqLiteHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据星期几来查询全天的课程数据
     *
     * @param day 星期几
     * @return
     */

    public List<Course> getClassByDay(int day) {
        List<Course> oneDayCourse = new ArrayList<Course>();
//		String[] columns = {MySQLiteHelper.COURSE_NAME, MySQLiteHelper.COURSE_TEACHER, MySQLiteHelper.TIME_AND_PLACE};
        String selection = "day_of_week = ?";
        String myDay = "" + day;
        String[] selectionArgs = {myDay};
        String orderBy = "times_of_course asc";
        if (db != null) {
            if (db.isOpen()) {
                Cursor cursor = db.query(MySQLiteHelper.TABLE_NAME, null, selection, selectionArgs, null, null, orderBy);
                int courseNameIndex = cursor.getColumnIndex(MySQLiteHelper.COURSE_NAME);
                int courseTeacherIndex = cursor.getColumnIndex(MySQLiteHelper.COURSE_TEACHER);
                int timePlaceIndex = cursor.getColumnIndex(MySQLiteHelper.TIME_AND_PLACE);
                int dayOfWeek = cursor.getColumnIndex(MySQLiteHelper.DAY_OF_WEEK);
                int courseNum = cursor.getColumnIndex(MySQLiteHelper.TIMES_OF_COURSE);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Course course = new Course();
                    course.setCourseName(cursor.getString(courseNameIndex));
                    course.setCourseTeacher(cursor.getString(courseTeacherIndex));
                    course.setTimeAndPlace(cursor.getString(timePlaceIndex));
                    course.setDayOfWeek(cursor.getInt(dayOfWeek));
                    course.setCourseNum(cursor.getInt(courseNum));
                    cursor.moveToNext();
                    oneDayCourse.add(course);
                }
                cursor.close();
                cursor = null;
            }
        }
        return oneDayCourse;
    }

    /**
     * 插入一节课信息
     *
     * @param schedule
     * @return
     */

    public void addSchedule(Schedule schedule) {
        if (db != null) {
            if (db.isOpen()) {
                db.execSQL("insert into " + "schedule" +
                        " (cur_name,teacher,place,week,day,day_time,stu_id) " +
                        "values(?,?,?,?,?,?,?)", new Object[]{schedule.getCurName(),
                        schedule.getTeacher(), schedule.getPlace(), schedule.getWeek(),
                        schedule.getDay(), schedule.getDayTime(), schedule.getStuId()});
            }
        }
    }

    /*
     * 关闭数据库
     */
    public void closeDB() {
        if (db != null) {
            if (db.isOpen()) {
                db.close();
            }
            db = null;
        }
    }

    /**
     * 得到可写数据库
     *
     * @return
     */
    public boolean openWritableDataBase() {
        if (db != null) {
            if (db.isOpen()) {
                return true;
            }
        } else {
            try {
                db = sqLiteHelper.getWritableDatabase();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 判断数据库是否为空
     * true 表示表为空
     * false 表示表不为空
     *
     * @return
     */
    public boolean isEmpty() {
        if (db != null && db.isOpen()) {
            if (db.isOpen()) {
                Cursor cursor = db.query(sqLiteHelper.TABLE_NAME, null, null, null, null, null, null);
                if (cursor.getCount() < 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    //清空数据表中数据，用于调试时使用
    public void clearTableSchedule() {
        if (db != null) {
            if (db.isOpen()) {
                String sql = "delete from " + sqLiteHelper.TABLE_NAME + " where _id > 0";
                db.execSQL(sql);
            }
        }
    }


}
