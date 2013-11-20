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

<<<<<<< HEAD

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
=======
public class AdapterForSchedule extends MyBaseAdapter implements Runnable{
        
//        private Context context;
        private LayoutInflater inflater;
        private ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
        private LinkedList<String> list;
        
        private String className;
        
        
        //用来存课表的变化
        private ArrayList<Integer> isOneLine  = new ArrayList<Integer>();
        
        
        
        private boolean isOK=false;
        
        public AdapterForSchedule(Context context,int n,String name){
                inflater = LayoutInflater.from(context);
                this.className = name;
                Thread thread = new Thread(this);
                thread.start();
                isOneLine.add(0);
                data = getByDay(n-1);
                
        }
>>>>>>> 162cab5ea663f10e00c9cda8fca1a4be5c4614c8


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

<<<<<<< HEAD
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
		
		if(isOneLine.get(position+1)==1){
			course.courseName.setBackgroundColor(Color.rgb(55,222,106));
			course.courseTeacher.setBackgroundColor(Color.rgb(55,222,106));
			course.courseTime.setBackgroundColor(Color.rgb(55,222,106));
		}else{
			course.courseName.setBackgroundColor(Color.rgb(100,222,137));
			course.courseTeacher.setBackgroundColor(Color.rgb(100,222,137));
			course.courseTime.setBackgroundColor(Color.rgb(100,222,137));
			
		}
		
		
		return convertView;
	}
	
	

	
	private final class MyListCourse{
		public TextView courseName;
		public TextView courseTeacher;
		public TextView courseTime;
	}

	
	
=======
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
                        course.courseName.setBackgroundColor(Color.rgb(55,222,106));
                        course.courseTeacher.setBackgroundColor(Color.rgb(55,222,106));
                        course.courseTime.setBackgroundColor(Color.rgb(55,222,106));
                }else{
                        course.courseName.setBackgroundColor(Color.rgb(100,222,137));
                        course.courseTeacher.setBackgroundColor(Color.rgb(100,222,137));
                        course.courseTime.setBackgroundColor(Color.rgb(100,222,137));
                        
                }
                
                
                return convertView;
        }
        
        
        private ArrayList<HashMap<String,String>> getByDay(int day) {
                        HashMap<String,String> map;
                        Log.v("this","167 is ok");
                        while(!isOK){
                                
                        }
//                        pd = ProgressDialog.show(context, "please wait", "please wait ...");
                        
                                for(int i = day;i<35;i+=7){
                                        //map = new 
                                        String total = list.get(i);
                                        System.out.println("total is "+total);
                                        if(!"".equals(total)){
                                                String[] devided = total.split("\\|");
                                                int coun = devided.length;
                                                System.out.println("coun   "+coun);
                                                for(int xx = 0;xx<devided.length;xx++){
                                                        System.out.println(xx+"   "+devided[xx]);
                                                }
                                                ////////////////
                                                for(int x = 0;x<=coun/4;x++){///////////////
                                                        map = new HashMap<String,String>();
                                                        int y = x*4;
//                                                        System.out.println("y"+y);////////////////////////////////
                                                        map.put("name", devided[y+0]);
                                                        map.put("teacher",devided[y+1]);
                                                        map.put("time", devided[y+2]);
                                                        
                                                        System.out.println(map);
                                                        if(x==0){
                                                                isOneLine.add(isOneLine.get(isOneLine.size()-1)==1?0:1);
                                                        }else{
                                                                isOneLine.add(isOneLine.get(isOneLine.size()-1));
                                                        }
                                                        data.add(map);
                                                }////////////////////////////////////////////////////////这个还没有测啊
                                                
                                        }else{
                                                map = new HashMap<String,String>();
                                                map.put("name", "");
                                                map.put("time", "");
                                                map.put("teacher","");
                                                data.add(map);
                                                isOneLine.add(isOneLine.get(isOneLine.size()-1)==1?0:1);
                                        }
//                                        System.out.println(total);
//                                        System.out.println(i);
//                                        System.out.println(map);
                                        
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
                        Log.v("in AdapterForSchedule in run 170",className);
                        if(ScheduleActivity.NO_NAME.equals(className)){
                                System.out.println("this shoud be in no class name");
                                list =(LinkedList<String>)  hbut.myselfSchedule();
//                                System.out.println("the size  is "+list.size());
                                for(int i=0;i<list.size();i++){
                                        System.out.println(list.get(i)+"    "+i);
                                }
                        }else if(className!=null){
                                list = (LinkedList<String>) hbut.classSchedule(className);
//                                for(String name:list){
//                                        System.out.println(name);
//                                }
                        }
                        isOK = true;
                }catch(IOException e){
                        
                        e.printStackTrace();
                }

        }
        
>>>>>>> 162cab5ea663f10e00c9cda8fca1a4be5c4614c8

}
