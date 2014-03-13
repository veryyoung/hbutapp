package com.young.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.young.R;
import com.young.entry.Score;

public class AdapterForScore extends BaseAdapter {
	private List<Score> scores;
	private LayoutInflater inflater;
	
	
	public AdapterForScore(Context context,List<Score> scores){
		this.scores = scores;
		inflater = LayoutInflater.from(context);
	}



	@Override
	public int getCount() {
		return scores.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		
		viewItem.coureName.setText(scores.get(position).getCourseName());
		viewItem.pointAverage.setText("绩点："+scores.get(position).getGradePoint()+"   学分："+scores.get(position).getCourseCredit());
		viewItem.totalPoints.setText("总成绩："+scores.get(position).getGrade());
		return convertView;
	}

	//item of score
	private final class MyViewItem{
		public  TextView coureName;
		public  TextView pointAverage;
		public  TextView totalPoints;
	}

}
