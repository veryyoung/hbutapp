package com.young.adapter;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.young.R;



public class AdapterForSchedule extends MyBaseAdapter{
	
//	private Context context;
	private LayoutInflater inflater;
	
	private ArrayList<HashMap<String,String>> data ;
	
	
	private ArrayList<Integer> isOneLine;
	

	
	
	
	
	public AdapterForSchedule(Context context,ArrayList<Integer> isOneLine, ArrayList<HashMap<String,String>> data){
		inflater = LayoutInflater.from(context);
		//接收isOneLine
		//接收data;
		this.isOneLine = isOneLine;
		this.data = data;
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
//                Log.v("name",name);
//                Log.v("time",time);
//                Log.v("teacher",teacher);
//                if("".equals(name)&&"".equals(time)&&"".equals(teacher)){
//                        course.courseName.setHeight(5);
//                        course.courseTime.setHeight(5);
//                        course.courseTeacher.setHeight(5);
//                }else{
                        course.courseName.setText(name);
                        course.courseTime.setText(time);
                        course.courseTeacher.setText(teacher);
//                }
                
                if(isOneLine.get(position+1)==1){
                        course.courseName.setBackgroundColor(Color.rgb(198,219,228));
                        course.courseTeacher.setBackgroundColor(Color.rgb(198,219,228));
                        course.courseTime.setBackgroundColor(Color.rgb(198,219,228));
                }else{
                        course.courseName.setBackgroundColor(Color.rgb(122, 179, 205));
                        course.courseTeacher.setBackgroundColor(Color.rgb(122, 179, 205));
                        course.courseTime.setBackgroundColor(Color.rgb(122, 179, 205));
                        
                }
                
                
                return convertView;
        }
        
        
  
        
        private final class MyListCourse{
                public TextView courseName;
                public TextView courseTeacher;
                public TextView courseTime;
        }



}
