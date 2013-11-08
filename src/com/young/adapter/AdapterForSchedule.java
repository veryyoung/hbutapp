package com.young.adapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.young.R;
import com.young.business.HBUT;

public class AdapterForSchedule extends MyBaseAdapter implements Runnable{
	
//	private Context context;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String,String>> data;
	private LinkedList<String> list;
	
	private ArrayList<HashMap<String,String>> monday;
	private ArrayList<HashMap<String,String>> tuesday;
	private ArrayList<HashMap<String,String>> wednesday;
	private ArrayList<HashMap<String,String>> thursday;
	private ArrayList<HashMap<String,String>> friday;
	private ArrayList<HashMap<String,String>> saturday;
	private ArrayList<HashMap<String,String>> sunday;
	
	public AdapterForSchedule(Context context,int n){
//		this.context = context;
		inflater = LayoutInflater.from(context);
		data = getSchedule();
		Thread thread = new Thread(this);
		thread.start();
		switch(n){
		case 1:data = getMonday();break;
		case 2:data = getTuesday();break;
		case 3:data = getWednesday();break;
		case 4:data = getThursday();break;
		case 5:data = getFriday();break;
		case 6:data = getSaturday();break;
		case 7:data = getSunday();break;
		default:break;
		}
//		monday = getMonday();
	}

	private ArrayList<HashMap<String, String>> getSunday() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<HashMap<String, String>> getSaturday() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<HashMap<String, String>> getFriday() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<HashMap<String, String>> getThursday() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<HashMap<String, String>> getWednesday() {
		// TODO Auto-generated method stub
		return null;
	}

	private ArrayList<HashMap<String, String>> getTuesday() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyListCourse course = null;
		if(null==convertView){
			course = new MyListCourse();
			convertView = inflater.inflate(R.layout.activity_schedule_item, null);
			course.courseName = (TextView)convertView.findViewById(R.id.text_for_course);
			course.courseTime = (TextView)convertView.findViewById(R.id.text_for_coursetime);
			course.courseTeacher = (TextView)convertView.findViewById(R.id.schedule_text_teacher);
			convertView.setTag(course);
		}else{
			course = (MyListCourse) convertView.getTag();
		}
		String name = data.get(position).get("name");
		String time = data.get(position).get("time");
		String teacher = data.get(position).get("teacher");
//		Log.v("name",name);
//		Log.v("time",time);
//		Log.v("teacher",teacher);
//		if("".equals(name)&&"".equals(time)&&"".equals(teacher)){
//			course.courseName.setHeight(5);
//			course.courseTime.setHeight(5);
//			course.courseTeacher.setHeight(5);
//		}else{
			course.courseName.setText(name);
			course.courseTime.setText(time);
			course.courseTeacher.setText(teacher);
//		}
		
		return convertView;
	}
	
	private ArrayList<HashMap<String,String>> getSchedule(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map = null;
	
			map = new HashMap<String,String>();
			map.put("name", "计算机组成原理");
			map.put("time", "2-005 第3-15周 ");
			map.put("teacher", "邵雄凯-主讲");
			list.add(map);
			
			map = new HashMap<String,String>();
			map.put("name", "");
			map.put("time", "");
			map.put("teacher", "");
			list.add(map);
			
			map = new HashMap<String,String>();
			map.put("name", "");
			map.put("time", "");
			map.put("teacher", "");
			list.add(map);
			
			map = new HashMap<String,String>();
			map.put("name", "软件设计模式");
			map.put("time", "2-412 第7-14周 ");
			map.put("teacher", "王华东-主讲");
			list.add(map);
//			System.out.println(list);
		return list;
	}
	
	private ArrayList<HashMap<String,String>> getMonday() {
			HashMap<String,String> map;
				for(int i = 0;i<list.size();i+=7){
					map = new HashMap<String,String>();
					String total = list.get(i);
					map.put("name", "");
					map.put("time", "");
					map.put("teacher",total);
					
					System.out.println(total);
					Log.v("total",total);
					data.add(map);
				}
				return data;
	}
	
	private final class MyListCourse{
		public TextView courseName;
		public TextView courseTeacher;
		public TextView courseTime;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			HBUT hbut = HBUT.getInstance();
			list =(LinkedList<String>)  hbut.myselfSchedule();
//			System.out.println("the size \n is "+list.size());
//			for(int i=0;i<list.size();i++){
//				System.out.println(list.get(i)+"    "+i);
//			}
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	

}
