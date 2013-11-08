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
	private ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
	private LinkedList<String> list;
	
	private boolean isOK=false;
	
	public AdapterForSchedule(Context context,int n){

		inflater = LayoutInflater.from(context);

		Thread thread = new Thread(this);
		thread.start();
		data = getByDay(n-1);

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
	
	
	private ArrayList<HashMap<String,String>> getByDay(int day) {
			HashMap<String,String> map;
			Log.v("this","167 is ok");
			while(!isOK){
				
			}
				for(int i = day;i<35;i+=7){
					map = new HashMap<String,String>();
					String total = list.get(i);
					System.out.println(total);
					if(!"".equals(total)){
						String[] devided = total.split("\\|");
						map.put("name", devided[0]);
						map.put("time", devided[2]);
						map.put("teacher",devided[1]);
					}else{
						map.put("name", "");
						map.put("time", "");
						map.put("teacher","");
					}
					System.out.println(total);
					System.out.println(i);
					System.out.println(map);
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
//			System.out.println("the size  is "+list.size());
//			for(int i=0;i<list.size();i++){
//				System.out.println(list.get(i)+"    "+i);
//			}
			isOK = true;
		}catch(IOException e){
			e.printStackTrace();
		}

	}
	

}
