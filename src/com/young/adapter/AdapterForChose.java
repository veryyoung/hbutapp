package com.young.adapter;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;

public class AdapterForChose extends MyBaseAdapter{

	private ArrayList<String> listData;
	private LayoutInflater inflater;
	
	public AdapterForChose(Context context){
		inflater = LayoutInflater.form(context);
		listData = getData();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
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
		if(null==convertView){
			convertView = inflater.inflate(R.layout.);
		}
		return null;
	}
	
	
	public ArrayList<String> getData(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("个人课表");
		list.add("其他课表");
		return list;
		
	}

}
