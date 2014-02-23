package com.young.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbut.other.Course;
import com.hbut.sqlite.OperateDatabase;
import com.young.R;
import com.young.adapter.AdapterForSchedule;
import com.young.adapter.MyBaseAdapter;
import com.young.business.HBUT;
//import android.content.Intent;

public class ScheduleActivity extends Activity implements OnTouchListener, OnGestureListener ,Runnable{
	
	private TextView textView;
	private ListView listView;
	private String text = "星期一";
	private MyBaseAdapter adapter;
	private int n = 1;
	private GestureDetector mDetector;
	private static final int FLING_MIN_DISTANCE = 100;
	private static final int FLING_MIN_VELOCITY = 0;
	
	public static final String NO_NAME = "no_class_name";
	private String className;
	//用来存课表的变化
	private ArrayList<Integer> isOneLine ;// = new ArrayList<Integer>();
	private LinkedList<String> list = new LinkedList<String>();
	private ArrayList<HashMap<String,String>> data;
	private ProgressDialog pd;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			data = getByDay(n-1);
			adapter = new AdapterForSchedule(ScheduleActivity.this,isOneLine,data);
			upDate();
			//判断数据库是否为空，如果是为空则写入数据
			OperateDatabase operate = new OperateDatabase(ScheduleActivity.this);
//			operate.clearTableSchedule();//清空schedule数据表中的内容
			if(operate.isEmpty()){
				//将数据格式化//将数据写入数据库.
				for(int i = 1;i<8;i++){
					List<Course> course = getCourse(i);
					for(Course cou : course){
						operate.insertData(cou);
					}
				}
			}
			operate.closeDB();
			//end 判断数据库是否为空，如果是为空则写入数据
			//让等待消失
			pd.dismiss();
			super.handleMessage(msg);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		textView = (TextView)this.findViewById(R.id.text_schedule_title);
		listView = (ListView)this.findViewById(R.id.list_schedule_course);
		listView.setDividerHeight(0);
		mDetector = new GestureDetector(this,this);
		mDetector.setIsLongpressEnabled(true);
//		listView.setEnabled(false);
		RelativeLayout layout = (RelativeLayout) findViewById(R.id.Schedule_relative_layout);
		layout.setOnTouchListener(this);
		layout.setLongClickable(true);
		layout.setFocusable(true);
		layout.setClickable(true);
		layout.setLongClickable(true);

		//这里接收Intent传来的消息，然后传到Adapter里面去
		className = this.getIntent().getStringExtra(ChoseItemActivity.CLASS_NAME);
		//在这里如果是使用本地数据就不需要使用线程来加载数据了
		if(isOpenNetwork()){
			Thread thread = new Thread(this);
			thread.start();
			pd = ProgressDialog.show(ScheduleActivity.this, "加载中", "加载中，请稍后...");
			pd.setCancelable(true);
		}else{
			data = getDataFromDatabase(n);
			adapter = new AdapterForSchedule(ScheduleActivity.this,isOneLine,data);
			upDate();
		}
		
	}
	
	public void upDate(){
		
		textView.setText(text);
		listView.setAdapter(adapter);
	}


	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		mDetector.onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return  mDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		//fling to left
		if(e1.getX()-e2.getX()>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
			n = (n==7)?1:n+1;
			setDate();
			//如果打开了网络
			if(isOpenNetwork()){
				data = getByDay(n-1);
			}else{
				data = getDataFromDatabase(n);
			}
			adapter = new AdapterForSchedule(ScheduleActivity.this,isOneLine,data);
			upDate();
		}//fling to right
		else if(e2.getX()-e1.getX()>FLING_MIN_DISTANCE&&Math.abs(velocityX)>FLING_MIN_VELOCITY){
			n = (n==1)?7:n-1;
			setDate();
			//如果打开了网络
			if(isOpenNetwork()){
				data = getByDay(n-1);
			}else{
				data = getDataFromDatabase(n);
			}
			adapter = new AdapterForSchedule(ScheduleActivity.this,isOneLine,data);
			upDate();
		}
		
		return false;
	}
	
	private void setDate(){
		switch(n){
		case 1:text = "星期一";break;
		case 2:text = "星期二";break;
		case 3:text = "星期三";break;
		case 4:text = "星期四";break;
		case 5:text = "星期五";break;
		case 6:text = "星期六";break;
		case 7:text = "星期天";break;
		}
	}
	
	

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			HBUT hbut = HBUT.getInstance();

			if(ScheduleActivity.NO_NAME.equals(className)){
				list =(LinkedList<String>)  hbut.myselfSchedule();
			}else if(className!=null){
				list = (LinkedList<String>) hbut.classSchedule(className);
			}
			handler.sendEmptyMessage(0);

		}catch(IOException e){
			
			e.printStackTrace();
		}

	}
	
	//处理得到的数据
	private ArrayList<HashMap<String,String>> getByDay(int day) {
		data = new ArrayList<HashMap<String,String>>();
		isOneLine = new ArrayList<Integer>();
		isOneLine.add(0);
		HashMap<String,String> map;
		
			for(int i = day;i<35;i+=7){
				String total = list.get(i);
				if(!"".equals(total)){
					String[] devided = total.split("\\|");
					int coun = devided.length;
					for(int x = 0;x<=coun/4;x++){///////////////
						map = new HashMap<String,String>();
						int y = x*4;
						map.put("name", devided[y+0]);
						map.put("teacher",devided[y+1]);
						map.put("time", devided[y+2]);
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
				
			}
			return data;
}
	/**
	 * 从网络得到数据 从而存入数据库
	 * 根据星期几得到一天的课程
	 * @param day 星期几
	 * @return
	 */
	public List<Course> getCourse(int day){
		
		List<Course> oneDayCourse =  new ArrayList<Course>();
		ArrayList<HashMap<String,String>> mydata = getByDay(day-1);
		int couNum = 0;
		for(int i = 0; i< mydata.size(); i++){
			Course course = new Course();
			course.setCourseName(mydata.get(i).get("name"));
			course.setCourseTeacher(mydata.get(i).get("teacher"));
			course.setTimeAndPlace(mydata.get(i).get("time"));
			course.setDayOfWeek(day);
			if(isOneLine.get(i+1) != isOneLine.get(i)){
				couNum ++;
			}
			course.setCourseNum(couNum);
			course.setIsChanged(0);
			
			oneDayCourse.add(course);
		}
		return oneDayCourse;
	}
	

	
	//判断是否有网
	private boolean isOpenNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
	/**
	 * 查询一天的课程
	 * 将数据库中数据读出并且用于创建adapter
	 * @return
	 */
	public ArrayList<HashMap<String, String>> getDataFromDatabase(int day){
		ArrayList<HashMap<String, String>> myList = new ArrayList<HashMap<String, String>>();
		isOneLine = new ArrayList<Integer>();
		isOneLine.add(0);
		isOneLine.add(1);
		OperateDatabase od = new OperateDatabase(ScheduleActivity.this);
			List<Course> cou = od.getClassByDay(day);
			for(int j = 0;j<cou.size();j++){
				HashMap<String, String> course = new HashMap<String, String>();
				course.put("name", cou.get(j).getCourseName());
				course.put("time", cou.get(j).getTimeAndPlace());
				course.put("teacher", cou.get(j).getCourseTeacher());
				if(j > 0){
					if(cou.get(j-1).getCourseNum() != cou.get(j).getCourseNum()){
						isOneLine.add(isOneLine.get(j-1)==0?1:0);
					}else{
						isOneLine.add(isOneLine.get(j-1)==0?0:1);
					}
				}
				myList.add(course);
			}
		return myList;
	}
	

}
