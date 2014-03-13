package com.young.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.young.entry.Schedule;
import com.young.entry.Score;
import com.young.entry.Student;

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
                Cursor cursor = db.rawQuery(
                        "select * from score where stu_id = ? and task_no like ?",
                        new String[]{id, term + "%"});
                Score score = null;
                scores = new ArrayList<Score>();
                cursor.moveToFirst();
                do {
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
                } while (cursor.moveToNext());
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

    public ArrayList<String> getTerms(String stu_id) {
        ArrayList<String> listTerms = null;
        if (db != null) {
            if (db.isOpen()) {
                listTerms = new ArrayList<String>();
                String sql = " select distinct substr(task_no,0,6) as terms from score where stu_id = ? order by terms desc ";
                Cursor cursor = db.rawQuery(sql, new String[]{stu_id});
                int columnIndex = cursor.getColumnIndex("terms");
                cursor.moveToFirst();
                do {
                    listTerms.add(cursor.getString(columnIndex));
                } while (cursor.moveToNext());
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


    //清空score表中数据，用于刷新分手，需要删除以前分数
    public void clearTableScore(String id) {
        if (db != null) {
            if (db.isOpen()) {
                String sql = "delete from score where stu_id = ? ";
                db.execSQL(sql, new Object[]{id});
            }
        }
    }

    //清空schedule表中数据，用于刷新分手，需要删除以前的课表
    public void clearTableSchedule(String id, Boolean isLocal) {
        if (db != null) {
            if (db.isOpen()) {
                String tableName = "schedule";
                if (isLocal) {
                    tableName = "local_schedule";
                }
                String sql = "delete from " + tableName + " where stu_id = ? ";
                db.execSQL(sql, new Object[]{id});
            }
        }
    }

    /**
     * 插入一个学生的信息
     *
     * @param stu
     * @return
     */

    public void insertStudent(Student stu) {
        String insetSql = " insert into student (class_name , stu_name , stu_id , id_card, " +
                "sex , ethnic , college , major , year , " +
                "political_status , birth_day , enter_school , leave_school ) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        if (db != null) {
            if (db.isOpen()) {
                db.execSQL(insetSql, new String[]{
                        stu.getClassName(),
                        stu.getStuName(),
                        stu.getStuNum(),
                        stu.getIDCard(),
                        stu.getSex(),
                        stu.getEthnic(),
                        stu.getCollege(),
                        stu.getMajor(),
                        stu.getYear(),
                        stu.getPoliticalStatus(),
                        stu.getBirthDay(),
                        stu.getEnterScholl(),
                        stu.getLeftScholl()
                });
            }
        }
    }

    /**
     * 查询学生信息
     *
     * @param stuId
     */

    public Student getStudent(String stuId) {
        Student stu = null;
        if (db != null) {
            if (db.isOpen()) {
                Cursor cursor = db.rawQuery("select * from student where stu_id = ?",
                        new String[]{stuId});
                if (cursor.getColumnCount() < 1) {
                    return null;
                }
                while (cursor.moveToNext()) {
                    stu = new Student();
                    stu.setClassName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.CLASS_NAME)));
                    stu.setStuName(cursor.getString(cursor.getColumnIndex(SQLiteHelper.STU_NAME)));
                    stu.setStuNum(cursor.getString(cursor.getColumnIndex(SQLiteHelper.STU_NUM)));
                    stu.setIDCard(cursor.getString(cursor.getColumnIndex(SQLiteHelper.ID_CARD)));
                    stu.setSex(cursor.getString(cursor.getColumnIndex(SQLiteHelper.SEX)));
                    stu.setEthnic(cursor.getString(cursor.getColumnIndex(SQLiteHelper.ETHNIC)));
                    stu.setCollege(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLLEGE)));
                    stu.setMajor(cursor.getString(cursor.getColumnIndex(SQLiteHelper.MAJOR)));
                    stu.setYear(cursor.getString(cursor.getColumnIndex(SQLiteHelper.YEAR)));
                    stu.setPoliticalStatus(cursor.getString(cursor.getColumnIndex(SQLiteHelper.POLITICAL_STATUS)));
                    stu.setBirthDay(cursor.getString(cursor.getColumnIndex(SQLiteHelper.BIRTH_DAY)));
                    stu.setEnterScholl(cursor.getString(cursor.getColumnIndex(SQLiteHelper.ENTER_SCHOOL)));
                    stu.setLeftScholl(cursor.getString(cursor.getColumnIndex(SQLiteHelper.LEAVE_SCHOOL)));
                }
            }
        }
        return stu;
    }

    /**
     * 清空student数据表
     */

    public void clearTableStudent() {
        String sql = "delete  from student where _id > 0";
        if (db != null) {
            if (db.isOpen()) {
                db.execSQL(sql);
            }
        }
    }


}
