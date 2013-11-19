package com.young.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.young.R;
import com.young.activity.ScheduleActivity;
import com.young.business.HBUT;

public class AdapterForSchedule extends MyBaseAdapter{

	// private Context context;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	private LinkedList<String> list;
    private int day;
	private String className;

	// 用来存课表的变化
	private ArrayList<Integer> isOneLine = new ArrayList<Integer>();


	public AdapterForSchedule(Context context, int n, String name) {
		this.day=n-1;
		this.className = name;
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					HBUT hbut = HBUT.getInstance();
					if (ScheduleActivity.NO_NAME.equals(className)) {
						list = (LinkedList<String>) hbut.myselfSchedule();
					} else if (className != null) {
						list = (LinkedList<String>) hbut.classSchedule(className);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				HashMap<String, String> map;
				for (int i = day; i < 35; i += 7) {
					String total = list.get(i);
					if (!"".equals(total)) {
						String[] devided = total.split("\\|");
						int coun = devided.length;
						System.out.println("coun   " + coun);
						for (int xx = 0; xx < devided.length; xx++) {
							System.out.println(xx + "   " + devided[xx]);
						}
						for (int x = 0; x <= coun / 4; x++) {// /////////////
							map = new HashMap<String, String>();
							int y = x * 4;
							map.put("name", devided[y + 0]);
							map.put("teacher", devided[y + 1]);
							map.put("time", devided[y + 2]);

							System.out.println(map);
							if (x == 0) {
								isOneLine
										.add(isOneLine.get(isOneLine.size() - 1) == 1 ? 0
												: 1);
							} else {
								isOneLine.add(isOneLine.get(isOneLine.size() - 1));
							}
							data.add(map);
						}// //////////////////////////////////////////////////////这个还没有测啊

					} else {
						map = new HashMap<String, String>();
						map.put("name", "");
						map.put("time", "");
						map.put("teacher", "");
						data.add(map);
						isOneLine.add(isOneLine.get(isOneLine.size() - 1) == 1 ? 0 : 1);
					}
					System.out.println("this is in data");
					System.out.println("the size of date is " +data.size());
				}
			}
		}).start();
		isOneLine.add(0);
		inflater = LayoutInflater.from(context);
	}


	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyListCourse course = null;
			System.out.println("this is data size  haha     "+data.size());
		while(data.size()==0);
		if (null == convertView) {
			course = new MyListCourse();
			convertView = inflater.inflate(R.layout.activity_schedule_item,
					null);
			course.courseName = (TextView) convertView
					.findViewById(R.id.text_for_course);
			course.courseTime = (TextView) convertView
					.findViewById(R.id.text_for_coursetime);
			course.courseTeacher = (TextView) convertView
					.findViewById(R.id.schedule_text_teacher);
			convertView.setTag(course);
		} else {
			course = (MyListCourse) convertView.getTag();
		}
		String name = data.get(position).get("name");
		String time = data.get(position).get("time");
		String teacher = data.get(position).get("teacher");
		course.courseName.setText(name);
		course.courseTime.setText(time);
		course.courseTeacher.setText(teacher);
		if (isOneLine.get(position + 1) == 1) {
			course.courseName.setBackgroundColor(Color.rgb(55, 222, 106));
			course.courseTeacher.setBackgroundColor(Color.rgb(55, 222, 106));
			course.courseTime.setBackgroundColor(Color.rgb(55, 222, 106));
		} else {
			course.courseName.setBackgroundColor(Color.rgb(100, 222, 137));
			course.courseTeacher.setBackgroundColor(Color.rgb(100, 222, 137));
			course.courseTime.setBackgroundColor(Color.rgb(100, 222, 137));

		}
		return convertView;
	}


	private final class MyListCourse {
		public TextView courseName;
		public TextView courseTeacher;
		public TextView courseTime;
	}

	@Override
	public int getCount() {
		System.out.println("this is data size     "+data.size());
		return data.size();
		
	}


}
