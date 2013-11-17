package com.young.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.young.R;
import com.young.entry.SubjectScore;

public class AdapterForScore extends MyBaseAdapter{
//	private ArrayList<HashMap<String,String>> list= new ArrayList<HashMap<String,String>>();
//	private String name;//name用来获取数据的年号
	private List<SubjectScore> score;
	private LayoutInflater inflater;
	
	
	public AdapterForScore(Context context,List<SubjectScore> score){
		inflater = LayoutInflater.from(context);
		this.score = score;
	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return score.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		MyViewItem viewItem;
		if(null==convertView){
			viewItem = new MyViewItem();
			convertView = inflater.inflate(R.layout.activity_score_management_item, null);
			viewItem.coureName = (TextView)convertView.findViewById(R.id.score_corese_name);
			viewItem.pointAverage = (TextView)convertView.findViewById(R.id.score_grade_point_average);
			viewItem.totalPoints = (TextView)convertView.findViewById(R.id.score_total_points);
			convertView.setTag(viewItem);
		}else{
			viewItem = (MyViewItem) convertView.getTag();
		}
		
		viewItem.coureName.setText(score.get(position).getTaskName());
		viewItem.pointAverage.setText("绩点："+score.get(position).getGPA()+"   学分："+score.get(position).getTaskCredit());
		viewItem.totalPoints.setText("总成绩："+score.get(position).getScore());
		return convertView;
	}

	//item of score
	private final class MyViewItem{
		public  TextView coureName;
		public  TextView pointAverage;
		public  TextView totalPoints;
	}

}
