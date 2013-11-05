package com.young.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.young.R;

public class AdapterForSchedule extends MyBaseAdapter{
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String,String>> data;
	
	public AdapterForSchedule(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
		data = getSchedule();
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
			convertView.setTag(course);
		}else{
			course = (MyListCourse) convertView.getTag();
		}
		String name = data.get(position).get("name");
		String time = data.get(position).get("time");
		if(!"".equals(name)){
			course.courseName.setText(name);
		}
		if(!"".equals(time)){
			course.courseTime.setText(time);
		}
		
		
		return convertView;
	}
	
	private ArrayList<HashMap<String,String>> getSchedule(){
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> map = null;
		for(int i =0;i<3;i++){
			map = new HashMap<String,String>();
			map.put("name", "courseName"+i);
			map.put("time", "course time"+i);
			list.add(map);
		}
		return list;
	}
	
	private final class MyListCourse{
		public TextView courseName;
		public TextView courseTime;
	}
	

}
