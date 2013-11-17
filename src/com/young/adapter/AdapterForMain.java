package com.young.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.young.R;

public class AdapterForMain extends MyBaseAdapter{
	private List<Map<String,Object>> mData;
	private LayoutInflater inflater;
	private Context context;
	
	public AdapterForMain(Context context){
		this.context  = context;
		inflater = LayoutInflater.from(context);
		mData = getData();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
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
		ListItemView itemView = null;
		if(null==convertView){
			itemView = new ListItemView();
			convertView = inflater.inflate(R.layout.activity_main_item, null);
//			Log.v("in adapter","this is OK 50");
			itemView.image  = (ImageView) convertView.findViewById(R.id.image);
			itemView.textView = (TextView) convertView.findViewById(R.id.text_main);
			convertView.setTag(itemView);
		}else{
			itemView = (ListItemView) convertView.getTag();
		}
		itemView.textView.setText(mData.get(position).get("text").toString());
//		BitmapDrawable bd = (BitmapDrawable) mData.get(position).get("imag");
		itemView.image.setScaleType(ImageView.ScaleType.FIT_XY);
		BitmapDrawable bd=(BitmapDrawable)	context.getResources().getDrawable((Integer) mData.get(position).get("imag"));
		Bitmap bitmpa = bd.getBitmap();
//		Resource res = (Resource) mData.get(position).get("imag");
		
		itemView.image.setImageBitmap(bitmpa);
//		itemView.image.setImageResource((Integer) mData.get(position).get("imag"));
		return convertView;
	}
	
	private List<Map<String,Object>> getData(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("imag",R.drawable.jiaoxuekebiao_1);
		map.put("text", "教学课表");
		list.add(map);
		
		Map<String,Object> map1 = new HashMap<String,Object>();
		map1.put("imag",R.drawable.xuejiguanli);
		map1.put("text", "学籍管理");
		list.add(map1);
		
		Map<String,Object> map2 = new HashMap<String,Object>();
		map2.put("imag",R.drawable.xinxi);
		map2.put("text", "选课管理");
		list.add(map2);
		
		Map<String,Object> map3 = new HashMap<String,Object>();
		map3.put("imag",R.drawable.chengjiguanli_b);
		map3.put("text", "成绩管理");
		list.add(map3);
		
		Map<String,Object> map4 = new HashMap<String,Object>();
		map4.put("imag",R.drawable.gerenpeizhi);
		map4.put("text", "个人配置");
		list.add(map4);
		
		
		return list;
	}
	
	
	public final class ListItemView{
		public ImageView image;
		public TextView textView;
	}

}
