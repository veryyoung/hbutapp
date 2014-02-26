package com.young.entry;

public class Schedule {
    private String curName; // 课程名
    private int day; // 星期几
    private int dayTime; // 本日第几次课
    private String place;// 课程地点
    private String teacher;// 老师
    private String week;// 上课周数
    private String id;//     学号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDayTime() {
        return dayTime;
    }

    public void setDayTime(int dayTime) {
        this.dayTime = dayTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

}