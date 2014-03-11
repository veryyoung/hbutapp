package com.young.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.young.entry.Schedule;
import com.young.entry.Score;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper {

    public static final String tag = "in OperateDataBase";

    private SQLiteDatabase db;
    private SQLiteHelper sqLiteHelper;

    public DatabaseHelper(Context context) {
        sqLiteHelper = new SQLiteHelper(context);
        db = sqLiteHelper.getWritableDatabase();
    }

    /**
     * 根据星期几来查询全天的课程数据
     *
     * @param day     星期几
     * @param id      学号
     * @param isLocal 是否为本地课表
     * @return
     */

    public List<Schedule> getClassByDay(int day, String id, Boolean isLocal) {
        List<Schedule> oneDayCourse = null;
        if (db != null) {
            if (db.isOpen()) {
                String tableName = "schedule";
                if (isLocal) {
                    tableName = "local_schedule";
                }
                Cursor cursor = db.rawQuery(
                        "select * from " + tableName + "  where day = ? and stu_id = ? order by " + SQLiteHelper.DAY_TIME + " asc",
                        new String[]{day + "", id});
                Schedule schedule = null;
                oneDayCourse = new ArrayList<Schedule>();
                while (cursor.moveToNext()) {
                    schedule = new Schedule();
                    schedule.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                    schedule.setCurName(cursor.getString(cursor
                            .getColumnIndex("cur_name")));
                    schedule.setWeek(cursor.getString(cursor
                            .getColumnIndex("week")));
                    schedule.setTeacher(cursor.getString(cursor
                            .getColumnIndex("teacher")));
                    schedule.setPlace(cursor.getString(cursor
                            .getColumnIndex("place")));
                    schedule.setDay(cursor.getInt(cursor.getColumnIndex("day")));
                    schedule.setDayTime(cursor.getInt(cursor
                            .getColumnIndex("day_time")));
                    schedule.setStuId(cursor.getString(cursor
                            .getColumnIndex("stu_id")));
                    oneDayCourse.add(schedule);
                }
                cursor.close();
            }
        }
        return oneDayCourse;
    }

    /**
     * 根据课程id查询
     *
     * @param id
     * @return
     */
    public Schedule getScheduleById(String id) {
        Schedule schedule = null;
        if (db != null) {
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery(
                        "select * from local_schedule where _id = ?",
                        new String[]{id});
                while (cursor.moveToNext()) {
                    schedule = new Schedule();
                    schedule.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                    schedule.setCurName(cursor.getString(cursor
                            .getColumnIndex("cur_name")));
                    schedule.setWeek(cursor.getString(cursor
                            .getColumnIndex("week")));
                    schedule.setTeacher(cursor.getString(cursor
                            .getColumnIndex("teacher")));
                    schedule.setPlace(cursor.getString(cursor
                            .getColumnIndex("place")));
                    schedule.setDay(cursor.getInt(cursor.getColumnIndex("day")));
                    schedule.setDayTime(cursor.getInt(cursor
                            .getColumnIndex("day_time")));
                    schedule.setStuId(cursor.getString(cursor
                            .getColumnIndex("stu_id")));
                }
                cursor.close();
            }
        }
        return schedule;
    }

    /**
     * 插入一节课信息
     *
     * @param isLocal  是否为本地课表
     * @param schedule
     * @return
     */

    public void addSchedule(Schedule schedule, Boolean isLocal) {
        if (db != null) {
            if (db.isOpen()) {
                String tableName = "schedule";
                if (isLocal) {
                    tableName = "local_schedule";
                }
                db.execSQL(
                        "insert into " + tableName + " (cur_name,teacher,place,week,day," +
                                "day_time,stu_id) values(?,?,?,?,?,?,?)",
                        new Object[]{schedule.getCurName(),
                                schedule.getTeacher(), schedule.getPlace(),
                                schedule.getWeek(), schedule.getDay(),
                                schedule.getDayTime(), schedule.getStuId()}
                );
            }
        }
    }

    /**
     * 修改课程
     *
     * @param schedule
     */
    public void updateSchedule(Schedule schedule) {
        if (db != null) {
            if (db.isOpen()) {
                db.execSQL("update local_schedule set cur_name = ? ,teacher = ?, place = ? ," +
                        "week = ? where _id = ?", new Object[]{
                        schedule.getCurName(), schedule.getTeacher(), schedule.getPlace(),
                        schedule.getWeek(), schedule.get_id()
                });
            }
        }

    }

    /**
     * 删除一节课信息
     *
     * @return
     */

    public void deleteSchedule(String id) {
        if (db != null) {
            if (db.isOpen()) {
                db.execSQL("delete from local_schedule where _id = ?", new Object[]{id});
            }
        }
    }

    /**
     * 查询该学号的所有成绩
     *
     * @param id
     * @return
     */
    public List<Score> getScore(String id, String term) {
        List<Score> scores = null;
        if (db != null) {
            if (db.isOpen()) {
            	System.out.println("id is "+id);
                Cursor cursor = db.rawQuery(
                        "select * from score where stu_id = ? and task_no like ?",
                        new String[] {id, term+"%"});
                Score score = null;
                scores = new ArrayList<Score>();
                cursor.moveToFirst();
               do{
                    score = new Score();
                    short isShowScore = cursor.getShort(cursor
                            .getColumnIndex(SQLiteHelper.IS_SHOW_SCORE));
                    if (0 == isShowScore) {
                        score.setShowScore(false);
                    } else {
                        score.setShowScore(true);
                    }
                    score.setStuId(cursor.getString(cursor.getColumnIndex(SQLiteHelper.STU_ID)));
                    score.setGradePoint(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.GRADE_POINT)));
                    score.setGrade(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.GRADE)));
                    score.setCourseCredit(cursor.getDouble(cursor.getColumnIndex(SQLiteHelper.COURSE_CREDIT)));
                    score.setCourseName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COURSE_NAME)));
                    score.setCourseType(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COURSE_TYPE)));
                    score.setTaskNo(cursor.getString(cursor.getColumnIndex(SQLiteHelper.TASK_NO)));
                    scores.add(score);
                }while (cursor.moveToNext());
                cursor.close();
            }

        }
        return scores;
    }

    /**
     * 插入成绩
     *
     * @param score
     */

    public void addScore(Score score) {
        if (db != null) {
            if (db.isOpen()) {
                db.execSQL("insert into score (task_no,course_name,course_type," +
                                "course_credit,grade,grade_point,is_show_score,stu_id)  values(?,?,?,?,?,?,?,?)",
                        new Object[]{score.getTaskNo(), score.getCourseName(), score.getCourseType(),
                                score.getCourseCredit(), score.getGrade(), score.getGradePoint(),
                                score.isShowScore() ? 1 : 0, score.getStuId()}
                );
            }
        }

    }

    /**
     * 根据学号查找有多少个学期
     *
     * @param stu_id
     * @return
     */
    public ArrayList<String> getTerms(String stu_id){
    	ArrayList<String> listTerms = null;
    	if(db != null){
    		if(db.isOpen()){
    			listTerms = new ArrayList<String>();
    			String sql = " select distinct substr(task_no,0,6) as terms from score where stu_id = ? ";
    			Cursor cursor = db.rawQuery(sql, new String[] {stu_id});
    			int columnIndex = cursor.getColumnIndex("terms");
    			cursor.moveToFirst();
    			do{
    				listTerms.add(cursor.getString(columnIndex));
    			}while(cursor.moveToNext());
    			cursor.close();
    		}
    	}
    	return listTerms;
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
     * 判断数据库对应学号记录是否为空 true 表示表为空 false 表示表不为空
     *
     * @return
     */
    public boolean isEmpty(String tableName, String id) {
        if (db != null && db.isOpen()) {
            if (db.isOpen()) {
                Cursor cursor = db.query(tableName, null, "stu_id = " + id, null, null, null, null);
                if (cursor.getCount() < 1) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    // 清空数据表中数据，用于调试时使用
    public void clearTableSchedule() {
        if (db != null) {
            if (db.isOpen()) {
                String sql = "delete from schedule where _id > 0";
                db.execSQL(sql);
            }
        }
    }
    
    //清空score表中数据，调试时使用
    public void clearTableScore(){
    	if(db != null){
    		if(db.isOpen()){
    			String sql = "delete from score where _id > 0 ";
    			db.execSQL(sql);
    		}
    	}
    }
    
    


}
