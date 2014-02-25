package com.young.entry;

public class Score {
    private String taskNo; // 任务号
    private String courseName; // 科目名
    private String courseType; // 科目类别
    private double courseCredit; // 学分
    private double grade; // 成绩
    private double gradePoint; // 绩点
    private boolean isShowScore; //是否公共

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public double getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(double courseCredit) {
        this.courseCredit = courseCredit;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGradePoint() {
        return gradePoint;
    }

    public void setGradePoint(double gradePoint) {
        this.gradePoint = gradePoint;
    }

    public boolean isShowScore() {
        return isShowScore;
    }

    public void setShowScore(boolean isShowScore) {
        this.isShowScore = isShowScore;
    }



}
