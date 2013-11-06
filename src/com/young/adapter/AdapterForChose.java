/**
 * 
 * this class has been wasted
 * 

package com.young.adapter;

import java.util.ArrayList;

import com.young.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AdapterForChose extends MyBaseAdapter{

	private ArrayList<String> listData;
	private LayoutInflater inflater;
	private Context context;
	
	public AdapterForChose(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
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
		TextView textView ;
		if(null==convertView){
			textView = new TextView(context);
			convertView = inflater.inflate(R.layout.activity_chose_item, null);
			textView = (TextView) convertView.findViewById(R.id.chose_item_text);
				
		}
		
//		textView.setText(listData.get(position));
		return null;
	}
	
	
	public ArrayList<String> getData(){
		ArrayList<String> list = new ArrayList<String>();
		list.add("���˿α�");
		list.add("����α�");
		return list;
		
	}

}
*/
