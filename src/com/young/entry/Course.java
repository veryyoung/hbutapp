package com.young.entry;

public class Course {
	
	private String courseName; //课程名称
	private String courseTeacher; //上课老师
	private String timeAndPlace; //上课地点和周数
	private int dayOfWeek; //星期几
	private int courseNum; //第几次课
	private int isChanged; //是否被修改, 0,没有被修改， 1，被修改.
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseTeacher() {
		return courseTeacher;
	}
	public void setCourseTeacher(String courseTeacher) {
		this.courseTeacher = courseTeacher;
	}
	public String getTimeAndPlace() {
		return timeAndPlace;
	}
	public void setTimeAndPlace(String timeAndPlace) {
		this.timeAndPlace = timeAndPlace;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	public int getIsChanged() {
		return isChanged;
	}
	public void setIsChanged(int isChanged) {
		this.isChanged = isChanged;
	}
	
	
}
